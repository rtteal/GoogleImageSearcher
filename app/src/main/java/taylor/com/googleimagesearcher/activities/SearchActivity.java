package taylor.com.googleimagesearcher.activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import taylor.com.googleimagesearcher.R;
import taylor.com.googleimagesearcher.adapters.ImageResultsAdapter;
import taylor.com.googleimagesearcher.fragments.EditSettingsFragment;
import taylor.com.googleimagesearcher.listeners.EndlessScrollListener;
import taylor.com.googleimagesearcher.models.SearchResult;
import taylor.com.googleimagesearcher.models.Settings;


public class SearchActivity extends ActionBarActivity implements EditSettingsFragment.EditSettingsFragmentListener {
    private GridView gvResults;
    private List<SearchResult> searchResults = new ArrayList<>();
    private ImageResultsAdapter aImageResults;
    private static final String SEARCH_URL_PART1 = "https://ajax.googleapis.com/ajax/services/search/images";
           // "?v=1.0&q=";
    private static final String SEARCH_URL_PART2 = "&rsz=8";
    private AsyncHttpClient client = new AsyncHttpClient();
    private RequestParams params;
    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        params = new RequestParams();
        params.add("v", "1.0");
        params.add("rsz", "8");
        setContentView(R.layout.activity_search);
        setupViews();
        aImageResults = new ImageResultsAdapter(this, searchResults);
        gvResults.setAdapter(aImageResults);
    }

    private void setupViews() {
        gvResults = (GridView) findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                SearchResult result = searchResults.get(position);
                i.putExtra("url", result.url);
                startActivity(i);
            }
        });
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                params.remove("q");
                params.add("q", query);
                executeSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void executeSearch(){
        Log.d("DEBUG", "executing search with params: " + params.toString());
        client.get(SEARCH_URL_PART1, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (!response.isNull("responseData")) {
                        JSONArray results = response.getJSONObject("responseData").getJSONArray("results");
                        aImageResults.addAll(SearchResult.fromJsonArray(results));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("ERROR", "Google API call failed. " + responseString + " " + throwable.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FragmentManager fm = getSupportFragmentManager();
            EditSettingsFragment editSettings = EditSettingsFragment.newInstance(settings);
            editSettings.show(fm, "fragment_edit_settings");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinishEditDialog(Settings settings) {
        this.settings = settings;
        Log.d("DEBUG", settings.toString());
        params.remove("imgcolor");
        params.remove("imgtype");
        params.remove("imgsz");
        params.remove("as_sitesearch");
        if (!settings.color.equals("all")) params.add("imgcolor", settings.color);
        if (!settings.type.equals("all")) params.add("imgtype", settings.type);
        if (!settings.size.equals("all")) params.add("imgsz", settings.size);
        if (!(null == settings.site || settings.site.equals(""))) params.add("as_sitesearch", settings.site);
    }

    // Append more data into the adapter
    private void customLoadMoreDataFromApi(int offset) {
        Log.d("DEBUG", "offset: " + offset);
        if (offset == 2) aImageResults.clear();
        offset *= 8;
        Log.d("DEBUG", "offset *= 8: " + offset);
        params.remove("start");
        params.add("start", "" + offset);
        executeSearch();
    }
}
