package user;
import sendOTPService.SendOTPService;
import java.util.Scanner;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
public class IssueAtmCard extends UserFunctionality {
    public void userIssueAtmCard(int accountNumber, String email) {
        Scanner sc = new Scanner(System.in);
        String email1 = "";
        System.out.println("Welcome to Hyde Bank");
        System.out.println("Enter some details for issuing a dabit card (ATM card):-");
        System.out.print("Enter Your  Bank Account Number : ");
        int accountNumber1 = sc.nextInt();
        System.out.print("Enter Your registered email : ");
        email1 = sc.next();
        if (accountNumber == accountNumber1 && email.equals(email1)) {
            // System.out.println("Your ATM (debit card) is generated Successfully");
            int min = 1000000000, max = 999999999;
            int AtmNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);
            // Create ATMNumber Column in user Table in mysql dataBase
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem",
                        "govind",
                        "govind@2003");
                PreparedStatement ps1 = con.prepareStatement("update USERCREATEACCOUNT set AtmNumber = ?");
                ps1.setInt(1, AtmNumber);
                ps1.executeUpdate();
                String atmNo = String.valueOf(AtmNumber);
                // Calling FUnction of Static Class using Class Name
                SendOTPService.sendOTP(email, atmNo, "Your Permanent ATM Number is");
                // System.out.print("Your Debit card Number is " + AtmNumber);
            } catch (Exception e) {
                System.out.println("Something went Wrong " + e);
            }
        }
        // sc.close();
    }
}
