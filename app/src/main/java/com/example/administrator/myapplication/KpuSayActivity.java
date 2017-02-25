package com.example.administrator.myapplication;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nanit on 2016-05-22.
 */
public class KpuSayActivity extends ListActivity {
    // constants
    private interface KpuSayArticleKey {
        String Id = "ID";
        String No = "No";
        String Date = "Date"; // 날짜
        String Category = "Category"; // 가족 연애 학교 기타
        String Content = "Content"; // 내용
    }
    // vars
    private ArrayList< HashMap<String, String> > mHashMapArrayList;
    //
    private String mStrPageId;
    private String mStrClientSecret;
    private String mStrClientId;
    private String mStrAccessToken;
    private int mNumArticles;
    private String mStrPageUrl;
    //
    private JSONObject mJsonObject;
    private JSONArray mJsonArray;
    private JSONParser mJsonParser;
    private AsyncTask mJsonDownloader;
    private int mnDownload;
    // functions
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setDividerHeight(30);
        try {
            initFbSdkSetUp(); // sdk 초기화
            initDownloadJson(); // 다운로드 스레드 시작
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//onCreate()
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String[] identifier = mHashMapArrayList.get(position).get(KpuSayArticleKey.Id).split("_");
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + mStrPageId + "/posts/" + identifier[1])));
        Log.d("Clicked Item "+position, position + " " + position + " " + position + " " + position + " " + position + " " + position + " " );
    }
    //
    private void initFbSdkSetUp() throws Exception {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
    private void initDownloadJson() {
        new AsyncTask(){ // 메인스레드에서 그냥 하려고 했는데, 이게 네트워크 관련 작업이라서 개별 스레드에서 처리 필요
            private ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                progressDialog = new ProgressDialog(KpuSayActivity.this);
                progressDialog.setTitle("불러오는 중...");
                progressDialog.setMessage("산대전 불러온다. 기다려라.");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(true);
                progressDialog.show();
            } // onPreExecute()
            @Override
            protected Object doInBackground(Object[] params) {
                mStrPageId = "856489241044583";
                mStrClientId = "670933036381006";
                mStrClientSecret = "6a7417497949e01ada23cac744fbb0a1";
                mNumArticles = 100;
                try {
                    mStrAccessToken = new BufferedReader(
                            new InputStreamReader(
                                    new URL("https://graph.facebook.com/oauth/access_token?client_id=" + mStrClientId
                                            + "&client_secret=" + mStrClientSecret + "&grant_type=client_credentials").openStream()
                            )
                    ).readLine();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mStrPageUrl = "https://graph.facebook.com/v2.2/" + mStrPageId + "/feed?" + mStrAccessToken + "&limit=" + mNumArticles;
                // 주소 획득
                mJsonParser = new JSONParser();
                mJsonObject = mJsonParser.makeHttpRequest(mStrPageUrl, "GET", null);
                if (mJsonObject != null) {
                    Log.d("JSON result", mJsonObject.toString());
                } else {
                    Log.d("JSON Failed", "nah");
                }
                return null;
            } // doInBackground()
            @Override
            protected void onPostExecute(Object object) {
                if (mJsonObject != null) { // 성공하면 토스트 냠냠
                    try {
                        mJsonArray = mJsonObject.getJSONArray("data");
                        Log.d("Success!", mJsonArray.getJSONObject(0).getString("message"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(KpuSayActivity.this, "다운로드 완료했어요!", Toast.LENGTH_SHORT).show();
                } else { // 망하면 액티비티 종료
                    KpuSayActivity.this.finish();
                    try {
                        Log.d("Failure", "Failed to get JSON object");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    setmHashMapArrayList(); // 리스트뷰 어댑터가 참조할 수 있는 자료구조 만든다
                    setListAdapter();  // 로드 끝났으니까 이제 리스트뷰를 디자인한다
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 로딩상자 끄기
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                mJsonDownloader = new AsyncDownloader();
                mJsonDownloader.execute();
            } // onPostExecute()
        }.execute();
    } //initDownloadJson()
    private void setmHashMapArrayList() throws Exception{
        // 다운로드해서 얻은 JSON을 이제 리스트뷰 어댑터가 참조할 수 있는 자료구조에 넣는다
        mHashMapArrayList = new ArrayList<HashMap<String, String>>();
        Log.d("go", "go");
        for(int i=0;i<mJsonArray.length();i++){
            HashMap<String,String> hashMap = new HashMap<String,String>();
            //
            String messageTemp;
            if(mJsonArray.getJSONObject(i).has("message")) {
                messageTemp = mJsonArray.getJSONObject(i).getString("message");
            } else if (mJsonArray.getJSONObject(i).has("story")){
                messageTemp = "(사진 공유 게시물)";
            } else {
                messageTemp = "(사진은 보여주기 시렁시렁~~)";
            }
            BufferedReader reader = new BufferedReader(new StringReader(messageTemp));
            String no = reader.readLine();
            String message = "";
            String category = "(미분류)";
            if(no.charAt(0)=='#') {
                category = reader.readLine();
            } else {
                message = no;
                no = "(번호 없음)";
            }
            for (String line = ""; line != null; line = reader.readLine()) {
                message += line;
            }
            if (message == "") {
                message = "(삭제됨)";
            }
            Log.d("message", message);
            //
            String[] dateTemp = mJsonArray.getJSONObject(i).getString("created_time").split("\\+");
            String date = dateTemp[0];
            //
            hashMap.put(KpuSayArticleKey.Id, mJsonArray.getJSONObject(i).getString("id"));
            hashMap.put(KpuSayArticleKey.No, no);
            hashMap.put(KpuSayArticleKey.Category, category);
            hashMap.put(KpuSayArticleKey.Date, date);
            hashMap.put(KpuSayArticleKey.Content, message);
            mHashMapArrayList.add(hashMap);
        }
    }
    private void setListAdapter(){
        setListAdapter(new BaseAdapter() { // 로드 끝났으니까 이제 리스트뷰를 디자인한다
            @Override
            public int getCount() { // 전체크기
                return mHashMapArrayList.size();
            }

            @Override
            public Object getItem(int position) { // 몰라
                return null;
            }

            @Override
            public long getItemId(int position) { // 몰라
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) { // 겟겟
                View rowView = getLayoutInflater().inflate(R.layout.listitem, null, true);
                ((TextView) rowView.findViewById(R.id.tvNo)).setText((String) ((HashMap) mHashMapArrayList.get(position)).get(KpuSayArticleKey.No));
                ((TextView) rowView.findViewById(R.id.tvId)).setText((String) ((HashMap) mHashMapArrayList.get(position)).get(KpuSayArticleKey.Id));
                ((TextView) rowView.findViewById(R.id.tvDate)).setText((String) ((HashMap) mHashMapArrayList.get(position)).get(KpuSayArticleKey.Date));
                ((TextView) rowView.findViewById(R.id.tvCategory)).setText((String) ((HashMap) mHashMapArrayList.get(position)).get(KpuSayArticleKey.Category));
                ((TextView) rowView.findViewById(R.id.tvContent)).setText((String) ((HashMap) mHashMapArrayList.get(position)).get(KpuSayArticleKey.Content));
                return rowView;
            }
        }); // setListAdapter()
    }
    // inner classes
    class AsyncDownloader extends AsyncTask {
        final int FAIL = 0;
        final int SUCC = 1;

        @Override
        protected void onPreExecute() {
            mnDownload++;
        }
        @Override
        protected Object doInBackground(Object[] params) {
            try {
                mStrPageUrl = mJsonObject.getJSONObject("paging").getString("next");
                mJsonObject = mJsonParser.makeHttpRequest(mStrPageUrl, "GET", null);
                if (mJsonObject != null) {
                    Log.d("JSON result", mJsonObject.toString());
                } else {
                    Log.d("JSON Failed", "nah");
                }
            } catch (Exception e){
                return FAIL;
            }
            return SUCC;
        }
        @Override
        protected void onPostExecute(Object o) {
            if ((int)o == SUCC) { // 성공하면 토스트 냠냠
                try {
                    for(int i = 0 ; i<mNumArticles ; i++) {
                        mJsonArray.put(mJsonObject.getJSONArray("data").getJSONObject(i));
                    }
                    Log.d("Success!", mJsonArray.getJSONObject(mnDownload * 100).getString("message"));
                    //
                    setmHashMapArrayList();
                    ((BaseAdapter)KpuSayActivity.this.getListAdapter()).notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(KpuSayActivity.this, "산대전 " + (100 + (mnDownload*100)) + "개 게시물 업데이트", Toast.LENGTH_SHORT).show();
            } else { // 망하면 재귀
                mnDownload--;
            }
            if (mnDownload >= 3) {return;} // 400개만 받자
            mJsonDownloader = new AsyncDownloader();
            mJsonDownloader.execute();
        }
    } // inner class
}//class Activity
