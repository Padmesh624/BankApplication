package com.bank;

public class PasswordValidation {
    public static String encryptPassword(String password)
    {
        String encryptedPassword="";
        for(int i=0;i<password.length();i++)
        {
            int ascii=(int) password.charAt(i);
            if(ascii>=48 && ascii<=57)
            {
                if(ascii==57)
                    encryptedPassword+=(char)48;
                else
                    encryptedPassword+=(char)(ascii+1);
            }
            else if(ascii>=65 && ascii<=90)
            {
                if(ascii==90)
                    encryptedPassword+=(char)65;
                else
                    encryptedPassword+=(char)(ascii+1);
            }
            else if(ascii>=97 && ascii<=122)
            {
                if(ascii==122)
                    encryptedPassword+=(char)97;
                else
                    encryptedPassword+=(char)(ascii+1);
            }
        }
        return encryptedPassword;
    }
    public static void main(String[] args) {

    }
}

