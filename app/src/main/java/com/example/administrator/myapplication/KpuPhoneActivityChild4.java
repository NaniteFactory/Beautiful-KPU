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
public class KpuPhoneActivityChild4 extends ListActivity {
    private String[][] strData = {
            {"기계공학과 학과사무실","031-8041-0400"},
            {"기계설계공학과 학과사무실","031-8041-0420"},
            {"메카트로닉스공학과 학과사무실","031-8041-0450"},
            {"전자공학부 학과사무실","031-8041-0470"},
            {"컴퓨터공학부 학과사무실","031-8041-0510"},
            {"게임공학부 학과사무실","031-8041-0550"},
            {"신소재공학과 학과사무실","031-8041-0580"},
            {"생명화학공학과 학과사무실","031-8041-0610"},
            {"디자인학부 학과사무실","031-8041-0660"},
            {"경영학부 학과사무실","031-8041-0670"},
            {"나노-광공학과학과사무실","031-8041-0710"},
            {"에너지-전기공학과 학과사무실","031-8041-0690"},
            {"지식융합학부 학과사무실","031-8041-0730"},
            {"산학협력학부 행정지원팀","031-8041-0634"}
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
