package com.example.administrator.myapplication;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by yong on 2016-05-29.
 */
public class RecorderActivity extends AppCompatActivity implements ActionBar.OnNavigationListener{
    private static final String LOG_TAG = "AudioRecorderTest";
    private static String filename = null;

    private SeekBar seekBar;
    private Button play, record, btn_stop;
    TextView text_lyrics;
    ScrollView scrollView;

    MediaPlayer mp = null;
    AudioManager audioManager;
    ActionBar actionBar;
    SpinnerAdapter adapter;

    private int nMax;
    private int nCurrentVolumn;

    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);

        actionBar = getSupportActionBar();
        adapter = ArrayAdapter.createFromResource(this, R.array.music_list2, android.R.layout.simple_spinner_dropdown_item);
        actionBar.setListNavigationCallbacks(adapter, this);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        nMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        nCurrentVolumn = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        filename = Environment.getExternalStorageDirectory().getAbsolutePath();
        filename += "/test.3gp";
        play = (Button)findViewById(R.id.btn_recordPlay);
        record = (Button)findViewById(R.id.btn_recordStart);
        text_lyrics =(TextView)findViewById(R.id.text_lyrics);
        btn_stop = (Button)findViewById(R.id.btn_rc_songstop);
        seekBar = (SeekBar)findViewById(R.id.rc_seekbar);
        scrollView = (ScrollView)findViewById(R.id.scrollView);

        seekBar.setMax(nMax);
        seekBar.setProgress(nCurrentVolumn);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp != null) {
                    mp.stop();
                    mp.reset();
                }
                mp = null;
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(player == null){
                    player = new MediaPlayer();
                    try{
                        player.setDataSource(filename);
                        player.prepare();
                        player.start();
                    }catch (IOException e) {
                        Log.e(LOG_TAG, "prepate() failed");
                    }
                    play.setText("재생 중지");

                    }else {
                    player.release();;
                    player = null;
                    play.setText("재생 시작");
                }
            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recorder == null) {
                    recorder = new MediaRecorder();
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    recorder.setOutputFile(filename);
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                    try {
                        recorder.prepare();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "prepare() failed");
                    }
                    recorder.start();
                    record.setText("녹음 중지");
                }
                    else{
                        recorder.stop();
                        recorder.release();
                        recorder = null;
                        record.setText("녹음 시작");
                }
            }
        });
    }
    @Override
    public void onPause(){
        super.onPause();
        if (recorder != null){
            recorder.release();
            recorder = null;
        }
        if (player != null){
            player.release();
            player = null;
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mp != null)
            mp.release();
        mp = null;
    }
    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        int select =1;
        if (mp != null)
            mp.stop();
        mp = null;

        switch (i) {
            case 0:
                select = 0;
                break;
            case 1:
                select = 1;
                mp = MediaPlayer.create(this, R.raw.music01);
                text_lyrics.setText(R.string.lyrics01);
                scrollView.scrollTo(0,0);
                break;
            case 2:
                select = 1;
                mp = MediaPlayer.create(this, R.raw.music02);
                text_lyrics.setText(R.string.lyrics02);
                scrollView.scrollTo(0,0);
                break;
            case 3:
                select = 1;
                mp = MediaPlayer.create(this, R.raw.music03);
                text_lyrics.setText(R.string.lyrics03);
                scrollView.scrollTo(0,0);
                break;
            case 4:
                select = 1;
                mp = MediaPlayer.create(this, R.raw.music04);
                text_lyrics.setText(R.string.lyrics04);
                scrollView.scrollTo(0,0);
                break;
            case 5:
                select = 1;
                mp = MediaPlayer.create(this, R.raw.music05);
                text_lyrics.setText(R.string.lyrics06);

                scrollView.scrollTo(0,0);
                break;
            case 6:
                select = 1;
                mp = MediaPlayer.create(this, R.raw.music06);
                text_lyrics.setText(R.string.lyrics05);
                scrollView.scrollTo(0,0);
                break;

        }
        if(select ==1)
        mp.start();
         return false;
    }

}