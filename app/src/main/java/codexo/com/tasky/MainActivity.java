package codexo.com.tasky;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import codexo.com.tasky.adapters.TaskListAdapter;
import codexo.com.tasky.database.DBHelper;
import codexo.com.tasky.database.TasksTable;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;
    private TaskListAdapter taskListAdapter;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                openTaskContentActivity();
            }
        });

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        cursor = dbHelper.getTasks(db);

        final RecyclerView recyclerView = findViewById(R.id.tasks_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskListAdapter = new TaskListAdapter(this, cursor);
        recyclerView.setAdapter(taskListAdapter);

        taskListAdapter.notifyDataSetChanged();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeTask((long)viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);
    }
    private void removeTask(long id){
        String mId = String.valueOf(id);
        db.delete(TasksTable.TABLE_TASKS, TasksTable.COLUMN_ID+"="+mId, null);
        taskListAdapter.swapCursor(DBHelper.getTasks(db));
    }
    //Opens the TaskContent activity.
    private void openTaskContentActivity(){
        Intent intent = new Intent(MainActivity.this, TaskContent.class);
        startActivity(intent);
    }

    //closes the database and cursor onDestroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
