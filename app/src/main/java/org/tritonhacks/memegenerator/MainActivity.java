package org.tritonhacks.memegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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
