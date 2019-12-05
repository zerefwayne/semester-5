#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>

int main()
{
    int server_socket;

    char server_message[2000];
    char recieved_from_client;

    struct sockaddr_in client_address;
    struct sockaddr_in server_address;

    socklen_t client_length = sizeof(client_address);

    server_socket = socket(AF_INET, SOCK_DGRAM, 0);

    server_address.sin_family = AF_INET;
    server_address.sin_port = htons(4321);
    server_address.sin_addr.s_addr = INADDR_ANY;

       int bind_return = bind(server_socket, (struct sockaddr *)&server_address, sizeof(server_address));

    if (bind_return == -1)
    {
        printf("Couldn't connect to port 4321");
        exit(0);
    }

    int recieve_m = recvfrom(server_socket, &server_message, sizeof(server_message), 0, (const struct sockaddr *) &client_address, &client_length);

    if (recieve_m == -1)
    {
        printf("Couldn't recieve message from client!");
        exit(1);
    }

    printf("%s\n", server_message);

    close(server_socket);

    return 0;
}