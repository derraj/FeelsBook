package com.example.jarred.feelsbook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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


        Log.d("list", emoString.toString());

        history = (ListView) findViewById(R.id.listview);

        ArrayAdapter<String> adapter = new MyListAdapter(this, R.layout.list_item, emoString);
        history.setAdapter(adapter);

        /*
        // Go back to FeelsBookActivity
        Button backBtn = (Button) findViewById(R.id.backBtn);
        View.OnClickListener backListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(getApplicationContext(), FeelsBookActivity.class);
                startActivity(goBack);
            }
        };
        backBtn.setOnClickListener(backListener);
        */


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

                    //String emo_string = emoList.get(position).getEmotion();
                    //count.decrement(emo_string);


                    emoList.remove(position);
                    emoString.remove(position);
                    notifyDataSetChanged();

                    saveData();

                }
            });
            mainViewholder.editBtn.setOnClickListener(new View.OnClickListener() {

                // Pop up created with help from https://stackoverflow.com/a/35861189
                @Override
                public void onClick(View v) {
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.custom_dialog, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    final EditText userInput = (EditText) promptsView
                            .findViewById(R.id.editTextDialogUserInput);

                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("Edit Date",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            try{
                                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                                Date parsedDate = formatter.parse(userInput.getText().toString());

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
                            .setNeutralButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    })
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
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();

        String json2 = gson.toJson(emoList);
        editor.putString("emotion object list", json2);
        editor.apply();
    }

    public void updateDate(){
        Collections.sort(emoList, new Comparator<Emotion>() {
            DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");//or your pattern
            @Override
            public int compare(Emotion o1, Emotion o2) {
                try {
                    return f.parse(o1.getDate()).compareTo(f.parse(o2.getDate()));
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

    public Date validateDateFormat(String dateToValdate) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        //To make strict date format validation
        formatter.setLenient(false);
        Date parsedDate = null;
        try {
            parsedDate = formatter.parse(dateToValdate);
            System.out.println("++validated DATE TIME ++"+formatter.format(parsedDate));

        } catch (ParseException e) {
            //Handle exception
        }
        return parsedDate;
    }

}

