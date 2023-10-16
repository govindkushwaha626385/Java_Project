package user;
import java.util.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
public class UserLogin {
    public void userLogin() {
        // Creating the Object of class Userfunctionality
        UserFunctionality UF = new UserFunctionality();
        Scanner sc = new Scanner(System.in);
        int accountNumber, accountNumber1;
        String email = " ", email1 = " ", password = " ", password1 = " ";
        System.out.println("Enter Details for Login as User :- ");
        System.out.println("Enter Your Account Number :-");
        accountNumber = sc.nextInt();
        System.out.println("Enter Your registered Email :- ");
        email = sc.next();
        System.out.println("Enter Your Password :-");
        password = sc.next();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem",
                    "govind",
                    "govind@2003");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from USERLOGIN");
            PreparedStatement pst = con.prepareStatement("update USERLOGIN set LastLogin = ?");
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String dates = formatter.format(date);
            pst.setString(1, dates);
            pst.executeUpdate();
            while (rs.next()) {
                accountNumber1 = rs.getInt(3);
                email1 = rs.getString(1);
                password1 = rs.getString(2);
                if (accountNumber == accountNumber1 && email.equals(email1) && password.equals(password1)) {
                    System.out.println("*** Dear {name} you are Successfully Logged in your account ***");
                    // Calling function
                    UF.UserFunctionalities(accountNumber, email);
                    break;
                } else {
                    System.out.println("Enter Correct details for loing as user");
                }
            }
        } catch (Exception e) {
            System.out.println("Something went Wrong " + e);
        }
        // sc.close();
    }
}