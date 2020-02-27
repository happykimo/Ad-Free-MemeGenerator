package org.tritonhacks.memegenerator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

public class EditMemeActivity extends AppCompatActivity {

    ImageView imageView;
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    EditText[] editTexts;
    Button createBtn;
    String templateId;
    int boxCount;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meme_layout);
        requestQueue = Volley.newRequestQueue(this);
        initViews();

        templateId = getIntent().getStringExtra("id");
        final String imageUrl = getIntent().getStringExtra("url");
        boxCount = getIntent().getIntExtra("boxCount", 0);

        // Set visibility of text views
        for (int i = 0; i < boxCount; i++) editTexts[i].setVisibility(View.VISIBLE);

        Picasso.get().load(imageUrl).into(imageView);
    }

    private void fetchCustomMeme() {
        // Build URL
        final StringBuilder urlBuilder = new StringBuilder();
        final String baseUrl = "https://api.imgflip.com/caption_image?";
        urlBuilder.append(baseUrl);
        urlBuilder.append("username=homelessman023");
        urlBuilder.append("&");
        urlBuilder.append("password=password");
        urlBuilder.append("&");
        urlBuilder.append("template_id=").append(templateId);
        urlBuilder.append("&");
        for (int i = 0; i < boxCount; i++) {
            final String text = editTexts[i].getText().toString();
            urlBuilder.append("text").append(i).append("=").append(text);
            urlBuilder.append("&");
        }
        final String getCustomMemeApiUrl = urlBuilder.toString();

        // Create GET request to get custom meme
        final StringRequest r = new StringRequest(Request.Method.GET, getCustomMemeApiUrl,
                response -> {
                    // Get URL of custom meme
                    final String customMemeUrl = ((JsonObject) JsonParser.parseString(response))
                            .getAsJsonObject("data")
                            .get("url")
                            .getAsString();

                    // Display custom meme
                    Picasso.get().load(customMemeUrl).into(imageView);
                },
                error -> Log.e("Volley request error", String.valueOf(error)));

        // Schedule GET request for custom meme
        requestQueue.add(r);
    }

    private void initViews() {
        imageView = findViewById(R.id.imageView1);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        editText5 = findViewById(R.id.editText5);
        editTexts = new EditText[]{editText1, editText2, editText3, editText4, editText5};

        createBtn = findViewById(R.id.create_meme);
        createBtn.setOnClickListener(v -> fetchCustomMeme());
    }
}
