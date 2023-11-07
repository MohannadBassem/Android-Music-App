package com.example.musicalstructureappprototype2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.musicalstructureappprototype2.MainActivity.files;

public class albummusiclist extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView TheAlbumPhoto;
    String Album_Name_Getit;
    ArrayList<Files> TheAlbumSongsFiles = new ArrayList<>();
    TheAdapterOfAlbumTab theAdapterOfAlbumTab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albummusiclist);
        recyclerView = findViewById(R.id.recyclerView);
        TheAlbumPhoto = findViewById(R.id.TheAlbumPhoto);
        Album_Name_Getit = getIntent().getStringExtra("albumName");
        int Y = 0;
        for(int i = 0 ; i < files.size() ; i++)
        {
            if(Album_Name_Getit.equals(files.get(i).getAlbum()))
            {
                TheAlbumSongsFiles.add(Y, files.get(i));
                Y++;
            }
        }
        byte[] image = Fetch_AlbumImg_Art(TheAlbumSongsFiles.get(0).getPath());
        if(image != null)
        {
             Glide.with(this)
                     .load(image)
                     .into(TheAlbumPhoto);
        }
        else
            {
            Glide.with(this)
                    .load(R.drawable.music)
                    .into(TheAlbumPhoto);
            }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(!(TheAlbumSongsFiles.size() < 1))
        {
            theAdapterOfAlbumTab = new TheAdapterOfAlbumTab(this,TheAlbumSongsFiles);
            recyclerView.setAdapter(theAdapterOfAlbumTab);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL, false));
        }
    }
    private byte[] Fetch_AlbumImg_Art(String uri)
    {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] ART = retriever.getEmbeddedPicture();
        retriever.release();
        return ART;
    }
}