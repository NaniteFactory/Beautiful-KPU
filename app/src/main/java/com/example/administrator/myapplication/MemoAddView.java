package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016-05-13.
 */
public class MemoAddView extends Activity {

    public static int numTitle = 1;
    public static String curDate = "";
    public static String curDate_hour = "";
    public static String curText = "";
    private EditText mTitleText;
    private EditText mBodyText;
    private TextView mDateText;
    private Long mRowId;

    private Cursor note;

    private DBAdapter mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDbHelper = new DBAdapter(this);
        mDbHelper.open();

        setContentView(R.layout.memo_add_activity);
        setTitle(R.string.app_name);

        mTitleText = (EditText) findViewById(R.id.title);
        mBodyText = (EditText) findViewById(R.id.body);
        mDateText = (TextView) findViewById(R.id.notelist_date);

        curDate = getDateFormat("M'/'d");
        curDate_hour = getDateFormat("y'.'M'.'d h':'m");
        mDateText.setText(""+curDate_hour);


        mRowId = (savedInstanceState == null) ? null :
                (Long) savedInstanceState.getSerializable(DBAdapter.KEY_ROWID);
        if (mRowId == null) {
            Bundle extras = getIntent().getExtras();
            mRowId = extras != null ? extras.getLong(DBAdapter.KEY_ROWID)
                    : null;
        }

        populateFields();

    }

    private String getDateFormat(String str) {
        String Date;

        long msTime = System.currentTimeMillis();
        Date curDateTime = new Date(msTime);

        SimpleDateFormat formatter = new SimpleDateFormat(str);
        Date = formatter.format(curDateTime);

        return Date;
    }

    public static class LineEditText extends EditText{
        // we need this constructor for LayoutInflater
        public LineEditText(Context context, AttributeSet attrs) {
            super(context, attrs);

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putSerializable(DBAdapter.KEY_ROWID, mRowId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.memomenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.memo_cancel:
                if(note != null){
                    note.close();
                    note = null;
                }
                if(mRowId != null){
                    mDbHelper.deleteNote(mRowId);
                }
                finish();

                return true;
            case R.id.memo_save:
                saveState();
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveState() {
        String title = mTitleText.getText().toString();
        String body = mBodyText.getText().toString();

        if(mRowId == null){
            long id = mDbHelper.createNote(title, body, curDate_hour);
            if(id > 0){
                mRowId = id;
            }else{
                Log.e("saveState","failed to create note");
            }
        }else{
            if(!mDbHelper.updateNote(mRowId, title, body, curDate_hour)){
                Log.e("saveState","failed to update note");
            }
        }
    }


    private void populateFields() {
        if (mRowId != null) {
            note = mDbHelper.fetchNote(mRowId);
            startManagingCursor(note);
            mTitleText.setText(note.getString(
                    note.getColumnIndexOrThrow(DBAdapter.KEY_TITLE)));
            mBodyText.setText(note.getString(
                    note.getColumnIndexOrThrow(DBAdapter.KEY_BODY)));
            curText = note.getString(
                    note.getColumnIndexOrThrow(DBAdapter.KEY_BODY));
        }
    }


}
