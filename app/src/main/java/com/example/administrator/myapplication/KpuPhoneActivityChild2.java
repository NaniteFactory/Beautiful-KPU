package com.example.administrator.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nanit on 2016-05-24.
 */
public class KpuPhoneActivityChild2 extends ListActivity {
    private String[][] strData = {
            {"어학원",	"031-8041-0811"},
            {"생활관",	"031-8041-0082"},
            {"평생교육원", "031-8041-0822"}
    };
    private ArrayList<HashMap<String,String>> mHashMapArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHashMapArrayList = new ArrayList<HashMap<String, String>> ();
        for(int i=0;i<strData.length;i++) {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("item1", strData[i][0]);
            hashMap.put("item2", strData[i][1]);
            mHashMapArrayList.add(hashMap);
        }
        setListAdapter(new SimpleAdapter(this, mHashMapArrayList, android.R.layout.simple_list_item_2,
                new String[]{"item1", "item2"}, new int[]{android.R.id.text1, android.R.id.text2}));
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + strData[position][1])));
    }
}
