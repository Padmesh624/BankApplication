package com.bank;

import java.util.HashMap;

public class CustomerData {
    int custID;
    int accountNumber;
    String name;
    int balance;
    String encryptedPassword;




    public CustomerData(int accountNumber, int custId, String name, int balance, String encryptedPassword) {
        this.custID=custId;
        this.accountNumber=accountNumber;
        this.name=name;
        this.balance=balance;
        this.encryptedPassword=encryptedPassword;
    }


}
