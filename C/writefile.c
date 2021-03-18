#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main() {
    int size = 100;
    char text[size], name[size];

    printf("Enter the name and type of the new file: \n");
    fgets(name, size, stdin);

    FILE * fPtr = fopen(name, "w");
    if (fPtr == NULL){
        printf("Unable to create file.\n");
        fclose(fPtr);
        exit(EXIT_FAILURE);
    }
    
    printf("Enter what you want in the new file: \n");
    
    fgets(text, size, stdin);
    fputs(text, fPtr);
    fclose(fPtr);
    printf("%s, created and saved successfully.\n", name);

    return 0;
}