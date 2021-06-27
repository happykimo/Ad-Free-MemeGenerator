package org.tritonhacks.memegenerator;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class GetMemeActivity extends AppCompatActivity {

    final static String URL = "https://meme-api.herokuapp.com/gimme/cleanmemes/50";
    final static String POSTLINK_KEY = "postLink";
    final static String SUBREDDIT_KEY = "subreddit";
    final static String TITLE_KEY = "title";
    final static String URL_KEY = "url";

    private ArrayList<RandomMeme> memeList;
    private String memeUrl;

    private ImageView imgVMeme;
    private Button btnGetAnotherMeme;
    private Button btnSaveToGallery;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_meme);

        initViews();

        ActivityCompat.requestPermissions(GetMemeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(GetMemeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        /**
         * Permissions Request
         */

        if(savedInstanceState == null) {
             getMeme();
        }
    }

    /**
     * Initializes all necessary views.
     */
    private void initViews() {
        // TODO: Assign references of UI components to instance variables
        // TODO: set the button's click listener to call getMeme()
        this.btnGetAnotherMeme = findViewById(R.id.btn_get_another_meme);
        this.btnGetAnotherMeme.setOnClickListener(v -> getMeme());


        this.imgVMeme = findViewById(R.id.imgV_meme);

        this.btnSaveToGallery=findViewById(R.id.btn_save_to_gallery);
        this.btnSaveToGallery.setOnClickListener(v -> saveToGallery());
        /**
         * Code for saving Meme to gallery
         */

    }

    /**
     * Get a meme either through request or load the meme if list is not empty.
     */
    private void getMeme() {
        // TODO
        if(isMemeListEmpty()==true){
            getRequest();
        }
        else {
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
        outState.putString(URL_KEY, this.memeUrl);
        super.onSaveInstanceState(outState);
    }

    /**
     * Restores the previous state, with URL of the previously viewed meme.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        this.memeUrl = savedInstanceState.getString(URL_KEY);
        Picasso.get().load(this.memeUrl).into(this.imgVMeme);
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * Helper method to load the meme image onto ImageView
     */
    private void loadMeme() {
        // TODO: set this.memeUrl to the url of the RandomMeme object pulled off from this.memeList
        this.memeUrl=popMeme();
        System.out.println(popMeme());
        Picasso.get().load(this.memeUrl).into(this.imgVMeme);
    }

    /**
     * Make a GET request to the memes by the URL string.
     */
    private void getRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                response -> {

                    // Gets 50 to 100 memes formatted as a json String and converts it into a
                    // generic JsonObject
                    JsonObject randomMemes = (JsonObject) JsonParser.parseString(response);

                    // Converts the JsonObject (randomMemes) into a JsonArray
                    JsonArray jsonMemesArray = randomMemes.getAsJsonArray("memes");

                    // Turns the JsonArray (jsonMemesArray) into an ArrayList of RandomMeme objects
                    // and store it inside this.memeList, which contains RandomMeme objects
                    System.out.println("getRequest() is being executed--------------------------------------------------");
                    this.memeList = jsonArrayToMemeList(jsonMemesArray);

                    // load a meme from memeList
                    loadMeme();

                }, error -> {
            // make a Toast message to show error
            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
        });

        queue.add(stringRequest);
    }

    /**
     * Converts a JsonArray into a RandomMeme ArrayList
     * @param jsonArray
     */
    private ArrayList<RandomMeme> jsonArrayToMemeList(JsonArray jsonArray) {
        // TODO: construct an ArrayList called randomMemeList that will be returned at the end
        ArrayList<RandomMeme> randomMemeList = new ArrayList<RandomMeme>();

        for(int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = ((JsonObject) jsonArray.get(i));
            String postLink = jsonObject.get(POSTLINK_KEY).getAsString();
            String subreddit = jsonObject.get(SUBREDDIT_KEY).getAsString();
            String title = jsonObject.get(TITLE_KEY).getAsString();
            String url = jsonObject.get(URL_KEY).getAsString();

            // TODO: construct a RandomMeme object, passing in the variables above
            RandomMeme randomMeme = new RandomMeme(postLink,subreddit,title,url);

            randomMemeList.add(randomMeme);
        }
        System.out.println(randomMemeList);
        return randomMemeList;
    }

    /**
     * Removes the last meme object in the array and gets its url.
     *
     * @return url as a String
     */
    private String popMeme() {
        // TODO: remove/pop off a RandomMeme object from this.memeList and return its url.
        String resultUrl=memeList.get(0).getUrl();
        memeList.remove(0);
        System.out.println(resultUrl);
        return resultUrl;
    }

    private void saveToGallery(){
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgVMeme.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();

        FileOutputStream outputStream = null;
        File file = Environment.getExternalStorageDirectory();
        File dir = new File(file.getAbsolutePath() + "/TritonMemes");
        dir.mkdirs();

        String fileName = String.format("%d.png", System.currentTimeMillis());
        File outFile = new File(dir,fileName);
        try{
            outputStream = new FileOutputStream(outFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        try{
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * Checks if memeList is null or empty. SHORT-CIRCUITING
     *
     * @return true if memeList is empty, false otherwise
     */
    private boolean isMemeListEmpty() {
        // TODO
        if(this.memeList==null){
            return true;
        }
        else if (memeList.size() > 0) {
            return false;
        } else {
            return true;
        }
    }
}