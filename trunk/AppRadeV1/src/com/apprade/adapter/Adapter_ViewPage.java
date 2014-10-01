/**
 * 
 */
package com.apprade.adapter;

import com.apprade.activity.Fragment_Intro_1;
import com.apprade.activity.Fragment_Intro_2;

import android.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author renzo
 *
 */

public class Adapter_ViewPage extends FragmentPagerAdapter {
	private static final int TAG_PAGINAS = 2;

	public Adapter_ViewPage(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {

		switch (arg0) {

		case 0:
			return new Fragment_Intro_1();
		case 1:
			return new Fragment_Intro_2();
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
