package com.sadman.app.list_test;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    int workerId;

    Spinner sp_position;
    TextView tv_name;
    TextView tv_salary;
    Button btn_cansel;
    Button btn_save;

    String[] arr_position = {
            "Developer",
            "Designer",
            "Content Manager",
            "SEO",
            "QA",
            "Manager"
    };
    ArrayAdapter<String> ad_position;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //load id, data
        dbHelper = new DBHelper(this);
        Intent intent = getIntent();
        workerId = intent.getIntExtra("id",0);
        ListObj workerData = loadData(workerId);

        //textView name
        tv_name = (TextView) findViewById(R.id.detail_name);
        tv_name.setText(workerData.name);

        //textView salary
        tv_salary = (TextView) findViewById(R.id.detail_salary);
        tv_salary.setText( String.valueOf(workerData.salary) );

        //spinner
        sp_position = (Spinner) findViewById(R.id.detail_position);
        ad_position = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,
                arr_position);
        ad_position.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sp_position.setAdapter(ad_position);
        for(int i=0; i<arr_position.length; i++){
            if(arr_position[i].equals( workerData.position ) )
                sp_position.setSelection(i);
        }

        // button save
        btn_save = (Button) findViewById(R.id.detail_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges(workerId);
                finish();
            }
        });

        // // button cansel
        btn_cansel = (Button) findViewById(R.id.detail_cansel);
        btn_cansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private ListObj loadData(int _id){
        ListObj obj = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("worker", null, "id=?", new String[] { String.valueOf(_id) }, null, null, null);

        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int positionColIndex = c.getColumnIndex("position");
            int salaryColIndex = c.getColumnIndex("salary");

            do {
                int id = c.getInt(idColIndex);
                String name = c.getString(nameColIndex);
                String position = c.getString(positionColIndex);
                int salary = c.getInt(salaryColIndex);

                Log.d("SQLITE", "ID = " + id
                        + ", name = " + name
                        + ", position = " + position
                        + ", salary = " + salary
                );
                obj = new ListObj(name, position, salary, id);
            } while (false);
        } else
            Log.d("SQLITE", "0 rows");

        c.close();
        db.close();
        return obj;
    }
    private void saveChanges(int _id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
//
        String name = tv_name.getText().toString();
        String position = sp_position.getSelectedItem().toString();
        int salary = Integer.parseInt( tv_salary.getText().toString() );
//
        cv.put("name", name);
        cv.put("position", position);
        cv.put("salary", salary);
//
        long rowID = db.update( "worker", cv, "id=?", new String[] { String.valueOf(_id) } );
        Log.d("SQLite", "row inserted, ID = " + rowID);

        db.close();
    }
}
