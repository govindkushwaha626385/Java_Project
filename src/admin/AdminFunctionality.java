package admin;

import java.util.Scanner;

public class AdminFunctionality {
    public void adminFunctionalities() {
        ShowAllAccounts Aa = new ShowAllAccounts();
        AdminCheckUserHistory ACUH = new AdminCheckUserHistory();
        TotelBankBalance TBB = new TotelBankBalance();
        AddNewEmployee ANE = new AddNewEmployee();
        RemoveEmployee RE = new RemoveEmployee();
        AdminCheckEmployeeDetails ACUD = new AdminCheckEmployeeDetails();
        AllEmployee AE = new AllEmployee();
        Scanner sc = new Scanner(System.in);
        int choice1 = 0;
        while (choice1 != 8080) {
            System.out.println(
                    "\n\nEnter 1 for See all Accounts :- \nEnter 2 for Check History of Perticular Account using AccountNumber :- \nEnter 3 for Add New Employee :- \nEner 4 for Delete Employee :- \nEnter 5 for see Totel Bank Balance :- \nEnter 6 for See All Employee :- \nEnter 7 for Check Employee Detauls :- \nEnter 8 for Exit :- ");
            System.out.print("Enter your Choice :- ");
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
                case 6:
                    AE.allEmployee();
                    break;
                case 7:
                    ACUD.adminCheckEmployeeDetails();
                    break;
                case 8:
                    choice1 = 8080;
                    break;
            }
        }
        // sc.close();
    }
}
