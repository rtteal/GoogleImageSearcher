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
    public final int imageHeight, imageWidth;

    private SearchResult(String url, String thumbUrl, String title, int imageHeight, int imageWidth){
        this.url = url;
        this.thumbUrl = thumbUrl;
        this.title = title;
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
        Log.d("DEBUG", this.toString());
    }

    public static SearchResult fromJson(JSONObject json){
        try {
            String url = json.getString("url");
            String tbUrl = json.getString("tbUrl");
            String title = json.getString("title");
            int imageHeight = Integer.parseInt(json.getString("tbHeight"));
            int imageWidth = Integer.parseInt(json.getString("tbWidth"));
            return new SearchResult(url, tbUrl, title, imageHeight, imageWidth);
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
