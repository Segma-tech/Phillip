package imc.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;

public class App {
    private Connection con = null;

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException var4) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;

        for(int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");

            try {
                Thread.sleep(10000L);
                this.con = DriverManager.getConnection("jdbc:mysql://db:3306/employees?useSSL=false&allowPublicKeyRetrieval=true", "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException var6) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }

    }

    public void disconnect() {
        if (this.con != null) {
            try {
                this.con.close();
            } catch (Exception var2) {
                System.out.println("Error closing connection to database");
            }
        }

    }

    public Employee getEmployee(int ID) {
        try {
            Statement stmt = this.con.createStatement();
            String strSelect = "SELECT emp_no, first_name, last_name FROM employees WHERE emp_no = " + ID;
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                return emp;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }

    public LinkedList<Employee> getAllSalaries() {
        try {
            Statement stmt = this.con.createStatement();
            String strSelect = "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary FROM employees, salaries WHERE employees.emp_no = salaries.emp_no AND salaries.to_date = '9999-01-01' ORDER BY employees.emp_no ASC";
            ResultSet rset = stmt.executeQuery(strSelect);
            LinkedList<Employee> employees = new LinkedList();

            while(rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");
                employees.add(emp);
            }

            return employees;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }

    public void displayEmployee(Employee emp) {
        if (emp != null) {
            System.out.println(emp.emp_no + " " + emp.first_name + " " + emp.last_name + "\n" + emp.title + "\nSalary:" + emp.salary + "\n" + emp.dept_name + "\nManager: " + emp.manager + "\n");
        }

    }


    public void printSalaries(ArrayList<Employee> employees)
    {
        // Check employees is not null
        if (employees == null)
        {
            System.out.println("No employees");
            return;
        }
        // Print header
        System.out.println(String.format("%-10s %-15s %-20s %-8s", "Emp No", "First Name", "Last Name", "Salary"));
        // Loop over all employees in the list
        for (Employee emp : employees)
        {
            if (emp == null)
                continue;
            String emp_string =
                    String.format("%-10s %-15s %-20s %-8s",
                            emp.emp_no, emp.first_name, emp.last_name, emp.salary);
            System.out.println(emp_string);
        }
    }

    public static void main(String[] args) {
        App a = new App();
        a.connect();
        LinkedList<Employee> employees = a.getAllSalaries();
        if (employees != null) {
            System.out.println(employees.size());
        }

        a.disconnect();
    }
}