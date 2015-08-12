package bis.kart.biskart;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpPage extends ActionBarActivity {


    EditText email, phone, name, address, password;
    TextView signUpStatus;
    Button submit,toLogin;
    String response, nameStr, emailStr, phoneStr, addressStr, passwordStr;
   int status;
    JSONObject jsonObj;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        email = (EditText)findViewById(R.id.signUpEmailValue);
        name = (EditText)findViewById(R.id.signUpNameValue);
        password = (EditText)findViewById(R.id.signUpPasswordValue);
        phone = (EditText)findViewById(R.id.signUpContactValue);
        address = (EditText)findViewById(R.id.signUpAddressValue);
        submit = (Button)findViewById(R.id.signUpSubmit);
        signUpStatus =(TextView)findViewById(R.id.signUpError);
        toLogin = (Button) findViewById(R.id.signUpProceedToLogin);

        email.setBackgroundResource(R.drawable.shape);
        password.setBackgroundResource(R.drawable.shape);
        phone.setBackgroundResource(R.drawable.shape);
        address.setBackgroundResource(R.drawable.shape);
        name.setBackgroundResource(R.drawable.shape);

        toLogin.setVisibility(View.INVISIBLE);


        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameStr = name.getText().toString();
                emailStr = email.getText().toString();
                phoneStr = phone.getText().toString();
                addressStr = address.getText().toString();
                passwordStr = password.getText().toString();
                new SignUp().execute();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class SignUp extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(SignUpPage.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity;
            HttpResponse httpResponse;
            JSONObject json = new JSONObject();
            try {
                HttpPost httpPost = new HttpPost("http://52.74.138.41:8080/biskart/api/signup/customer");
                json.put("name", nameStr);
                json.put("email", emailStr);
                json.put("password", passwordStr);
                json.put("address", addressStr);
                json.put("contact", phoneStr);
                Log.d("Request: ", "> " + json);
                StringEntity se = new StringEntity( json.toString());
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPost.setEntity(se);


                httpResponse = httpClient.execute(httpPost);
                httpEntity = httpResponse.getEntity();
                response = EntityUtils.toString(httpEntity);
                Log.d("Response: ", "> " + response+"hw");

                if(response!=null){
                    try{
                        jsonObj = new JSONObject(response);
                        status =jsonObj.getInt("status");
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }else{
                    Log.e("ServiceHandler", "Couldn't get any data from the url");
                }
            } catch(Exception e) {
                e.printStackTrace();
                Log.e("Error", "Cannot Estabilish Connection");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
             //Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            if(status == 1) {
                signUpStatus.setText("Sign Up Successful");
                toLogin.setVisibility(View.VISIBLE);
            } else {
                signUpStatus.setText("Sign up error!Email ID already exists");
           }

        }

    }
}
