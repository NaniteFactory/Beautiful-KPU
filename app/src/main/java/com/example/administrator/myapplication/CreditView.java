package com.example.administrator.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Random;

/**
 * Created by Administrator on 2016-05-25.
 */
public class CreditView extends View {
    int ParkX, ParkY = 0;
    int MoonX, MoonY = 0;
    int PhX, PhY = 0;
    int ParkSpeed, MoonSpeed, PhSpeed ;
    DisplayMetrics dm;
    Random random;

    public CreditView(Context context) {
        super(context);
        setBackgroundColor(Color.BLACK);
        dm = this.getResources().getDisplayMetrics();

        random = new Random();

        ParkSpeed = MoonSpeed = PhSpeed = random.nextInt(15) + 1;
        MoonSpeed = random.nextInt(15) + 1;
         PhSpeed = random.nextInt(15) + 1;

        ParkY = MoonY = PhY = random.nextInt(dm.heightPixels-100) + 100;
        MoonY = random.nextInt(dm.heightPixels-100) + 100;
        PhY = random.nextInt(dm.heightPixels-100) + 100;

        ParkX = MoonX = PhX = -200;

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(100);
        Resources r=getResources();
        BitmapDrawable bdPark=(BitmapDrawable)r.getDrawable(R.drawable.park);
        BitmapDrawable bdMoon=(BitmapDrawable)r.getDrawable(R.drawable.moon);
        BitmapDrawable bdPh=(BitmapDrawable)r.getDrawable(R.drawable.ph);
        Bitmap bitPark=bdPark.getBitmap();
        Bitmap bitMoon=bdMoon.getBitmap();
        Bitmap bitPh=bdPh.getBitmap();

        canvas.drawBitmap(bitPark,ParkX, ParkY,null);
        canvas.drawBitmap(bitMoon,MoonX, MoonY,null);
        canvas.drawBitmap(bitPh,PhX, PhY,null);
        ParkX += ParkSpeed;
        if( ParkX > dm.widthPixels ) {
            ParkX = -200;
            ParkY = random.nextInt(dm.heightPixels-800) + 100;
            MoonSpeed = random.nextInt(50) + 1;
        }
        MoonX += MoonSpeed;
        if( MoonX > dm.widthPixels ) {
            MoonX = -200;
            MoonY = random.nextInt(dm.heightPixels-800) + 100;
            MoonSpeed = random.nextInt(50) + 1;
        }
        PhX += PhSpeed;
        if( PhX > dm.widthPixels ) {
            PhX = -200;
            PhY = random.nextInt(dm.heightPixels-800) + 100;
            PhSpeed = random.nextInt(50) + 1;
        }

        invalidate();
    }
}
