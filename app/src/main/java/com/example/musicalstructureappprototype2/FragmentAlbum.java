package com.example.musicalstructureappprototype2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.example.musicalstructureappprototype2.MainActivity.MusicDuplicateFilter;
import static com.example.musicalstructureappprototype2.MainActivity.files;

public class FragmentAlbum extends Fragment {
    RecyclerView recyclerView;
    TheAdapterOfAlbum theAdapterOfAlbum;

    public FragmentAlbum() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        if (!(MusicDuplicateFilter.size() < 1)) {
            theAdapterOfAlbum = new TheAdapterOfAlbum(getContext(), MusicDuplicateFilter);
            recyclerView.setAdapter(theAdapterOfAlbum);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
        return view;
    }
}