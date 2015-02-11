package taylor.com.googleimagesearcher.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import taylor.com.googleimagesearcher.R;
import taylor.com.googleimagesearcher.models.SearchResult;

/**
 * Created by rtteal on 2/10/2015.
 */
public class ImageResultsAdapter extends ArrayAdapter<SearchResult> {

    public ImageResultsAdapter(Context context, List<SearchResult> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchResult result = getItem(position);
        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivImage.setImageResource(0);
        viewHolder.tvTitle.setText(Html.fromHtml(result.title));
        Picasso.with(getContext()).load(result.thumbUrl).into(viewHolder.ivImage);
        return convertView;
    }

    private static class ViewHolder{
        ImageView ivImage;
        TextView tvTitle;
    }
}
