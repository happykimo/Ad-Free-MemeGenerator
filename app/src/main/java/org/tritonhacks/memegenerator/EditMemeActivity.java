package org.tritonhacks.memegenerator;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;

public class EditMemeActivity extends AppCompatActivity {

    final static String USERNAME = "homelessman023";
    final static String PASSWORD = "password";

    private ImageView imageView;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private EditText[] editTexts;
    private Button createBtn;
    private Button btnSaveToGallery;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meme_layout);
        this.requestQueue = Volley.newRequestQueue(this);
        initViews();

        ActivityCompat.requestPermissions(EditMemeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(EditMemeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        /**
         * Permissions Request
         */

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

    private void saveToGallery(){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
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
     * Initializes all necessary views.
     */
    private void initViews() {
        this.imageView = findViewById(R.id.imageView1);
        this.createBtn = findViewById(R.id.btn_create_meme);

        this.btnSaveToGallery=findViewById(R.id.btn_save_meme);
        this.btnSaveToGallery.setOnClickListener(v -> saveToGallery());

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