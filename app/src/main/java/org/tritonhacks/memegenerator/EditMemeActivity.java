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

    final static String USERNAME = "homelessman023";
    final static String PASSWORD = "password";

    ImageView imageView;
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    EditText[] editTexts;
    Button createBtn;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meme_layout);
        this.requestQueue = Volley.newRequestQueue(this);
        initViews();

        // TODO: extract out the id from the intent
        String templateId = null;

        String imageUrl = getIntent().getStringExtra("url");
        int boxCount = getIntent().getIntExtra("boxCount", 0);
        String id = getIntent().getStringExtra("id");

        this.createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: call fetchCustomMeme with the correct arguments
                fetchCustomMeme(id,boxCount);
            }
        });

        // Set visibility of text views
        // TODO: set visibility to View.VISIBLE instead of View.GONE
        for (int i = 0; i < boxCount; i++) this.editTexts[i].setVisibility(View.VISIBLE);

        // loads meme image into the imageView
        Picasso.get().load(imageUrl).into(this.imageView);
    }

    /**
     * Get the meme template
     * @param templateId id for the template
     * @param boxCount number of textboxes for the template
     */
    private void fetchCustomMeme(String templateId, int boxCount) {
        // Build request body
        final String requestBody = buildRequestBody(templateId, boxCount);
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
                    Picasso.get().load(customMemeUrl).into(this.imageView);
                },
                error -> Log.e("Volley request error", String.valueOf(error))) {

            // We must override this to actually include the request body
            @Override
            public byte[] getBody() throws AuthFailureError {
                return requestBody.getBytes();
            }
        };

        // Schedule POST request for custom meme
        this.requestQueue.add(r);
    }

    /**
     * Builds the POST request body
     * @param templateId id of the meme template
     * @param boxCount number of textboxes of the meme tempalte
     * @return String of the request body
     */
    private String buildRequestBody(String templateId, int boxCount) {
        final StringBuilder requestBody = new StringBuilder();

        requestBody.append("username=");
        requestBody.append(USERNAME);
        requestBody.append("&");
        requestBody.append("password=");
        requestBody.append(PASSWORD);
        requestBody.append("&");
        requestBody.append("template_id=").append(templateId);
        requestBody.append("&");
        for (int i = 0; i < boxCount; i++) {
            final String text = this.editTexts[i].getText().toString();
            requestBody.append(String.format(Locale.US, "boxes[%d][text]=%s", i, text));
            requestBody.append("&");
        }

        return requestBody.toString();
    }

    /**
     * Initializes all necessary views.
     */
    private void initViews() {
        this.imageView = findViewById(R.id.imageView1);
        this.createBtn = findViewById(R.id.create_meme);

        this.editText1 = findViewById(R.id.editText1);
        // TODO: initialize the rest of the four EditText views here
        this.editText2 = findViewById(R.id.editText2);
        this.editText3 = findViewById(R.id.editText3);
        this.editText4 = findViewById(R.id.editText4);
        this.editText5 = findViewById(R.id.editText5);


        this.editTexts = new EditText[]{ this.editText1, this.editText2, this.editText3,
                this.editText4, this.editText5 };
    }
}