package employee;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.util.Scanner;

public class EmployeeLogin {
    public void employeeLogin() {
        Scanner sc = new Scanner(System.in);
        EmployeeFunctionality EF = new EmployeeFunctionality();
        int employeeID, employeeID1;
        String email1 = " ", password = " ", password1 = " ";
        System.out.println("Enter required details for Login as Employee :- ");
        System.out.print("Enter Your EmployeeID :- ");
        employeeID = sc.nextInt();
        System.out.print("Enter Your Password :- ");
        password = sc.next();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem",
                        "govind",
                        "govind@2003");
            PreparedStatement pst1 = con.prepareStatement("select * from EMPLOYEELOGIN where EmployeeId = ?");
            pst1.setInt(1, employeeID);
            ResultSet rs = pst1.executeQuery();
            PreparedStatement pst = con.prepareStatement("update EMPLOYEECREATEACCOUNT set LastLogin = ? where EmployeeId = ?");
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            Date date = new Date();
            String dates = formatter.format(date);
            pst.setString(1, dates);
            pst.setInt(2, employeeID);
            rs.next();
            employeeID1 = rs.getInt(1);
            email1 = rs.getString(2);
            password1 = rs.getString(3);

            if (employeeID == employeeID1 && password.equals(password1)) {
                pst.executeUpdate();
                System.out.println("You are successfully Logged in as Employee");
                EF.employeeFunctionality(email1);
            } else {
                System.out.println("Enter Correct Details for Login as Employee");
            }

        } catch (Exception e) {
            System.out.println("Something went Wrong " + e);
        }
        // sc.close();
    }
}
