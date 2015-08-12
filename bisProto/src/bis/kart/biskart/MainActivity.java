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
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    EditText email;
    TextView error;
    EditText password;
    Button submit, signup;
    String response = null;
    String name,emailText,passText;
    CheckBox showPassword;
    private static String url;
    private ProgressDialog pDialog;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (EditText)findViewById(R.id.LoginEmailID);
        password = (EditText)findViewById(R.id.LoginPassword);
        submit = (Button)findViewById(R.id.LoginSubmit);
        error = (TextView)findViewById(R.id.loginError);
        signup = (Button)findViewById(R.id.loginSignUp);
        showPassword = (CheckBox)findViewById(R.id.loginShowPasswordCheckBox);

        email.setBackgroundResource(R.drawable.shape);
        password.setBackgroundResource(R.drawable.shape);

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    password.setInputType(129);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),
                        SignUpPage.class);
                startActivity(in);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailText=email.getText().toString();
                passText=password.getText().toString();
                System.out.println(emailText);
                System.out.println(passText);
                new ValidateLogin().execute();

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

    private class ValidateLogin extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
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
                HttpPost httpPost = new HttpPost("http://52.74.138.41:8080/biskart/api/login");
                json.put("username",emailText);
                json.put("password", passText);
                Log.d("request: ", "> " + json);
                StringEntity se = new StringEntity( json.toString());
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPost.setEntity(se);


                httpResponse = httpClient.execute(httpPost);
                httpEntity = httpResponse.getEntity();
                response = EntityUtils.toString(httpEntity);
                Log.d("Response: ", "> " + response);
                if(response!=null){
                    try{
                        JSONObject jsonObj = new JSONObject(response);

                        id = jsonObj.getInt("id");
                        name = jsonObj.getString("name");

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
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            if(id <= 0) {
                error.setText("User not valid");
            } else {
                error.setText("Valid!"+id);
                Intent in = new Intent(getApplicationContext(),
                       ProductListing.class);
                in.putExtra("name", name);
                startActivity(in);
            }

        }

    }
}
