package org.cs.parshwabhoomiapp.ui;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.cs.parshwabhoomiapp.R;
import org.xml.sax.SAXException;

import org.cs.parshwabhoomiapp.data.Config;
import org.cs.parshwabhoomiapp.parse.LoginResult;
import org.cs.parshwabhoomiapp.util.SmartSearchLocationListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity  implements OnClickListener
{
	private Button loginBtn;
	private EditText username;
	private EditText password;
	private EditText serverURL;
	private Display myDisplay;
	private LoginResult loginResult;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.loginscreen);
        
        Config.setContext(getApplicationContext());
        
        SmartSearchLocationListener listener = new SmartSearchLocationListener();
        
        listener.registerLocationListener();
        
        Config.setListener(listener);
        
        initUI();
    }
    
    
    public void initUI()
    {
    	myDisplay = getWindowManager().getDefaultDisplay();
    	
    	
    	username = (EditText)findViewById(R.id.username);
    	//username.setWidth((int) ((int)myDisplay.getWidth()*0.9));
    	
    	password =(EditText)findViewById(R.id.password);
    	//password.setWidth((int) ((int)myDisplay.getWidth()*0.9));
    	
    	serverURL =(EditText)findViewById(R.id.serverURL);
    	
    	//Login Button
    	loginBtn = (Button)findViewById(R.id.loginbtn);
    	loginBtn.setOnClickListener(this);

    }

	public void onClick(View v) 
	{
		//Showing progress bar while authenticating
		final ProgressDialog dialog = ProgressDialog.show
		(Login.this,"Please wait!!!!","Logging in .......",true,false);
		
		new Thread()
		{
			public void run()
			{
				try
				{
					Config.getSearchEngine().parseLoginResult(username.getText().toString().trim(), password.getText().toString().trim(), serverURL.getText().toString().trim());
					loginResult = Config.getSearchEngine().getLoginResult();
					if(loginResult.getStatus().equalsIgnoreCase("True"))
					{
						System.out.println("Result = "+loginResult.getStatus());
						dialog.dismiss();
						Intent i = new Intent();
						i.putExtra("user", username.getText().toString());
						i.setClassName(Login.this, org.cs.parshwabhoomiapp.ui.SearchScreen.class.getName());
						startActivityForResult(i, 0);
					}
					else if(loginResult.getStatus().equalsIgnoreCase("False"))
					{
						dialog.dismiss();
						showAuthenticationDialog(loginResult.getErrorText());
					}
					
				} 
				catch (IOException e) 
				{
					dialog.dismiss();
					e.printStackTrace();
				}
				catch (ParserConfigurationException e)
				{
					dialog.dismiss();
					e.printStackTrace();
				}
				catch (SAXException e) 
				{
					dialog.dismiss();
					e.printStackTrace();
				}
			}
		}.start();
		
	}
	
	 public void showAuthenticationDialog(final String s)
	    {
	    	runOnUiThread(new Runnable() 
	    	{
				public void run() 
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
					builder.setMessage(s).setPositiveButton("Ok",
							new DialogInterface.OnClickListener() 
					{
						public void onClick(DialogInterface arg0, int arg1) 
						{
								arg0.dismiss();	
						}
					});
					builder.setTitle("Authenticaion");
					AlertDialog alert = builder.create();
					alert.show();
			    }

	    	});
	    }
}