#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

int main(){
	char *menu = "\n1.ps\n2.fork\n3.join\n4.exec\n5.wait\n>";
	printf("%s", menu);
	int option;
	scanf("%d", &option);
	switch(option){
		case 1:
			system("ps -A");
			break;
		case 2:
			fork();
			printf("%d\n", getpid());
			break;
		case 3:
		    //TODO: join function
			break;
		case 4:
		    //TODO: exec family function
		    execv("./EXEC", NULL);
		    break;
		case 5:
		    system("wait");
		    break;
			
		default:
			printf("Invalid");
	}
	return 0;
	
}
