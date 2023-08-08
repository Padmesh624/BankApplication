package com.bank;

import java.io.File;
import java.util.*;

public class Function  {
    static Scanner sc = new Scanner(System.in);

    static int accountNumber=1003;
    static int custId=14;
    static int transactionId=0;
    static HashMap<Integer,Object> customerData= new HashMap<>();
    static HashMap<Integer,Object> transactionHistory= new HashMap<>();
    static HashMap<Integer,ArrayList<String>> customerPassword= new HashMap<>();

    public static void main(String[] args)
    {
        CustomerData cd1= new CustomerData(1000,11,"Kumar",10000,"ApipNbjm");
        CustomerData cd2= new CustomerData(1001,12,"Madhu",20000,"Cboljoh");
        CustomerData cd3= new CustomerData(1002,13,"Rahul",30000,"dbnqvt");
        CustomerData cd4= new CustomerData(1003,14,"Robin",40000,"kbwb22");
        customerData.put(1000,cd1);
        customerData.put(1001,cd2);
        customerData.put(1002,cd3);
        customerData.put(1003,cd4);


        mainLoop: while(true)
        {
            System.out.println("1.Show Customer Details");
            System.out.println("2.Add new customers");
            System.out.println("3.Authentication");
            System.out.println("4.Customer Login");
            System.out.println("5.To get top N customers");
            System.out.println("6.Show transaction history");
            System.out.println("7.Break");
            int action=sc.nextInt();

            switch (action)
            {
                case 1:
                    showCustomerDetails();
                    break;
                case 2:
                    addNewCustomer();
                    break;
                case 3:
                    authentication();
                    break;
                case 4:
                    customerLogin();
                    break;
                case 5:
                    getNcustomers();
                    break;
                case 6:
                    showTransactionHistory();
                    break;
                case 7:
                    break mainLoop;
            }
        }
    }

    private static void showTransactionHistory()
    {
        System.out.println("Transaction id     Account Number    transaction type    amount     balance");
        for(Map.Entry<Integer, Object> map: transactionHistory.entrySet())
        {
            History ob= (History) map.getValue();
            System.out.println("         "+ob.transactionId+"    "+ob.accountNumber+"    "+ob.transactionType+"    "+ob.amount+" "+ob.balance);
        }
        System.out.println("============================================================");
    }

    private static void getNcustomers() {
        System.out.println("Enter the no.of.customers");
        int customers=sc.nextInt();
        String customerArray[] = new String[customers];
        int max=0;
        ArrayList<Integer> accountNumber= new ArrayList<>();
        if(customers<=customerData.size())
        {
            for(int i=customers-1;i>=0;i--)
            {
                String name="";
                int tempAccNum=0;
                for(Map.Entry<Integer, Object> map: customerData.entrySet())
                {
                    CustomerData ob= (CustomerData) map.getValue();
                    if(ob.balance>max && !accountNumber.contains(ob.accountNumber))
                    {
                        max=ob.balance;
                        name= ob.name;
                        tempAccNum= ob.accountNumber;
                    }
                }
                customerArray[i]=name;
                accountNumber.add(tempAccNum);
                System.out.println("Name : "+name+"  Account number : "+tempAccNum+" Amount : "+max);
                max=0;
            }
        }
        else {
            System.out.println("Number of customer is less than your requirement");
        }
        System.out.println("======================================================");
        System.out.println();
    }

    private static void customerLogin() {
        System.out.println("Enter your Account Number");
        int accountNumber=sc.nextInt();
        System.out.println("Enter your password");
        String password=sc.next();
        String finalPassword=PasswordValidation.encryptPassword(password);

        int count=0;
        for(Map.Entry<Integer, Object> data: customerData.entrySet())
        {
            CustomerData ob= (CustomerData) data.getValue();
            int accNumber=ob.accountNumber;
            String pass=ob.encryptedPassword;
            int balance=ob.balance;

            if(accNumber==accountNumber && pass.equals(finalPassword))
            {
                innerLoop: while (true)
                {
                    System.out.println("1.ATM Withdrawal");
                    System.out.println("2.Cash Deposit");
                    System.out.println("3.Account Transfer");
                    System.out.println("4.Show transaction history");
                    System.out.println("5.Change password");
                    System.out.println("6.Break");
                    int process=sc.nextInt();
                    switch (process)
                    {
                        case 1:
                            int newBalance=ATMWithdraw(ob,balance);
                            balance=newBalance;
                            break;
                        case 2:
                            int newBalance1=cashDeposit(ob,balance);
                            balance=newBalance1;
                            break;
                        case 3:
                            int newBalance2=accountTransfer(ob,balance);
                            balance=newBalance2;
                            break;
                        case 4:
                            showTransactionHistory();
                            break;
                        case 5:
                            changePassword(ob,password);
                            break;
                        case 6:
                            break innerLoop;
                    }
                }
            }
        }
        if(count==0)
        {
            System.out.println("Entered account details not available");
            System.out.println("=======================================");
            System.out.println();
        }

    }

    private static void changePassword(CustomerData ob, String password) {
        System.out.print("Enter your new password : ");
        String newPassword= sc.next();
        ArrayList<String> arrayList= customerPassword.get(ob.accountNumber);
        if(arrayList.contains(newPassword))
        {
            System.out.println("Your password is same as the previous  password. Please enter new password");
        }

        else {
            if(arrayList.size()==3)
            {
                arrayList.remove(0);
            }
            arrayList.add(newPassword);
            customerPassword.put(ob.accountNumber,arrayList);
            ob.encryptedPassword=PasswordValidation.encryptPassword(newPassword);
            System.out.println("Password changed successfully");
        }
        System.out.println("======================================================================================");
    }


    private static int accountTransfer(CustomerData ob, int balance) {
        System.out.println("Enter the account number to deposit");
        int recepientAccountNumber=sc.nextInt();
        if(customerData.get(recepientAccountNumber)!=null)
        {
            System.out.println("Enter the amount to transfer");
            int amount=sc.nextInt();
            if(balance-1000>amount)
            {
                balance=balance-amount;
                CustomerData ob2= (CustomerData) customerData.get(recepientAccountNumber);
                CustomerData cd8= new CustomerData(ob2.accountNumber,ob2.custID,ob2.name,ob2.balance+amount,ob2.encryptedPassword);
                customerData.put(recepientAccountNumber,cd8);
                CustomerData cd9= new CustomerData(ob.accountNumber,ob.custID,ob.name,balance,ob.encryptedPassword);
                customerData.put(ob.accountNumber,cd9);
                History history2= new History(++transactionId,ob.accountNumber,"Transfer To "+String.valueOf(recepientAccountNumber),amount,balance);
                transactionHistory.put(transactionId,history2);
                System.out.println("Fund transferred successfully . Your balance is : "+balance);
            }
            else
            {
                System.out.println("Your balance is insufficient for fund transfer");
            }
        }
        else
        {
            System.out.println("Entered Account number is not available");
        }
        System.out.println("===========================================================================");
        return balance;
    }

    private static int cashDeposit(CustomerData ob, int balance) {
        System.out.println("Enter the amount to be Deposit");
        int amount = sc.nextInt();
        balance=balance+amount;
        CustomerData cd7= new CustomerData(ob.accountNumber,ob.custID,ob.name,balance, ob.encryptedPassword);
        customerData.put(accountNumber,cd7);
        History history2= new History(++transactionId,ob.accountNumber,"CashDeposit",amount,balance);
        transactionHistory.put(transactionId,history2);
        System.out.println("Cash deposited successfully. Total balance is : "+balance);
        System.out.println("==============================================");
        return balance;
    }

    private static int ATMWithdraw(CustomerData ob, int balance) {
        System.out.println("Enter the amount to be withdraw");
        int amount = sc.nextInt();
        if((balance-1000)<amount)
        {
            System.out.println("Your amount is insufficient for withdrawal");
            System.out.println("==============================================");
        }
        else {
            balance=balance-amount;
            CustomerData cd6= new CustomerData(ob.accountNumber,ob.custID, ob.name,balance, ob.encryptedPassword);
            customerData.put(accountNumber,cd6);
            History history1= new History(++transactionId,ob.accountNumber,"ATM Withdrawal",amount,balance);
            transactionHistory.put(transactionId,history1);
            System.out.println("Amount withdrawed successfully Total balance is : "+balance);
            System.out.println("==============================================");
        }
        return balance;
    }



    private static void authentication() {
        System.out.println("Enter your name : ");
        String name=sc.next();
        System.out.println("Enter your password : ");
        String password= sc.next();
        String finalPassword=PasswordValidation.encryptPassword(password);
        int temp=0;
        for(Map.Entry<Integer, Object> data: customerData.entrySet())
        {
            CustomerData ob= (CustomerData) data.getValue();
            String Name=ob.name;

            String pass=ob.encryptedPassword;
            if(Name.equals(name) && pass.equals(finalPassword))
            {
                System.out.println("************** You are logged in ****************");
                temp++;
                System.out.println("========================");
                System.out.println();
                break;
            }

        }
        if(temp==0)
        {
            System.out.println("Your password is wrong ");
            System.out.println("==============================================");
        }
    }

    private static void addNewCustomer() {
        System.out.println("Enter your name : ");
        String name=sc.next();
        System.out.println("Enter your password : ");
        String password= sc.next();
        while(true)
        {
            System.out.println("Re-Enter your password : ");
            String reTypePassword= sc.next();
            if(!password.equals(reTypePassword))
                System.out.println("Enter correct password");
            else break;
        }
        accountNumber++;
        custId++;
        transactionId++;
        CustomerData cd5= new CustomerData(accountNumber,custId,name,10000,PasswordValidation.encryptPassword(password));
        customerData.put(accountNumber,cd5);
        History history1= new History(transactionId,accountNumber,"Account creation",10000,10000);
        transactionHistory.put(transactionId,history1);
        ArrayList<String > arrayList= new ArrayList<>();
        arrayList.add(password);
        customerPassword.put(accountNumber,arrayList);
        System.out.println("Account created successfully");
        System.out.println("===================================");
        System.out.println();
    }

    private static void showCustomerDetails() {
        for(Map.Entry<Integer, Object> map: customerData.entrySet())
        {
            CustomerData ob= (CustomerData) map.getValue();

            System.out.println(ob.custID+" "+ob.accountNumber+" "+ob.name+" "+ob.balance+" "+ob.encryptedPassword);
        }
        System.out.println("=========================================================");
        System.out.println();
    }
}
