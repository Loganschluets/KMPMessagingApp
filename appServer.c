#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <json-c/json.h>

#define MESSAGE_FILE "messages.txt"

void handle_get_messages(int client_sock, const char *receiver) {
    printf("Handling GET request for receiver: %s\n", receiver);

    FILE *file = fopen(MESSAGE_FILE, "r");
    if (!file) {
        const char *error_response = "HTTP/1.1 500 Internal Server Error\r\nContent-Type: text/plain\r\n\r\nFailed to open message file.";
        send(client_sock, error_response, strlen(error_response), 0);
        return;
    }

    struct json_object *json_array = json_object_new_array();

    char line[1024];
    while (fgets(line, sizeof(line), file)) {
        struct json_object *message_obj = json_tokener_parse(line);
        struct json_object *msg_receiver;

        if (json_object_object_get_ex(message_obj, "receiver", &msg_receiver) &&
            strcmp(json_object_get_string(msg_receiver), receiver) == 0) {
            json_object_array_add(json_array, message_obj);
        } else {
            json_object_put(message_obj);
        }
    }

    fclose(file);

    const char *json_response = json_object_to_json_string(json_array);
    char response[2048];
    snprintf(response, sizeof(response),
             "HTTP/1.1 200 OK\r\n"
             "Content-Type: application/json\r\n"
             "Content-Length: %ld\r\n"
             "\r\n"
             "%s", strlen(json_response), json_response);

    send(client_sock, response, strlen(response), 0);
    json_object_put(json_array);

    printf("GET request handled for receiver: %s\n", receiver);
}

void handle_send_message(int client_sock, const char *body) {
    printf("Handling POST request with body: %s\n", body);

    struct json_object *parsed_json, *sender, *receiver, *message;

    parsed_json = json_tokener_parse(body);
    if (!parsed_json) {
        const char *error_response = "HTTP/1.1 400 Bad Request\r\nContent-Type: text/plain\r\n\r\nInvalid JSON format.";
        send(client_sock, error_response, strlen(error_response), 0);
        return;
    }

    json_object_object_get_ex(parsed_json, "sender", &sender);
    json_object_object_get_ex(parsed_json, "receiver", &receiver);
    json_object_object_get_ex(parsed_json, "message", &message);

    FILE *file = fopen(MESSAGE_FILE, "a");
    if (file) {
        fprintf(file, "%s\n", json_object_to_json_string(parsed_json));
        fclose(file);
        printf("Message saved to %s\n", MESSAGE_FILE);
    } else {
        const char *error_response = "HTTP/1.1 500 Internal Server Error\r\nContent-Type: text/plain\r\n\r\nFailed to write message to file.";
        send(client_sock, error_response, strlen(error_response), 0);
        json_object_put(parsed_json);
        return;
    }

    const char *success_response = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\nMessage received and stored.";
    send(client_sock, success_response, strlen(success_response), 0);

    json_object_put(parsed_json);

    printf("POST request handled successfully.\n");
}

int main() {
    int server_sock, client_sock;
    struct sockaddr_in server_addr, client_addr;
    char buffer[1024];

    server_sock = socket(AF_INET, SOCK_STREAM, 0);
    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = INADDR_ANY;
    server_addr.sin_port = htons(80);

    if (bind(server_sock, (struct sockaddr *)&server_addr, sizeof(server_addr)) < 0) {
        perror("Bind failed");
        return 1;
    }

    if (listen(server_sock, 5) < 0) {
        perror("Listen failed");
        return 1;
    }

    printf("Server is listening on port 80...\n");

    while (1) {
        socklen_t client_len = sizeof(client_addr);
        client_sock = accept(server_sock, (struct sockaddr *)&client_addr, &client_len);

        read(client_sock, buffer, sizeof(buffer));

        if (strncmp(buffer, "GET /getMessages", 16) == 0) {
            char *receiver_param = strstr(buffer, "receiver=");
            if (receiver_param != NULL) {
                receiver_param += 9;
                char *receiver_end = strstr(receiver_param, " ");
                if (receiver_end != NULL) {
                    *receiver_end = '\0';
                }
                handle_get_messages(client_sock, receiver_param);
            } else {
                const char *error_response = "HTTP/1.1 400 Bad Request\r\nContent-Type: text/plain\r\n\r\nReceiver parameter missing.";
                send(client_sock, error_response, strlen(error_response), 0);
            }
        } else if (strncmp(buffer, "POST /sendMessage", 17) == 0) {
            char *body = strstr(buffer, "\r\n\r\n") + 4;
            handle_send_message(client_sock, body);
        } else {
            const char *not_found = "HTTP/1.1 404 Not Found\r\nContent-Type: text/plain\r\n\r\nEndpoint not found.";
            send(client_sock, not_found, strlen(not_found), 0);
        }

        close(client_sock);
    }

    close(server_sock);
    return 0;
}
