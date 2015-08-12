package bis.kart.biskart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeliveryDetailsMain extends Activity implements View.OnClickListener {

    EditText name, contact, email, address;
    String nameStr, contactStr, emailStr, addressStr;
    Button submit;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_details);

        name = (EditText) findViewById(R.id.etName);
        address = (EditText) findViewById(R.id.etAddress);
        email = (EditText) findViewById(R.id.etEmail);
        contact = (EditText) findViewById(R.id.etContact);
        submit = (Button) findViewById(R.id.buttonSubmit);

        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        String msg;

        nameStr = name.getText().toString();
        addressStr = address.getText().toString();
        emailStr = email.getText().toString();
        contactStr = contact.getText().toString();

        if(nameStr.isEmpty() || contactStr.isEmpty() || emailStr.isEmpty() || addressStr.isEmpty())
        {
            if(nameStr.isEmpty())
            {
                msg = "Name field cannot be empty";
            }
            else if(addressStr.isEmpty())
            {
                msg = "Address field cannot be empty";

            }
            else if(emailStr.isEmpty())
            {
                msg = "Email field cannot be empty";

            }
            else
            {
                msg = "Contact field cannot be empty";
            }

            Toast.makeText(getApplicationContext(),
                    msg, Toast.LENGTH_LONG).show();
        }
        else
        {
            intent = new Intent(getApplicationContext(), ActivityFragments.class);

            intent.putExtra("name", nameStr);
            intent.putExtra("address", addressStr);
            intent.putExtra("email", emailStr);
            intent.putExtra("contact", contactStr);
            startActivity(intent);
            finish();
        }

    }
}
