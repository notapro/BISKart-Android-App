package bis.kart.biskart.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import bis.kart.biskart.DeliveryDetailsFragment;
import bis.kart.biskart.PaymentFragment;
import bis.kart.biskart.SummaryFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new DeliveryDetailsFragment();
		case 1:
			// Games fragment activity
			return new SummaryFragment();
		case 2:
			// Movies fragment activity
			return new PaymentFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}
}
