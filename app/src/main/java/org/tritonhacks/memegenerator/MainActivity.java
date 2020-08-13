package org.tritonhacks.memegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button getMeme;
    Button createMeme;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO
    }

    /**
     * The method called when Get Meme button is clicked.
     */
    private void onGetMemeClicked() {
        final Intent intent = new Intent(MainActivity.this, GetMemeActivity.class);
        startActivity(intent);
    }

    /**
     * The method called when Create A Meme button is clicked.
     */
    private void onCreateMemeClicked() {
        // TODO
    }

    /**
     * Initializes all necessary views.
     */
    private void initViews() {
        this.getMeme = findViewById(R.id.btn_get_a_meme);
        this.getMeme.setOnClickListener(v -> onGetMemeClicked());

        // TODO
    }
}
