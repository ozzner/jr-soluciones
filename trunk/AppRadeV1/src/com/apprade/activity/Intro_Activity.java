/**
 * 
 */
package com.apprade.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.apprade.R;
import com.apprade.adapter.Adapter_ViewPage;

/**
 * @author renzo
 *
 */
public class Intro_Activity extends FragmentActivity {
	ViewPager vp_Intro;
	
	public Intro_Activity() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_intro);
		vp_Intro = (ViewPager) findViewById(R.id.vp_intro);
		
		Adapter_ViewPage vpAdapter = new Adapter_ViewPage(getSupportFragmentManager());
		vp_Intro.setAdapter(vpAdapter);
	}
	
	
	

}
