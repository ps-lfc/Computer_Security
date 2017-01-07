#include<stdio.h>
#include"openssl/blowfish.h"
#include<stdlib.h>
#include<string.h>
BF_KEY key;
//unsigned char *p = NULL;
unsigned char *output = (unsigned char *)calloc(100,sizeof(char));
unsigned char *output1 = (unsigned char *)calloc(100,sizeof(char));
void *fs_encrypt(void *plaintext, int bufsize, char *keystr,int *resultlen)
{	unsigned char *fplain = (unsigned char*) plaintext;
	BF_set_key(&key,16, (const unsigned char *)keystr);
	unsigned char *cipher = output;
	unsigned char initialization_vector[]="00000000";	
	BF_cbc_encrypt(fplain,output,bufsize,&key,initialization_vector,BF_ENCRYPT);
	//printf("%s \n",output);
	*resultlen=strlen((const char *)output);
	//printf("%d \n",*resultlen);	
	return (void *) output;
}
void *fs_decrypt(void *ciphertext, int bufsize, char *keystr,int *resultlen)
{	unsigned char * fcipher=(unsigned char *) ciphertext;
	unsigned char initialization_vector[]="00000000";
	BF_cbc_encrypt(fcipher,output1,bufsize,&key,initialization_vector,BF_DECRYPT);
	//printf("%s \n",output1);	
	*resultlen=strlen((const char *)output1)+1;
	printf("\n");	
	return (void *) output1;
}
