import java.io.IOException;
import java.util.Scanner;

public abstract class User implements Login {
    protected static String id;
    protected String email;
    protected String password;
    public User()
    {}
    public User(String id,String email,String password)
    {
        this.id=id;
        this.email=email;
        this.password=password;
    }

    public boolean login(String role,String email,String password) throws IOException {
        String filePath = "files/"+role+".txt";
        FileHandler fileHandler = new FileHandler(filePath);
        if(!fileHandler.emailExists(email)) {
            System.out.println("Email not found ");
            return false;
        }
        if(!fileHandler.passwordExists(password)) {
            System.out.println("password is wrong!! ");
            return false;
        }
        System.out.println("Login successful!");
        return true;

    }
    static String getValidatedInput(Scanner input, String prompt) {
        String value;
        do {
            System.out.print(prompt);
            value = input.nextLine().trim();
            if (value.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        } while (value.isEmpty());
        return value;
    }

    static int getValidatedIntInput(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (input.hasNextInt()) {
                return input.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                input.next();
            }
        }
    }
    public abstract void DisplayMenu() throws IOException;
}

