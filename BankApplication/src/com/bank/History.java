package com.bank;

public class History {
    int amount;
    int balance;
    int transactionId;
    String transactionType;
    int accountNumber;

    public History(int transactionId,int accountNumber,String transactionType, int amount, int balance) {
        this.transactionType=transactionType;
        this.amount=amount;
        this.balance=balance;
        this.transactionId=transactionId;
        this.accountNumber=accountNumber;
    }
}
