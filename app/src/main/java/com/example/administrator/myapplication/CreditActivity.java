package com.example.administrator.myapplication;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Administrator on 2016-05-23.
 */
public class CreditActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreditView v = new CreditView(this);
        setContentView(v);
    }
}
