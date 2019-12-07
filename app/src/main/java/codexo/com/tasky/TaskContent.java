package codexo.com.tasky;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.security.PublicKey;

import codexo.com.tasky.database.DBHelper;
import codexo.com.tasky.database.TasksTable;

public class TaskContent extends AppCompatActivity {
    public static final String TASK_POSITION = "codexo.com.tasky.TaskContent";
    private DatePicker dp;
    Button mAdd, mDate, mTime;
    EditText mTitle, mDescription;
    private String name;
    private String description;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String str;
    private String mId;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_task_content);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            mId = extras.getString(TASK_POSITION);
            mTitle = findViewById(R.id.task_title);
            mTitle.setText("task id: " + mId);
        }



        mDescription = findViewById(R.id.task_content);
        mDate = findViewById(R.id.btn_date);
        mTime = findViewById(R.id.btn_time);
        mAdd = findViewById(R.id.add);
        dp = findViewById(R.id.date_picker);

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker();
            }
        });
        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePicker();
            }
        });
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });

    }

    //A method that calls the addTask() method to add task to the Database.
    private void addTask() {
        name = Strin g.valueOf(mTitle.getText());
        description = String.valueOf(mTitle.getText());
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        cursor = dbHelper.getTasks(db);

        DBHelper.addTask(db, name, description, "3", "09-10-2019");
        Intent intent = new Intent(TaskContent.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //Alert Dialog with a Date Picker
    public void DatePicker(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(TaskContent.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.datepicker, null);

        dialog.setView(dialogView);
        dialog.setCancelable(true);

        dialog.show();
    }

    //Alert Dialog with a Time Picker
    public void TimePicker(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(TaskContent.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.timepicker, null);

        dialog.setView(dialogView);
        dialog.setCancelable(true);

        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            openMainActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    //Opens the Main Activity
    private void openMainActivity(){
        Intent intent = new Intent(TaskContent.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
