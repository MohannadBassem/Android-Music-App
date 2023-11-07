package com.example.musicalstructureappprototype2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int Code_Request = 1;
    static ArrayList<Files> files;
    static boolean shuffleBoolean = false, repeatBoolean = false;
    static ArrayList<Files> MusicDuplicateFilter = new ArrayList<>();

    public static ArrayList<Files> FetchTheSongs(Context context) {
        ArrayList<String> Duplicate_Detector = new ArrayList<>();
        ArrayList<Files> TheMediaList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA
        };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String duration = cursor.getString(0);
                String album = cursor.getString(1);
                String title = cursor.getString(2);
                String artist = cursor.getString(3);
                String path = cursor.getString(4);
                Files files = new Files(artist, album, duration, path, title);
                Log.e("Path : " + path, "Album : " + album);
                TheMediaList.add(files);
                if (!Duplicate_Detector.contains(album)) {
                    MusicDuplicateFilter.add(files);
                    Duplicate_Detector.add(album);
                }
            }
            cursor.close();
        }
        return TheMediaList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permission();
    }

    private void permission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                    , Code_Request);
        } else {
            files = FetchTheSongs(this);
            ViewPager_Initial();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Code_Request) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                files = FetchTheSongs(this);
                ViewPager_Initial();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Code_Request);
            }
        }
    }

    private void ViewPager_Initial() {
        ViewPager viewPager = findViewById(R.id.ThePageViewer);
        TabLayout tabLayout = findViewById(R.id.Upper_Layout_Tab);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new FragmentSong(), "Songs");
        viewPagerAdapter.addFragments(new FragmentAlbum(), "Albums");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        void addFragments(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}