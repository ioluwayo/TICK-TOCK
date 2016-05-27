/**
 *
 * @author Riko Hamblin
 */
package com.encloode.tick_tock;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class EmployeeDatabase implements Serializable {

    public ArrayList<Employee> employees ;

    static public int maxEmployeeSize = 15;
    static public int listOfAvailableIDs[];

    public EmployeeDatabase() {
        this.employees = new ArrayList<>();
    }

    /**
     *  THIS METHOD TAKES A PIN AND CHECKS THAT IT DOESNT EXIST
     *  false = PIN IS TAKEN , true = PIN IS AVAILABLE
     */
      public boolean pinValid (int pin) {

        /*
            Go through entire database of employees if the
            pin is found then it is invalid
            if it IS found then pin is valid
        */


        for (int i=0; i<employees.size(); i++) {
    //!employees.get(i).isEmpty() &&
            if(employees.get(i).getPin()== pin )
                return false;

        }
        return true;



    }

    /**
     *  THIS METHOD TAKES AN ID AND CHECKS THAT IT DOESNT EXIST
     *  false = ID IS TAKEN , true = ID IS AVAILABLE
     */
    public boolean idValid (int id) {

        /*
            Go through entire database of employees if the
            pin is found then it is invalid
            if it IS found then pin is valid
        */


        for (int i=0; i<employees.size(); i++) {
            //!employees.get(i).isEmpty() &&
            if(employees.get(i).getID()== id )
                return false;

        }
        return true;



    }



    //THIS METHOD TAKES AN ID AND CHANGES THE NAME OF THAT EMPLOYEE TO String name
    public void setNameOf(int id, String name) {

                getEmployee(id).setName(name);

    }

    //THIS METHOD TAKES AN ID AND SETS THE PIN OF THAT EMPLOYEE TO int newPin
    public void setPinOf(int id, int newPin) {

        getEmployee(id).setPin(newPin);

    }

    //THIS METHOD TAKES AN ID AND RETURNS THE CURRENT NAME OF THAT EMPLOYEE
    public String getNameOf(int id) {


                return  getEmployee(id).getName();

    }

    //THIS METHOD TAKES AN ID AND RETURNS THE CURRENT PIN OF THAT EMPLOYEE
    public int getPinOf(int id) {

                return  getEmployee(id).getPin();

    }
/*

//THIS METHOD TAKES AN ID AND SETS THE IN TIME OF THAT EMPLOYEE TO int time
    public void setInTimeOf(int id, int time) {
        getEmployee(id).setSignInTime(time);
    }

    //THIS METHOD TAKES AN ID AND SETS THE OUT TIME OF THAT EMPLOYEE TO int time
    public void setOutTimeOf(int id, int time) {
        getEmployee(id).setSignOutTime(time);

    }

 //THIS METHOD TAKES AN ID AND RETURNS THE CURRENT IN TIME OF THAT EMPLOYEE
    public int getInTimeOf(int id) {

                return  getEmployee(id).getSignOutTime();
    }

    //THIS METHOD TAKES AN ID AND RETURNS THE CURRENT OUT TIME OF THAT EMPLOYEE
    public int getOutTimeOf(int id) {
                return  getEmployee(id).getSignOutTime();
    }

    //THIS METHOD TAKES AN ID AND RETURNS:
    // THE TOTAL HOURS WORKED THUS FAR BY THAT EMPLOYEE
    public int getHoursWorkedTodayFor(int id) {
                return  getEmployee(id).getHoursWorkedToday();
    }

    //THIS METHOD TAKES AN ID AND RETURNS:
    // THE TOTAL HOURS WORKED BY THAT EMPLOYEE FOR THE WEEK SPECIFIED
    public int getHoursWorkedDuringWeekFor(int id, int weekWanted) {

                return  getEmployee(id).getHoursWorkedForWeek(weekWanted);
    }*/

    //THIS METHOD TAKES AN ID AND RETURNS:
    // WHETHER THAT EMPLOYEE IS SIGNED IN, true = yes they are, false = no they're not
    public boolean isSignedIn(int id) {
        return getEmployee(id).isSignedIn();
    }

    //THIS METHOD TAKES AN ID AND TOGGLES THE signedIn FIELD OF THAT EMPLOYEE
    public void toggleSignInFor (int id) {

        getEmployee(id).toggleSignIn();

    }

    //THIS METHOD TAKES AN ID AND RETURNS THE EMPLOYEE OBJECT ASSOCIATED WITH IT
    public Employee getEmployee(int id) {

        for (int i=0; i< employees.size() ; i++) {
            if( employees.get(i).getID() == id) {
                return employees.get(i);
            }

        }

        return null;
    }

    //THIS METHOD TAKES A pin AND RETURNS THE EMPLOYEE OBJECT ASSOCIATED WITH IT
    public Employee getEmployeebyPin(int pin) {

        for (int i=0; i< employees.size() ; i++) {
            if( employees.get(i).getPin() == pin) {
                return employees.get(i);
            }

        }

        return null;
    }

    //THIS METHOD ADDS AN EMPLOYEE TO THE DATABASE
    public boolean addEmployee(Employee e) {
        try {
            employees.add(e);
            return true;
        } catch (Exception ex) { return false ;}

    }

    //THIS METHOD DELETES AN EMPLOYEE TO THE DATABASE
    public boolean deleteEmployee(Employee e) {

        try {
            //this makes the deleted id available to be taken again
            EmployeeDatabase.listOfAvailableIDs[e.getID()] = e.getID();

            Employee.numOfEmployees -- ;
            employees.remove(Global.accessDatabase().getEmployee(e.getID()));
            return true;

        } catch (Exception ex) { return false ;}

    }

    //THIS METHOD RETURNS THE CURRENT AMOUNT OF EMPLOYEES
    static int getNumOfCurrentEmployees(){
        return Employee.numOfEmployees;
    }

    //THIS METHOD RETURNS THE ARRAY LIST CONTAINING ALL THE EMPLOYEES IN THE DATABASE
    public ArrayList<Employee> getEmployeeList(){

      return employees;
    }

    //added for timeSummary
    public void setInTimeOf(int id, int week, int day, Date time) {
    getEmployee(id).setInTime(week,day,time);
    }

    public void setOutTimeOf(int id, int week, int day, Date time) {
        getEmployee(id).setOutTime(week,day,time);
    }

    public void setMinutesWorkedFor(int id, int week, int day, int minsWorked) {
getEmployee(id).setMinutesWorkedInDay(week,day,minsWorked);
    }

    public Date getInTimeOf(int id, int week, int day, int signInPosition) {
        return getEmployee(id).getTimeSummary().inTime[week][day][signInPosition];
    }

    public Date getOutTimeOf(int id, int week, int day,int signOutPosition) {
        return getEmployee(id).getTimeSummary().outTime[week][day][signOutPosition];
    }

    public int getMinutesWorkedFor(int id, int week, int day) {
        return getEmployee(id).getTimeSummary().minutesWorked[week][day];
    }

    public void clearIn_Out_timesFor(int id, int week, int day){
        for(int i=0;i< getEmployee(id).numOfSignIn ;i++){
            getEmployee(id).setInTime(week,day,i,null);
            getEmployee(id).setOutTime(week,day,i,null);
        }
    }

}
