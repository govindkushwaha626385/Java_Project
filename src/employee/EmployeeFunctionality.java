package employee;
import java.util.Scanner;
public class EmployeeFunctionality {
    public void employeeFunctionality(String email){
        Scanner sc = new Scanner(System.in);
        EmployeeCheckUserHistory ECUH = new EmployeeCheckUserHistory();
        EmployeeUpdateUserDetails EUUD = new EmployeeUpdateUserDetails();
        EmployeeLogout EL = new EmployeeLogout();

        int choice1 = 0;
           while(choice1 != 8080){
                System.out.println("\nEnter 1 for Check User History :- \nEnter 2 for Update user details :- \nEnter 3 for Logout :- ");
                System.out.print("Enter Your Choice :- ");
                int choice = sc.nextInt();
                switch(choice){
                    case 1:
                    ECUH.employeeCheckUserHistory();
                    break;
                    case 2:
                    EUUD.employeeUpdateUserDetails();
                    break;
                    case 3:
                    EL.employeeLogout(email);
                    break;
                }
           }
        //    sc.close();
    }
}
