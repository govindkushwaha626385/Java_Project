package employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.sql.*;

public class EmployeeUpdateUserDetails {
    public void employeeUpdateUserDetails() {
        Scanner sc = new Scanner(System.in);
        String email = " ", address = " ", password = " ";
        int phoneNumber, acc;
        System.out.print("Enter Account Number of User :- ");
        acc = sc.nextInt();

        try {
            if (isExists(acc)) {
                System.out.print("Enter New Email :-");
                email = sc.next();
                System.out.print("Enter new Phone Number :-");
                phoneNumber = sc.nextInt();
                System.out.print("Enter new Address :- ");
                address = sc.next();
                System.out.print("Enter new Password :-");
                password = sc.next();

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem",
                        "govind",
                        "govind@2003");
                PreparedStatement pst = con.prepareStatement(
                        "update USERCREATEACCOUNT set PhoneNumber = ?, Address = ?, Password = ?, Email = ?, LastUpdate = ? where AccountNumber = ?");
                PreparedStatement pst1 = con
                        .prepareStatement("update USERLOGIN set Email = ?, Password = ? where AccountNumber = ?");
                pst.setInt(6, acc);
                pst.setInt(1, phoneNumber);
                pst.setString(2, address);
                pst.setString(3, password);
                pst.setString(4, email);
                pst1.setString(1, email);
                pst1.setString(2, password);
                pst1.setInt(3, acc);
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                String dates = formatter.format(date);
                pst.setString(5, dates);
                int i = pst.executeUpdate();
                int j = pst1.executeUpdate();
                if (i == 1 && j == 1) {
                    System.out.println("Profile Updated Successfully");
                } else {
                    System.out.println("Something went Wrong");
                }
            } else {
                System.out.println("Please Enter Valid Account Number");
            }
        } catch (Exception e) {
            System.out.println("Something went Wrong " + e);
        }
    }

    public boolean isExists(int accountNumber) throws ClassNotFoundException, SQLException {
        boolean isExist = false;
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem", "govind",
                "govind@2003");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select AccountNumber from USERLOGIN");
        // String email1 = email;
        int accountNumber1;
        while (rs.next()) {
            accountNumber1 = rs.getInt(1);
            if (accountNumber == accountNumber1) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }
}
