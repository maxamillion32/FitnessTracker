package com.example.FitnessTracker;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.sql.SQLException;

/**
 * Created by Eman on 03/09/2015.
 */
public class Hammercurls extends Exercise{
    @Override
    public  void onCreate(Bundle savedInstanceState) {
        //Creates the Content view specifically for Hammercurls. Opens Hammercurls Table.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hammercurls);
        try {
            openTrackDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        populateTrackListView();
        longClick();

    }

    @Override
    public void insert() {
        //Inserts Hammercurls stats into database.
        try {
            dbtrack.insertHammercurls(dateinput.getText().toString(), weightinput.getText().toString(), repsinput.getText().toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void populateTrackListView() {
        //Gets stats from database.
        Cursor cursor = dbtrack.getHammercurls();
        String[] fromFieldnames = new String[]{DBTracker.COL_1, DBTracker.COL_2, DBTracker.COL_3};
        int[] toViewIDs = new int[]{R.id.btnDate, R.id.txtWeight, R.id.btnReps};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.headerlayout, cursor, fromFieldnames, toViewIDs, 0);
        ListView myList = (ListView) findViewById(R.id.statsview);
        //Log.d(getPackageName(),myList != null ? "not null":"Null");
        myList.setAdapter(myCursorAdapter);
    }

    @Override
    public void delete() {
        //Delete the whole Hammercurls database.
        dbtrack.deleteAllHammercurls();
    }

    @Override
    public boolean longClick() {
        //Long click Delete. This deletes a specific row upon long click.
        ListView myList = (ListView)findViewById(R.id.statsview);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dbtrack.deleteRowHammercurls(l);
                populateTrackListView();

            }
        });return true;
    }
}
