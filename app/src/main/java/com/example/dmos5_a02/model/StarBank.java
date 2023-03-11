package com.example.dmos5_a02.model;

import com.example.dmos5_a02.model.CreditCard;
import com.example.dmos5_a02.model.InsufficientBalanceException;

import java.util.ArrayList;

public class StarBank{
    private ArrayList<CreditCard> creditCards;

    public StarBank(){
        creditCards = new ArrayList<>();
    }

    public void startCreditCards(){
        for(int i = 0; i < 6; i++){
            creditCards.add(new CreditCard());
        }
    }

    public void roundCompleted(CreditCard card, double value){
        card.creditValue(value);
    }

    public double transfer(CreditCard payer, CreditCard receiver, double value) throws InsufficientBalanceException{
        payer.debitValue(value);
        receiver.creditValue(value);
        return value;
    }

    public void receive(CreditCard card, double value){
        card.creditValue(value);
    }

    public boolean pay(CreditCard card, double value) throws InsufficientBalanceException{
        card.debitValue(value);
        return true;
    }

    public CreditCard getCreditCard(int id){
        CreditCard card = creditCards.get(id);
        return card;
    }
}
