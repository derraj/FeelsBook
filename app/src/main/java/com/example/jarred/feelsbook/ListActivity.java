package com.example.jarred.feelsbook;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// List View created with help from https://www.youtube.com/watch?v=ZEEYYvVwJGY
public class ListActivity extends AppCompatActivity {

    private ArrayList<Emotion> emoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // get our list of Emotions from the bundle passed through
        Bundle bundleObject = getIntent().getExtras();
        emoList = (ArrayList<Emotion>) bundleObject.getSerializable("emoList");
        List<String> emoString = emoList.stream().
                map(Emotion::toString).collect(Collectors.toList());

        Log.d("list", emoString.toString());

        ListView history = (ListView) findViewById(R.id.listview);
        history.setAdapter(new MyListAdapter(
                this, R.layout.list_item, emoString));





    }


    private class MyListAdapter extends ArrayAdapter<String> {
        private int layout;
        public MyListAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_item_thumbnail);
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                viewHolder.button = (Button) convertView.findViewById(R.id.deleteBtn);
                viewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Button was clicked for list item" + position, Toast.LENGTH_SHORT).show();
                    }
                });
                convertView.setTag(viewHolder);
            }
            else {
                mainViewholder = (ViewHolder) convertView.getTag();
                mainViewholder.title.setText(getItem(position));
            }
            return convertView;
        }

    }
    public class ViewHolder {
        ImageView thumbnail;
        TextView title;
        Button button;
    }

}

