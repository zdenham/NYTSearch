package scope.nytsearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by zdenham on 8/6/16.
 */
public class ArticleArrayAdapter extends
        RecyclerView.Adapter<ArticleArrayAdapter.ViewHolder> {

    private ArrayList<Article> mArticleList;
    private Context mContext;

    public ArticleArrayAdapter(Context context, ArrayList<Article> articleList){
        this.mContext = context;
        this.mArticleList = articleList;
    }

    private Context getContext(){
        return mContext;
    }

    @Override
    public ArticleArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View articleView = inflater.inflate(R.layout.item_article_result, parent, false);

        ViewHolder viewHolder = new ViewHolder(articleView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticleArrayAdapter.ViewHolder holder, int position) {
        Article article = mArticleList.get(position);

        TextView textView = holder.tvTitle;
        ImageView imageView = holder.ivCover;

        textView.setText(article.getHeadline());

        if(article.getImagePath() != "") {
            Picasso.with(mContext).load(article.getImagePath()).into(imageView);
        } else {
            Picasso.with(mContext).load(R.mipmap.ic_launcher).into(imageView);
        }
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvTitle;
        public ImageView ivCover;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ivCover = (ImageView) itemView.findViewById(R.id.ivCover);
        }
    }
}
