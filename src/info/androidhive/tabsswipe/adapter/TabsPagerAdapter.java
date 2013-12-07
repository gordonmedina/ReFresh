package info.androidhive.tabsswipe.adapter;

import info.androidhive.tabsswipe.StorageFragment;
import info.androidhive.tabsswipe.CookFragment;
import info.androidhive.tabsswipe.GroceryFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public String[] tabs = { "What's in here?", "What's cooking?", "What's new?" };
	
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	@Override
    public CharSequence getPageTitle(int position) {
    	return tabs[position];
    }
	
	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((View) view);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return new StorageFragment();
		case 1:
			return new CookFragment();
		case 2:
			return new GroceryFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return tabs.length;
	}

}
