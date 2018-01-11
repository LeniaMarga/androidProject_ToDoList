package com.example.user.mytodolist;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.mytodolist.db.TaskContract;
import com.example.user.mytodolist.db.TaskDbHelper;

import java.util.ArrayList;

public class ToDoList_MainActivity extends AppCompatActivity {

    private static final String TAG = "ToDoList_MainActivity";
    private TaskDbHelper mHelper;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new TaskDbHelper(this);
        mTaskListView = (ListView) findViewById(R.id.list);
//        mTaskListView.setAdapter(toDoAdapter);
        updateUI();


    }

        private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},          ///TaskContract.TaskEntry.COL_TASK_NUMBER,
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            taskList.add(cursor.getString(idx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.activity_item,
                    R.id.title,
                    taskList);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }


        cursor.close();
        db.close();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){         ///create options menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_add_task:
//                Log.d(TAG, "Add a new task");
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new task")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                            }

                        })

                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                updateUI();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }




//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        if (item.getItemId()== R.id.action_add_task){
//            Toast.makeText(MainActivity.this, R.string.menu_toast_hello, Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        else if(item.getItemId() == R.id.action_hello_again){
//            Toast.makeText(MainActivity.this, R.string.menu_toast_hello_again, Toast.LENGTH_SHORT).show();
//        }
//        return super.onOptionsItemSelected(item);  ////returns false
//    }


    public void getToDo(View listItem){
        ToDo toDo = (ToDo) listItem.getTag();
        Log.d("Title:", toDo.getTitle());

        Intent intent = new Intent(this, ToDo_activity.class );
        intent.putExtra("to Do", toDo);
        startActivity(intent);
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry.COL_TASK_TITLE + " = ?",
                new String[]{task});
        db.close();
        updateUI();
    }
}
