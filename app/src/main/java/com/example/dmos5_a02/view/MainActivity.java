package com.example.dmos5_a02.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.dmos5_a02.R;
import com.example.dmos5_a02.model.StarBank;
import com.example.dmos5_a02.model.CreditCard;
import com.example.dmos5_a02.model.InsufficientBalanceException;

public class MainActivity extends AppCompatActivity{
        StarBank bank = new StarBank();

        private EditText payerIdEditText, receiverIdEditText, transferValueEditText, cardIdEditText, valueEditText, rewardedIdEditText, rewardValueEditText;
        private RadioGroup receivePayRadioGroup;
        private RadioButton receiveRadioButton, payRadioButton;
        private Button transferButton, receivePayButton, rewardButton;

        @Override
        protected void onCreate(Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                bank.startCreditCards();

                payerIdEditText = findViewById(R.id.payer_id_edittext);
                receiverIdEditText = findViewById(R.id.receiver_id_edittext);
                transferValueEditText = findViewById(R.id.transfer_value_edittext);
                cardIdEditText = findViewById(R.id.card_id_edittext);
                valueEditText = findViewById(R.id.value_edittext);
                rewardedIdEditText = findViewById(R.id.rewarded_id_edittext);
                rewardValueEditText = findViewById(R.id.reward_value_edittext);
                receivePayRadioGroup = findViewById(R.id.receive_pay_radiogroup);
                receiveRadioButton = findViewById(R.id.receive_radiobutton);
                payRadioButton = findViewById(R.id.pay_radiobutton);
                transferButton = findViewById(R.id.transfer_button);
                receivePayButton = findViewById(R.id.receive_pay_button);
                rewardButton = findViewById(R.id.reward_button);

                // Função de transferência
                transferButton.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                                // Busca os IDs e o valor da transferência inseridos nas caixas de texto
                                int payerId = Integer.parseInt(payerIdEditText.getText().toString());
                                int receiverId = Integer.parseInt(receiverIdEditText.getText().toString());
                                double transferValue = Double.parseDouble(transferValueEditText.getText().toString());

                                try{
                                        bank.transfer(bank.getCreditCard(payerId), bank.getCreditCard(receiverId), transferValue);
                                        Toast.makeText(MainActivity.this, "Transferência concluída!", Toast.LENGTH_SHORT).show();
                                }catch(InsufficientBalanceException e){
                                        Toast.makeText(MainActivity.this, "A transferência fracassou: saldo insuficiente.", Toast.LENGTH_SHORT).show();
                                }
                        }
                });

                // Função de receber/pagar
                receivePayButton.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                                // Busca o ID e o valor da transferência inseridos nas caixas de texto
                                int cardId = Integer.parseInt(cardIdEditText.getText().toString());
                                double value = Double.parseDouble(valueEditText.getText().toString());

                                // Checa entre receber ou pagar
                                if(receiveRadioButton.isChecked()){
                                        // Recebimento
                                        bank.receive(bank.getCreditCard(cardId), value);
                                        Toast.makeText(MainActivity.this, "Transferência concluída!", Toast.LENGTH_SHORT).show();
                                }else if(payRadioButton.isChecked()){
                                        // Pagamento
                                        boolean paymentSuccessful = false;
                                        try{
                                                paymentSuccessful = bank.pay(bank.getCreditCard(cardId), value);
                                        } catch(InsufficientBalanceException e){
                                                throw new RuntimeException(e);
                                        }
                                        if(paymentSuccessful){
                                                Toast.makeText(MainActivity.this, "Pagamento efetuado.", Toast.LENGTH_SHORT).show();
                                        }else{
                                                Toast.makeText(MainActivity.this, "O pagamento fracassou: saldo insuficiente.", Toast.LENGTH_SHORT).show();
                                        }
                                }else{
                                        // Exibe uma mensagem de erro caso nenhuma opção seja selecionada
                                        Toast.makeText(MainActivity.this, "Escolha qual operação deve ser efetuada.", Toast.LENGTH_SHORT).show();
                                }
                        }
                });

                // Função de fim da rodada
                rewardButton.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                                // Busca o ID e o valor da recompensa ao fim da rodada
                                int rewardedId = Integer.parseInt(rewardedIdEditText.getText().toString());
                                double rewardValue = Double.parseDouble(rewardValueEditText.getText().toString());

                                bank.roundCompleted(bank.getCreditCard(rewardedId), rewardValue);
                                Toast.makeText(MainActivity.this, "Recompensa recebida!", Toast.LENGTH_SHORT).show();
                        }
                });
        }
}
