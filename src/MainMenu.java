import java.util.Scanner;
import java.io.*;
public class MainMenu {
    public static void Main() throws IOException {
        Scanner input = new Scanner(System.in);
//        tha main menu that appears to all users
        System.out.println("    <<<Welcome to Bug Tracking System>>>    \n");
        System.out.println("1.login as Admin");
        System.out.println("2.login as Tester");
        System.out.println("3.login as Developer");
        System.out.println("4.login as Project Manger");
        System.out.println("5.Exit");
        int choice = User.getValidatedIntInput(input, "Enter your choice: ");
//        email and password for login
        if(choice==5){
            System.exit(0);
        }
        Scanner a = new Scanner(System.in);
        String email = User.getValidatedInput(a,"Enter your email :");
        while (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            System.out.println("invalid format ! try again");
            System.out.print("Enter your email :");
            email = a.nextLine().trim();
        }
        String password = User.getValidatedInput(a,"Enter your Password :");
        switch (choice) {
            case 1:
//                this is the login and menu for the admin only
                User admin = new Admin("1", email, password);
                while (!admin.login("Admin", email, password)) {
                    System.out.print("Enter your email :");
                    email = a.nextLine().trim();
                    while (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                        System.out.println("invalid format ! try again");
                        System.out.print("Enter your email :");
                        email = a.nextLine().trim();
                    }
                    System.out.print("Enter your Password :");
                    password = a.nextLine().trim();
                    while (password.isEmpty()) {
                        System.out.print("Enter your Password :");
                        password = a.nextLine().trim();
                    }
                }
                admin.DisplayMenu();
                break;
            case 2:
//                this is the login and menu for the tester only
                User tester = new Tester("2", email, password);
                while (!tester.login("Tester", email, password)) {
                    System.out.print("Enter your email :");
                    email = a.nextLine().trim();
                    while (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                        System.out.println("invalid format ! try again");
                        System.out.print("Enter your email :");
                        email = a.nextLine().trim();
                    }
                    System.out.print("Enter your Password :");
                    password = a.nextLine().trim();
                    while (password.isEmpty()) {
                        System.out.print("Enter your Password :");
                        password = a.nextLine().trim();
                    }
                }
                tester.DisplayMenu();
                break;
            case 3:
//                this is the login and menu for the dev only
                User developer = new Developer("3", email, password);
                while (!developer.login("Developer", email, password)) {
                    System.out.print("Enter your email :");
                    email = a.nextLine().trim();
                    while (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                        System.out.println("invalid format ! try again");
                        System.out.print("Enter your email :");
                        email = a.nextLine().trim();
                    }
                    System.out.print("Enter your Password :");
                    password = a.nextLine().trim();
                    while (password.isEmpty()) {
                        System.out.print("Enter your Password :");
                        password = a.nextLine().trim();
                    }
                }
                developer.DisplayMenu();
                break;
            case 4:
//                this is the login and menu for the pm only

                User projectmanger = new ProjectManager("4", email, password);
                while (!projectmanger.login("ProjectManger", email, password)) {
                    System.out.print("Enter your email :");
                    email = a.nextLine().trim();
                    while (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                        System.out.println("invalid format ! try again");
                        System.out.print("Enter your email :");
                        email = a.nextLine().trim();
                    }
                    System.out.print("Enter your Password :");
                    password = a.nextLine().trim();
                    while (password.isEmpty()) {
                        System.out.print("Enter your Password :");
                        password = a.nextLine().trim();
                    }
                }
                projectmanger.DisplayMenu();
                break;

            default:
//                go to the main menu ,if the input wasn't valid
                System.out.println("invalid choice!");
                Main();
        }
    }
}
