package user;
import java.util.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
public class DeleteAccount extends UserFunctionality {
    public void userDeleteAccount(int accountNumber, String email) {
        Scanner sc = new Scanner(System.in);
        String email1 = "";
        System.out.print("Enter Your Account Number : ");
        int accountNumber1 = sc.nextInt();
        System.out.print("Enter Your Registered Email : ");
        email1 = sc.next();
        if (accountNumber == accountNumber1 && email.equals(email1)) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem",
                        "govind",
                        "govind@2003");
                String query1 = "delete from USERCREATEACCOUNT where AccountNumber = ?";
                String query2 = "delete from USERLOGIN where Email = ?";
                PreparedStatement ps1 = con.prepareStatement(query1);
                PreparedStatement ps2 = con.prepareStatement(query2);
                ps1.setInt(1, accountNumber);
                ps2.setString(1, email);
                ps1.executeUpdate();
                ps2.executeUpdate();
                System.out.println("User Deleted Succesfully");
            } catch (Exception e) {
                System.out.println("Something went Wrong " + e);
            }
        }
        // sc.close();
    }
}
