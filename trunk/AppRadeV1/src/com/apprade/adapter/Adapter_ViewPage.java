/**
 * 
 */
package com.apprade.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author renzo
 *
 */

public class Adapter_ViewPage extends FragmentPagerAdapter  {
	private static final int TAG_PAGINAS = 3;


	public Adapter_ViewPage(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		
		switch (arg0) {
		
		case 0:
			
		case 1:
			
		case 2:

		default:
			break;
		}
		return null;
	}

	@Override
	public int getCount() {
		return TAG_PAGINAS;
	}

}
