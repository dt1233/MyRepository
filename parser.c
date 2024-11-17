#include <stdio.h>

int satirsayisiL=1, satirsayisiS=1;

int komutMu(char *komut, char *islem, int uzunluk)
{
    int i;
	   
	for(i=0; i<uzunluk; i++)
	{
        if(komut[i]!=islem[i])
		{
			return 0;
		} 
    }
    return 1;
}

int sayiMi(char karakter)
{
    return karakter>='0' && karakter<='9';
}

void lexicalAnalysis(char islem[])
{
    int i=0;
    char *komutlar[]={"TOP", "CIK", "BOL", "CRP"};
    int komut=-1;
    
    for(i=0; i<4; i++)
	{
        if(komutMu(islem, komutlar[i], 3) && islem[3]==' ')
		{
            komut=i;
            break;
        }
    }
    
    if(komut==-1)
	{
        printf("Hata: %d. satirda gecersiz komut.\n", satirsayisiL++);
        return;
    }
    
    int j=4;
    int operand_sayisi=0;
    while(islem[j]!='\0' && islem[j]!='\n')
	{
        if(sayiMi(islem[j]))
		{
            operand_sayisi++;
            while(sayiMi(islem[j]))
			{
				j++;
			}
        }
        
        if(islem[j]==',')
		{
			j++;
		}
        else if(!sayiMi(islem[j]) && islem[j]!='\n' && islem[j]!='\0')
		{
            printf("Hata: %d. satirda lexical hata.\n", satirsayisiL++);
            return;
        }
    }
    
    if(operand_sayisi!=2)
	{
        printf("Hata: %d. satirda operand eksik veya fazla.\n", satirsayisiL++);
    } 
	else
	{
        satirsayisiL++;
    }
}

void syntaxAnalysis(char islem[])
{
    int i=4;
    int operand1=0, operand2=0, virgul=0;
    
    while(islem[i]!='\0' && islem[i]!=',' && islem[i]!='\n')
	{
        if(sayiMi(islem[i]))
		{
			operand1=1;
		}
        else if(!sayiMi(islem[i]) && islem[i]!= ' ')
		{
            printf("Hata: %d. satirda syntax hatasi: Gecersiz karakter.\n", satirsayisiS++);
            return;
        }
        i++;
    }
    
    if(islem[i] == ',')
	{
		virgul=1;
	}
    else
	{
        printf("Hata: %d. satirda syntax hatasi: Virgul eksik.\n", satirsayisiS++);
        return;
    }
    
    i++;
    
    while(islem[i] != '\0' && islem[i] != '\n')
	{
        if(sayiMi(islem[i]))
		{
			operand2 = 1;
		}
        else if(!sayiMi(islem[i]) && islem[i] != ' ')
		{
            printf("Hata: %d. satirda syntax hatasi: Gecersiz karakter.\n", satirsayisiS++);
            return;
        }
        i++;
    }
    
    if(!operand1 || !operand2)
	{
        printf("Hata: %d. satirda syntax hatasi: Eksik operand.\n", satirsayisiS++);
    }
	else if(virgul==0)
	{
        printf("Hata: %d. satirda syntax hatasi: Eksik virgul.\n", satirsayisiS++);
    }
	else
	{
        satirsayisiS++;
    }
}

int main()
{	
	FILE *dosya;
	
	char dosyaAdi[20];
	char islem[100];
	
	printf("Dosya adini giriniz: ");
	scanf("%s",dosyaAdi);
	
	dosya=fopen(dosyaAdi,"r");
	
	if(dosya==NULL)
	{	
		printf("Hata: Dosya bulunamadi!");
	}
	else
	{	
		printf("Lexical analiz baslatiliyor...\n\n");
		while(fgets(islem,sizeof(islem),dosya)!=NULL)
		{	
			lexicalAnalysis(islem);
		}
		printf("\nLexical analiz sonlandiriliyor...\n");
		
		fseek(dosya,0,SEEK_SET);
		
		printf("Syntax analiz baslatiliyor...\n\n");
		while(fgets(islem,sizeof(islem),dosya)!=NULL)
		{
			syntaxAnalysis(islem);
		}
		printf("\nSyntax analiz sonlandiriliyor...\n");
	}
	
	fclose(dosya);
}
