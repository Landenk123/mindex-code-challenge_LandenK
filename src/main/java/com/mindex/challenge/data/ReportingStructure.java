package com.mindex.challenge.data;

/**
 * The Reporting Structure is used to store and employee and the number of reports they have based on their direct reports.
 */
public class ReportingStructure {
    private Employee employee;
    private int numberOfReports;

    public ReportingStructure() {
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getNumberOfReports() {
        return numberOfReports;
    }

    //number of reports is calculated by counting the number of direct reports down the hierarchy
    public void calculateNumberOfReports(){
        numberOfReports = countReportsInHierarchy(employee);
    }

    //recursive method to count all reports by moving through the hierarchy of direct reports
    private int countReportsInHierarchy(Employee employee){
        //if there are no direct reports, return 0
        if(employee.getDirectReports() == null) {
            return 0;
        }

        //if there are direct reports, count them and move down the hierarchy
        int countOfReports = employee.getDirectReports().size();
            for(Employee directReport : employee.getDirectReports()) {
                countOfReports = countOfReports + countReportsInHierarchy(directReport);
            }
        return countOfReports;
    }
}
