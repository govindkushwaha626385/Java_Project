
package employee;
import java.util.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class EmployeeCheckUserHistory {
    public void employeeCheckUserHistory() {
        Scanner sc = new Scanner(System.in);
        int accountNumber;
        System.out.println("Enter Account Number :- ");
        accountNumber = sc.nextInt();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem",
                    "govind",
                    "govind@2003");
            PreparedStatement pst = con.prepareStatement("select * from USERHISTORY where AccountNumber = ?");
            pst.setInt(1, accountNumber);
            ResultSet rs = pst.executeQuery();

            System.out.println("This is User History :- ");

            System.out.println("Transection type" + "   " + "Amount" + "   " + "Date");
            while (rs.next()) {
                System.out
                        .println("   " + rs.getString(2) + "          " + rs.getInt(3) + "         " + rs.getString(4));
            }
        } catch (Exception e) {
            System.out.println("Something went Wring " + e);
        }
        // sc.close();
    }
}
