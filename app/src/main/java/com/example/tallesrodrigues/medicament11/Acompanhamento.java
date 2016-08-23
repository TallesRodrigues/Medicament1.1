package com.example.tallesrodrigues.medicament11;

/**
 * Created by TallesRodrigues on 8/14/2016.
 */

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Acompanhamento extends AppCompatActivity {

    //ui control
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    MyAdapter adapter;
    List<Medicine> medicinesList;

    DatabaseController crud = null;
    Cursor cursorData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        collapsingToolbarLayout.setTitle("Medicament");
        setSupportActionBar(toolbar);

        //recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        initializeData();

        adapter = new MyAdapter(medicinesList);

        //set adapter
        recyclerView.setAdapter(adapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "You clicked on the fab", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeData() {
        medicinesList = new ArrayList<>();

        crud = new DatabaseController(getBaseContext());
        cursorData = crud.loadData();

        /*Add Mock data in case of empty database*/
        if (cursorData.getCount() == 0) {
            medicinesList.add(new Medicine("Rivotril 1", "200mg", 2, 3, 2, R.mipmap.pills));
        }

        cursorData.moveToPosition(-1);

        while (cursorData.moveToNext()) {

            medicinesList.add((new Medicine(cursorData.getString(3),
                    cursorData.getString(4),
                    cursorData.getInt(12),
                    cursorData.getInt(5),
                    cursorData.getInt(10),
                    R.mipmap.pills)));
        }

        //Load Data from database and set to Medicine Object
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