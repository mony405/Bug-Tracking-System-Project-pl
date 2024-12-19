import java.io.IOException;
import java.util.Scanner;

public class Admin extends User {
    //view all bugs,add,delete,update users
    public Admin(String id, String email, String password) {
        super(id, email, password);
    }

    @Override
    public void DisplayMenu() throws IOException {
        System.out.println("    <<Admin Menu>>    ");
        Scanner inp = new Scanner(System.in);
        int choice = getValidatedIntInput(inp, "1. View all Bugs\n2. Manage users: Add/Update/Delete users\n3. Log out\nEnter your choice: ");

        switch (choice) {
            case 1:
                String allbugs = Admin.viewallbugs();
                System.out.println("\t\t<<All Bugs Of System>>\n" + allbugs);
                break;
            case 2:
                Scanner input = new Scanner(System.in);
                int ch = getValidatedIntInput(input, "1. Add user\n2. Update user\n3. Delete user\nEnter your choice: ");

                String role = getValidatedInput(input, "Enter the user role: ");
                String idrole = getValidatedInput(input, "Enter the ID of the user: ");
                String emailrole = getValidatedInput(input, "Enter the email of the user: ");
                String passrole = getValidatedInput(input, "Enter the password of the user: ");

                switch (ch) {
                    case 1:
                        Admin.addusers(role, idrole, emailrole, passrole);
                        break;
                    case 2:
                        String idupdaterole = getValidatedInput(input, "Enter the ID you want to update for the user "+idrole+" :");
                        String emailupdaterole = getValidatedInput(input, "Enter the ID you want to update for the user "+idrole+" :");
                        String passupdaterole = getValidatedInput(input, "Enter the ID you want to update for the user "+idrole+" :");
                        Admin.updateusers(role, idrole, emailrole, passrole, idupdaterole, emailupdaterole, passupdaterole);
                        break;
                    case 3:
                        Admin.deleteusers(role, idrole, emailrole, passrole);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
                break;
            case 3:
                MainMenu.Main();
                break;
            default:
                System.out.println("Invalid choice!");
                DisplayMenu();
                break;
        }
    }

    public static String viewallbugs()
    {
        String filePath = "files/bugs.txt";
        FileHandler fileHandler = new FileHandler(filePath);
        return fileHandler.readFile();
    }
    public static void addusers(String role, String id, String email, String pass) throws IOException {
        role=role.toUpperCase();
        String filePath = "files/"+role+".txt";
        FileHandler fileHandler = new FileHandler(filePath);
        if (fileHandler.userExists(id)) {
            System.out.println(role+" "+id +" already exists!");
        } else {
            String userData = id + "," + email + "," + pass;
            if (fileHandler.append(userData)) {
                System.out.println(role+" added successfully!");
            } else {
                System.out.println("Failed to add ."+role);
            }
        }
    }
    public static void updateusers(String role, String id, String email, String pass,String newid, String newemail, String newpass) throws IOException
    {
        role = role.toUpperCase();
        String filePath = "files/" + role + ".txt";
        FileHandler fileHandler = new FileHandler(filePath);
        String existingUser = id + "," + email + "," + pass;
        String updatedUser = newid + "," + newemail + "," + newpass;
        boolean isUpdated = fileHandler.update(existingUser, updatedUser);
        if (isUpdated) {
            System.out.println("User updated successfully.");
        } else {
            System.out.println("User not found in the file.");
        }
    }
    public static void deleteusers(String role, String id, String email, String pass) throws IOException {
        role = role.toUpperCase();
        String filePath = "files/" + role + ".txt";
        FileHandler fileHandler = new FileHandler(filePath);
        if (fileHandler.userExists(id)) {
            String lineToDelete = id + "," + email + "," + pass;
            boolean isDeleted = fileHandler.delete(lineToDelete);
            if (isDeleted) {
                System.out.println(id+" deleted successfully.");
            } else {
                System.out.println(role + " " + id + " doesn't exists!");

            }
        }
    }
}