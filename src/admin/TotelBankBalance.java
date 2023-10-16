package admin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class TotelBankBalance {
    public void checkTotelBalance() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem", "govind",
                    "govind@2003");
            PreparedStatement pst = con.prepareStatement("select sum(Balance) as totelBalance from USERCREATEACCOUNT");
            ResultSet rs = pst.executeQuery();
            rs.next();
            String sum = rs.getString("totelBalance");
            System.out.print("Totel Balance Avalible in Bank  = " + sum);
        } catch (Exception e) {
            System.out.println("Something sent wrong " + e);
        }
    }
}
