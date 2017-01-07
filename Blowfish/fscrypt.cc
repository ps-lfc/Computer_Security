#include<stdio.h>
#include<stdlib.h>
#include"openssl/blowfish.h"
#include<string.h>

BF_KEY key;
unsigned char *p = NULL;
unsigned char *output = (unsigned char *)malloc(100 * sizeof(char));
unsigned char *output1 = (unsigned char *)malloc(100 * sizeof(char));
char initialization_vector[]="00000000";		
void *fs_encrypt(void *plaintext, int bufsize, char *keystr, int *resultlen)
{	unsigned char cipher_text[bufsize];
	//unsigned char *temp = (unsigned char *)malloc(sizeof(temp));
	unsigned char *fplain = (unsigned char*) plaintext;
	BF_set_key(&key, 16, (const unsigned char *)keystr);
	unsigned char *cipher = output;
	int i=0;	
	do
	{	cipher_text[i]=(*fplain)^initialization_vector[i];
		i++;
		fplain++;
	}while(i<=7);
	//printf("Plash %s\n",(unsigned char *)plaintext);
	//temp = (unsigned char *)encrypt(plain_text/*,plaintext*/,fplain);
	//fplain+=8;
	//memcpy(plain_text,temp,sizeof(temp));
	BF_ecb_encrypt(&cipher_text[0],output,&key,BF_ENCRYPT);
	//encrypted(bufsize,plain_text,fplain);
	bufsize-=8;
	int encryption_index1=8;
	int encryption_index=0;
	int encryption_index2=0;	
	if(bufsize>0)
	{	do
		{	
			//if(bufsize>0)
			//{	//printf("Inside if");			
			while(encryption_index2<=7)
			{	cipher_text[encryption_index1++]=(*output)^(*fplain);
				output=output+1;
				fplain=fplain+1;
				encryption_index2=encryption_index2+1;	
			}
			encryption_index+=8;
			BF_ecb_encrypt(&cipher_text[encryption_index],output,&key,BF_ENCRYPT);
			//printf("before:%d \n",bufsize);	
			bufsize-=8;
			encryption_index2=0;
			//encryption_index1+=8;
			//printf("after:%d \n",bufsize);
		}while(bufsize>0);		
		//}
		//else
		//{	
		//}
	}
	//p = (unsigned char *)malloc((bufsize+1)*sizeof(p));
	//p = plain_text;
	//*resultlen=strlen((const char *)p);	
	*resultlen=strlen((const char *)cipher);
	//printf("%d \n",*resultlen);	
	return (void *) cipher;
	//return (void *) p;
}
void *fs_decrypt(void *ciphertext, int bufsize, char *keystr, int *resultlen)
{	unsigned char *plain_text = (unsigned char *)malloc((bufsize+1)*sizeof(plain_text));
	unsigned char * fcipher=(unsigned char *) ciphertext;
	unsigned char * cipher=(unsigned char *) ciphertext;
	unsigned char *plaintext = output1;    
 	int i=0;   	          
        BF_ecb_encrypt(fcipher,output1,&key,BF_DECRYPT);
	do
	{	plain_text[i]=(*output1)^initialization_vector[i];
		output1++;
		i++;
	}while(i<=7);
	//printf("%s \n",cipher_text);	
	bufsize-=8;
	int decryption_index2=0;
	int decryption_index1=8;	
	if(bufsize>0)
	{	while(bufsize>0)
		{       fcipher+=8;
			BF_ecb_encrypt(fcipher,output1,&key,BF_DECRYPT);
			while(decryption_index2<=7)                   	
			{	plain_text[decryption_index1]=(*cipher)^(*output1);
				output1+=1;
			 	cipher+=1;
				decryption_index1+=1;
				decryption_index2+=1;	
			}
			decryption_index2=0;
			//printf("%s \n",plain_text);
			//printf("before:%d \n",bufsize);
			bufsize-=8;
			//printf("after:%d \n",bufsize);       
		}
	}
	*resultlen=strlen((const char*) plain_text)+1;
	p = (unsigned char *)malloc((bufsize+1)*sizeof(p));	
	//printf("%s \n",cipher_text);
	//printf("%d \n",*resultlen+1);
	p = plain_text;
	//printf("%s\n",p);	
	return (void *) p;
}
