package bis.kart.biskart;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ProductListing extends ActionBarActivity implements View.OnClickListener{


    TextView welcome;
    int laptop1, laptop2, laptop3, TV1, TV2, TV3, Mobile1, Mobile2, Mobile3;
    ImageButton laptopOne, laptopTwo, laptopThree, TVOne, TVTwo, TVThree, MobileOne, MobileTwo, MobileThree;
    TextView laptopOneName, laptopTwoName, laptopThreeName, TVOneName, TVTwoName, TVThreeName, MobileOneName, MobileTwoName, MobileThreeName;
    String subcat, response,name,imgURL;
    String[] names= new String[9];
    String imgURLs[] = new String[9];
    int pids[] = new  int[9];

    int pid;
    JSONObject jsonObj[];
    JSONArray prods = null;
    private ProgressDialog pDialog;
    Bitmap bm[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_listing_page);

       // Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Arial.otf");
        welcome = (TextView) findViewById(R.id.productUserWelcome);
        //welcome.setTypeface(tf);
        bm = new Bitmap[10];
        laptopOne = (ImageButton) findViewById(R.id.productLaptopImageOne);
        laptopTwo = (ImageButton) findViewById(R.id.productLaptopImageTwo);
        laptopThree = (ImageButton) findViewById(R.id.productLaptopImageThree);
        TVOne = (ImageButton) findViewById(R.id.productTVImageOne);
        TVTwo = (ImageButton) findViewById(R.id.productTVImageTwo);
        TVThree = (ImageButton) findViewById(R.id.productTVImageThree);
        MobileOne = (ImageButton) findViewById(R.id.productMobileImageOne);
        MobileTwo = (ImageButton) findViewById(R.id.productMobileImageTwo);
        MobileThree = (ImageButton) findViewById(R.id.productMobileImageThree);

        laptopOneName = (TextView) findViewById(R.id.productLaptopNameOne);
        laptopTwoName = (TextView) findViewById(R.id.productLaptopNameTwo);
        laptopThreeName = (TextView) findViewById(R.id.productLaptopNameThree);
        TVOneName = (TextView) findViewById(R.id.productTVNameOne);
        TVTwoName = (TextView) findViewById(R.id.productTVNameTwo);
        TVThreeName = (TextView) findViewById(R.id.productTVNameThree);
        MobileOneName = (TextView) findViewById(R.id.productMobileNameOne);
        MobileTwoName = (TextView) findViewById(R.id.productMobileNameTwo);
        MobileThreeName = (TextView) findViewById(R.id.productMobileNameThree);

        laptopOne.setOnClickListener(this);
        laptopTwo.setOnClickListener(this);
        laptopThree.setOnClickListener(this);
        MobileOne.setOnClickListener(this);
        MobileTwo.setOnClickListener(this);
        MobileThree.setOnClickListener(this);
        TVOne.setOnClickListener(this);
        TVTwo.setOnClickListener(this);
        TVThree.setOnClickListener(this);

        new Listing("Laptops",0).execute();
        new Listing("TV",3).execute();
        new Listing("Mobiles",6).execute();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        if (name != null) {
            welcome.setText("Hello " + name);
        } else {
            welcome.setText("Welcome to BISKART!!!");
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_login:
                // search action
                Intent in = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(in);
                return true;
            case R.id.action_cart:
            	Intent in1 = new Intent(getApplicationContext(),
                        Cart.class);
                startActivity(in1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.productLaptopImageOne){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm[0].compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            Intent in = new Intent(getApplicationContext(),
                    ProductPage.class);
            in.putExtra("pid", pids[0]);
            in.putExtra("imgUrl",byteArray);
            startActivity(in);
        }
        else if(v.getId()==R.id.productLaptopImageTwo){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm[1].compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            Intent in = new Intent(getApplicationContext(),
                    ProductPage.class);
            in.putExtra("pid", pids[1]);
            in.putExtra("imgUrl",byteArray);
            startActivity(in);
        }
        else if(v.getId()==R.id.productLaptopImageThree){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm[2].compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            Intent in = new Intent(getApplicationContext(),
                    ProductPage.class);
            in.putExtra("pid", pids[2]);
            in.putExtra("imgUrl",byteArray);
            startActivity(in);
        }
        else if(v.getId()==R.id.productTVImageOne){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm[3].compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            Intent in = new Intent(getApplicationContext(),
                    ProductPage.class);
            in.putExtra("pid", pids[3]);
            in.putExtra("imgUrl",byteArray);
            startActivity(in);

        }
        else if(v.getId()==R.id.productTVImageTwo){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm[4].compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            Intent in = new Intent(getApplicationContext(),
                    ProductPage.class);
            in.putExtra("pid", pids[4]);
            in.putExtra("imgUrl",byteArray);
            startActivity(in);

        }
        else if(v.getId()==R.id.productTVImageThree){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm[5].compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            Intent in = new Intent(getApplicationContext(),
                    ProductPage.class);
            in.putExtra("pid", pids[5]);
            in.putExtra("imgUrl",byteArray);
            startActivity(in);
        }
        else if(v.getId()==R.id.productMobileImageOne){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm[6].compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            Intent in = new Intent(getApplicationContext(),
                    ProductPage.class);
            in.putExtra("pid", pids[6]);
            in.putExtra("imgUrl",byteArray);
            startActivity(in);

        }
        else if(v.getId()==R.id.productMobileImageTwo){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm[7].compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            Intent in = new Intent(getApplicationContext(),
                    ProductPage.class);
            in.putExtra("pid", pids[7]);
            in.putExtra("imgUrl",byteArray);
            startActivity(in);

        }
        else if(v.getId()==R.id.productMobileImageThree){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm[8].compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            Intent in = new Intent(getApplicationContext(),
                    ProductPage.class);
            in.putExtra("pid", pids[8]);
            in.putExtra("imgUrl",byteArray);
            startActivity(in);

        }
    }

    private class Listing extends AsyncTask<Void, Void, Void> {

        String subcat;
        int k;

        public Listing(String cat,int num){
            subcat=cat;
            k=num;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            if(k==0) {
                pDialog = new ProgressDialog(ProductListing.this);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity;
            HttpResponse httpResponse;
            JSONObject json = new JSONObject();

            try {
                HttpPost httpPost = new HttpPost("http://52.74.138.41:8080/biskart/api/products");
                json.put("subcat",subcat);
                Log.d("Request: ", "> " + json);
                StringEntity se = new StringEntity(json.toString());
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPost.setEntity(se);

                httpResponse = httpClient.execute(httpPost);
                httpEntity = httpResponse.getEntity();
                response = EntityUtils.toString(httpEntity);
                Log.d("Response: ", "> " + response + "hw");

                if (response != null) {
                    try {
                        JSONArray obj = new JSONArray(response);
                        prods = obj;
                        for(int i=0;i<obj.length();i++){
                            JSONObject list = prods.getJSONObject(i);
                            pids[i+k]=list.getInt("pid");
                            System.out.println(pids[i+k]);
                            names[i+k]=list.getString("name");
                           System.out.println(names[i+k]);
                            imgURLs[i+k]=list.getString("imgUrl");
                            System.out.println(imgURLs[i+k]);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("ServiceHandler", "Couldn't get any data from the url");
                }
            } catch (Exception e) {
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
            if(response!=null){
                if(subcat=="Laptops"){
                    laptopOneName.setText(names[k]);
                    laptopTwoName.setText(names[k+1]);
                    laptopThreeName.setText(names[k+2]);
                    new ImageLoader( laptopOne,"http://52.74.138.41:8080/biskart/img/"+imgURLs[k], 128, 128,0).execute();
                    new ImageLoader( laptopTwo,"http://52.74.138.41:8080/biskart/img/"+imgURLs[k+1], 128, 128,1).execute();
                    new ImageLoader( laptopThree,"http://52.74.138.41:8080/biskart/img/"+imgURLs[k+2], 128, 12,2 ).execute();
                }
                else if(subcat=="TV"){
                    TVOneName.setText(names[k]);
                    TVTwoName.setText(names[k+1]);
                   TVThreeName.setText(names[k+2]);
                    new ImageLoader( TVOne,"http://52.74.138.41:8080/biskart/img/"+imgURLs[k], 128, 128,3).execute();
                    new ImageLoader( TVTwo,"http://52.74.138.41:8080/biskart/img/"+imgURLs[k+1], 128, 128,4 ).execute();
                    new ImageLoader( TVThree,"http://52.74.138.41:8080/biskart/img/"+imgURLs[k+2], 128, 128,5 ).execute();
                }
                else{
                    MobileOneName.setText(names[k]);
                    MobileTwoName.setText(names[k+1]);
                    MobileThreeName.setText(names[k+2]);
                    new ImageLoader( MobileOne,"http://52.74.138.41:8080/biskart/img/"+imgURLs[k], 128, 128,6 ).execute();
                    new ImageLoader( MobileTwo,"http://52.74.138.41:8080/biskart/img/"+imgURLs[k+1], 128, 128,7 ).execute();
                    new ImageLoader( MobileThree,"http://52.74.138.41:8080/biskart/img/"+imgURLs[k+2], 128, 128,8 ).execute();
                }


            }

        }

    }

    public class ImageLoader extends AsyncTask<URI, Integer, Bitmap> {
        private String imageUri;
        Bitmap bitmap;
        private ImageButton imgButton;
        private int preferredWidth = 80;
        private int preferredHeight = 80;
        int i;

        public ImageLoader(ImageButton imgButton, String imageUri, int scaleWidth, int scaleHeight,int i) {
            this.imageUri = imageUri;
            this.imgButton = imgButton;
            this.preferredWidth = scaleWidth;
            this.preferredHeight = scaleHeight;
            this.i=i;
        }

        public Bitmap doInBackground(URI... params) {
            if (imageUri == null) return null;
            // String url = imageUri.toString();
            //if( url.length() == 0 ) return null;
            HttpPost httpPost = new HttpPost(imageUri);
            DefaultHttpClient client = new DefaultHttpClient();
            try {
                HttpResponse response = client.execute(httpPost);
                InputStream is = new BufferedInputStream(response.getEntity().getContent());
                try {
                    bitmap = BitmapFactory.decodeStream(is);
                    if (preferredWidth > 0 && preferredHeight > 0 && bitmap.getWidth() > preferredWidth && bitmap.getHeight() > preferredHeight) {
                        return Bitmap.createScaledBitmap(bitmap, preferredWidth, preferredHeight, false);
                    } else {
                        return bitmap;
                    }
                } finally {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Error", "Cannot Estabilish Connection 1");
            }
            return null;
        }


    public void onPostExecute( Bitmap drawable ) {
    	imgButton.setImageBitmap(drawable);
    	bm[i]=Bitmap.createBitmap(drawable);
    }
}

}
