package com.sadman.app.list_test;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by root on 11/1/17.
 */

public class Settings {
    Boolean position;
    Boolean salary;
    
    String head_color;
    String text_color;
    String text_size;

//    Context context;
    SharedPreferences sPref;
    
    final String SAVED_POSITION_SHOW = "position";
    final String SAVED_SALARY_SHOW = "salary";
    final String SAVED_HEAD_COLOR = "head_color";
    final String SAVED_TEXT_COLOR = "text_color";
    final String SAVED_TEXT_SIZE = "text_size";
    
    Settings(Context context){
        sPref = context.getSharedPreferences("Settings",context.MODE_PRIVATE);

        position    = sPref.getBoolean(SAVED_POSITION_SHOW, false);
        salary      = sPref.getBoolean(SAVED_SALARY_SHOW, false);
        head_color  = sPref.getString(SAVED_HEAD_COLOR, "ORANGE");
        text_color  = sPref.getString(SAVED_TEXT_COLOR, "BLACK");
        text_size   = sPref.getString(SAVED_TEXT_SIZE, "30sp");
    }
}
