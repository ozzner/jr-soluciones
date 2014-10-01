/**
 * 
 */
package com.apprade.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.apprade.R;
import com.apprade.adapter.Adapter_ViewPage;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * @author renzo
 *
 */
public class Intro_Activity extends FragmentActivity {
	private ViewPager vp_Intro;
	private CirclePageIndicator pi_Intros;
	
	
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
		
		pi_Intros = (CirclePageIndicator)findViewById(R.id.indicator);
		pi_Intros.setViewPager(vp_Intro);

		
	}
	
	
	

}
