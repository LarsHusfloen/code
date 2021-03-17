#include <fcntl.h>

int main() {

    int fd, sz;

    fd = open("test.txt", O_WRONLY | O_CREAT | O_TRUNC, 0644);
    if (fd < 0) {
        perror("feil ved skriving av filen"); exit(1);
    }
    sz = write(fd, "Godkjenning\0", 12);

    close(fd);
