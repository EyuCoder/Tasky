package codexo.com.tasky.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "tasky";
    private static final int DB_VERSION = 1;

    //DBHelper Constructor
    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    //creates the database with tables the first time the database is requested.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TasksTable.SQL_CREATE);
        addTask(db, "coding is fun isnt it", "android", "3", "02-09-2019");

    }

    //Called when there is a change to the db Structure
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TasksTable.SQL_DELETE);
        onCreate(db);
    }

    //Adds new task to the database
    public static void addTask(SQLiteDatabase db, String name, String description, String priority, String date){
        String zero = "0";
        ContentValues cv = new ContentValues();
        cv.put(TasksTable.COLUMN_NAME, name);
        cv.put(TasksTable.COLUMN_DESCRIPTION, description);
        cv.put(TasksTable.COLUMN_PRIORITY, priority);
        cv.put(TasksTable.COLUMN_DATE, date);
        cv.put(TasksTable.COLUMN_STATUS, zero);

        db.insert(TasksTable.TABLE_TASKS, null, cv);
    }

    //Updates a task in the database
    public static void updateTask(SQLiteDatabase db, String id, String name, String description, String priority, String date){
        ContentValues cv = new ContentValues();
        cv.put(TasksTable.COLUMN_NAME, name);
        cv.put(TasksTable.COLUMN_DESCRIPTION, description);
        cv.put(TasksTable.COLUMN_PRIORITY, priority);
        cv.put(TasksTable.COLUMN_DATE, date);

        db.update("tasks", cv, "_id = ?", new String[]{id});
    }

    public static void updateStatus(SQLiteDatabase db, String id, String status){
        ContentValues cv = new ContentValues();
        cv.put(TasksTable.COLUMN_STATUS, status);

        db.update("tasks", cv, "_id = ?", new String[]{id});
    }

    //Deletes a task from the database
    public static void deleteTask(SQLiteDatabase db, String id){
        db.delete("tasks", "_id = ?", new String[]{id});
    }

    //Runs a query to get all tasks from the databasein Ascending order.
    public static Cursor getTasks(SQLiteDatabase db){
        // Define a projection that specifies which columns from the database you will actually use after this query.
        String[] projection = {
                "_id", TasksTable.COLUMN_NAME,
                TasksTable.COLUMN_DESCRIPTION,
                TasksTable.COLUMN_PRIORITY,
                TasksTable.COLUMN_DATE,
                TasksTable.COLUMN_STATUS
        };

        String sortOrder = TasksTable.COLUMN_STATUS +" ASC, " + TasksTable.COLUMN_ID + " DESC";
        return db.query(
                TasksTable.TABLE_TASKS,
                projection,
                null, null, null, null,
                sortOrder
        );
    }

}
