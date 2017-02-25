package com.example.administrator.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MemoActivity extends ListActivity {

    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;
    private static final int ACTIVITY_OPTION = 2;

    private static final int DELETE_ID = Menu.FIRST;

    private DBAdapter mDbHelper;

    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_activity);
        mDbHelper = new DBAdapter (this);
        mDbHelper.open();
        inputSearch = (EditText)findViewById(R.id.inputSearch);
        inputSearch.setTextColor(Color.WHITE);
        fillData();
        registerForContextMenu(getListView());
        Button addnote = (Button)findViewById(R.id.addnotebutton);
        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNote();
            }
        });

        //서치 기능
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Get all of the notes from the database and create the item list
               // findData(s);
                doSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_create:
                createNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createNote() {
        //노트 생성 인텐트
        Intent i = new Intent(this, MemoAddView.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, MemoAddView.class);
        i.putExtra(DBAdapter.KEY_ROWID, id);
        startActivityForResult(i, ACTIVITY_EDIT);
    }


    private void doSearch(String search) {

        //mDbHelper.searchEditions();
        Cursor notesCursor = mDbHelper.searchNote(search);

        //startManagingCursor(cur);
        startManagingCursor(notesCursor);


        String[] from = new String[] { DBAdapter.KEY_TITLE ,DBAdapter.KEY_DATE};
        int[] to = new int[] { R.id.text1 ,R.id.date_row};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes =
                new SimpleCursorAdapter(this, R.layout.note_row, notesCursor, from, to);
        setListAdapter(notes);

    }

    private void fillData() {
        // Get all of the notes from the database and create the item list
        Cursor notesCursor = mDbHelper.fetchAllNotes();
        startManagingCursor(notesCursor);


        String[] from = new String[] { DBAdapter.KEY_TITLE ,DBAdapter.KEY_DATE};
        int[] to = new int[] { R.id.text1,R.id.date_row};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes =
                new SimpleCursorAdapter(this, R.layout.note_row, notesCursor, from, to);
        setListAdapter(notes);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info
                        = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                mDbHelper.deleteNote(info.id);
                fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }

    private void ToastMakeText(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

}
