package org.tritonhacks.memegenerator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.util.Locale;

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
        // Build request body
        final StringBuilder requestBody = new StringBuilder();
        requestBody.append("username=homelessman023");
        requestBody.append("&");
        requestBody.append("password=password");
        requestBody.append("&");
        requestBody.append("template_id=").append(templateId);
        requestBody.append("&");
        for (int i = 0; i < boxCount; i++) {
            final String text = editTexts[i].getText().toString();
            requestBody.append(String.format(Locale.US, "boxes[%d][text]=%s", i, text));
            requestBody.append("&");
        }
        final String createCustomMemeApiUrl = "https://api.imgflip.com/caption_image";

        // Create POST request to create custom meme
        final StringRequest r = new StringRequest(Request.Method.POST, createCustomMemeApiUrl,
                response -> {
                    // Get URL of just-created custom meme
                    final String customMemeUrl = ((JsonObject) JsonParser.parseString(response))
                            .getAsJsonObject("data")
                            .get("url")
                            .getAsString();

                    // Show custom meme
                    Picasso.get().load(customMemeUrl).into(imageView);
                },
                error -> Log.e("Volley request error", String.valueOf(error))) {

            // We must override this to actually include the request body
            @Override
            public byte[] getBody() throws AuthFailureError {
                return requestBody.toString().getBytes();
            }
        };

        // Schedule POST request for custom meme
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
