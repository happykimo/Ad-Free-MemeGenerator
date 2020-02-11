package org.tritonhacks.memegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        // testing request API here
        testRequest();
    }

    private void onGetMemeClicked() {
        final Intent intent = new Intent(MainActivity.this, GetMemeActivity.class);
        startActivity(intent);
    }

    private void onCreateMemeClicked() {
        final Intent intent = new Intent(MainActivity.this, CreateMemeActivity.class);
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
    // For testing purposes; please make the anonymous class methods into lambda
    // expressions; it's much cleaner, but I'm not familiar with it!... -Alex
    private void testRequest() {
        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.google.com";

        // Request a string response the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the first 500 characters of the response string.
                    System.out.println("\n-------\n");
                    System.out.println("Response is: " + response.substring(0, 500));
                    System.out.println("-------\n");
                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
