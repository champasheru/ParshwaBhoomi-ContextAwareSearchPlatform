package org.cs.parshwabhoomiapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.cs.parshwabhoomiapp.R;

public class SplashActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash);
		
		displayLoginScreen();
		
	}
	
	/* Display LoginActivity Screen. */
	public void displayLoginScreen()
	{
		new Thread()
		{
			public  void run()
			{
				try 
				{
					this.sleep(5000);
					
					Intent i = new Intent();
					
					i.setClassName(SplashActivity.this, LoginActivity.class.getName());
					
					startActivityForResult(i, 0);
					
					finish();
				}
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		}.start();
	
	}
}
