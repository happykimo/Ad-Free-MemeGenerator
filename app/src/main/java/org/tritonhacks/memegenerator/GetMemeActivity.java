package org.tritonhacks.memegenerator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

public class GetMemeActivity extends AppCompatActivity {

    final static String URL = "https://meme-api.herokuapp.com/gimme/cleanmemes/100";

    private static JsonObject randomMeme;
    private static JsonArray memeList;
    private ImageView imgV_Meme;
    private String memeUrl;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_meme);

        initViews();
        getMeme();
    }

    /**
     * Initializes all necessary views.
     */
    private void initViews() {
        final Button getAnotherMeme = findViewById(R.id.btn_get_another_meme);
        getAnotherMeme.setOnClickListener(v -> getMeme());
        imgV_Meme = findViewById(R.id.imgV_meme);
    }

    /**
     * Get a meme either through request or load the meme if list is not empty.
     */
    private void getMeme() {
        if (isMemeListEmpty()) {
            getRequest();
        } else {
            loadMeme();
        }
    }

    /**
     * Saves the current state. Namely the URL of the current meme.
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("url", memeUrl);
        super.onSaveInstanceState(outState);
    }

    /**
     * Restores the previous state, with URL of the previously viewed meme.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        memeUrl = savedInstanceState.getString("url");
        Picasso.get().load(memeUrl).into(imgV_Meme);
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * Helper method to load the meme image onto ImageView
     */
    private void loadMeme() {
        memeUrl = popMeme();
        Picasso.get().load(memeUrl).into(imgV_Meme);
    }

    /**
     * Make a GET request to the memes by the URL string.
     */
    private void getRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                response -> {

                    randomMeme = (JsonObject) JsonParser.parseString(response);
                    memeList = randomMeme.getAsJsonArray("memes");
                    loadMeme();

                }, error -> {
            // make a Toast message to show error. Needs to be polished.
            // TODO
            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
        });

        queue.add(stringRequest);

        // Add the request to the RequestQueue. NOT DONE -Alex
        // SingletonRQ.getInstance(this).addToRequestQueue(jsonObjRequest);
    }

    /**
     * Removes the last meme object in the array and gets its url.
     *
     * @return url as a String
     */
    private String popMeme() {
        JsonElement meme = memeList.remove(memeList.size() - 1);
        JsonElement jsonUrl = ((JsonObject) meme).get("url");
        return jsonUrl.getAsString();
    }

    /**
     * Checks if memeList is null or empty. SHORT-CIRCUITING
     *
     * @return true if memeList is empty, false otherwise
     */
    private boolean isMemeListEmpty() {
        return memeList == null || memeList.size() == 0;
    }
}
