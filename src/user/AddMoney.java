package user;
import java.util.Scanner;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
public class AddMoney {
    public void userAddMoney(int accountNumber) {
        Scanner sc = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem",
                    "govind",
                    "govind@2003");
            // Get User Balance from Database using accountNumber
            PreparedStatement pst = con
                    .prepareStatement("SELECT Balance from USERCREATEACCOUNT WHERE AccountNumber = ?");
            pst.setInt(1, accountNumber);
            ResultSet rs = pst.executeQuery();
            rs.next();
            int bal = rs.getInt(1);
            System.out.print("Your Current balance is " + bal);
            System.out.print("Enter amount for Deposit :- ");
            int amount = sc.nextInt();
            PreparedStatement pst1 = con
                    .prepareStatement("update USERCREATEACCOUNT set Balance=? where AccountNumber=?");
            // Update Bala nce
            PreparedStatement pst2 = con.prepareStatement(
                    "insert into USERHISTORY(AccountNumber, TransectionType, Amount, Date)" + "values(?,?,?,?)");
            int newBalance = bal + amount;
            pst1.setInt(1, newBalance);
            pst1.setInt(2, accountNumber);
            pst2.setInt(1, accountNumber);
            pst2.setString(2, "Debit");
            pst2.setInt(3, amount);
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String dates = formatter.format(date);
            pst2.setString(4, dates);
            pst2.executeUpdate();
            pst1.executeUpdate();
            System.out.println("*** Money Added Successfully in your Bank Account ***");
            System.out.println("Now Your Bank Bank Balance is " + newBalance);
        } catch (Exception e) {
            System.out.println("Something went Wrong " + e);
        }
        // sc.close();
    }
}
