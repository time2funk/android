package com.sadman.app.list_test;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity {

    AlertDialog.Builder builder;
    Toolbar toolbar = null;
    FloatingActionButton fab = null;
    ListView lv = null;
//    ListView {id, name, prof, sal}
    int selectedItemId = -1;

    DBHelper dbHelper;
    final String TAG = "MAIN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        dbHelper = new DBHelper(this);

        builder = new AlertDialog.Builder(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Add action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent i = new Intent(ScrollingActivity.this, AddActivity.class);
                startActivity(i);
            }
        });


        lv = (ListView) findViewById(R.id.list_id);
        populateListView(lv);
//        registerClickItemCallback(lv);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                builder.setMessage("Delete an item?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                TextView v = (TextView) view.findViewById(R.id.list_item_id);
                selectedItemId = Integer.parseInt( v.getText().toString() );

                return true;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView v = (TextView) view.findViewById(R.id.list_item_id);
                int workerId = Integer.parseInt( v.getText().toString() );
                Intent i = new Intent(ScrollingActivity.this, DetailActivity.class);
                i.putExtra("id", workerId);
                startActivity(i);
            }
        });
    }
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    if(selectedItemId>=0) {
                        delItem(selectedItemId);
                        selectedItemId = -1;
                    }
                    populateListView(lv);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    // No button clicked
                    // do nothing
                    break;
            }
        }
    };
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "MainActivity: onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "MainActivity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateListView(lv);
        Log.d(TAG, "MainActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "MainActivity: onPause()");
    }
    private void delItem(int rowID){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String TBL_NAME = "worker";
        db.execSQL("delete from "+TBL_NAME+" where id='"+rowID+"'");

        Log.d("SQLite", "row deleted, ID = " + rowID);
        db.close();
    }
    private void populateListView(ListView lv) {
//        ArrayList<ListObj> list = new ArrayList<>();
//        for(int i=0; i< 5; i++){
//            list.add(new ListObj(
//                    "Gogy"+i,
//                    "Developer",
//                    1000*i
//            ));
//        }
        ArrayList<ListObj> list = loadData();

        ObjAdapter objAdapter = new ObjAdapter(this, list);

        lv.setAdapter(objAdapter);
//        String[] items = {
//                "Nik",
//                "Bill",
//                "Rick"
//        };
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this,
//                R.layout.list_item,
//                items);
//        lv.setAdapter(adapter);
    }
    private ArrayList<ListObj> loadData(){      Log.d("SQLITE", "--- Rows in mytable: ---");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<ListObj> list = new ArrayList<>();
        Cursor c = db.query("worker", null, null, null, null, null, null);

        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int positionColIndex = c.getColumnIndex("position");
            int salaryColIndex = c.getColumnIndex("salary");

            do {
                int itemId = c.getInt(idColIndex);
                String itemName = c.getString(nameColIndex);
                String itemPosition = c.getString(positionColIndex);
                int itemSalary = c.getInt(salaryColIndex);
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d("SQLITE", "ID = " + itemId
                        + ", name = " + itemName
                        + ", position = " + itemPosition
                        + ", salary = " + itemSalary
                );
                list.add(new ListObj(itemName,itemPosition,itemSalary,itemId));
            } while (c.moveToNext());
        } else
            Log.d("SQLITE", "0 rows");
        c.close();
        db.close();
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        Log.d("STATE",""+id);
        if (id == R.id.action_settings) {
            Intent i = new Intent(ScrollingActivity.this, SettingsActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}