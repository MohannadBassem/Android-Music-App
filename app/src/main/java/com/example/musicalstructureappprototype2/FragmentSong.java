package com.example.musicalstructureappprototype2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.example.musicalstructureappprototype2.MainActivity.files;

public class FragmentSong extends Fragment {
    RecyclerView recyclerView;
    TheAdapterOfMusic theAdapterOfMusic;

    public FragmentSong() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        if (!(files.size() < 1)) {
            theAdapterOfMusic = new TheAdapterOfMusic(getContext(), files);
            recyclerView.setAdapter(theAdapterOfMusic);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        }
        return view;
    }
}