package bis.kart.biskart;

import android.graphics.Bitmap;

public class Product {
	int id;
	String name;
	Bitmap bmp;
	
	public Product(int id, String name, Bitmap bmp){
		this.id=id;
		this.name=name;
		this.bmp=bmp;
	}
}
