package user;
import java.util.Scanner;
public class UserFunctionality {
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
        int choice1 = 0;
        while (choice1 != 8080) {
            System.out.println(
                    "Enter 1 for Add Money:- \nEnter 2 for Withdraw Money :- \nEnter 3 for With Draw Money Using ATM card :- \nEnter 4 for issue ATM card :- \nEnter 5 for Check History :- \nEnter 6 for Update Details :- \nEnter 7 for Log Out :- \nEnter 8 for Delete Account :- \nEnter 9 fro Exit :- ");
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
                    break;
                case 8:
                    UDA.userDeleteAccount(accountNumber, email);
                    break;
            }
        }
        // sc.close();
    }
}
