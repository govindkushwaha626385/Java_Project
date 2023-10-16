package admin;

import java.sql.*;
import java.util.Scanner;

public class AdminCheckEmployeeDetails {
    public void adminCheckEmployeeDetails() {
        Scanner sc = new Scanner(System.in);
        int employeeId;
        System.out.println("Enter Employee ID :- ");
        employeeId = sc.nextInt();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem",
                    "govind",
                    "govind@2003");

            PreparedStatement pst = con.prepareStatement("select * from EMPLOYEECREATEACCOUNT where EmployeeId = ?");
            pst.setInt(1, employeeId);
            ResultSet rs = pst.executeQuery();
            rs.next();
            System.out.println("This is Deatails of Employee :- \n");

            System.out.print("\n");
            System.out.println("name = " + rs.getString(1));
            System.out.println("age = " + rs.getInt(2));
            System.out.println("Email = " + rs.getString(3));
            System.out.println("Adhar Number = " + rs.getInt(4));
            System.out.println("Phone Number = " + rs.getInt(5));
            System.out.println("Address = " + rs.getString(6));
            System.out.println("Employee Id = " + rs.getInt(9));
            System.out.println("Salary = " + rs.getInt(10));
            System.out.println("Joining Date = " + rs.getString(11));
            System.out.println("Role in Bank = " + rs.getString(12));

        } catch (Exception e) {
            System.out.println("Something went Wring " + e);
        }
        // sc.close();
    }
}
