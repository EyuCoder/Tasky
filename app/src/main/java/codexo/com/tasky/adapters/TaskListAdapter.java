package codexo.com.tasky.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import codexo.com.tasky.R;
import codexo.com.tasky.TaskContent;
import codexo.com.tasky.database.DBHelper;
import codexo.com.tasky.database.TasksTable;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private Cursor mCursor;
    private final Context mContext;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    //Adapters Constructor
    public TaskListAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }


    //We inflate the RecyclerView into a view and pass it to the ViewHolder function.
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.task_items, parent, false);
        return new TaskViewHolder(itemView);
    }


    //binding data from the SQLite Cursor to the views
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

//        String priority = mCursor.getString(mCursor.getColumnIndex(TasksTable.COLUMN_PRIORITY));
        int status = mCursor.getInt(mCursor.getColumnIndex(TasksTable.COLUMN_STATUS));
        String id = mCursor.getString(mCursor.getColumnIndex(TasksTable.COLUMN_ID));
        long swipeId = mCursor.getLong(mCursor.getColumnIndex(TasksTable.COLUMN_ID));
        String name = mCursor.getString(mCursor.getColumnIndex(TasksTable.COLUMN_NAME));
        String date = mCursor.getString(mCursor.getColumnIndex(TasksTable.COLUMN_DATE));

        System.out.println(status);

        holder.mName.setText(name);
        holder.mDate.setText(date);
        holder.mId = id;
        holder.itemView.setTag(swipeId);
        holder.stat = status;
        holder.position = position;

        if (holder.stat == 0) {
            holder.mName.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            holder.mStatus.setBackgroundResource(R.drawable.icon_unchecked);

        } else if (holder.stat == 1) {
            holder.mName.setPaintFlags(holder.mName.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            holder.mStatus.setBackgroundResource(R.drawable.icon_checked);
        }

    }

    //gets the length of the cursor
    //in this case instead of using '.length', we uses the Cursors own function ".getCount()"
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }

    }

    //This is the view holder function
    public class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView mName, mDate, mPriority;
        public Button mStatus;
        public int stat;
        public String mId;
        public int position;
        public CardView rvItem;

        public TaskViewHolder(@NonNull final View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.task_title);
            mDate = itemView.findViewById(R.id.task_date);
            mStatus = itemView.findViewById(R.id.status);
            rvItem = itemView.findViewById(R.id.items_cardview);

            rvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, TaskContent.class);
                    intent.putExtra(TaskContent.TASK_POSITION, mId);
                    System.out.println("this is : " + mId);
                    mContext.startActivity(intent);
                }
            });

            mStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("hello: " + mId);
                    if (stat == 0) {
                        updateStatus(mId, "1");
                        mName.setPaintFlags(mName.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                        mStatus.setBackgroundResource(R.drawable.icon_checked);
                        ;
                        System.out.println("is checked");

                    } else if (stat == 1) {
                        updateStatus(mId, "0");
                        mName.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        mStatus.setBackgroundResource(R.drawable.icon_unchecked);
                        System.out.println("is Unchecked");
                    }
                    swapCursor(DBHelper.getTasks(db));
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, TaskContent.class);
                    intent.putExtra(TaskContent.TASK_POSITION, mId);
                    mContext.startActivity(intent);
                }
            });
            this.setIsRecyclable(false);
        }


    }

    public void updateStatus(String id, String status) {
        dbHelper = new DBHelper(mContext);
        db = dbHelper.getWritableDatabase();
        cursor = dbHelper.getTasks(db);

        DBHelper.updateStatus(db, id, status);
    }
}