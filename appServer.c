Last login: Fri Nov  8 10:49:02 on ttys000
loganschlueter@Logans-MacBook-Pro ~ % curl http://localhost:8080/getMessages
curl: (7) Failed to connect to localhost port 8080 after 0 ms: Couldn't connect to server
loganschlueter@Logans-MacBook-Pro ~ % curl http://52.90.153.18:8080/getMessages
curl: (28) Failed to connect to 52.90.153.18 port 8080 after 75001 ms: Couldn't connect to server
loganschlueter@Logans-MacBook-Pro ~ % curl http://172.31.32.24:8080/getMessages
curl: (7) Failed to connect to 172.31.32.24 port 8080 after 5 ms: Couldn't connect to server
loganschlueter@Logans-MacBook-Pro ~ % curl https://52.90.153.18:8080/getMessages
curl: (28) Failed to connect to 52.90.153.18 port 8080 after 75001 ms: Couldn't connect to server
loganschlueter@Logans-MacBook-Pro ~ % curl http://52.90.153.18:80/getMessages 
curl: (7) Failed to connect to 52.90.153.18 port 80 after 64 ms: Couldn't connect to server
loganschlueter@Logans-MacBook-Pro ~ % curl http://52.90.153.18:80            
curl: (7) Failed to connect to 52.90.153.18 port 80 after 194 ms: Couldn't connect to server
loganschlueter@Logans-MacBook-Pro ~ % curl http://52.90.153.18:80/getMessages
[{"id": 1, "content": "Hello from server!"},{"id": 2, "content": "Another message"}]%                                                                           loganschlueter@Logans-MacBook-Pro ~ % ssh-aws
Last login: Sat Nov  9 16:42:53 2024 from syn-068-112-204-006.res.spectrum.com
   ,     #_
   ~\_  ####_        Amazon Linux 2
  ~~  \_#####\
  ~~     \###|       AL2 End of Life is 2025-06-30.
  ~~       \#/ ___
   ~~       V~' '->
    ~~~         /    A newer version of Amazon Linux is available!
      ~~._.   _/
         _/ _/       Amazon Linux 2023, GA and supported until 2028-03-15.
       _/m/'           https://aws.amazon.com/linux/amazon-linux-2023/

[ec2-user@ip-172-31-32-24 ~]$ ls
networkingApp
[ec2-user@ip-172-31-32-24 ~]$ cd networkingApp/
[ec2-user@ip-172-31-32-24 networkingApp]$ ls
server  server.c
[ec2-user@ip-172-31-32-24 networkingApp]$ sudo ./server
Listening on port 80
^C
[ec2-user@ip-172-31-32-24 networkingApp]$ ls
server  server.c
[ec2-user@ip-172-31-32-24 networkingApp]$ quit
-bash: quit: command not found
[ec2-user@ip-172-31-32-24 networkingApp]$ exit
logout
Connection to 52.90.153.18 closed.
loganschlueter@Logans-MacBook-Pro ~ % ssh-aws                                
Last login: Sun Nov 10 18:17:25 2024 from syn-035-148-030-016.res.spectrum.com
   ,     #_
   ~\_  ####_        Amazon Linux 2
  ~~  \_#####\
  ~~     \###|       AL2 End of Life is 2025-06-30.
  ~~       \#/ ___
   ~~       V~' '->
    ~~~         /    A newer version of Amazon Linux is available!
      ~~._.   _/
         _/ _/       Amazon Linux 2023, GA and supported until 2028-03-15.
       _/m/'           https://aws.amazon.com/linux/amazon-linux-2023/

[ec2-user@ip-172-31-32-24 ~]$ cd networkingApp/
[ec2-user@ip-172-31-32-24 networkingApp]$ sudo ./server
Listening on port 80
^C
[ec2-user@ip-172-31-32-24 networkingApp]$ ls
server  server.c
[ec2-user@ip-172-31-32-24 networkingApp]$ vim server.c
[ec2-user@ip-172-31-32-24 networkingApp]$ gcc server.c -o server
[ec2-user@ip-172-31-32-24 networkingApp]$ sudo ./server
Listening on port 80
^C
[ec2-user@ip-172-31-32-24 networkingApp]$ ls
server  server.c
[ec2-user@ip-172-31-32-24 networkingApp]$ vim server.c
[ec2-user@ip-172-31-32-24 networkingApp]$ gcc server.c -o server
[ec2-user@ip-172-31-32-24 networkingApp]$ sudo ./server
Listening on port 80
^C
[ec2-user@ip-172-31-32-24 networkingApp]$ ls
server  server.c
[ec2-user@ip-172-31-32-24 networkingApp]$ vim server.c
[ec2-user@ip-172-31-32-24 networkingApp]$ gcc server.c -o server
[ec2-user@ip-172-31-32-24 networkingApp]$ sudo ./server
Listening on port 80
^C
[ec2-user@ip-172-31-32-24 networkingApp]$ ls
server  server.c
[ec2-user@ip-172-31-32-24 networkingApp]$ vim server.c

    // Bind to port 8080
    bind(server_fd, (struct sockaddr *)&address, sizeof(address));
    listen(server_fd, 3);

    printf("Listening on port %d\n", PORT);

    while (1) {
        client_sock = accept(server_fd, (struct sockaddr *)&address, (socklen_t*)&addrlen);
        char buffer[1024] = {0};
        read(client_sock, buffer, 1024);

        // Check HTTP method
        if (strncmp(buffer, "GET /getMessages", 16) == 0) {
            handle_get_messages(client_sock);
        } else if (strncmp(buffer, "POST /sendMessage", 17) == 0) {
            char *body = strstr(buffer, "\r\n\r\n") + 4;
            handle_send_message(client_sock, body);
        }

        close(client_sock);
    }
    return 0;
}
                                                                                                                  65,1          Bot


