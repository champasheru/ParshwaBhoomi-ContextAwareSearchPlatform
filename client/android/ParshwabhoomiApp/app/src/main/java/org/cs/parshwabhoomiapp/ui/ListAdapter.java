package org.cs.parshwabhoomiapp.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.cs.parshwabhoomiapp.PBApplication;
import org.cs.parshwabhoomiapp.R;
import org.cs.parshwabhoomiapp.client.framework.TaskEventListener;
import org.cs.parshwabhoomiapp.client.pb.PBClientImpl;
import org.cs.parshwabhoomiapp.client.pb.tasks.GetSearchResultsTask;
import org.cs.parshwabhoomiapp.location.LocationService;
import org.cs.parshwabhoomiapp.model.SearchContext;
import org.cs.parshwabhoomiapp.model.SearchResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurabh on 23/12/17.
 */

public class ListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{
    public static final String TAG = ListAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private List<SearchResult> results;
    private Context context;

    private static class SearchResultViewHolder {
        TextView title;
        TextView tagline;
        TextView snippet;
        TextView businessCategory;
        TextView type;
        TextView provider;
        TextView displayLink;
    };

    public ListAdapter(Context context) {
        // Cache the LayoutInflate to avoid asking for a new one each time.
        results = new ArrayList<SearchResult>();
        this.context = context;
        mInflater = LayoutInflater.from(this.context);
    }


    public void loadSearchResults(String searchQuery){
        PBClientImpl client = PBApplication.getInstance().getClient();

        SearchContext searchContext = new SearchContext();
        searchContext.setUsername(client.getUserCredential().getUsername());
        searchContext.setQuery(searchQuery);
        searchContext.setLatitude((float) LocationService.getLatitude());
        searchContext.setLongitude((float) LocationService.getLongitude());

        GetSearchResultsTask searchResultsTask = client.getSearchResultsTask(searchContext);
        searchResultsTask.execute(new TaskEventListener() {
            @Override
            public void onFinish(Object result, Exception exception) {
                Log.i(TAG, "SearchActivity result response="+result+", exception"+exception);
                if(exception == null && result != null){
                    results = (List<SearchResult>)result;
                    notifyDataSetChanged();
                }
                ((SearchActivity)context).onSearchResultsRendered((List<SearchResult>)result, exception);
            }
        });
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
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    /**
     * Make a view to hold each row.
     *
     * @see android.widget.ListAdapter#getView(int, android.view.View,
     *      android.view.ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        // A ViewHolder keeps references to children views to avoid unneccessary calls
        // to findViewById() on each row.
        SearchResultViewHolder srViewHolder = null;

        SearchResult searchResult = results.get(position);

        // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.search_result_layout, null);
            srViewHolder = new SearchResultViewHolder();
            srViewHolder.title = (TextView) convertView.findViewById(R.id.title);
            srViewHolder.tagline = (TextView) convertView.findViewById(R.id.tagline);
            srViewHolder.snippet = (TextView) convertView.findViewById(R.id.snippet);
            srViewHolder.businessCategory = (TextView) convertView.findViewById(R.id.businessCategory);
            srViewHolder.type = (TextView) convertView.findViewById(R.id.type);
            srViewHolder.provider = (TextView) convertView.findViewById(R.id.provider);
            srViewHolder.displayLink = (TextView) convertView.findViewById(R.id.displayLink);
            convertView.setTag(srViewHolder);
        } else {
            // Get the ViewHolder back to get fast access to the containing widgets
            srViewHolder = (SearchResultViewHolder)convertView.getTag();
        }

        Log.i(TAG, "View holder="+srViewHolder);

        srViewHolder.title.setText(searchResult.getTitle());
        srViewHolder.tagline.setText(searchResult.getTagline());
        srViewHolder.snippet.setText(searchResult.getSnippet());
        srViewHolder.businessCategory.setText(searchResult.getBusinessCategory());
        srViewHolder.type.setText(searchResult.getType());
        srViewHolder.provider.setText(searchResult.getProvider());
        srViewHolder.displayLink.setText(searchResult.getDisplayLink());
        if(searchResult.getType().contains("TYPE_PB") || searchResult.getProvider().contains("PARSHWABHOOMI")){
            convertView.setBackgroundColor(Color.parseColor("#F9F9F9"));
        }else{
            convertView.setBackgroundColor(Color.WHITE);
        }

        return convertView;
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        SearchResult searchResult = results.get(position);
        String url = "";
        if(searchResult.getType().contains("TYPE_PB")){
            url = searchResult.getDisplayLink();
        }else {
            url = searchResult.getLink();
        }
        Log.i(TAG, "URL="+url);

        Intent i = new Intent();
        i.putExtra("url", url);
        i.setClassName(context, WebViewActivity.class.getName());
        Log.i(TAG, "Starting web view activity...");
        context.startActivity(i);
//        Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse((searchResult).getDisplayLink()));
//        context.startActivity(browserIntent);
    }
}
