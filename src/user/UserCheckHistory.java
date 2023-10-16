package user;
import java.util.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
public class UserCheckHistory {
    public void userCheckHistory(int accountNumber) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Your Account Number to check Previous history :-");
        int accountNumber1 = sc.nextInt();

        if (accountNumber1 == accountNumber) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://locahost:3306/BankManagementSystem",
                        "govind", "govind@2003");
                PreparedStatement pst = con.prepareStatement("select * from USERHISTORY where AccountNumber = ?");
                pst.setInt(1, accountNumber);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getString(2)); // Transection type
                    System.out.println(rs.getInt(3)); // Amount
                    System.out.println(rs.getString(4)); // Transection Date
                }
                // Build functionality
            } catch (Exception e) {
                System.out.println("Something went Wrong " + e);
            }
        }
        // sc.close();
    }
}
