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
 * Created by nanit on 2016-05-23.
 */
public class KpuPhoneActivityChild1 extends ListActivity {
    private String[][] strData = {
            {"교무팀",	"031-8041-0011"},
            {"학사팀",	"031-8041-0021"},
            {"교수학습지원센터", "031-8041-0067"},
            {"창조융합교육센터", "031-8041-0063"},
            {"기획예산팀",	"031-8041-0211"},
            {"정책개발팀",	"031-8041-0221"},
            {"학생지원팀",	"031-8041-0072"},
            {"취업지원센터",	"031-8041-0121"},
            {"학생종합서비스센터",	"031-8041-0091"},
            {"예비군연대본부",	"031-8041-0101"},
            {"입학관리팀",	"1588-2036"},
            {"홍보팀",	"031-8041-0291"},
            {"총무팀",	"031-8041-0141"},
            {"회계팀",	"031-8041-0151"},
            {"시설관리팀",	"031-8041-0161"},
            {"전산정보팀",	"031-8041-0279"},
            {"학술정보팀",	"031-8041-0772"},
            {"정보통신팀",	"031-8041-0283"},
            {"국제교류센터",	"031-8041-0792"},
            {"한국어교육센터",	"031-8041-0808"},
            {"대학원교학팀",	"031-8041-0341"}
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
