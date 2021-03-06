package com.encloode.tick_tock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Riko Hamblin on 05/30/16.
 */
public class displayTotalTimeWorked_listAdapter_two extends ArrayAdapter<Employee> implements Serializable {

    private DateTime start;
    private DateTime end;
    private ArrayList<Integer> hiddenItems;
    private ArrayList<Employee> list;
    private int size;

    /*
    * this adapter hides views that have no time worked during period
    * implented using the following guide:
    * http://stackoverflow.com/questions/18771923/listview-hide-some-items
    */

    public displayTotalTimeWorked_listAdapter_two(Context context, ArrayList<Employee> employees, DateTime start, DateTime end) {
        super(context,0, employees);
        this.start = start;
        this.end = end;
        size = employees.size();
        list = employees;
        hiddenItems = new ArrayList<>();
        hide();
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        // Get the data item for this position
        Employee employee = getItem(position);

            //Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {

                convertView = LayoutInflater.from(getContext()).inflate(R.layout.displaytotaltime_two_listformat, parent, false);
            }
            //Lookup view for data population
            TextView tv_Name = (TextView) convertView.findViewById(R.id.displaytotaltime_two_listName);
            TextView tv_Hours = (TextView) convertView.findViewById(R.id.displaytotaltime_two_listHours);
            TextView tv_Minutes = (TextView) convertView.findViewById(R.id.displaytotaltime_two_listMinutes);

            //populate the data into the template view using the data object
            tv_Name.setText(employee.getName());
            tv_Hours.setText("" + employee.getTimeSummary().totalHoursDuringInterval(start, end));
            tv_Minutes.setText("" + employee.getTimeSummary().totalMinutesDuringInterval(start, end));

            //return the completed view to render on the screen
            return convertView;

    }

    @Override
    public int getCount() {
        return size - hiddenItems.size();
    }

    @Override
    public Employee getItem(int position)
    {
        //this bypasses employees with no time worked
        for (Integer hiddenIndex : hiddenItems) {
            if(hiddenIndex <= position)
                position++;
        }

        return list.get(position);
    }

    public void hide(){

        //this builds a list with all indexes that have no time worked
        for (int i = 0; i < size; i++) {
            if(list.get(i).getTimeSummary().totalTimeDuringInterval(start,end)==0){
                hiddenItems.add(i);
            }

        }


    }
}
