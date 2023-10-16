
package employee;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
public class EmployeeLogout {
    public void employeeLogout(String email) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem", "govind",
                    "govind@2003");
            PreparedStatement pst = con.prepareStatement("delete from EMPLOYEELOGIN where Email = ?");
            pst.setString(1, email);
            int i = pst.executeUpdate();
            if (i == 1) {
                System.out.println("Log Out Successfully");
            }
        } catch (Exception e) {
            System.out.println("Something went Wrong " + e);
        }
    }
}

