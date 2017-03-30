package com.smartsearch.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash);
		
		displayLoginScreen();
		
	}
	
	/* Display Login Screen. */
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
					
					i.setClassName(Splash.this, com.smartsearch.ui.Login.class.getName());
					
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
