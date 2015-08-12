package bis.kart.biskart;

import android.graphics.Bitmap;

public class CartObject {

	int pid;
	int sid;
	double price;
	
	public CartObject(int pid, int sid, double price){
		this.pid = pid;
		this.sid = sid;
		this.price  = price;
	}
}
