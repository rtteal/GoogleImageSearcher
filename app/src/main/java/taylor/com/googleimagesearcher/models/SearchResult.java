package taylor.com.googleimagesearcher.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rtteal on 2/10/2015.
 */
public class SearchResult {
    public final String url, thumbUrl, title;

    private SearchResult(String url, String thumbUrl, String title){
        this.url = url;
        this.thumbUrl = thumbUrl;
        this.title = title;
        Log.d("DEBUG", this.toString());
    }

    public static SearchResult fromJson(JSONObject json){
        try {
            String url = json.getString("url");
            String tbUrl = json.getString("tbUrl");
            String title = json.getString("title");
            return new SearchResult(url, tbUrl, title);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<SearchResult> fromJsonArray(JSONArray array){
        List<SearchResult> results = new ArrayList<>();
        for (int i = 0; i < array.length(); i++){
            try {
                SearchResult result = fromJson(array.getJSONObject(i));
                if (null != result) results.add(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  results;
    }

    @Override
    public String toString(){
        return String.format("{url: %s, thumbUrl: %s, title: %s}", url, thumbUrl, title);
    }
}
