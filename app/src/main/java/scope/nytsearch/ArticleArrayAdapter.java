package scope.nytsearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by zdenham on 8/6/16.
 */
public class ArticleArrayAdapter extends ArrayAdapter<Article> {
    public ArticleArrayAdapter(Context context, ArrayList<Article> articles){
        super(context, android.R.layout.simple_list_item_1, articles);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Article article = this.getItem(position);

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_article_result, parent, false);
        }

        ImageView image = (ImageView)convertView.findViewById(R.id.ivCover);

        image.setImageResource(0);

        TextView title = (TextView)convertView.findViewById(R.id.tvTitle);

        title.setText(article.getHeadline());

        if(article.getImagePath() != "") {
            Picasso.with(getContext()).load(article.getImagePath()).into(image);
        } else {
            Picasso.with(getContext()).load(R.mipmap.ic_launcher).into(image);
        }

        return convertView;
    }
}
