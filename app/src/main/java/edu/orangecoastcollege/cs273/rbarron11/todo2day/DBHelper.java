package edu.orangecoastcollege.cs273.rbarron11.todo2day;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class DBHelper extends SQLiteOpenHelper {

    //TASK 1: DEFINE THE DATABASE VERSION, NAME AND TABLE NAME
    private static final String DATABASE_NAME = "ToDo2Day"; //Data bases can have multiple tables
    static final String DATABASE_TABLE = "Tasks"; // this specific table is called tasks
    private static final int DATABASE_VERSION = 1;


    //TASK 2: DEFINE THE FIELDS (COLUMN NAMES) FOR THE TABLE
    private static final String KEY_FIELD_ID = "id";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_IS_DONE = "is_done";


    public DBHelper (Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase database){
        String table = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIELD_DESCRIPTION + " TEXT, "
                + FIELD_IS_DONE + " INTEGER" + ")";
        database.execSQL (table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

    //create a method to add a brand new task to the database
    public void addTask(Task newTask)
    {
        //step 1) create reference
        SQLiteDatabase db = this.getWritableDatabase();

        //step 2) make a key value pair  for each data type you want to insert
        ContentValues values = new ContentValues();
       // values.put(KEY_FIELD_ID, newTask.getId());
        values.put(FIELD_DESCRIPTION, newTask.getDescription());
        values.put(FIELD_IS_DONE, newTask.getIsDone());

        // step 3) insert the values into our database
        db.insert(DATABASE_TABLE, null,values);

        //Step 4) always remember to close database after usage
        db.close();
    }

    //Create a method to get all the tasks in the database;
    public ArrayList<Task> getAllTasks()
    {
        //create referece
        SQLiteDatabase db = this.getReadableDatabase();

        //make new empty array list
        ArrayList<Task> allTask = new ArrayList<>();

        //Query the database for all records (all rows) and all fields (all columns)
        //return type of a Query is a Cursor

        Cursor results = db.query(DATABASE_TABLE, null, null, null, null, null, null);

        //loop through results, create Tasks objects, add to ArrayList
        if(results.moveToFirst())
        {
            do{
                int id = results.getInt(0);
                String description = results.getString(1);
                int isDone = results.getInt(2);
                allTask.add(new Task(id, description, isDone));
            }while(results.moveToNext());
        }

        return allTask;
    }

    public void updateTask(Task existingTask)
    {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();

        values.put(FIELD_DESCRIPTION, existingTask.getDescription());
        values.put(FIELD_IS_DONE, existingTask.getIsDone());


        db.update(DATABASE_TABLE,
                values,
                KEY_FIELD_ID + "=?",
                new String[] {String.valueOf(existingTask.getId())});


        db.close();
    }

    public void deleteAllTasks()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, null, null);
        db.close();
    }
}
