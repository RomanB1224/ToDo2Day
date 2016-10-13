package edu.orangecoastcollege.cs273.rbarron11.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

        //add one dummy task
        //database.addTask(new Task("Dummy task", 1));

        //fill the list with tasks from the database
        taskList = database.getAllTasks();

        //create our custom task list adapter
        //(We want to associate the adapter with the context, the layout and the list)
        taskListAdapter = new TaskListAdapter(this, R.layout.task_item, taskList);

        //Connect the list view with our layout
        taskListView = (ListView) findViewById(R.id.taskListView);

        //Associate the adapter with the list view
        taskListView.setAdapter(taskListAdapter);

        //Connect EditText with our layout
        taskEditText = (EditText) findViewById(R.id.taskEditText);

    }

    public void addTask(View view)
    {
        String description = taskEditText.getText().toString();
        if(description.isEmpty())
        {
            Toast.makeText(this, "Task description cannot be empty ya dummy.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            Task newTask = new Task(description, 0);
            //make a new task
            //taskList.add(newTask);
            //add task to task adapter
            taskListAdapter.add((newTask));
            //add task to database
            database.addTask(newTask);
            taskEditText.setText("");
        }
    }

    public void changeTaskStatus (View view)
    {

        if(view instanceof CheckBox) {
            CheckBox selectedCheckBox = (CheckBox) view;
            Task selectedTask = (Task) selectedCheckBox.getTag();
            selectedTask.setIsDone(selectedCheckBox.isChecked() ? 1 : 0);
            //update it to the database
            database.updateTask(selectedTask);
        }
    }

    public void clearAllTasks(View view)
    {
        //Clear the list
        taskList.clear();
        //Delete all records (Tasks) in the data base
        database.deleteAllTasks();
        //tell the taskListAdapter to update itself
        taskListAdapter.notifyDataSetChanged();
    }
}
