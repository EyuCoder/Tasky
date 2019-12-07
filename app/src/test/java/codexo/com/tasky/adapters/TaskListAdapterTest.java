package codexo.com.tasky.adapters;

import android.graphics.Paint;
import android.graphics.Typeface;

import org.junit.Test;

import codexo.com.tasky.R;
import codexo.com.tasky.database.DBHelper;

import static org.junit.Assert.*;

public class TaskListAdapterTest{

    @Test
    public void updateStatus(String stat, String s){
        DBHelper.updateStatus(db, id, status);

    }
}