package com.mindex.challenge.data;
import com.mindex.challenge.service.EmployeeService;
import java.util.List;
import java.util.ArrayList;

public class Employee {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String position;
    private String department;
    private List<Employee> directReports;

    public Employee() {
    }


    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Employee> getDirectReports() {
        
        return directReports;
    }


    public void fillInDirectReports(EmployeeService employeeRep){
        if (this.directReports == null){
            return;
        }
        List<Employee> fullReports = new ArrayList();
        for (int i=0; i < directReports.size(); i++){
            Employee employee = directReports.get(i);
            Employee fullEmployee = employeeRep.read(employee.employeeId);
            System.out.println(fullEmployee);
            fullReports.add(fullEmployee);
        }
        this.directReports = fullReports;

    }
    public void setDirectReports(List<Employee> directReports) {
        this.directReports = directReports;
    }
}
