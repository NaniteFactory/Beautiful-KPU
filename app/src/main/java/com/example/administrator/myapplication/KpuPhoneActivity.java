package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by nanit on 2016-05-23.
 */
public class KpuPhoneActivity extends Activity {
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_phone);

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.strArr, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("교무행정")) {
                    startActivity(new Intent(KpuPhoneActivity.this, KpuPhoneActivityChild1.class));
                } else if (parent.getItemAtPosition(position).toString().equals("TIP")) {
                    startActivity(new Intent(KpuPhoneActivity.this, KpuPhoneActivityChild2.class));
                } else if (parent.getItemAtPosition(position).toString().equals("대학원")) {
                    startActivity(new Intent(KpuPhoneActivity.this, KpuPhoneActivityChild3.class));
                } else if (parent.getItemAtPosition(position).toString().equals("학과사무실")) {
                    startActivity(new Intent(KpuPhoneActivity.this, KpuPhoneActivityChild4.class));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
