package org.cs.parshwabhoomiapp.ui;


import java.util.List;

import org.cs.parshwabhoomiapp.R;

import org.cs.parshwabhoomiapp.model.SearchResult;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class SearchActivity extends Activity implements OnClickListener {
	public static final String TAG = SearchActivity.class.getSimpleName();
	private ProgressDialog dialog;
	private ListAdapter adapter;
	private EditText searchBox;
	private Button talkButton;
	private static final int SPEECH_REQUEST_CODE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchscreen);
		initUI();
	}
	
	public void initUI() {
		searchBox = findViewById(R.id.searchbox);
		searchBox.setWidth((int) (getWindowManager().getDefaultDisplay().getWidth()*.85));//
		
		Button searchButton = findViewById(R.id.searchbtn);
		searchButton.setOnClickListener(this);

		talkButton = findViewById(R.id.micbtn);
		talkButton.setOnClickListener(this);

		ListView listView = findViewById(R.id.searchresults);
		adapter = new ListAdapter(this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(adapter);
	}

	public void onClick(View v) {
		if (v == talkButton){
			displaySpeechRecognizer();
		}else{
			//Showing progress bar while authenticating
			dialog = ProgressDialog.show(
					this,
					"Please wait!",
					"Fetching search results...",
					true,
					false);
			String searchQuery = searchBox.getText().toString().trim();
			adapter.loadSearchResults(searchQuery);
		}
	}


	public void onSearchResultsRendered(List<SearchResult> results, Exception exception){
		dialog.dismiss();
		if(exception != null){
			showMessageDialog(exception.getMessage());
		}else if(results.size() == 0){
			showMessageDialog("No search results found!");
		}
	}


	public void showMessageDialog(String s) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(s).setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
					arg0.dismiss();	
			}
		});
		builder.setTitle("");
		AlertDialog alert = builder.create();
		alert.show();
	}


	// Create an intent that can start the Speech Recognizer activity
	private void displaySpeechRecognizer() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		// Start the activity, the intent will be populated with the speech text
		startActivityForResult(intent, SPEECH_REQUEST_CODE);
	}


	// This callback is invoked when the Speech Recognizer returns.
	// This is where you process the intent and extract the speech text from the intent.
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
									Intent data) {
		if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
			List<String> results = data.getStringArrayListExtra(
					RecognizerIntent.EXTRA_RESULTS);
			Log.i(TAG, "Recognized terms="+results);
			String spokenText = results.get(0);
			Log.i(TAG, "1st term="+results.get(0));
			// Do something with spokenText
			searchBox.setText(results.toString());
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
