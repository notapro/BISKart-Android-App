package bis.kart.biskart;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
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
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ProductPage extends ActionBarActivity {

    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url = "http://52.74.138.41:8080/biskart/api/fullproductdetails";
    private static String imgURL;
    private String jsonStr;
    private int pid=5;

    // JSON Node names
    private static final String TAG_NAME = "name";
    private static final String TAG_CATEGORY ="category";
    private static final String TAG_SUBCATEGORY="subcategory";
    private static final String TAG_RATING ="rating";
    private static final String TAG_VERIFIED  = "is_verified";
    private static final String TAG_SELLERDETAILS  = "sellerList";
    private static final String TAG_IMAGEURL = "imgUrl";

    private static final String TAG_SELLERNAME = "name";
    private static final String TAG_SELLERID = "id";
    private static final String TAG_SELLERPRICE = "price";


    TableLayout table;
    TextView prodNameDisp, subCatDisp, priceDisp;
    ArrayList<String> sellerList;
    ArrayList<Double> priceList;
    ArrayList<Integer> sellerIdList;

    ImageView productImage;

	private Bitmap bmp;
	
	private int pid2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        
        productImage = (ImageView) findViewById(R.id.productpage_productimage1);
        Intent intent = getIntent();
        pid2 = intent.getIntExtra("pid",0);
        byte[] byteArray = getIntent().getByteArrayExtra("imgUrl");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        //priceDisp = (TextView) findViewById(R.id.productpage_txtproductprice);
        subCatDisp = (TextView) findViewById(R.id.productpage_txtproductname);
        prodNameDisp = (TextView) findViewById(R.id.productpage_txtbrandname);
        
        table = (TableLayout)findViewById(R.id.productpage_specTable);
        sellerList = new ArrayList();
        priceList = new ArrayList();
        sellerIdList = new ArrayList();

        new GetContacts().execute();
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


    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        private String name;
		private String subcategory;

		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ProductPage.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        // API Input     pid

        @Override
        protected Void doInBackground(Void... arg0) {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity;
            HttpResponse httpResponse;
            JSONObject json = new JSONObject();
            try {
                HttpPost httpPost = new HttpPost("http://52.74.138.41:8080/biskart/api/fullproductdetails");
                json.put("pid", pid2);
                StringEntity se = new StringEntity(json.toString());
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPost.setEntity(se);


                httpResponse = httpClient.execute(httpPost);
                httpEntity = httpResponse.getEntity();
                jsonStr = EntityUtils.toString(httpEntity);
                Log.d("asd",jsonStr);
            }
            catch(Exception e) {
                e.printStackTrace();
                Log.e("Error", "Cannot Estabilish Connection");
            }

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    name = jsonObj.getString(TAG_NAME);
                    String category = jsonObj.getString(TAG_CATEGORY);
                    subcategory = jsonObj.getString(TAG_SUBCATEGORY);
                    Double rating = jsonObj.getDouble(TAG_RATING);
                    Boolean is_verified = jsonObj.getBoolean(TAG_VERIFIED);
                    JSONArray  sellerDetails = jsonObj.getJSONArray(TAG_SELLERDETAILS);
                    String imgURL = jsonObj.getString(TAG_IMAGEURL);
                    //specs = jsonObj.get(TAG_IMAGEURL);
                    
                   
                    
                    Info.products[pid2] = new Product(pid2, name, bmp);
                    //Info.ppos++;


                    for (int i = 0; i < sellerDetails.length(); i++) {
                        JSONObject c = sellerDetails.getJSONObject(i);

                        String sellerName = c.getString(TAG_SELLERNAME);
                        int sellerId = c.getInt(TAG_SELLERID);
                        Double sellerPrice = c.getDouble(TAG_SELLERPRICE);

                        sellerList.add(sellerName);
                        priceList.add(sellerPrice);
                        sellerIdList.add(sellerId);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler11", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            
            productImage.setImageBitmap(bmp);
            prodNameDisp.setText(name);
            //subCatDisp.setText(subcategory);
            
            createTableRows();
            
            //new ImageLoader( productImage,"http://52.74.138.41:8080/biskart/img/5.jpg", 128, 128 ).execute();
        }

    }

    /*public class ImageLoader extends AsyncTask<URI, Integer, Bitmap> {
        private String imageUri;

        private ImageView imgView;
        private int preferredWidth = 80;
        private int preferredHeight = 80;

        public ImageLoader(ImageView imgView, String imageUri, int scaleWidth, int scaleHeight) {
            this.imageUri = imageUri;
            this.imgView = imgView;
            this.preferredWidth = scaleWidth;
            this.preferredHeight = scaleHeight;
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
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
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
            imgView.setImageBitmap(drawable);
        }
    }*/

    public void createTableRows()
    {
        final float scale = getResources().getDisplayMetrics().density;
        RadioGroup rg =(RadioGroup) findViewById(R.id.productpage_radioGroup);
        for(int i=0 ; i<sellerList.size() ; i++)
        {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT,5f);
            row.setLayoutParams(params);
            TextView tv1 = new TextView(this);
            TextView tv2 = new TextView(this);
            RadioButton rb = new RadioButton(this);

            tv1.setText(sellerList.get(i).toString());
            int pixels = (int) (0 * scale + 0.5f);
            tv1.setWidth(pixels);
            tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP , 16);
            tv1.setBackground(getResources().getDrawable(R.drawable.cell_shape));
            tv1.setTextColor(Color.parseColor("#000000"));
            TableRow.LayoutParams params1 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,3f);
            params1.setMargins(0,0,0,4);
            tv1.setLayoutParams(params1);
            pixels = (int) (5 * scale + 0.5f);
            tv1.setPadding(pixels, tv1.getPaddingTop(), tv1.getPaddingRight(), tv1.getPaddingBottom());
            pixels = (int) (30 * scale + 0.5f);
            tv1.setHeight(pixels);



            tv2.setText(priceList.get(i).toString());
            pixels = (int) (0 * scale + 0.5f);
            tv2.setWidth(pixels);
            tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP , 16);
            tv2.setBackground(getResources().getDrawable(R.drawable.cell_shape));
            tv2.setTextColor(Color.parseColor("#000000"));
            TableRow.LayoutParams params2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,2f);
            params2.setMargins(0,0,0,4);
            tv2.setLayoutParams(params2);
            pixels = (int) (5 * scale + 0.5f);
            tv2.setPadding(pixels, tv2.getPaddingTop(), tv2.getPaddingRight(), tv2.getPaddingBottom());
            pixels = (int) (30 * scale + 0.5f);
            tv2.setHeight(pixels);

            rb.setId(i);
            //rb.setWidth();
            rg.addView(rb);
            row.addView(tv1);
            row.addView(tv2);
            table.addView(row);
        }
        if(sellerList.size()!=0) {
            rg.check(0);
        }
    }

    public void addToCart(View view)
    {
        Intent intent = new Intent(ProductPage.this, Cart.class);

        RadioGroup rg = (RadioGroup) findViewById(R.id.productpage_radioGroup);
        int checked = rg.getCheckedRadioButtonId();
        int sellerid = sellerIdList.get(checked);
        double price = priceList.get(checked);
        Info.cart[Info.pos] = new CartObject(pid2, sellerid, price);
        Info.pos++;
        //Put to cart

        startActivity(intent);
    }
}

class ServiceHandler1 {

    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    public ServiceHandler1() {

    }

    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * */
    public String makeServiceCall(String url, int method) {
        return this.makeServiceCall(url, method, null);
    }

    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     * */
    public String makeServiceCall(String url, int method,
                                  List<NameValuePair> params) {
        try {
            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }

                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) {
                // appending params to url
                if (params != null) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);

                httpResponse = httpClient.execute(httpGet);

            }
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }
}

class SellerDetails{
    private String name;
    private int id;
    private double price;
}

class ProductPageDetails{
    private String name;
    private String category;
    private String subcategory;
    private double rating;
    private boolean is_verified;
    private ArrayList<SellerDetails> sellerList;
    private String imgUrl;
}

