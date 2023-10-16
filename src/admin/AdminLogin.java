package admin;
import java.util.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
public class AdminLogin {
    public void adminLogin() {
        Scanner sc = new Scanner(System.in);
        String email1 = " ", email = " ", password1 = " ", password = " ";
        AdminFunctionality AF = new AdminFunctionality();
        // Takin Input from User
        System.out.println("Enter required Details : ");
        System.out.print("Enter Registered Email : ");
        email = sc.nextLine();
        System.out.print("Enter Password : ");
        password = sc.nextLine();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem", "govind",
                    "govind@2003");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from ADMINLOGIN");
            rs.next();
            email1 = rs.getString(1);
            password1 = rs.getString(2);
            System.out.println(email1);
            System.out.println(password1);
            if (email1.equals(email) && password.equals(password1)) {
                System.out.print("Admin Logged in Successfully");
                // Calling Function
                AF.adminFunctionalities();
            } else {
                System.out.println("Enter Correct Email and Password for Login as Admin");
            }
        } catch (Exception e) {
            System.out.print("Something went Wrong " + e);
        }
        // sc.close();
    }
}
