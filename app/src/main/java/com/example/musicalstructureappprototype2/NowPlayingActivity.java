package com.example.musicalstructureappprototype2;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

import static com.example.musicalstructureappprototype2.MainActivity.files;
import static com.example.musicalstructureappprototype2.MainActivity.repeatBoolean;
import static com.example.musicalstructureappprototype2.MainActivity.shuffleBoolean;

public class NowPlayingActivity extends AppCompatActivity {
    TextView TheNameOfSong, TheNameOfArtist, ThePastDuration, TheTotalDuration;
    ImageView TheAlbumPhoto_InLayouts, Next_Skip_Button,Back_Button, Previous_Skip_Button, The_Shuffle_Button, The_Repeat_Button;
    FloatingActionButton ThePlayPause_Button;
    SeekBar seekBar;
    int position = -1;
    static ArrayList<Files> TheMediaList = new ArrayList<>();
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private Thread PlayingTheThread, PlayingThePreviousThread, PlayingTheNextThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);
        The_Initial_View();
        IntentMethod();
        TheNameOfSong.setText(TheMediaList.get(position).getTitle());
        TheNameOfArtist.setText(TheMediaList.get(position).getArtist());
        Back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer != null && fromUser)
                {
                    mediaPlayer.seekTo(progress* 1000);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        NowPlayingActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null)
                {
                    int Current_Position = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(Current_Position);
                    ThePastDuration.setText(TimeConvertor(Current_Position));
                }
                handler.postDelayed(this,1000);
            }
        });
        The_Shuffle_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shuffleBoolean)
                {
                    shuffleBoolean = false;
                    The_Shuffle_Button.setImageResource(R.drawable.shuffle_off);
                }
                else{
                    shuffleBoolean = true;
                    The_Shuffle_Button.setImageResource(R.drawable.shuffle_on);
                }
            }
        });
        The_Repeat_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repeatBoolean)
                {
                    repeatBoolean = false;
                    The_Repeat_Button.setImageResource(R.drawable.repeat_off);
                }
                else{
                    repeatBoolean = true;
                    The_Repeat_Button.setImageResource(R.drawable.repeat_on);
                }
            }
        });
    }
    @Override
    protected void onResume() {
        PlayingTheThread_Button();
        PlayingTheNextThread_Button();
        PlayingThePreviousThread_Button();
        super.onResume();
    }

    private void PlayingThePreviousThread_Button() {
        PlayingThePreviousThread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                Previous_Skip_Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PreviousButton_ClickMethod();
                    }
                });
            }
        };
        PlayingThePreviousThread.start();
    }

    private void PreviousButton_ClickMethod() {
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            if(shuffleBoolean && !repeatBoolean)
            {
                position = getRandom(TheMediaList.size() -1);
            }
            else if (!shuffleBoolean && !repeatBoolean)
            {
                position = ((position - 1) < 0 ? (TheMediaList.size() - 1) : (position - 1));
            }
            uri = Uri.parse(TheMediaList.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            TheNameOfSong.setText(TheMediaList.get(position).getTitle());
            TheNameOfArtist.setText(TheMediaList.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            NowPlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null)
                    {
                        int Current_Position = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(Current_Position);
                    }
                    handler.postDelayed(this,1000);
                }
            });
            ThePlayPause_Button.setImageResource(R.drawable.baseline_pause);
            mediaPlayer.start();
        }else{

            mediaPlayer.stop();
            mediaPlayer.release();
            if(shuffleBoolean && !repeatBoolean)
            {
                position = getRandom(TheMediaList.size() -1);
            }
            else if (!shuffleBoolean && !repeatBoolean)
            {
                position = ((position - 1) < 0 ? (TheMediaList.size() - 1) : (position - 1));
            }
            uri = Uri.parse(TheMediaList.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            TheNameOfSong.setText(TheMediaList.get(position).getTitle());
            TheNameOfArtist.setText(TheMediaList.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            NowPlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null)
                    {
                        int Current_Position = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(Current_Position);
                    }
                    handler.postDelayed(this,1000);
                }
            });
            ThePlayPause_Button.setImageResource(R.drawable.play_arrow);
        }
    }

    private void PlayingTheNextThread_Button() {

        PlayingTheNextThread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                Next_Skip_Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NextButton_ClickMethod();
                    }
                });
            }
        };
        PlayingTheNextThread.start();
    }

    private void NextButton_ClickMethod() {
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            if(shuffleBoolean && !repeatBoolean)
            {
                position = getRandom(TheMediaList.size() -1);
            }
            else if (!shuffleBoolean && !repeatBoolean)
            {
                position = ((position + 1) % TheMediaList.size());
            }
            uri = Uri.parse(TheMediaList.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            TheNameOfSong.setText(TheMediaList.get(position).getTitle());
            TheNameOfArtist.setText(TheMediaList.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            NowPlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null)
                    {
                        int Current_Position = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(Current_Position);
                    }
                    handler.postDelayed(this,1000);
                }
            });
            ThePlayPause_Button.setImageResource(R.drawable.baseline_pause);
            mediaPlayer.start();
        }else{
            mediaPlayer.stop();
            mediaPlayer.release();
            if(shuffleBoolean && !repeatBoolean)
            {
                position = getRandom(TheMediaList.size() -1);
            }
            else if (!shuffleBoolean && !repeatBoolean)
            {
                position = ((position + 1) % TheMediaList.size());
            }
            uri = Uri.parse(TheMediaList.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            TheNameOfSong.setText(TheMediaList.get(position).getTitle());
            TheNameOfArtist.setText(TheMediaList.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            NowPlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null)
                    {
                        int Current_Position = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(Current_Position);
                    }
                    handler.postDelayed(this,1000);
                }
            });
            ThePlayPause_Button.setImageResource(R.drawable.play_arrow);
        }
    }
    private int getRandom(int i) {
        Random random = new Random();
        return random.nextInt(i + 1);
    }
    private void PlayingTheThread_Button() {
        PlayingTheThread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                ThePlayPause_Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PlayPauseButton_ClickMethod();
                    }
                });
            }
        };
        PlayingTheThread.start();
    }
    private void PlayPauseButton_ClickMethod() {
        if(mediaPlayer.isPlaying())
        {
            ThePlayPause_Button.setImageResource(R.drawable.play_arrow);
            mediaPlayer.pause();
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            NowPlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null)
                    {
                        int Current_Position = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(Current_Position);
                    }
                    handler.postDelayed(this,1000);
                }
            });
        }
        else{
            ThePlayPause_Button.setImageResource(R.drawable.baseline_pause);
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            NowPlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null)
                    {
                        int Current_Position = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(Current_Position);
                    }
                    handler.postDelayed(this,1000);
                }
            });
        }
    }
    private String TimeConvertor(int Current_Position) {
        String TimeOut = "";
        String NewTimeConverted = "";
        String seconds = String.valueOf(Current_Position % 60);
        String minutes = String.valueOf(Current_Position / 60);
        TimeOut = minutes + ":" + seconds;
        NewTimeConverted = minutes + ":" + "0" + seconds;
        if (seconds.length() == 1)
        {
            return NewTimeConverted;
        }
        else
            {
           return TimeOut;
            }
    }
    private void IntentMethod()
    {
        position = getIntent().getIntExtra("position", -1);
        TheMediaList = files;
        if(TheMediaList != null)
        {
            ThePlayPause_Button.setImageResource(R.drawable.baseline_pause);
            uri = Uri.parse(TheMediaList.get(position).getPath());
        }
        if(mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        else
            {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        seekBar.setMax(mediaPlayer.getDuration() / 1000 );
        metaData(uri);
    }
    private void The_Initial_View() {
        TheNameOfSong = findViewById(R.id.TheNameOfSong);
        TheNameOfArtist = findViewById(R.id.song_artist);
        ThePastDuration = findViewById(R.id.ThePast_Duration_Layout);
        TheTotalDuration = findViewById(R.id.TheTotal_Duration_Layout);
        TheAlbumPhoto_InLayouts = findViewById(R.id.TheAlbumPhoto_InLayouts);
        Next_Skip_Button = findViewById(R.id.NextButton_Layout);
        Previous_Skip_Button = findViewById(R.id.PreviousButton_Layout);
        The_Shuffle_Button = findViewById(R.id.ShuffleButton_Layout);
        The_Repeat_Button = findViewById(R.id.RepeatButton_Layout);
        ThePlayPause_Button = findViewById(R.id.PlayPause_Layout);
        Back_Button = findViewById(R.id.Back_Button);
        seekBar = findViewById(R.id.TheSeekBar_Layout);
    }
    private void metaData(Uri uri)
    {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int durationTotal = Integer.parseInt(TheMediaList.get(position).getDuration()) / 1000;
        TheTotalDuration.setText(TimeConvertor(durationTotal));
        byte[] art = retriever.getEmbeddedPicture();
        if(art != null)
        {
            Glide.with(this)
                    .asBitmap()
                    .load(art)
                    .into(TheAlbumPhoto_InLayouts);
        }
        else{
            Glide.with(this)
                    .asBitmap()
                    .load(R.drawable.music)
                    .into(TheAlbumPhoto_InLayouts);
        }
    }
}