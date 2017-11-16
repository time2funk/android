package com.sadman.app.list_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by root on 11/1/17.
 */

public class ObjAdapter  extends BaseAdapter{
    Context context;
    LayoutInflater lInflater;
    ArrayList<ListObj> objs;

    ObjAdapter(Context context, ArrayList<ListObj> objs){
        this.context = context;
        this.objs = objs;
        lInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objs.size();
    }

    @Override
    public Object getItem(int position) {
        return objs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) view = lInflater.inflate(R.layout.list_item2,parent, false);

        ListObj lo = getObj(position);

        ((TextView) view.findViewById(R.id.list_item_name)).setText(lo.name);
        ((TextView) view.findViewById(R.id.list_item_salary)).setText(" $ "+lo.salary);
        ((TextView) view.findViewById(R.id.list_item_position)).setText(" [ "+lo.position+" ]");
        ((TextView) view.findViewById(R.id.list_item_id)).setText(""+lo.id);
        return view;
    }

    ListObj getObj(int position){
        return ((ListObj) getItem(position));
    }
}
