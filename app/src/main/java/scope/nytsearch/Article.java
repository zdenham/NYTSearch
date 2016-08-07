package scope.nytsearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zdenham on 8/6/16.
 */
public class Article {
    public String link;
    public String headline;
    public String imagePath;

    public String getLink() {
        return link;
    }

    public String getHeadline() {
        return headline;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Article (JSONObject myObject){
        try {
            link = myObject.getString("web_url");
            headline = myObject.getJSONObject("headline").getString("main");

            JSONArray media = myObject.getJSONArray("multimedia");

            if(media.length() > 0){
                imagePath = String.format("http://nytimes.com/%s", media.getJSONObject(0).getString("url"));
            } else {
                imagePath = "";
            }

        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Article> convirtArray(JSONArray docs) {
        ArrayList<Article> myList = new ArrayList<Article>();
        try {
            for(int x = 0; x < docs.length(); x++){
                Article myArticle = new Article( docs.getJSONObject(x) );
                myList.add(myArticle);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

        return myList;
    }
}
