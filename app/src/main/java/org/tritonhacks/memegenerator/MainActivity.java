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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // used in GetMemeActivity; move it there
    final static String URL = "https://meme-api.herokuapp.com/gimme";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
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
}
