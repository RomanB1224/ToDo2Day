package edu.orangecoastcollege.cs273.rbarron11.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for now (temporary) delete old database and then create the new
        this.deleteDatabase(DBHelper.DATABASE_TABLE);

        //lets make the DBHelper reference:
        DBHelper db = new DBHelper(this);

        //lets make a new task and add it to the database:
        /*Task newTask = new Task (1, "Study for CS273 Midterm", 0);
        db.addTask(newTask);*/
        //or vvvv

        db.addTask(new Task(1, "Study for CS 273 Midterm", 0));
        db.addTask(new Task(2, "Finish yo Dam Superheroes Project", 0));
        db.addTask(new Task(3, "Play YuGiOh", 0));
        db.addTask(new Task(4, "Stop playing YuGiOh", 0));
        db.addTask(new Task(5, "What the hell, keep playing YuGiOh", 0));

        //lets get all the tasks from the database and print them with Log.i()
        ArrayList<Task> allTasks = db.getAllTasks();
        //loop through each task
        for(Task singleTask : allTasks)
            Log.i("DATABASE TASK" , singleTask.toString());

    }
}
