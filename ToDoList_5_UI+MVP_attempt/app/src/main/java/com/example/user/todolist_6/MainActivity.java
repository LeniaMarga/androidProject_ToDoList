package com.example.user.todolist_6;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    dbHelper dbHelper;
    ArrayAdapter<String> mAdapter;
    ListView taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new dbHelper(this);

        taskList = findViewById(R.id.taskList);

        loadTaskList();

    }

    private void loadTaskList() {
        ArrayList<String> task_list = dbHelper.getTaskList();
        if(mAdapter == null){
            mAdapter = new ArrayAdapter<String>(this, R.layout.item, R.id.title, task_list);
            taskList.setAdapter(mAdapter);
        }
        else{
            mAdapter.clear();
            mAdapter.addAll(task_list);
            mAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_task:
                final EditText taskEditText = new EditText(this);
                LayoutInflater addTask = LayoutInflater.from(this);
                final View entryView = addTask.inflate(R.layout.input, null);
                //text_entry is an Layout XML file containing two text field to display in alert dialog
                final EditText input1 = (EditText) entryView.findViewById(R.id.title);
                final EditText input2 = (EditText) entryView.findViewById(R.id.details);
//        input1.setText("DefaultValue", TextView.BufferType.EDITABLE)
//        input2.setText("DefaultValue", TextView.BufferType.EDITABLE)
                AlertDialog.Builder dialog = new AlertDialog.Builder(this)

                        .setTitle("Αdd Task")
                        .setView(entryView)
                        .setPositiveButton("Save",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Log.i("AlertDialog","TextEntry 1 Entered "+input1.getText().toString());
                                        Log.i("AlertDialog","TextEntry 2 Entered "+input2.getText().toString());
                                        String title = input1.getText().toString();
                                        String details= input2.getText().toString();
                                        dbHelper.insertNewTask(title, details);
                                        loadTaskList();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {
                                    }
                                });
                dialog.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.title);
        String task = String.valueOf(taskTextView.getText());
        dbHelper.deleteTask(task);
        loadTaskList();
    }


//    public void getToDo(View listItem){
//        View parent = (View) listItem.getParent();
//        TextView taskTextView = (TextView) parent.findViewById(R.id.title);
//        String title = String.valueOf(taskTextView.getText());
//        String details = dbHelper.getTaskDetails(title);
//
//        Intent intent = new Intent(this, Info_Activity.class );
//        intent.putExtra("Details", details);
//        startActivity(intent);
//    }

}