#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include<string.h>
int main(int argc,  char* argv[])
{	char* target_address[20];
	char attack_string[150];
	char string;	
	char r;
	int i;
	for(i=0;i<112;i++)
	{	srand(time(0));
		r = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"[rand() % 26];		
		//printf("%c\n",r);
		attack_string[i] = r;
		//sleep(1);
	}	
	attack_string[i] = '\0';
	printf("%s",attack_string);
	*target_address=argv[1];
	printf("%s",*target_address);
	return 0;
}
