package org.tritonhacks.memegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    final static String URL = "https://meme-api.herokuapp.com/gimme";
    private RandomMeme randomMeme;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        // testing request API here
        // testRequest();
    }

    private void onGetMemeClicked() {
        final Intent intent = new Intent(MainActivity.this, GetMemeActivity.class);
        startActivity(intent);
        testRequest();
    }

    private void onCreateMemeClicked() {
        final Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
        startActivity(intent);
    }

    private void initViews() {
        final Button getMeme = findViewById(R.id.btn_get_a_meme);
        getMeme.setOnClickListener(v -> onGetMemeClicked());

        final Button createMeme = findViewById(R.id.btn_create_a_meme);
        createMeme.setOnClickListener(v -> onCreateMemeClicked());
    }

    // A simple request; will need another implementation through a singleton
    // class.
    private void testRequest() {
        Gson gson = new Gson();
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
            response -> {

                randomMeme = gson.fromJson(response, RandomMeme.class);
                ImageView imgV_Meme = findViewById(R.id.imgV_meme);

            }, error -> System.out.println("Error!"));

        queue.add(stringRequest);

        // Add the request to the RequestQueue. NOT DONE -Alex
        // SingletonRQ.getInstance(this).addToRequestQueue(jsonObjRequest);
    }
}
