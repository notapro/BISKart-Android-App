package bis.kart.biskart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import bis.kart.biskart.R;


public class DeliveryDetails extends Activity implements View.OnClickListener{

	Button b;
	EditText et1, et2, et3, et4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delivery_details);
		
		et1 = (EditText)findViewById(R.id.editText1);
		et2 = (EditText)findViewById(R.id.editText2);
		et3 = (EditText)findViewById(R.id.editText3);
		et4 = (EditText)findViewById(R.id.editText4);
		b = (Button)findViewById(R.id.submitdetails);
		b.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delivery_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String name, email, address, phone;
		name = et1.getText().toString().trim();
		email = et2.getText().toString().trim();
		address = et3.getText().toString().trim(); 
		phone = et4.getText().toString().trim();
		Intent i = new Intent("android.intent.action.PAYMENT");
		i.putExtra("name", name);
		i.putExtra("email", email);
		i.putExtra("address", address);
		i.putExtra("phone", phone);
		startActivity(i);
	}
}
