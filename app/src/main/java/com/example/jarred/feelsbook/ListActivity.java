package com.example.jarred.feelsbook;

import android.content.Context;
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

    public ArrayList<Emotion> emoList;
    public List<String> emoString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // get our list of Emotions from the bundle passed through
        Bundle bundleObject = getIntent().getExtras();
        emoList = (ArrayList<Emotion>) bundleObject.getSerializable("emoList");

        //https://stackoverflow.com/a/30130428
        emoString = emoList.stream().map(Emotion::toString).collect(Collectors.toList());

        Log.d("list", emoString.toString());

        ListView history = (ListView) findViewById(R.id.listview);

        ArrayAdapter<String> adapter = new MyListAdapter(this, R.layout.list_item, emoString);
        history.setAdapter(adapter);


    }


    private class MyListAdapter extends ArrayAdapter<String> {
        private int layout;
        public MyListAdapter(Context context, int resource, List<String> objects) {
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
                viewHolder.deleteBtn = (Button) convertView.findViewById(R.id.deleteBtn);
                viewHolder.editBtn = (Button) convertView.findViewById(R.id.editBtn);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
            mainViewholder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    emoList.remove(position);
                    emoString.remove(position);
                    notifyDataSetChanged();
                }
            });
            mainViewholder.editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Edit was clicked for list item" + position, Toast.LENGTH_SHORT).show();
                }
            });
            mainViewholder.title.setText(getItem(position));


            return convertView;
        }

    }
    public class ViewHolder {
        ImageView thumbnail;
        TextView title;
        Button deleteBtn;
        Button editBtn;
    }

}

