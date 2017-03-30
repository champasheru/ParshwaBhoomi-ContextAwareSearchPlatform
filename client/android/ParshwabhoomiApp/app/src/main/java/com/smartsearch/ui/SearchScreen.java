package com.smartsearch.ui;



import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.smartsearch.data.Config;
import com.smartsearch.parse.CustomSearchResult;
import com.smartsearch.parse.SearchServiceResult;
import com.smartsearch.parse.SearchResult;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchScreen extends Activity implements OnClickListener,OnItemClickListener
{
	
	private EditText searchBox;
	private Button searchButton;
	private ListView searchResults;
	private static ArrayList<SearchResult> results;
	private String username;
	private EfficientAdapter adapter;
	public static Resources res;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		Bundle bun = getIntent().getExtras();

        username = bun.getString("user");
        
        System.out.println("User Name is "+username);
        
		setContentView(R.layout.searchscreen);
		
		res = this.getResources();
		
		initUI();

	}
	
	public void initUI()
	{
		
		searchBox = (EditText)findViewById(R.id.searchbox);
		
		searchBox.setWidth((int) (getWindowManager().getDefaultDisplay().getWidth()*.85));//
		
		searchButton = (Button)findViewById(R.id.searchbtn);
		
		searchResults = (ListView)findViewById(R.id.searchresults);
		searchResults.setAdapter(adapter=new EfficientAdapter(SearchScreen.this));
		
		searchButton.setOnClickListener(this);
		
		searchResults.setOnItemClickListener(this);
		
	}
	
	
	private static class EfficientAdapter extends BaseAdapter 
    {
        private LayoutInflater mInflater;
        private static int LIST_ITEM_TYPE_SEPARATOR = 0;
        private static int LIST_ITEM_TYPE_SERVICE_SEARCH_RESULT = 1;
        private static int LIST_ITEM_TYPE_CUSTOM_SEARCH_RESULT = 2;
        
        public EfficientAdapter(Context context) 
        {
            // Cache the LayoutInflate to avoid asking for a new one each time.
            mInflater = LayoutInflater.from(context);
            results=new ArrayList<SearchResult>();
        }

        /**
         * The number of items in the list is determined by the number of speeches
         * in our array.
         *
         * @see android.widget.ListAdapter#getCount()
         */
        public int getCount() 
        {
            return results.size();
        }

        /**
         * Since the data comes from an array, just returning the index is
         * sufficent to get at the data. If we were using a more complex data
         * structure, we would return whatever object represents one row in the
         * list.
         *
         * @see android.widget.ListAdapter#getItem(int)
         */
        public Object getItem(int position) 
        {
            return position;
        }

        /**
         * Use the array index as a unique id.
         *
         * @see android.widget.ListAdapter#getItemId(int)
         */
        public long getItemId(int position) 
        {
            return position;
        }
        
        @Override
		public int getItemViewType(int position) {
			// TODO Auto-generated method stub
        	String resultType = results.get(position).getType();
        	Log.i("SearchScreen", "resultType = "+resultType);
        	
        	if(resultType.equalsIgnoreCase(Config.getTypeCategory())){
        		return LIST_ITEM_TYPE_SEPARATOR;
        	}else if(resultType.equalsIgnoreCase(Config.getTypeVendor())){
        		return LIST_ITEM_TYPE_CUSTOM_SEARCH_RESULT;
        	}else{
        		return LIST_ITEM_TYPE_SERVICE_SEARCH_RESULT;
        	}
		}

		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return 3;
		}

		/**
         * Make a view to hold each row.
         *
         * @see android.widget.ListAdapter#getView(int, android.view.View,
         *      android.view.ViewGroup)
         */
        public View getView(int position, View convertView, ViewGroup parent) 
        {
            // A ViewHolder keeps references to children views to avoid unneccessary calls
            // to findViewById() on each row.
            ViewHolder holder = null;
            SearchResultViewHolder srViewHolder = null;
            CustomSearchResultViewHolder csrViewHolder = null;
            
            SearchResult searchResult = results.get(position);

            // When convertView is not null, we can reuse it directly, there is no need
            // to reinflate it. We only inflate a new View when the convertView supplied
            // by ListView is null.
            if (convertView == null) 
            {
            	if(searchResult.getType().equalsIgnoreCase(Config.getTypeVendor())){
            		convertView = mInflater.inflate(R.layout.custom_search_result, null);
            		csrViewHolder = new CustomSearchResultViewHolder();
            		csrViewHolder.title = (TextView) convertView.findViewById(R.id.title);
            		csrViewHolder.address = (TextView) convertView.findViewById(R.id.address);
            		csrViewHolder.phone = (TextView) convertView.findViewById(R.id.contact);
            		csrViewHolder.category = (TextView) convertView.findViewById(R.id.category);
            		csrViewHolder.offerings = (TextView) convertView.findViewById(R.id.offerings);
            		convertView.setTag(csrViewHolder);
            	}else if(searchResult.getType().equalsIgnoreCase(Config.getTypeCategory())){
            		convertView = mInflater.inflate(R.layout.result_category_layout, null);
            		holder = new ViewHolder();
            		holder.title = (TextView) convertView.findViewById(R.id.title);
            		convertView.setTag(holder);
            	}else{
            		convertView = mInflater.inflate(R.layout.service_search_result, null);
            		srViewHolder = new SearchResultViewHolder();
            		srViewHolder.title = (TextView) convertView.findViewById(R.id.title);
            		srViewHolder.summary = (TextView) convertView.findViewById(R.id.summary);
            		srViewHolder.url = (TextView) convertView.findViewById(R.id.url);
            		convertView.setTag(srViewHolder);
            	}
            }
            else
            {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
            	
            	if(searchResult.getType().equalsIgnoreCase(Config.getTypeVendor())){
            		csrViewHolder = (CustomSearchResultViewHolder)convertView.getTag(); 
            	}else if(searchResult.getType().equalsIgnoreCase(Config.getTypeCategory())){
            		holder = (ViewHolder) convertView.getTag();
            	}else{
            		srViewHolder = (SearchResultViewHolder)convertView.getTag();
            	}
            }
            
            Log.i("SmartSearch", "View Holder = "+convertView.getTag());
            
            if(convertView.getTag() instanceof ViewHolder){
            	holder.title.setText(searchResult.getTitle());
            }else if(convertView.getTag() instanceof CustomSearchResultViewHolder){
            	CustomSearchResult customSearchResult = (CustomSearchResult)searchResult;
            	csrViewHolder.title.setText(customSearchResult.getTitle());
            	csrViewHolder.address.setText(customSearchResult.getAddress());
            	csrViewHolder.phone.setText(customSearchResult.getContact());
            	csrViewHolder.category.setText(customSearchResult.getCategory());
            	csrViewHolder.offerings.setText(customSearchResult.getOfferings());
            }else{
            	SearchServiceResult searchServiceResult = (SearchServiceResult)searchResult;
            	srViewHolder.title.setText(searchServiceResult.getTitle());
            	srViewHolder.summary.setText(searchServiceResult.getSummary());
            	srViewHolder.url.setText(searchServiceResult.getUrl());
            }
            
            
            return convertView;
        }
        
        
        static class ViewHolder
        {
            TextView title;
        }
        
        static class SearchResultViewHolder
        {
            TextView title;
            TextView summary;
            TextView url;
        }
        
        static class CustomSearchResultViewHolder
        {
            TextView title;
            TextView address;
            TextView phone;
            TextView category;
            TextView offerings;
        }
        
    }


	public void onClick(View v) 
	{
		if(v == searchButton)
		{
			//Showing progress bar while authenticating
			final ProgressDialog dialog = ProgressDialog.show
			(this,"Please wait!!!!","Fetching results .......",true,false);
			if(!results.isEmpty()){
				results.clear();
			}
			new Thread()
			{
				public void run()
				{
					try 
					{
						Config.getSearchEngine().parseSearchResults(searchBox.getText().toString(), username, Config.getLat(), Config.getLon());
						
						results = Config.getSearchEngine().getSearchResults();
						
						runOnUiThread(new Runnable() 
						{
							public void run() 
							{
								//searchResults.setAdapter(new EfficientAdapter(SearchScreen.this));
								adapter.notifyDataSetChanged();
								dialog.dismiss();
								
								if(results.size()==0){
									showMessageDialog("No Search Results Found");
								}
							}
						});
						
						
						/*
						if(results.size()>0)
						{
							runOnUiThread(new Runnable() 
							{
								public void run() 
								{
									//searchResults.setAdapter(new EfficientAdapter(SearchScreen.this));
									
									dialog.dismiss();
	
								}
							});
						}
						else
						{
							dialog.dismiss();
						
							showMessageDialog("No Search Results Found");
						}	
						*/
						
						
					} 
					catch (SocketException e) 
					{
						dialog.dismiss();
						e.printStackTrace();
					}
					catch (SAXException e) 
					{
						dialog.dismiss();
						e.printStackTrace();
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
				}
			}.start();
		}
		
	}
	
	public void showMessageDialog(String s)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(s).setPositiveButton("Ok",
				new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface arg0, int arg1) 
			{
					arg0.dismiss();	
			}
		});
		builder.setTitle("Information");
		AlertDialog alert = builder.create();
		alert.show();

	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) 
	{
		SearchResult searchResult = results.get(position);
		if(!searchResult.getType().equalsIgnoreCase(Config.getTypeCategory()) && !searchResult.getType().equalsIgnoreCase(Config.getTypeVendor())){
			System.out.println("URL = "+((SearchServiceResult)searchResult).getUrl());
			Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(((SearchServiceResult)searchResult).getUrl()));
			startActivity(browserIntent);
		}
//		if(temp.getType().equals(Config.getTypeVendor()) && !temp.getType().equals(Config.getTypeHeader()))
//		{
//			SpannableStringBuilder mark = new SpannableStringBuilder("(C)");
//            final ForegroundColorSpan fcs1 = new ForegroundColorSpan(Color.RED); 
//            mark.setSpan(fcs1, 0, mark.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//           
//            String s = mark + "  " +temp.getDetails();
//            
//        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setMessage(s).setPositiveButton("Ok",
//					new DialogInterface.OnClickListener() 
//			{
//				public void onClick(DialogInterface arg0, int arg1) 
//				{
//						arg0.dismiss();	
//				}
//			});
//			builder.setTitle(Config.getResultDialogTitile());
//			AlertDialog alert = builder.create();
//			alert.show();
//
//		}
//		else if(!temp.getType().equals(Config.getTypeVendor()) && !temp.getType().equals(Config.getTypeHeader()))
//		{
//			SearchServiceResult res = (SearchServiceResult)temp;
//			System.out.println("URL = "+res.getUrl());
//			Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(res.getUrl()));
//			startActivity(browserIntent);
//		}
	}


}
