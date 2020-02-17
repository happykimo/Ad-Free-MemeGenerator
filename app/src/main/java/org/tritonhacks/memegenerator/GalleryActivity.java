package org.tritonhacks.memegenerator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {
    ImageView imageView;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        imageView = (ImageView) findViewById(R.id.imageView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        ArrayList imageUrlList = prepareData();
        DataAdapter dataAdapter = new DataAdapter(getApplicationContext(), imageUrlList);
        recyclerView.setAdapter(dataAdapter);

        /*imageView.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick (View v){
            Toast.makeText(GalleryActivity.this, "You clicked on ImageView", Toast.LENGTH_SHORT).show();
        }
        });*/
    }

    private ArrayList prepareData() {

// here you should give your image URLs and that can be a link from the Internet
        String imageUrls[] = {
                "https://images.theconversation.com/files/38926/original/5cwx89t4-1389586191.jpg?ixlib=rb-1.1.0&q=45&auto=format&w=926&fit=clip",
                "https://www.21cpw.com/wp-content/uploads/2016/09/batman-meme-blank-1.jpg",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSrqvyRnWUYTbidqEOClDSm533Eda-2My1CJY_0-hUeGYiKjxIK",
                "https://www.meme-arsenal.com/memes/1a07ebd5a3352c612920ab6954afd6fa.jpg",
                "https://memeguy.com/photos/images/i-noticed-you-posted-a-meme-using-only-a-title-and-an-image-but-no-text-19782.jpg",
                "https://imgflip.com/s/meme/Y-U-No.jpg",
                "https://i.kym-cdn.com/photos/images/facebook/001/248/930/07e.jpg",
                "https://www.nicepng.com/png/detail/30-306429_pngs-for-trolls-and-memes-you-don-t.png",
                "https://i.redd.it/fwfdtcsk91e11.jpg",
                "https://imgflip.com/s/meme/Kevin-Hart.jpg",
                "https://i.ytimg.com/vi/r1s8pPVobGo/maxresdefault.jpg",
                "https://imgflip.com/s/meme/Mocking-Spongebob.jpg",
        };

        ArrayList imageUrlList = new ArrayList<>();
        for (int i = 0; i < imageUrls.length; i++) {
            ImageUrl imageUrl = new ImageUrl();
            imageUrl.setImageUrl(imageUrls[i]);
            imageUrlList.add(imageUrl);
        }
        Log.d("MainActivity", "List count: " + imageUrlList.size());
        return imageUrlList;
    }
}
