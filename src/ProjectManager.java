
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProjectManager extends User{


    public ProjectManager(String id, String email, String password) {
        super(id,email,password);
    }

    @Override
    public void DisplayMenu() throws IOException {
        System.out.println("    <<Project Manager Menu>>    ");
        Scanner input = new Scanner(System.in);

        int choice = getValidatedIntInput(input,
                "1. Check performance of developers\n" +
                        "2. Check performance of testers\n" +
                        "3. Monitor open and closed bugs\n" +
                        "4. Log out\n" +
                        "Enter your choice: ");
        switch (choice) {
            case 1:
                ProjectManager.checkDeveloperPerformance();
                break;
            case 2:
                ProjectManager.checkTesterPerformance();
                break;
            case 3:
                ProjectManager.monitorBugs();
                break;
            case 4:
                MainMenu.Main();
                break;
            default:
                System.out.println("Invalid choice!");
                DisplayMenu();
                break;
        }
    }
    // Method to check the performance of developers
    public static void checkDeveloperPerformance() {
        Map<String, Integer> totalBugs = new HashMap<>();
        Map<String, Integer> fixedBugs = new HashMap<>();

        try {
            String filePath="files/bugs.txt";
            FileHandler f=new FileHandler(filePath);
            String fileContent = f.readFile();
            if (fileContent.equals("error")) {
                System.out.println("Error reading the bugs file.");
                return;
            }

            String[] lines = fileContent.split("\n");
            for (String line : lines) {
                String[] bugData = line.split(",");
                if (bugData.length >= 10) {
                    String developerId = bugData[9].trim();
                    String bugStatus = bugData[7].trim(); // Assuming status is in column 7

                    totalBugs.put(developerId, totalBugs.getOrDefault(developerId, 0) + 1);

                    if (bugStatus.equalsIgnoreCase("closed")) {
                        fixedBugs.put(developerId, fixedBugs.getOrDefault(developerId, 0) + 1);
                    }
                }
            }

            // Print performance for each developer
            System.out.println("Developer Performance:");
            for (String developerId : totalBugs.keySet()) {
                int total = totalBugs.get(developerId);
                int fixed = fixedBugs.getOrDefault(developerId, 0);
                System.out.println("Developer " + developerId + ": Fixed " + fixed + " out of " + total + " bugs.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    // Method to check the performance of testers
    public static void checkTesterPerformance() {
        Map<String, Integer> reportedBugs = new HashMap<>();

        try {
            String filePath="files/bugs.txt";
            FileHandler f=new FileHandler(filePath);
            String fileContent = f.readFile();
            if (fileContent.equals("error")) {
                System.out.println("Error reading the bugs file.");
                return;
            }

            String[] lines = fileContent.split("\n");
            for (String line : lines) {
                String[] bugData = line.split(",");
                if (bugData.length >= 9) {
                    String testerId = bugData[8].trim();

                    reportedBugs.put(testerId, reportedBugs.getOrDefault(testerId, 0) + 1);
                }
            }

            // Print performance for each tester
            System.out.println("Tester Performance:");
            for (String testerId : reportedBugs.keySet()) {
                int total = reportedBugs.get(testerId);
                System.out.println("Tester " + testerId + ": Reported " + total + " bugs.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    // Method to monitor bugs
    public static void monitorBugs() {
        int openBugs = 0;
        int closedBugs = 0;

        try {
            String filePath="files/bugs.txt";
            FileHandler f=new FileHandler(filePath);
            String fileContent = f.readFile();
            if (fileContent.equals("error")) {
                System.out.println("Error reading the bugs file.");
                return;
            }

            String[] lines = fileContent.split("\n");
            for (String line : lines) {
                String[] bugData = line.split(",");
                if (bugData.length >= 8) {
                    String bugStatus = bugData[7].trim();

                    if (bugStatus.equalsIgnoreCase("open")) {
                        openBugs++;
                    } else if (bugStatus.equalsIgnoreCase("closed")) {
                        closedBugs++;
                    }
                }
            }

            // Print bug statistics
            System.out.println("Bug Monitoring:");
            System.out.println("Open Bugs: " + openBugs);
            System.out.println("Closed Bugs: " + closedBugs);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }


}
