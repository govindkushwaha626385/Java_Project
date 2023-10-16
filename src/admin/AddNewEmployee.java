package admin;

import java.util.*;
import java.util.Date;
import sendOTPService.SendOTPService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.*;

public class AddNewEmployee {
    public void addNewEmployee() {
        Scanner sc = new Scanner(System.in);
        String email = " ", password = " ", name = " ", address = " ", role = " ";
        int salary, age, phoneNumber, adharNumber;
        System.out.print("Enter Email of New Employee :- ");
        email = sc.nextLine();
        System.out.print("Enter Password of New Employee :- ");
        password = sc.nextLine();
        System.out.print("Enter Name of New Employee :- ");
        name = sc.nextLine();
        System.out.print("Enter Address of New Employee :- ");
        address = sc.nextLine();
        System.out.print("Enter Salary of New Employee :- ");
        salary = sc.nextInt();
        System.out.print("Enter Age of New Employee :- ");
        age = sc.nextInt();
        System.out.print("Enter Phone Number of New Employee :- ");
        phoneNumber = sc.nextInt();
        System.out.print("Enter Adhar Number of New Employee :- ");
        adharNumber = sc.nextInt();
        System.out.print("Enter Role of New Employee :- ");
        role = sc.nextLine();

        try {
            if (isExists(email)) {
                System.out.println("This Email is Already Exist");
            } else {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem",
                        "govind",
                        "govind@2003");

                PreparedStatement pst = con.prepareStatement(
                        "insert into EMPLOYEECREATEACCOUNT(Name, Email, Age, AdharNumber, PhoneNumber, Password, Address, JoiningDate, Salary, Role, LastLogin, EmployeeId)"
                                + "values(?,?,?,?,?,?,?,?,?,?,?,?)");

                PreparedStatement pst1 = con
                        .prepareStatement("insert into EMPLOYEELOGIN(EmployeeId, Email, Password)" + "values(?,?,?)");

                pst.setString(1, name);
                pst.setString(2, email);
                pst.setInt(3, age);
                pst.setInt(4, adharNumber);
                pst.setInt(5, phoneNumber);
                pst.setString(6, password);
                pst.setString(7, address);
                pst.setInt(9, salary);
                pst.setString(10, role);
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                String dates = formatter.format(date);
                pst.setString(8, dates);
                pst.setString(11, dates);
                int min = 100; // Minimum value of range
                int max = 999; // Maximum value of range
                // Generate random int value from min to max
                int employeeid = (int) Math.floor(Math.random() * (max - min + 1) + min);
                pst.setInt(12, employeeid);
                pst1.setInt(1, employeeid);
                pst1.setString(2, email);
                pst1.setString(3, password);
                pst.executeUpdate();
                pst1.executeUpdate();
                System.out.println("New Employee Added Successfully");
                String empID = String.valueOf(employeeid);
                SendOTPService.sendOTP(email, empID,
                        "Your Employee ID is : ");
            }
        } catch (Exception e) {
            System.out.println("Something went Wrong " + e);
        }
    }

    public boolean isExists(String email) throws ClassNotFoundException, SQLException {
        boolean isExist = false;
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankManagementSystem", "govind",
                "govind@2003");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select Email from EMPLOYEELOGIN");
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
