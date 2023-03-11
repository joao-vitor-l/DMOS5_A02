package com.example.dmos5_a02.model;

public class CreditCard{
    private int id;
    private double balance;
    private static int LAST_CARD_ID = 1;

    public CreditCard(){
        this.id = LAST_CARD_ID++;
        this.balance = 15000;
    }

    public void creditValue(double value){
        balance += value;
    }

    public void debitValue(double value) throws InsufficientBalanceException{
        if(balance >= value){
            balance -= value;
        }else{
            throw new InsufficientBalanceException("Saldo insuficiente.");
        }
    }

    public int getId(){
        return id;
    }

    public double getBalance(){
        return balance;
    }
}
