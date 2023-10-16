package user;
import java.util.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
public class UserUpdateDtails {
    public void userUpdateDetails(int accountNumber) {
        Scanner sc = new Scanner(System.in);
        String email = " ", address = " ", password = " ";
        int phoneNumber;
        System.out.print("Enter New Email :-");
        email = sc.nextLine();
        System.out.print("Enter new Phone Number :-");
        phoneNumber = sc.nextInt();
        System.out.print("Enter new Address :- ");
        address = sc.nextLine();
        System.out.print("Enter new Password :-");
        password = sc.nextLine();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem", "govind",
                    "govind@2003");
            PreparedStatement pst = con.prepareStatement(
                    "update USERCREATEACCOUNT set PhoneNumber = ?, Address = ?, Password = ?, Email = ?, LastUpdated = ? where AccountNumber = ?");
            PreparedStatement pst1 = con.prepareStatement("update USERLOGIN set Email = ?, Password = ?");
            pst.setInt(6, accountNumber);
            pst.setInt(1, phoneNumber);
            pst.setString(2, address);
            pst.setString(3, password);
            pst.setString(4, email);
            pst1.setString(1, email);
            pst1.setString(2, password);
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
        } catch (Exception e) {
            System.out.println("Something went Wrong " + e);
        }
        // sc.close();
    }
}