package user;
import java.util.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
public class UserWithDrawMoneyUsingAtm {
    public void withDrawMoneyUsingAtm(int accountNumber) {
        Scanner sc = new Scanner(System.in);
        int atmNumber;
        System.out.print("Enter Your Atm Number :-");
        atmNumber = sc.nextInt();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem",
                    "govind",
                    "govind@2003");
            // Get User Balance from Database using accountNumber
            PreparedStatement st = con.prepareStatement("select Balance from USERLOGIN where AtmNumber = ?");
            st.setInt(1, atmNumber);
            ResultSet rs = st.executeQuery();
            rs.next();
            int bal = rs.getInt(1);
            System.out.println("Your Current balance is " + bal);
            System.out.print("Enter amount for Withdraw :- ");
            int amount = sc.nextInt();
            if (amount <= bal) {
                PreparedStatement ps1 = con.prepareStatement("update USERCREATEACCOUNT set Balance = ?");
                PreparedStatement ps2 = con.prepareStatement(
                        "insert into USERHISTORY(AccountNumber, TransectionType, Amount, Date)" + "values(?,?,?,?)");
                ps2.setInt(1, accountNumber);
                ps2.setString(2, "Credit");
                ps2.setInt(3, amount);
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                String dates = formatter.format(date);
                ps2.setString(4, dates);
                ps2.executeUpdate();
                // Update Balance
                int newBalance = bal - amount;
                ps1.setInt(1, newBalance);
                ps1.executeUpdate();
                System.out.println("*** Money Wthdraw Successfully in your Bank Account ***");
                System.out.print("Your Current Balance is ");
                System.out.println(newBalance);
            } else {
                System.out.println("Enter Sufficient Balance to Withdraw");
            }
        } catch (Exception e) {
            System.out.println("Something went Wrong " + e);
        }
        // sc.close();
    }
}
