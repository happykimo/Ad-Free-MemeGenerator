package org.tritonhacks.memegenerator;
//test
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

        // TODO: Call initViews()
        initViews();
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
        final Intent intent = new Intent( MainActivity.this, GalleryActivity.class);
        startActivity(intent);
    }

    /**
     * Initializes all necessary views.
     */
    private void initViews() {
        // Example: Gets the button object with the id "btn_get_a_meme" and make it so that when it
        //          is clicked, it calls the method onGetMemeClicked()
        this.getMeme = findViewById(R.id.btn_get_a_meme);
        this.getMeme.setOnClickListener(v -> onGetMemeClicked());

        // TODO: Create a Button object and set its onClickListener to call
        //       the correct method
        this.createMeme = findViewById(R.id.btn_create_a_meme);
        this.createMeme.setOnClickListener(v -> onCreateMemeClicked());
    }
}
