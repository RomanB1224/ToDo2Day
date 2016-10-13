package edu.orangecoastcollege.cs273.rbarron11.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper database;
    private List<Task> taskList;
    private TaskListAdapter taskListAdapter;

    private EditText taskEditText;
    private ListView taskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for now (temporary) delete old database and then create the new
       //  this.deleteDatabase(DBHelper.DATABASE_TABLE);

        //lets make the DBHelper reference:
        database = new DBHelper(this);

        //fill the list with tasks from the database
        taskList = database.getAllTasks();

        //create our custom task list adapter
        //(We want to associate the adapter with the context, the layout and the list)
        taskListAdapter = new TaskListAdapter(this, R.layout.task_item, taskList);

        //Connect the list view with our layout
        taskListView = (ListView) findViewById(R.id.taskListView);

        //Associate the adapter with the list view
        taskListView.setAdapter(taskListAdapter);


    }
}
