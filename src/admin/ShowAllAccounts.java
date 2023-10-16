package admin;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
public class ShowAllAccounts {
    public void allAccounts() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem", "govind",
                    "govind@2003");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from USERCREATEACCOUNT");
            while (rs.next()){
                System.out.print("\n");
                System.out.println("name = " + rs.getString(1));
                System.out.println("age = " + rs.getInt(2));
                System.out.println("Email = " + rs.getString(3));
                System.out.println("Adhar Number = " + rs.getInt(4));
                System.out.println("Phone Number = " + rs.getInt(5));
                System.out.println("Address = " + rs.getString(6));
                System.out.println("Account Type = " + rs.getString(7));
                System.out.println("Accont Number = " + rs.getInt(8));
                System.out.println("Your Balance = " + rs.getInt(10));
                System.out.println("ATM Number = " + rs.getInt(11));
            }
        } catch (Exception e) {
            System.out.println("Something went Wrong " + e);
        }
    }
}
