package com.example.administrator.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.test.PerformanceTestCase;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView text_rating;   //레이팅 평가
    private Activity activity;
    private static final int PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_rating = (TextView)findViewById(R.id.text_rating);
        activity = this;

        //Manifest.permission.RECORD_AUDIO
        //Manifest.permission.WRITE_EXTERNAL_STORAGE
        checkPermission(Manifest.permission.RECORD_AUDIO);
        requestPermission(Manifest.permission.RECORD_AUDIO);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);


        //이메일 전송
        ((Button)findViewById(R.id.btn_email)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SendEmailActivity.class));
            }
        });

        //전화 걸기
        ((Button)findViewById(R.id.btn_call)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, KpuPhoneActivity.class));
            }
        });

        //푸드코트
        ((Button)findViewById(R.id.btn_food)).setOnClickListener(new View.OnClickListener() { // 학식충 // 푸드코트
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, KpuDishesActivity.class));
            }
        });

        //지도
        ((Button)findViewById(R.id.btn_map)).setOnClickListener(new View.OnClickListener() { // 지도
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("geo:%f,%f?z=17",37.340210,126.733597))));
            }
        });

        //산대전
        ((Button)findViewById(R.id.btn_say)).setOnClickListener(new View.OnClickListener() { // 산대전
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, KpuSayActivity.class));
            }
        });

        //메모
        ((Button)findViewById(R.id.btn_memo)).setOnClickListener(new View.OnClickListener() { // 산대전
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MemoActivity.class));
            }
        });

        //만든이들
        ((Button)findViewById(R.id.btn_creator)).setOnClickListener(new View.OnClickListener() { // 산대전
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreditActivity.class));
            }
        });

        //음악 듣기
        ((Button)findViewById(R.id.btn_song)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MusicActivity.class));
            }
        });

        //노래부르기
        ((Button)findViewById(R.id.btn_record)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RecorderActivity.class));
            }
        });

    }

    //메뉴 옵션 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    //메뉴 옵션이 눌릴때
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rating:   //레이팅 메뉴
                RatingDialog ratingDialog = new RatingDialog(this);
                ratingDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setRating(float score){ //레이팅 초기화
        text_rating.setTextSize(20);
        text_rating.setText("평가점수 : "+score);
    }

    private boolean checkPermission(String str){
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), str);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }

    private void requestPermission(String str){

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,str)){
            Toast.makeText(getApplicationContext(),
                    "녹음에 대한 권한이 존재하지 않습니다." , Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{str},
                    PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getApplicationContext(),
                            "권한 부여 안하면 녹음 못해요! 고마워요",Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "권한 부여 안하면 녹음 못해요! 고마워요",Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
}
