package com.dylan.sqlite_demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dylan.sqlite_demo.CustomViewListener.SwipeDismissListViewTouchListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listViewRecords;
    UserSQLHelper userSQLHelper;
    List<UserRecord> values;
    public static final int EDIT_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.TB);
        setSupportActionBar(mToolbar);

        listViewRecords = (ListView) findViewById(R.id.list_view_content_main);
        listViewRecords.setOnItemClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_main);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                displayToast("Favorite button pressed");
                break;
            case R.id.action_settings:
                displayToast("Settings button pressed");
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (!values.isEmpty()) {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra(getResources().getString(R.string.user_record), values.get(position));
            startActivityForResult(intent, EDIT_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK && null != data) {
            displayToast(data.getStringExtra("MESSAGE"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    private void updateList() {
        userSQLHelper = new UserSQLHelper(this);
        values = userSQLHelper.getAllUsers();
        RelativeLayout relativeLayout = findViewById(R.id.layout_no_record);
        if (values.isEmpty()) {
            relativeLayout.setVisibility(View.VISIBLE);
        }else{
            relativeLayout.setVisibility(View.GONE);
            UserRecordAdapter mAdapter = new UserRecordAdapter(this, R.layout.user_record, values);
            listViewRecords.setAdapter(mAdapter);

            SwipeDismissListViewTouchListener touchListener =
                    new SwipeDismissListViewTouchListener(listViewRecords,
                            new SwipeDismissListViewTouchListener.DismissCallbacks() {
                                @Override
                                public boolean canDismiss(int position) {
                                    return true;
                                }

                                @Override
                                public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                    for (int position : reverseSortedPositions) {
                                        int deletedId = userSQLHelper.deleteUser(values.get(position).getId());
                                        displayToast(String.format("User detail number %d have been deleted", deletedId));
                                        mAdapter.remove(mAdapter.getItem(position));
                                    }
                                    mAdapter.notifyDataSetChanged();
                                }
                            });
            listViewRecords.setOnTouchListener(touchListener);
        }

    }

}