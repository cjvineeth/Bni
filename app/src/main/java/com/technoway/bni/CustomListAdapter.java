package com.technoway.bni;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;



/**
 * Created by vineeth on 2/25/2016.
 */
public class CustomListAdapter extends BaseAdapter{

   ArrayList<String> nameList;
   ArrayList<String> catagoryList;
   ArrayList<String> chaspterList;
    ArrayList<String> telephoneList;
   Activity activity;

    LayoutInflater inflater;
    /*public CustomListAdapter(Activity activity,ArrayList<String> idList,ArrayList<String> itemList) {
        this.idList = idList;
        this.activity = activity;
        this.itemList = itemList;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }*/


    public CustomListAdapter(Activity activity,ArrayList<String> nameList, ArrayList<String> catagoryList, ArrayList<String> chaspterList, ArrayList<String> telephoneList) {
        this.nameList = nameList;
        this.catagoryList = catagoryList;
        this.chaspterList = chaspterList;
        this.telephoneList = telephoneList;
        this.activity = activity;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.spinner_adapter_layout, null);
            TextView name_view= (TextView) vi.findViewById(R.id.name_text);
            TextView catagory_view= (TextView) vi.findViewById(R.id.catagory_text);

            TextView chapter_view= (TextView) vi.findViewById(R.id.chapter_text);
            TextView phone_view= (TextView) vi.findViewById(R.id.phone_text);


            name_view.setText(nameList.get(position));
            catagory_view.setText(catagoryList.get(position));
            chapter_view.setText(chaspterList.get(position));
            phone_view.setText(telephoneList.get(position));





        }





        return vi;
    }
}
