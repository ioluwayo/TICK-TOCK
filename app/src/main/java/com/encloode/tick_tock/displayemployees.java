package com.encloode.tick_tock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class displayemployees extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayemployees);

        populateList();
    }

    public void onClickExit(View view){
        //just go back to ownermenu
        Intent intent = new Intent(this,ownermenu.class);
        startActivity(intent);
    }

    private void populateList(){
        //Construct the data source
        ArrayList<Employee> arrayOfEmployees = Global.accessDatabase().getEmployeeList();
        //create the Adapter to convert the array to view
        ListAdapter ourAdapter = new ListAdapter(this,arrayOfEmployees);
        //attach the adapter to listView
        ListView listView=(ListView) findViewById(R.id.list);
        listView.setAdapter(ourAdapter);

        //determine what shows when no items are in list
        TextView whenEmpty = (TextView) findViewById(R.id.displayEmployeesEmptyView);
        listView.setEmptyView(whenEmpty);

    }
    @Override
    public void onBackPressed() {}
}
