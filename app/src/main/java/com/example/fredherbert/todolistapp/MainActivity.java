package com.example.fredherbert.todolistapp;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DbHelper dbHelper;
    ArrayAdapter<String> mAdapter;
    ListView lstTask;

    FloatingActionButton addFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);

        lstTask = (ListView)findViewById(R.id.lstTask);
        addFab = (FloatingActionButton)findViewById(R.id.addFAB);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        TextView textviewdate = (TextView)findViewById(R.id.datetxt);
        textviewdate.setText(currentDate);

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText taskEditText = new EditText(MainActivity.this);
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);



                mBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String task = String.valueOf(taskEditText.getText());
                        dbHelper.insertNewTask(task);
                        loadTaskList();
                        dialog.dismiss();

                    }
                });

                mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mBuilder.setTitle("Jipangie Kazi");
                mBuilder.setMessage("Kipi ungependa kukifanya kwa siku ya leo");
                mBuilder.setView(taskEditText);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });


        loadTaskList();

    }

    private void loadTaskList() {
        ArrayList<String> taskList = dbHelper.getTaskList();
        if (mAdapter==null){
            mAdapter = new ArrayAdapter<String>(this,R.layout.row,R.id.tasktitle,taskList);
            lstTask.setAdapter(mAdapter);
        }
        else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void deleteTask(View view){
        View parent = (View)view.getParent();
        TextView taskTextView = (TextView)findViewById(R.id.tasktitle);
        String task = String.valueOf(taskTextView.getText());
        dbHelper.deleteTask(task);
        loadTaskList();
    }

}
