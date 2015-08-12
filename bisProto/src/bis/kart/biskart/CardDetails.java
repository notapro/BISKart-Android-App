package bis.kart.biskart;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CardDetails extends Activity{

    EditText cardNum, cvv;
    Spinner month, year;
    Button btProceed;
    CardData data;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_details);

        data = new CardData();

        btProceed = (Button) findViewById(R.id.buttonProceed);
        cardNum = (EditText) findViewById(R.id.etCardNum);
        cvv = (EditText) findViewById(R.id.etCvv);
        month = (Spinner) findViewById(R.id.spinnerMonth);
        year = (Spinner) findViewById(R.id.spinnerYear);
        tv=(TextView) findViewById(R.id.carddetailstotal);
        
        tv.setText("You are paying Rs."+getIntent().getExtras().getDouble("amount"));

        btProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Card card = new Card(cardNum.getText().toString(),Integer.parseInt(month.getSelectedItem().toString()), Integer.parseInt(year.getSelectedItem().toString()),cvv.getText().toString());

                if(card.isValidFormat(card.cardNum, card.month, card.year, card.cvv)){
                    if(data.paymentSuccessful(card))
                    {
                        AlertDialog.Builder alert = new AlertDialog.Builder(CardDetails.this);
                        alert.setTitle("Payment");
                        alert.setMessage("Payment Successful.");
                        alert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent i = getIntent();

                                Intent intent = new Intent(getApplicationContext(), CashOnDelivery.class);
                                intent.putExtra("details", i.getStringExtra("details"));
                                startActivity(intent);
                            }
                        });
                        alert.show();


                    }
                    else
                    {
                        AlertDialog.Builder alert = new AlertDialog.Builder(CardDetails.this);
                        alert.setTitle("Payment");
                        alert.setMessage("Payment Unsuccessful.Redirecting to Card page.");
                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                Intent i = getIntent();

                                Intent intent = new Intent(getApplicationContext(), CardDetails.class);
                                intent.putExtra("details", i.getStringExtra("details"));
                                intent.putExtra("amount", i.getDoubleExtra("amount",0.0));
                                finish();
                                startActivity(intent);
                            }
                        });
                        alert.show();



                    }
                }
                else{
                    String msg = "Please enter valid details";
                    Toast.makeText(getApplicationContext(),
                            msg, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
