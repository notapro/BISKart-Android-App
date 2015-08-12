package bis.kart.biskart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CashOnDelivery extends Activity{

    TextView address;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_on_delivery);

        address = (TextView) findViewById(R.id.textCashOnDelDetails);
        intent = getIntent();

        address.setText("Delivery Details : \n" + intent.getStringExtra("details").toString());
    }
}
