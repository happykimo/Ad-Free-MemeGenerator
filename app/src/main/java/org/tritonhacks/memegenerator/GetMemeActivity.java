package org.tritonhacks.memegenerator;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import static org.tritonhacks.memegenerator.MainActivity.URL;

public class GetMemeActivity extends AppCompatActivity {

    private static RandomMeme randomMeme;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_meme);

        testRequest();


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
                    String url = randomMeme.getUrl();
                    ImageView imgV_Meme = findViewById(R.id.imgV_meme);
                    Picasso.with(this).load(url).into(imgV_Meme);

                }, error -> System.out.println("Error!"));

        queue.add(stringRequest);

        // Add the request to the RequestQueue. NOT DONE -Alex
        // SingletonRQ.getInstance(this).addToRequestQueue(jsonObjRequest);
    }
}
