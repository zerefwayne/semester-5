#include<stdio.h>
#include<stdlib.h>
#include<sys/socket.h>
#include<sys/types.h>
#include<netinet/in.h>
#include<unistd.h>
#include <fcntl.h>

int main()
{
    int client_socket;
    int error_check;
    struct sockaddr_in client_address;
    socklen_t server_len;

    server_len = sizeof(client_address);

    client_socket = socket(AF_INET, SOCK_DGRAM, 0);

    if(client_socket == -1)
    {
        printf("Error creating socket\n");
        exit(1);
    }

    client_address.sin_family = AF_INET;
    client_address.sin_port = htons(4321);
    client_address.sin_addr.s_addr = INADDR_ANY;

    FILE* fp=fopen("hello.txt", "r");

    char c = fgetc(fp);
    char file[2000];
    int count = 0;

    while(c != EOF) {
        file[count] = c;
        count++;
        c = fgetc(fp);
    }
    file[count] = EOF;

    sendto(client_socket, &file, sizeof(file), 0, (struct sockaddr *) &client_address, server_len);

    printf("\n");

    close(client_socket);

    return 0;
}