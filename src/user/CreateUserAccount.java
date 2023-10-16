package user;
import generateOTP.GenerateOTP;
import sendOTPService.SendOTPService;
import java.util.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class CreateUserAccount {
    public void createUserAccount() {
        Scanner sc = new Scanner(System.in);
        String name = " ", address = " ", accountType = " ", password = " ", email = " ";
        int age, adharNumber, phoneNumber, accountNumber;
        System.out.println("Enter Some Details to create your Account :-");
        System.out.print("Enter Your Name :- ");
        name = sc.nextLine();
        System.out.print("Enter Your Current Age :- ");
        age = sc.nextInt();
        System.out.print("Enter Your Aadhar number :- ");
        adharNumber = sc.nextInt();
        System.out.print("Enter Yout Phone Number :- ");
        phoneNumber = sc.nextInt();
        System.out.print("Enter Password :- ");
        password = sc.next();
        System.out.print("Enter Your Permanent Address :- ");
        address = sc.nextLine();
        System.out.print("Enter Your Account type :- ");
        accountType = sc.next();
        System.out.print("Enter Your Email :- ");
        email = sc.next();
        String OTP = GenerateOTP.getOTP();
        // Calling FUnction of Static Class using Class Name
        SendOTPService.sendOTP(email, OTP, "Your one time OTP for Create  Account, Please Enter it");
        System.out.print("Enter Six digit OTP : ");
        String otp1 = sc.next();
        try {
            if (isExists(email)) {
                System.out.println("This Email Address is Already Exist, please choose another email");
            } else {
                if (otp1.equals(OTP)) {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem",
                            "govind",
                            "govind@2003");
                    String quiry1 = "insert into USERCREATEACCOUNT(Name, Age,Email, AdharNumber, PhoneNumber, Address, AccountType, AccountNumber, Password, LastUpdate)"
                            + "values(?,?,?,?,?,?,?,?,?,?)";
                    String quiry2 = "insert into USERLOGIN( Email, Password, AccountNumber,LastLogin)"
                            + "values(?,?,?,?)";
                    PreparedStatement ps1 = con.prepareStatement(quiry1);
                    PreparedStatement ps2 = con.prepareStatement(quiry2);
                    // Generate 6 digit Random Unique Account Number
                    int min = 100000000; // Minimum value of range
                    int max = 999999999; // Maximum value of range
                    // Generate random int value from min to max
                    accountNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);
                    ps1.setString(1, name);
                    ps1.setInt(2, age);
                    ps1.setString(3, email);
                    ps1.setInt(4, adharNumber);
                    ps1.setInt(5, phoneNumber);
                    ps1.setString(6, address);
                    ps1.setString(7, accountType);
                    ps1.setInt(8, accountNumber);
                    ps1.setString(9, password);
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();
                    String dates = formatter.format(date);
                    ps1.setString(10, dates);
                    ps2.setString(1, email);
                    ps2.setString(2, password);
                    ps2.setInt(3, accountNumber);
                    ps2.setString(4, dates);
                    int i = ps1.executeUpdate();
                    ps2.executeUpdate();
                    if (i > 0) {
                        System.out.println("\nDear {name} You are Sign up Successfully");
                    } else {
                        System.out.println("Something went wrong");
                    }
                    // sent Account on Email using NodeMailer
                    System.out.println(
                            "*** Dear {name} Your Account Number is successfully sent on your Registerd Email {email} ***");
                    String accNo = String.valueOf(accountNumber);
                    SendOTPService.sendOTP(email, accNo,
                            "ThankYou for Creating account in my Bank.Your Permanant Account Number is");
                } else {
                    System.out.println("Wrong OTP");
                }
            }
        } catch (Exception e) {
            System.out.println("Something sent Wrong " + e);
        }
        // sc.close();
    }
    // create a function to check the Existance of User
    public boolean isExists(String email) throws ClassNotFoundException, SQLException {
        boolean isExist = false;
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem", "govind",
                "govind@2003");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select Email from USERLOGIN");
        // String email1 = email;
        String email1 = " ";
        while (rs.next()) {
            email1 = rs.getString(1);
            if (email.equals(email1)) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }
}
