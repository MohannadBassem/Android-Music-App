package com.example.musicalstructureappprototype2;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TheAdapterOfAlbumTab extends RecyclerView.Adapter<TheAdapterOfAlbumTab.TheHolder2> {
    private Context bContext;
    private ArrayList<Files> The_Files_Of_Album;
    View view;
    public TheAdapterOfAlbumTab(Context bContext, ArrayList<Files> The_Files_Of_Album)
    {
        this.bContext = bContext;
        this.The_Files_Of_Album = The_Files_Of_Album;
    }
    @NonNull
    @Override
    public TheHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(bContext).inflate(R.layout.items,parent,false);
        return new TheHolder2(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TheHolder2 holder, final int position) {
        holder.TheName_OfAlbum.setText(The_Files_Of_Album.get(position).getTitle());
        byte[] image = Fetch_AlbumImg_Art(The_Files_Of_Album.get(position).getPath());
        if (image != null)
        {
            Glide.with(bContext).asBitmap()
                    .load(image)
                    .into(holder.TheImg_OfAlbum);
        }
        else{
            Glide.with(bContext)
                    .load(R.drawable.music)
                    .into(holder.TheImg_OfAlbum);
        }
    }
    @Override
    public int getItemCount() {
        return The_Files_Of_Album.size();
    }
    public class TheHolder2 extends RecyclerView.ViewHolder {
        ImageView TheImg_OfAlbum;
        TextView TheName_OfAlbum;
        public TheHolder2(@NonNull View itemView) {
            super(itemView);
            TheImg_OfAlbum = itemView.findViewById(R.id.SongBar_MusicImg);
            TheName_OfAlbum = itemView.findViewById(R.id.SongBar_MusicName);
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
