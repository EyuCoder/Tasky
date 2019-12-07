package codexo.com.tasky.database;

public class TasksTable {

    //Initializes all variables needed for database.
    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_DATE = "duedate";
    public static final String COLUMN_STATUS = "stat";

    public static final String[] ALL_COLUMNS =
            {TABLE_TASKS, COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_PRIORITY, COLUMN_DATE, COLUMN_STATUS};
    public static final String SQL_CREATE =
            "CREATE TABLE "+ TABLE_TASKS+"(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    COLUMN_NAME + " TEXT, "+
                    COLUMN_DESCRIPTION +" TEXT, "+
                    COLUMN_PRIORITY +" TEXT, "+
                    COLUMN_STATUS + " TEXT, "+
                    COLUMN_DATE + " NUMERIC);";
    public static final String SQL_DELETE = "DROP TABLE "+ TABLE_TASKS;

}
