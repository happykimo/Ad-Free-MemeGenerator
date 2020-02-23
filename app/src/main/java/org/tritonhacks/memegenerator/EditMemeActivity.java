package org.tritonhacks.memegenerator;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class EditMemeActivity extends AppCompatActivity {
    //String ImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meme_layout);

        ImageView imageView = findViewById(R.id.imageView1);
        Bundle b = getIntent().getExtras();
        String imageUrl = b.getString("url");
        Picasso.get().load(imageUrl).into(imageView);
    }
}
