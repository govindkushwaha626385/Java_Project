import java.util.Properties;
// import javax.mail.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// import java.math.*;

public class App {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        // Create Object's of Classes
        AdminLogin al = new AdminLogin();
        EmployeeLogin el = new EmployeeLogin();
        CreateUserAccount cua = new CreateUserAccount();
        UserLogin ul = new UserLogin();

        // int choice = 0;
        // while (choice != 8080) {
        System.out.println("Welcome to Hyde Bank :");
        System.out.println(
                "Enter 1 for enter(login) as Admin :- \nEnter 2 for enter(login) as Employee :-\nEnter 3 for CreateUserAccount :-\nEnter 4 for login as User :-\nEnter 8080 for Exit :-");
        System.out.print("Enter Your Choice :-");
        int menu = sc.nextInt();
        switch (menu) {
            case 1:
                al.adminLogin();
                break;
            case 2:
                el.employeeLogin();
                break;
            case 3:
                cua.createUserAccount();
                break;
            case 4:
                ul.userLogin();
                break;
            // case 8080:
            // choice = 8080;
            // break;
        }
        // }
        sc.close();
    }
}

class EmployeeLogin {
    public void employeeLogin() {
        Scanner sc = new Scanner(System.in);
        int employeeID, employeeID1;
        String email = " ", email1 = " ", password = " ", password1 = " ";
        System.out.println("Enter required details for Login as Employee :-");
        System.out.print("Enter EmployeeID :-");
        employeeID = sc.nextInt();
        System.out.print("Enter registered Email :-");
        email = sc.nextLine();
        System.out.print("Enter Password :-");
        password = sc.nextLine();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://locatlost:3306/BankManagementSystem?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                    "govind", "govind@2003");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from EMPLOYEELOGIN");
            PreparedStatement pst = con.prepareStatement("update EMPLOYEELOGIN set LastLogin = ?");
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String dates = formatter.format(date);
            pst.setString(1, dates);

            while (rs.next()) {
                employeeID1 = rs.getInt("EmployeeId");
                email1 = rs.getString("Email");
                password1 = rs.getString("Password");

                if (employeeID == employeeID1 && email.equals(email1) && password.equals(password1)) {
                    pst.executeUpdate();
                    System.out.println("You are successfully Logged in as Employee");
                } else {
                    System.out.println("Enter Correct Details for Login as Employee");
                }
            }

        } catch (Exception e) {
            System.out.println("Something went Wrong " + e);
        }
        sc.close();
    }
}

// Generate OTP
class GenerateOTP {
    public String getOTP() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(100000));
    }
}

// Send OTP
class SendOTPService {
    public static void sendOTP(String email, String genOTP, String messaage1) {
        // Recipient's email ID needs to be mentioned.
        String to = email;
        // hemkantkushwaha6263@gmail.com
        // Sender's email ID needs to be mentioned
        String from = "govindkushwahabusiness@gmail.com";

        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        // properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "zqsiwvapcjfqdqww");
            }
        });
        // Used to debug SMTP issues
        session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Bank Details");

            // Now set the actual message
            message.setText(messaage1 + " " + genOTP);

            System.out.println("sending...");
            // Send message
            Transport.send(message);

            System.out.println("message sent successfully....");
            System.out.println("Please Check Your Email...");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}

class CreateUserAccount {
    public void createUserAccount() {

        GenerateOTP GOTP = new GenerateOTP();

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
        String OTP = GOTP.getOTP();
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
        sc.close();
    }

    // }

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

class UserLogin {
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

        sc.close();
    }

}

class UserFunctionality {
    void UserFunctionalities(int accountNumber, String email) {

        AddMoney UA = new AddMoney();
        WithdrawMoney UW = new WithdrawMoney();
        IssueAtmCard UIATM = new IssueAtmCard();
        DeleteAccount UDA = new DeleteAccount();
        UserCheckHistory UCH = new UserCheckHistory();
        UserWithDrawMoneyUsingAtm UWUATM = new UserWithDrawMoneyUsingAtm();
        UserUpdateDtails UUD = new UserUpdateDtails();
        UserLogOutAccount ULA = new UserLogOutAccount();

        int accountNumber1 = accountNumber;
        Scanner sc = new Scanner(System.in);
        System.out.println(
                "Enter 1 for Add Money:- \nEnter 2 for Withdraw Money :- \nEnter 3 for With Draw Money Using ATM card :- \nEnter 4 for issue ATM card :- \nEnter 5 for Check History :- \nEnter 6 for Update Details :- \nEnter 7 for Log Out :- \nEnter 8 for Delete Account :- ");
        System.out.print("Enter Your Choice : ");

        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                UA.userAddMoney(accountNumber1);
                break;

            case 2:
                UW.userWithdrawMoney(accountNumber);
                break;

            case 3:
                UWUATM.withDrawMoneyUsingAtm(accountNumber);
                break;

            case 4:
                UIATM.userIssueAtmCard(accountNumber, email);
                break;

            case 5:
                UCH.userCheckHistory(accountNumber);
                break;

            case 6:
                UUD.userUpdateDetails(accountNumber);
                break;

            case 7:
                ULA.userLogOutAccount(email);    

            case 8:
                UDA.userDeleteAccount(accountNumber, email);
                break;

        }
        sc.close();
    }
}

class AddMoney {
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
        sc.close();
    }
}

class WithdrawMoney extends UserFunctionality {
    public void userWithdrawMoney(int accountNumber) {
        Scanner sc = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem",
                    "govind",
                    "govind@2003");

            // Get User Balance from Database using accountNumber
            PreparedStatement st = con
                    .prepareStatement("select Balance from USERCREATEACCOUNT where AccountNumber = ?");
            st.setInt(1, accountNumber);
            ResultSet rs = st.executeQuery();
            rs.next();
            int bal = rs.getInt(1);
            System.out.print("Your Current balance is " + bal);
            System.out.print("Enter amount for Withdraw :- ");
            int amount = sc.nextInt();

            // Check the Entered amount is less than Account Balance
            if (amount <= bal) {
                PreparedStatement ps1 = con
                        .prepareStatement("update USERCREATEACCOUNT set Balance=? where AccountNumber=?");
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
                ps1.setInt(2, accountNumber);
                ps1.executeUpdate();
                System.out.println("*** Money Wthdraw Successfully in your Bank Account ***");
                System.out.println("Your Current Balance is " + newBalance);
            } else {
                System.out.println("Enter Sufficient Balance to Withdraw");
            }

        } catch (Exception e) {
            System.out.println("Something went Wrong " + e);
        }

        sc.close();
    }
}

class UserWithDrawMoneyUsingAtm {
    public void withDrawMoneyUsingAtm(int accountNumber) {
        Scanner sc = new Scanner(System.in);
        int atmNumber;
        System.out.println("Enter Your Atm Number :-");
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
            System.out.println("Your Current balance is ");
            System.out.println(bal);
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
        sc.close();
    }
}

// Functionality for Issue ATM card for User
class IssueAtmCard extends UserFunctionality {
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
        sc.close();
    }
}

class UserUpdateDtails {
    public void userUpdateDetails(int accountNumber) {
        Scanner sc = new Scanner(System.in);
        String email = " ", address = " ", password = " ";
        int phoneNumber;

        System.out.print("Enter New Email :-");
        email = sc.nextLine();

        System.out.print("Enter new Phone Number :-");
        phoneNumber = sc.nextInt();

        System.out.print("Enter new Address :- ");
        address = sc.nextLine();

        System.out.print("Enter new Password :-");
        password = sc.nextLine();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem", "govind",
                    "govind@2003");

            PreparedStatement pst = con.prepareStatement(
                    "update USERCREATEACCOUNT set PhoneNumber = ?, Address = ?, Password = ?, Email = ?, LastUpdated = ? where AccountNumber = ?");

            PreparedStatement pst1 = con.prepareStatement("update USERLOGIN set Email = ?, Password = ?");
            pst.setInt(4, accountNumber);
            pst.setInt(1, phoneNumber);
            pst.setString(2, address);
            pst.setString(3, password);
            pst.setString(4, email);
            pst1.setString(1, email);
            pst1.setString(2, password);
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String dates = formatter.format(date);
            pst.setString(5, dates);
            int i = pst.executeUpdate();
            int j = pst1.executeUpdate();
            if (i == 1 && j == 1) {
                System.out.println("Profile Updated Successfully");
            } else {
                System.out.println("Something went Wrong");
            }
        } catch (Exception e) {
            System.out.println("Something went Wrong " + e);
        }
        sc.close();
    }
}

class UserLogOutAccount{
    public void userLogOutAccount(String email){
        try{
           Class.forName("com.mysql.cj.jdbc.Driver");
           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem","govind", "govind@2003");

           PreparedStatement pst = con.prepareStatement("delete from USERLOGIN where Email = ?");
           pst.setString(1, email);
           int i = pst.executeUpdate();
           if(i == 1){
            System.out.println("Log Out Successfully");
           }

        }catch(Exception e){
            System.out.println("Something went Wrong " + e);
        }
    }
}

class DeleteAccount extends UserFunctionality {
    public void userDeleteAccount(int accountNumber, String email) {
        Scanner sc = new Scanner(System.in);
        String email1 = "";

        System.out.print("Enter Your Account Number : ");
        int accountNumber1 = sc.nextInt();

        System.out.print("Enter Your Registered Email : ");
        email1 = sc.next();

        if (accountNumber == accountNumber1 && email.equals(email1)) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem",
                        "govind",
                        "govind@2003");
                String query1 = "delete from USERCREATEACCOUNT where AccountNumber = ?";
                String query2 = "delete from USERLOGIN where Email = ?";
                PreparedStatement ps1 = con.prepareStatement(query1);
                PreparedStatement ps2 = con.prepareStatement(query2);
                ps1.setInt(1, accountNumber);
                ps2.setString(1, email);
                ps1.executeUpdate();
                ps2.executeUpdate();
                System.out.println("User Deleted Succesfully");

            } catch (Exception e) {
                System.out.println("Something went Wrong " + e);
            }
        }
        sc.close();
    }
}

class UserCheckHistory {
    public void userCheckHistory(int accountNumber) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Your Account Number to check Previous history :-");
        int accountNumber1 = sc.nextInt();

        if (accountNumber1 == accountNumber) {
            try {

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://locahost:3306/BankManagementSystem",
                        "govind", "govind@2003");
                PreparedStatement pst = con.prepareStatement("select * from USERHISTORY where AccountNumber = ?");
                pst.setInt(1, accountNumber);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getString(2)); // Transection type
                    System.out.println(rs.getInt(3)); // Amount
                    System.out.println(rs.getString(4)); // Transection Date
                }
                // Build functionality
            } catch (Exception e) {
                System.out.println("Something went Wrong " + e);
            }
        }
        sc.close();
    }
}

// Admin Login Functionality
class AdminLogin {
    public void adminLogin() {

        Scanner sc = new Scanner(System.in);
        String email1 = " ", email = " ", password1 = " ", password = " ";

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
                adminFunctionalities();

            } else {
                System.out.println("Enter Correct Email and Password for Login as Admin");
            }
        } catch (Exception e) {
            System.out.print("Something went Wrong " + e);
        }
        sc.close();
    }

    void adminFunctionalities() {
        ShowAllAccounts Aa = new ShowAllAccounts();
        AdminCheckUserHistory ACUH = new AdminCheckUserHistory();
        TotelBankBalance TBB = new TotelBankBalance();
        AddNewEmployee ANE = new AddNewEmployee();
        RemoveEmployee RE = new RemoveEmployee();
        Scanner sc = new Scanner(System.in);
        System.out.println(
                "Enter 1 for See all Accounts :- \nEnter 2 for Check History of Perticular Account using AccountNumber :- \nEnter 3 for Add New Employee :- \nEner 4 for Delete Employee :- \nEnter 5 for see Totel Bank Balance :- ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                Aa.allAccounts();
                break;

            case 2:
                ACUH.checkHistory();
                break;

            case 3:
                ANE.addNewEmployee();
                break;

            case 4:
                RE.removeEmployee();
                break;

            case 5:
                TBB.checkTotelBalance();
                break;
        }
        sc.close();
    }
}

class AdminCheckUserHistory {
    public void checkHistory() {
        Scanner sc = new Scanner(System.in);
        UserCheckHistory UCH = new UserCheckHistory();
        int accountNumber;
        System.out.println("Enter Account Number :- ");
        accountNumber = sc.nextInt();
        UCH.userCheckHistory(accountNumber);
        // try {
        // Class.forName("com.mysql.cj.jdbc.Driver");
        // Connection con =
        // DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem",
        // "govind",
        // "govind@2003");

        // PreparedStatement pst = con.prepareStatement("select * from USERHISTORY where
        // AccountNumber = ?");
        // pst.setInt(1, accountNumber1);
        // ResultSet rs = pst.executeQuery();

        // while (rs.next()) {
        // System.out.println("This is User History :- ");
        // System.out.println(rs.getString(2)); // Transection type
        // System.out.println(rs.getInt(3)); // Amount
        // System.out.println(rs.getString(4)); // Date
        // }
        // } catch (Exception e) {
        // System.out.println("Something went Wring " + e);
        // }

        sc.close();
    }
}

class TotelBankBalance {
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

class ShowAllAccounts {
    public void allAccounts() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem", "govind",
                    "govind@2003");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from USERCREATEACCOUNT");

            while (rs.next()) {
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

class AddNewEmployee {
    public void addNewEmployee() {
        Scanner sc = new Scanner(System.in);
        String email = " ", password = " ", joiningDate = " ", name = " ", address = " ", role = " ";
        int salary, age, phoneNumber, adharNumber;

        System.out.println("Enter Email of New Employee :- ");
        email = sc.nextLine();

        System.out.println("Enter Password of New Employee :- ");
        password = sc.nextLine();

        System.out.println("Enter Joining Date of New Employee :- ");
        joiningDate = sc.nextLine();

        System.out.println("Enter Name of New Employee :- ");
        name = sc.nextLine();

        System.out.println("Enter Address of New Employee :- ");
        address = sc.nextLine();

        System.out.println("Enter Salary of New Employee :- ");
        salary = sc.nextInt();

        System.out.println("Enter Age of New Employee :- ");
        age = sc.nextInt();

        System.out.println("Enter Phone Number of New Employee :- ");
        phoneNumber = sc.nextInt();

        System.out.println("Enter Adhar Number of New Employee :- ");
        adharNumber = sc.nextInt();

        System.out.println("Enter Role of New Employee :- ");
        role = sc.nextLine();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem", "govind",
                    "govind@2003");

            PreparedStatement pst = con.prepareStatement(
                    "insert into EMPLOYEECREATEACCOUNT(Name, Email, Age, AdharNumber, PhoneNumber, Password, Address, JoiningDate, Salary, Role, LastLogin)"
                            + "values(?,?,?,?,?,?,?,?,?,?,?)");

            PreparedStatement pst1 = con
                    .prepareStatement("insert into EMPLOYEELOGIN(EmployeeId, Email, Password)" + "values(?,?,?)");

            pst.setString(1, name);
            pst.setString(2, email);
            pst.setInt(3, age);
            pst.setInt(4, adharNumber);
            pst.setInt(5, phoneNumber);
            pst.setString(6, password);
            pst.setString(7, address);
            pst.setString(8, joiningDate);
            pst.setInt(9, salary);
            pst.setString(10, role);
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String dates = formatter.format(date);
            pst.setString(11, dates);

            int min = 100; // Minimum value of range
            int max = 999; // Maximum value of range
            // Generate random int value from min to max
            int employeeid = (int) Math.floor(Math.random() * (max - min + 1) + min);

            pst1.setInt(1, employeeid);
            pst1.setString(2, email);
            pst1.setString(3, password);
            pst.executeUpdate();
            pst1.executeUpdate();
            System.out.println("New Employee Added Successfully");

        } catch (Exception e) {
            System.out.println("Something went Wrong " + e);
        }

        sc.close();
    }
}

class RemoveEmployee {
    public void removeEmployee() {
        Scanner sc = new Scanner(System.in);
        String email = " ";
        System.out.println("Enter Eamil of Employee which you want to Delete :-");
        email = sc.nextLine();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem", "govind",
                    "govind@2003");

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select Email from EMPLOYEELOGIN");
            PreparedStatement pst = con.prepareStatement("delete from USERLOGIN where Email = ?");
            pst.setString(1, email);
            pst.executeQuery();
            String email1 = " ";
            while (rs.next()) {
                if (email1.equals(email1)) {
                    pst.executeUpdate();
                    System.out.println("Employee Deleted Successfully");
                }
            }

        } catch (Exception e) {
            System.out.println("Something Went Wrong " + e);
        }

        sc.close();
    }
}
