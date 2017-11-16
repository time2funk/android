package com.sadman.app.list_test;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences sPref;

    CheckBox positionShow;
    CheckBox salaryShow;
    Button saveBtn;
    Spinner headColor;
    Spinner textColor;
    Spinner textSize;

    final String SAVED_POSITION_SHOW = "position";
    final String SAVED_SALARY_SHOW = "salary";
    final String SAVED_HEAD_COLOR = "head_color";
    final String SAVED_TEXT_COLOR = "text_color";
    final String SAVED_TEXT_SIZE = "text_size";

    String[] arr_head_colors = {"RED","WHITE","ORANGE", "BLUE"};
    String[] arr_text_colors = {"BLACK","GRAY","GREEN"};
    String[] arr_text_size = {"30sp","20sp","15sp"};

    ArrayAdapter<String> ad_head_colors;
    ArrayAdapter<String> ad_text_colors;
    ArrayAdapter<String> ad_text_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);

        Settings sett = new Settings(this);
        Log.d("CONSOLE",""+sett.head_color);
        Log.d("CONSOLE",""+sett.text_color);
        Log.d("CONSOLE",""+sett.text_size);
        Log.d("CONSOLE",""+sett.position);
        Log.d("CONSOLE",""+sett.salary);
        Log.d("SETT","loaded");
//        ???????

        positionShow = (CheckBox) findViewById(R.id.set_position);
        positionShow.setChecked(sett.position);

        salaryShow = (CheckBox) findViewById(R.id.set_salary);
        salaryShow.setChecked(sett.salary);

        ad_head_colors = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,
                arr_head_colors);
        ad_head_colors.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        ad_text_colors = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,
                arr_text_colors);
        ad_text_colors.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        ad_text_size = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,
                arr_text_size);
        ad_text_size.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        headColor = (Spinner) findViewById(R.id.set_head_color);
        headColor.setAdapter(ad_head_colors);
//        headColor.setPrompt("color");

        for(int i=0; i<arr_head_colors.length; i++){
            if(arr_head_colors[i].equals( sett.head_color.toString() ) )
                headColor.setSelection(i);
        }

        textColor = (Spinner) findViewById(R.id.set_text_color);
        textColor.setAdapter(ad_text_colors);
//        textColor.setPrompt("color");

        for(int i=0; i<arr_text_colors.length; i++){
            if(arr_text_colors[i].equals( sett.text_color.toString() ) )
                textColor.setSelection(i);
        }

        textSize = (Spinner) findViewById(R.id.set_text_size);
        textSize.setAdapter(ad_text_size);
//        textSize.setPrompt("color");

        for(int i=0; i<arr_text_size.length; i++){
            if(arr_text_size[i].equals( sett.text_size.toString() ) )
                textSize.setSelection(i);
        }

        saveBtn = (Button) findViewById(R.id.set_btn_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettings();
                Snackbar.make(view, "Settings are Saved", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        Log.d("SETT","btn clck "+v.getId());
//        Log.d("SETT","the btn is "+R.id.set_btn_save);
//
//        if(v.getId() == R.id.set_btn_save){
//            Log.d("SETT","btn clck");
//            saveSettings();
//        }
//    }

    void saveSettings(){
        sPref = getSharedPreferences("Settings",MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

//        Log.d("SETT","saved"+positionShow.isEnabled());
//        Log.d("SETT","saved"+salaryShow.isEnabled());
//        Log.d("SETT","saved");
//        Log.d("SETT","saved");
        ed.putBoolean(SAVED_POSITION_SHOW, positionShow.isChecked());
        ed.putBoolean(SAVED_SALARY_SHOW, salaryShow.isChecked());
        ed.putString(SAVED_HEAD_COLOR, headColor.getSelectedItem().toString());
        ed.putString(SAVED_TEXT_COLOR, textColor.getSelectedItem().toString());
        ed.putString(SAVED_TEXT_SIZE, textSize.getSelectedItem().toString());

        ed.commit();
        Log.d("SETT","saved");
    }
}
