package admin;

import java.util.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class RemoveEmployee {
    public void removeEmployee() {
        Scanner sc = new Scanner(System.in);
        String email = " ";
        System.out.println("Enter Eamil of Employee which you want to Delete :-");
        email = sc.nextLine();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem", "govind",
                    "govind@2003");

            PreparedStatement pst = con.prepareStatement("delete from EMPLOYEELOGIN where Email = ?");
            PreparedStatement pst1 = con.prepareStatement("delete from EMPLOYEECREATEACCOUNT where Email = ?");     
            pst.setString(1, email);
            pst1.setString(1, email);
            pst.executeUpdate();
            pst1.executeUpdate();
            System.out.println("Employee Deleted Successfully");

        } catch (Exception e) {
            System.out.println("Something Went Wrong " + e);
        }
        // sc.close();
    }
}
