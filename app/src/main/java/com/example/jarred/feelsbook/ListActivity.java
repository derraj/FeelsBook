package com.example.jarred.feelsbook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// List View created with help from https://www.youtube.com/watch?v=ZEEYYvVwJGY
public class ListActivity extends AppCompatActivity {
    final Context context = this;
    public ArrayList<Emotion> emoList;
    public List<String> emoString;
    public Counter count;
    private EditText result;
    public ListView history;

    // Set your date format here
    public DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    // When back button is pressed, reload FeelsBookActivity
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, FeelsBookActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // get our list of Emotions from the bundle passed through
        Bundle bundleObject = getIntent().getExtras();
        emoList = (ArrayList<Emotion>) bundleObject.getSerializable("emoList");
        emoString = emoList.stream().map(Emotion::toString).collect(Collectors.toList());
        updateDate();

        history = (ListView) findViewById(R.id.listview);
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
                // remove emotion from emotion list and string from string list
                @Override
                public void onClick(View v) {
                    emoList.remove(position);
                    emoString.remove(position);
                    notifyDataSetChanged();
                    saveData();

                }
            });
            mainViewholder.editBtn.setOnClickListener(new View.OnClickListener() {

                // Pop up created with help from https://stackoverflow.com/a/35861189
                // Edit button opens a pop-up window from custom_dialog.xml
                @Override
                public void onClick(View v) {
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.custom_dialog, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    alertDialogBuilder.setView(promptsView);

                    final EditText userInput = (EditText) promptsView
                            .findViewById(R.id.editTextDialogUserInput);

                    alertDialogBuilder
                            .setCancelable(false)

                            // creates button that modifies the date with values
                            // from user input
                            .setPositiveButton("Edit Date",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            try{

                                                Date parsedDate = sdf.parse(userInput.getText().toString());

                                                // run next code if above code causes no errors
                                                emoList.get(position).setDate(userInput.getText().toString());
                                                updateDate();
                                                notifyDataSetChanged();
                                                saveData();

                                            }
                                            catch(Exception e){
                                                // show small popup error message
                                                Toast.makeText(getContext(), "Wrong format. (Example date: 2018-11-05 20:22)", Toast.LENGTH_LONG).show();
                                                dialog.cancel();
                                            }


                                        }
                                    })

                            // creates a button that closes pop-up
                            .setNeutralButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    })

                            // creates a button that modifies the comment with values
                            // from user input
                            .setNegativeButton("Edit Comment",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            // Change the comment with user inputted text
                                            emoList.get(position).setComment(userInput.getText().toString());
                                            emoString.set(position, emoList.get(position).toString());
                                            notifyDataSetChanged();
                                            saveData();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

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

    // save emotion list
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();

        String json2 = gson.toJson(emoList);
        editor.putString("emotion object list", json2);
        editor.apply();
    }

    // sort the list of Emotions by date.
    // Once sorted, list of Strings is emptied, and new list of strings are pushed into it
    // using a temporary list. This is done since our adapter is using that list, so we can't
    // create a new one.
    public void updateDate(){
        Collections.sort(emoList, new Comparator<Emotion>() {
            @Override
            public int compare(Emotion o1, Emotion o2) {
                try {
                    return sdf.parse(o1.getDate()).compareTo(sdf.parse(o2.getDate()));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });

        // Update adapter values
        emoString.clear();
        List<String> newList = new ArrayList<>();
        newList = emoList.stream().map(Emotion::toString).collect(Collectors.toList()); //https://stackoverflow.com/a/30130428
        emoString.addAll(newList);

    }
}

