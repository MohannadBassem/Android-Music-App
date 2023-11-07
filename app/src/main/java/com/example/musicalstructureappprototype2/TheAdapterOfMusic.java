package com.example.musicalstructureappprototype2;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TheAdapterOfMusic extends RecyclerView.Adapter<TheAdapterOfMusic.TheHolder> {

    private Context bContext;
    private ArrayList<Files> bFiles;

    TheAdapterOfMusic(Context bContext, ArrayList<Files> bFiles) {
        this.bFiles = bFiles;
        this.bContext = bContext;
    }

    @NonNull
    @Override
    public TheHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(bContext).inflate(R.layout.items, parent, false);
        return new TheHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheHolder holder, final int position) {
        holder.TheName_OfAlbum.setText(bFiles.get(position).getTitle());
        byte[] image = Fetch_AlbumImg_Art(bFiles.get(position).getPath());
        if (image != null) {
            Glide.with(bContext).asBitmap()
                    .load(image)
                    .into(holder.TheImg_OfAlbum);
        } else {
            Glide.with(bContext)
                    .load(R.drawable.music)
                    .into(holder.TheImg_OfAlbum);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bContext, NowPlayingActivity.class);
                intent.putExtra("position", position);
                bContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bFiles.size();
    }

    private byte[] Fetch_AlbumImg_Art(String uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] ART = retriever.getEmbeddedPicture();
        retriever.release();
        return ART;

    }

    public class TheHolder extends RecyclerView.ViewHolder {
        TextView TheName_OfAlbum;
        ImageView TheImg_OfAlbum;

        public TheHolder(@NonNull View itemView) {
            super(itemView);
            TheName_OfAlbum = itemView.findViewById(R.id.SongBar_MusicName);
            TheImg_OfAlbum = itemView.findViewById(R.id.SongBar_MusicImg);
        }
    }
}
