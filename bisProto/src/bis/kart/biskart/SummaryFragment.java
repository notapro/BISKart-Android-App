package bis.kart.biskart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SummaryFragment extends Fragment {

	String[] names;// , imgNames;
	Bitmap bmpArr[];
	Button b;
	double[] prices;
	public static double total=0;
	private ListView listview1;
	TextView tv;
	LazyAdapter mAdapter1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_summary, container,
				false);
		names = new String[Info.pos];
		bmpArr = new Bitmap[Info.pos];
		// imgNames = new String[Info.pos];
		prices = new double[Info.pos];

		listview1 = (ListView) rootView.findViewById(R.id.summarylistView);
		
		tv=(TextView) rootView.findViewById(R.id.totaltext);
		b = (Button) rootView.findViewById(R.id.modifycart);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent("android.intent.action.CART");
				startActivity(i);
			}
		});
		total = 0.0;
		for (int i = 0; i < Info.pos; i++) {
			names[i] = Info.products[Info.cart[i].pid].name;
			// imgNames[i] = Info.products[Info.cart[i].pid].imgName;
			prices[i] = Info.cart[i].price;
			bmpArr[i] = Info.products[Info.cart[i].pid].bmp;
			total+=prices[i];
		}
		tv.setText("Total: "+total);
		
		if (Info.pos == 0) {
			listview1.setVisibility(View.GONE);
			b.setClickable(false);
		} else {
			mAdapter1 = new LazyAdapter(getActivity(), bmpArr, names, prices);
			listview1.setAdapter(mAdapter1);
			listview1.setScrollingCacheEnabled(false);
			b.setClickable(true);
			registerForContextMenu(listview1);
		}

		return rootView;
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
