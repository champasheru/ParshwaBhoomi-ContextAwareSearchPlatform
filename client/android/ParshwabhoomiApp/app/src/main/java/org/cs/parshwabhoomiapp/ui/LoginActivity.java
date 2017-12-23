package org.cs.parshwabhoomiapp.ui;

import org.cs.parshwabhoomiapp.PBApplication;
import org.cs.parshwabhoomiapp.R;
import org.cs.parshwabhoomiapp.client.framework.TaskEventListener;
import org.cs.parshwabhoomiapp.client.pb.PBClientImpl;
import org.cs.parshwabhoomiapp.client.pb.tasks.LoginTask;
import org.cs.parshwabhoomiapp.model.UserCredential;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity  implements OnClickListener {
	public static final String TAG = LoginActivity.class.getSimpleName();
	private Button loginButton;
	private EditText username;
	private EditText password;
	private EditText serverURL;
	private Display myDisplay;
	private UserCredential userCredential;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
        initUI();
    }
    
    
    public void initUI()
    {
    	myDisplay = getWindowManager().getDefaultDisplay();
		username = (EditText)findViewById(R.id.username);
		password = (EditText)findViewById(R.id.password);
		serverURL = (EditText)findViewById(R.id.serverURL);

    	//username.setWidth((int) ((int)myDisplay.getWidth()*0.9));

    	//password.setWidth((int) ((int)myDisplay.getWidth()*0.9));

    	//LoginActivity Button
    	loginButton = (Button)findViewById(R.id.loginbtn);
    	loginButton.setOnClickListener(this);
    }

	public void onClick(View v) {
		userCredential = new UserCredential(username.getText().toString().trim(), password.getText().toString().trim());
		Log.i(TAG, "username="+userCredential.getUsername()+"password="+userCredential.getPassword());
		PBApplication.getInstance().getClient().setBaseUrl(serverURL.getText().toString().trim());

		final ProgressDialog dialog = ProgressDialog.show(
				LoginActivity.this,
				"Please wait!",
				"Logging in...",
				true,
				false);

		final PBClientImpl pbClient = PBApplication.getInstance().getClient();
		LoginTask loginTask = pbClient.getLoginTask(userCredential);
		loginTask.execute(new TaskEventListener() {
			@Override
			public void onFinish(Object result, Exception exception) {
				if(exception != null){
					dialog.dismiss();
					showAuthenticationDialog(exception.getMessage());
				}else{
					boolean status = (Boolean) result;
					if(status){
						dialog.dismiss();
						Intent i = new Intent();
						i.setClassName(LoginActivity.this, SearchActivity.class.getName());
						startActivityForResult(i, 0);
					}else{
						//TODO: seems redundant; 401 will be handled by the exception case
						dialog.dismiss();
						showAuthenticationDialog("Invalid username and/or passowrd!");
					}
				}
			}
		});
	}
	
	 public void showAuthenticationDialog(final String s)
	    {
	    	runOnUiThread(new Runnable() 
	    	{
				public void run() 
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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