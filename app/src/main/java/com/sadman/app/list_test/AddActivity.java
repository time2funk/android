package com.sadman.app.list_test;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddActivity extends AppCompatActivity {

    Button addBtn;
    EditText etName;
    EditText etSalary;
    Spinner spPosition;

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
        setContentView(R.layout.activity_add);
        dbHelper = new DBHelper(this);

        etName = (EditText) findViewById(R.id.item_add_name);
        etSalary = (EditText) findViewById(R.id.item_add_salary);

        ad_position = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,
                arr_position);
        ad_position.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spPosition = (Spinner) findViewById(R.id.item_add_position) ;
        spPosition.setAdapter(ad_position);

        addBtn = (Button) findViewById(R.id.item_add_save);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues cv = new ContentValues();

                String name = etName.getText().toString();
                String position = spPosition.getSelectedItem().toString();
                int salary = Integer.parseInt( etSalary.getText().toString() );

                cv.put("name", name);
                cv.put("position", position);
                cv.put("salary", salary);

                long rowID = db.insert("worker", null, cv);
                Log.d("SQLite", "row inserted, ID = " + rowID);


                db.close();
                finish();
//                this.finishActivity();

            }
        });

    }

}
