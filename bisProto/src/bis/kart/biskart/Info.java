package bis.kart.biskart;

public class Info {
	
	static Product products[];
	static CartObject cart[];
	static int pos,ppos;
	
	static {
		pos=ppos=0;
		products = new Product[15];
		cart = new CartObject[40];
	}
}
