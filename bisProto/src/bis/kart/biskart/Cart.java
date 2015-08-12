package bis.kart.biskart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class Cart extends Activity implements View.OnClickListener {
	String[] names;//, imgNames;
	Bitmap bmpArr[];
	double[] prices;
	private Button b, c;
	private ListView listview1;
	TextView t;
	public LazyAdapter mAdapter1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);
		this.setFinishOnTouchOutside(false);
		names = new String[Info.pos];
		bmpArr = new Bitmap[Info.pos];
		//imgNames = new String[Info.pos];
		prices = new double[Info.pos];

		listview1 = (ListView) findViewById(R.id.cartlistView1);
		b = (Button) findViewById(R.id.checkoutBtn);
		b.setOnClickListener(this);
		t = (TextView) findViewById(R.id.carttextView1);
		c = (Button) findViewById(R.id.continueShopping);
		c.setOnClickListener(this);

		for (int i = 0; i < Info.pos; i++) {
			names[i] = Info.products[Info.cart[i].pid].name;
			//imgNames[i] = Info.products[Info.cart[i].pid].imgName;
			prices[i] = Info.cart[i].price;
			bmpArr[i]= Info.products[Info.cart[i].pid].bmp;
		}

		if (Info.pos == 0) {
			listview1.setVisibility(View.GONE);
			t.setVisibility(View.VISIBLE);
			b.setClickable(false);
		} else {
			mAdapter1 = new LazyAdapter(this, bmpArr, names, prices);
			listview1.setAdapter(mAdapter1);
			listview1.setScrollingCacheEnabled(false);
			b.setClickable(true);
			registerForContextMenu(listview1);
			SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(
					listview1,
					new SwipeDismissListViewTouchListener.DismissCallbacks() {
						@Override
						public boolean canDismiss(int position) {
							return true;
						}

						@Override
						public void onDismiss(ListView listView,
								int[] reverseSortedPositions) {
							for (int position : reverseSortedPositions) {
								for (int i = position; i < Info.pos - 1; i++) {
									Info.cart[i] = Info.cart[i + 1];
								}
								Info.pos--;
								if(Info.pos==0){
									listview1.setVisibility(View.GONE);
									t.setVisibility(View.VISIBLE);
									b.setClickable(false);
								}
								mAdapter1.remove(position);
							}
							mAdapter1.notifyDataSetChanged();
							// Intent i = new Intent(Cart.this, Cart.class);
							// startActivity(i);
							// overridePendingTransition(0, 0);
							// finish();
							// break;
						}
					});

			listview1.setOnTouchListener(touchListener);
			listview1.setOnScrollListener(touchListener.makeScrollListener());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cart, menu);
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
		if (arg0.getId() == R.id.checkoutBtn) {
			Intent i = new Intent("android.intent.action.DELIVERYDETAILSMAIN");
			startActivity(i);
		} else {
			finish();
		}
	}

	public class LazyAdapter extends BaseAdapter {

		private Activity activity;
		String[] names;
		String[] imgNames;
		Bitmap[] bmpArray;
		double[] prices;

		private LayoutInflater inflater = null;

		public LazyAdapter(Activity a, Bitmap[] bmpArray, String[] name,
				double[] price) {
			activity = a;
			this.bmpArray = bmpArray;
			names = name;
			prices = price;
			//imgNames = imgname;
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View vi = convertView;
			if (convertView == null)
				vi = inflater.inflate(R.layout.products_row, null);

			TextView name = (TextView) vi.findViewById(R.id.listproductname); // title
			TextView price = (TextView) vi.findViewById(R.id.listprice); // artist
																			// name
			ImageView iv = (ImageView) vi.findViewById(R.id.list_image); // duration

			// Setting all values in list view
			name.setText(names[position]);
			price.setText(prices[position] + "");
			iv.setImageBitmap(bmpArray[position]);
			//int resID = getResources().getIdentifier(imgNames[position],
			//		"drawable", getPackageName());
			//iv.setImageResource(resID);
			return vi;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public void remove(int positon) {
			names = new String[Info.pos];
			imgNames = new String[Info.pos];
			prices = new double[Info.pos];

			for (int i = 0; i < Info.pos; i++) {
				names[i] = Info.products[Info.cart[i].pid].name;
				prices[i] = Info.cart[i].price;
				bmpArray[i] = Info.products[Info.cart[i].pid].bmp;
			}
		}
	}

}
