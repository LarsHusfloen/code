#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(){
    int fd, sz, size = 50;
    char *buf, fileName[20];
    buf = (char * ) calloc(size, sizeof(char)); //dynamisk minne allokering

    printf("Enter the file to scan: ");
    scanf("%s", fileName);

    fd = open(fileName, O_RDONLY);
    if (fd < 0) {
	    perror("feil ved lesning av fil"); exit(1);
    }

    sz = read(fd, buf, size);
    printf("%d bytes ble lest.\n", sz);
    printf("Bytes som ble lest var: %s\n", buf);
    
    close(fd);
}