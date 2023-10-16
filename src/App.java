import employee.EmployeeLogin;
import user.CreateUserAccount;
import user.UserLogin;
import admin.AdminLogin;
import java.util.*;
public class App {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        // Create Object's of Classes
        AdminLogin al = new AdminLogin();
        EmployeeLogin el = new EmployeeLogin();
        CreateUserAccount cua = new CreateUserAccount();
        UserLogin ul = new UserLogin();
            System.out.println("Welcome to Hyde Bank :");
            System.out.println(
                    "Enter 1 for enter(login) as Admin :- \nEnter 2 for enter(login) as Employee :-\nEnter 3 for CreateUserAccount :-\nEnter 4 for login as User :-\nEnter 5 for 8080 for Exit :-");
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
            }
        // sc.close();
    }
}
