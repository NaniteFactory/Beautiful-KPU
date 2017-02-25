package com.example.administrator.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

/**
 * Created by yong on 2016-05-29.
 */
public class RatingDialog extends Dialog {

    Context context;
    RatingBar ratingbar;
    Button btn_ratingok;
    MediaPlayer mp = null;

    public RatingDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rating_dialog);
        setTitle("앱 평가하기");

        ratingbar = (RatingBar)findViewById(R.id.ratingbar);
        btn_ratingok = (Button)findViewById(R.id.btn_ratingok);

        btn_ratingok.setOnClickListener(new OKListener());
    }

    private class OKListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            ((MainActivity)context).setRating(ratingbar.getRating());
            RatingDialog.this.dismiss();
        }
    }
}

