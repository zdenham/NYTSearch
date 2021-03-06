package scope.nytsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class searchActivity extends AppCompatActivity
{
    private final int REQUEST_CODE = 20;
    EditText etSearch;
    Button btnSearch;
    RecyclerView rvResults;
    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;
    String query;
    Boolean adding = false;
    Restrictions restriction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpViews();

    }

    public void setUpViews(){
        articles = new ArrayList<Article>();
        etSearch = (EditText)findViewById(R.id.etSearch);
        btnSearch = (Button)findViewById(R.id.btnSearch);
        rvResults = (RecyclerView)findViewById(R.id.rvResults);
        adapter = new ArticleArrayAdapter(this, articles);
        rvResults.setAdapter(adapter);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rvResults.setLayoutManager(manager);
        restriction = new Restrictions();
        rvResults.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMoreData();
            }
        });
    }

    public void loadMoreData(){
        Toast.makeText(getApplicationContext(), "loading more data", Toast.LENGTH_LONG).show();
        adding = true;
        String synonymHunter;
        if(query.contains(" ")){
            synonymHunter = query.substring(0, query.indexOf(" "));
        } else {
            synonymHunter = query;
        }

        String url = "http://words.bighugelabs.com/api/2/ddf363b16b946ad3f805bcf7eebd7202/" + synonymHunter + "/json";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONArray names = response.names();
                try {
                    query = response.getJSONObject(names.getString(0)).getJSONArray("syn").getString(0);
                    onArticleSearch(etSearch);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public void onArticleSearch(View v) {
        if(!adding){
            query = etSearch.getText().toString();
        }
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=227c750bb7714fc39ef1559ef1bd8329";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("q", query);
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if(!adding) {
                        articles.clear();
                    } else {
                        adding = false;
                    }
                    ArrayList<Article> validArticles = vetArticles(Article.convirtArray(response.getJSONObject("response").getJSONArray("docs")));

                    articles.addAll(validArticles);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public ArrayList<Article> vetArticles(ArrayList<Article> allArticles){
        ArrayList<Article> qualified = new ArrayList<Article>();
        for(int x = 0; x < allArticles.size(); x++){
            qualified.add(allArticles.get(x));
            Article a = allArticles.get(x);
            if(restriction.isUsed().get(0)){
                //add restrictions here
            }
            if(restriction.isUsed().get(1)){
                //add restrictions here
            }
            if(restriction.isUsed().get(2)){
                //add restrictions here
            }
            if(restriction.isUsed().get(3)){
                //add restrictions here
            }
            if(restriction.isUsed().get(4)){
                //add restrictions here
            }
        }
        return allArticles;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            restriction = Parcels.unwrap(data.getParcelableExtra("restrictions"));
        }
    }

    public void getFilter(View v){
        Intent i = new Intent(getApplicationContext(), filterActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }

}
