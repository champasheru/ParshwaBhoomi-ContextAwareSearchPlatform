package org.cs.parshwabhoomiapp.ui;


import java.util.List;

import org.cs.parshwabhoomiapp.R;

import org.cs.parshwabhoomiapp.model.SearchResult;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

		ListView listView = findViewById(R.id.searchresults);
		adapter = new ListAdapter(this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(adapter);
	}

	public void onClick(View v) {
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
}
