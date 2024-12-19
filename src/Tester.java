import java.io.*;
import java.util.*;
public class Tester extends User {
    private static final String BUGS_FILE = "files/bugs.txt";
    private static final String DEVELOPERS_FILE = "files/Developers.txt";
    private static int nextBugID = 1;
    private static String TesterId ;
    public Tester(String id ,String email, String password) {
        super( id, email, password);
        initializeFileAndBugID();
    }
    @Override
    public void DisplayMenu() throws IOException {
        Scanner input = new Scanner(System.in); // Use a single Scanner instance for all inputs
        System.out.println("    <<Tester Menu>>    ");
        int choice = getValidatedIntInput(input, "1. Define Bug\n2. Assign Bug to Developer\n3. Attach Screenshot of Bug\n4. Monitor Open and Closed Bugs\n5. Log Out\nEnter your choice: ");
        switch (choice) {
            case 1:
                String bugname = getValidatedInput(input, "Enter Bug Name: ");
                String bugtype = getValidatedInput(input, "Enter Bug Type: ");
                String bugpriority = getValidatedInput(input, "Enter Bug Priority: ");
                String buglevel = getValidatedInput(input, "Enter Bug Level: ");
                String projectname = getValidatedInput(input, "Enter Project Name: ");
                String bugdate = getValidatedInput(input, "Enter Bug Date: ");
                String testerid = getValidatedInput(input, "Enter your ID: ");
                Tester.addBug(bugname, bugtype, bugpriority, buglevel, projectname, bugdate, testerid);
                break;

            case 2:
                String bugid = getValidatedInput(input, "Enter the Bug ID you want to assign to a developer: ");
                String developerid = getValidatedInput(input, "Enter the Developer ID to assign the bug to: ");
                Tester.assignBugToDeveloper(bugid, developerid);
                break;

            case 3:
                String bugidscreen = getValidatedInput(input, "Enter Bug ID to attach a screenshot: ");
                String screenshotpath = getValidatedInput(input, "Enter the screenshot path: ");
                Tester.attachScreenshot(bugidscreen, screenshotpath);
                break;

            case 4:
                System.out.println("View Open/Closed Bugs:");
                System.out.println("1. Open");
                System.out.println("2. Closed");
                int openClosedChoice;
                do {
                    openClosedChoice = getValidatedIntInput(input, "Enter your choice (1 for Open, 2 for Closed): ");
                    switch (openClosedChoice) {
                        case 1:
                            Tester.viewBugsByStatus("Open");
                            break;
                        case 2:
                            Tester.viewBugsByStatus("Closed");
                            break;
                        default:
                            System.out.println("Invalid choice, please try again.");
                    }
                } while (openClosedChoice != 1 && openClosedChoice != 2);
                break;

            case 5:
                MainMenu.Main();
                break;

            default:
                System.out.println("Invalid choice! Please try again.");
                DisplayMenu();
                break;
        }
    }
    public String getId() {
        return TesterId;
    }
    public static void addBug(String bugName, String bugType, String bugPriority, String bugLevel, String projectName, String startDate , String TesterId) {
        System.out.println("Attempting to add a bug...");
        System.out.println("BUGS_FILE Path: " + BUGS_FILE);

        if (isBugNameExists(bugName)) {
            System.out.println("Bug name already exists. Cannot add.");
            return;
        }

        String bugID = String.format("%03d", nextBugID++);
        String newBug = String.join(",",
                bugID,
                bugName,
                bugType,
                bugPriority,
                bugLevel,
                projectName,
                startDate,
                "Open",
                TesterId,
                "Unassigned" ,
                "noScreenShotYet");


        FileHandler fileHandler = new FileHandler("files/bugs.txt");
        boolean success = fileHandler.append(newBug);
        if (success) {
            System.out.println("Bug added successfully with ID: " + bugID);
        } else {
            System.out.println("Failed to add bug. Debugging file handler...");
        }

//        String fileContent = fileHandler.readFile();
//        System.out.println("File Content after append:\n" + fileContent);
    }

    private static boolean isBugNameExists(String bugName) {
        try {
            FileHandler fileHandler = new FileHandler("files/bugs.txt");
            String fileContent = fileHandler.readFile();
            for (String line : fileContent.split("\n")) {
                String[] fields = line.split(",");
                if (fields.length >= 2 && fields[1].equalsIgnoreCase(bugName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Error checking bug name: " + e.getMessage());
        }
        return false;
    }

    public static void attachScreenshot(String bugID, String screenshotPath) {
        updateBugField(bugID, 10, screenshotPath); // Field index 10 for screenshot
    }

//    public static void assignBugToDeveloper(String bugID, String developerID) {
//        if (!isDeveloperIDExists(developerID)) {
//            System.out.println("Developer ID " + developerID + " not found.");
//            return;
//        }
//        updateBugField(bugID, 9, developerID); // Field index 8 for Assigned Developer
//    }
    public static void assignBugToDeveloper(String bugID, String developerID) {
        if (!isDeveloperIDExists(developerID)) {
            System.out.println("Developer ID " + developerID + " not found.");
            return;
        }
        updateBugField(bugID, 9, developerID); // Field index 9 for Assigned Developer

        // Compose the email message
        String emailMessage = "From: TesterSystem@bugtracking.com\n"
                + "To: Developer_" + developerID + "@company.com\n"
                + "Subject: New Bug Assignment\n"
                + "\n"
                + "Dear Developer " + developerID + ",\n\n"
                + "You have been assigned a new bug.\n"
                + "Bug ID: " + bugID + "\n"
                + "Status: Assigned\n\n"
                + "Please review and take appropriate actions.\n"
                + "For further assistance, contact the Tester who assigned this bug.\n\n"
                + "Best Regards,\n"
                + "Bug Tracking System";

        // Print the email
        System.out.println(emailMessage);
    }

    public static void viewBugsByStatus(String status) {
        try {
            FileHandler fileHandler = new FileHandler("files/bugs.txt");
            String fileContent = fileHandler.readFile();
            System.out.println("\n--- Bugs with Status: " + status + " ---");
            for (String line : fileContent.split("\n")) {
                String[] fields = line.split(",");
                if (fields.length >= 8 && fields[7].equalsIgnoreCase(status)) { // Status is field index 7
                    System.out.println(Arrays.toString(fields));
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading bugs by status: " + e.getMessage());
        }
    }

    static boolean isDeveloperIDExists(String developerID) {
        FileHandler fileHandler = new FileHandler("files/Developer.txt");
        try {
            return fileHandler.userExists(developerID);
        } catch (Exception e) {
            System.out.println("Error validating Developer ID: " + e.getMessage());
        }
        return false;
    }

    private void initializeFileAndBugID() {
        FileHandler fileHandler = new FileHandler("files/bugs.txt");
        try {
            String fileContent = fileHandler.readFile();
            for (String line : fileContent.split("\n")) {
                String[] fields = line.split(",");
                if (fields.length >= 1) {
                    try {
                        int lastBugID = Integer.parseInt(fields[0]);
                        nextBugID = Math.max(nextBugID, lastBugID + 1);
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid bug ID: " + fields[0]);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error initializing bug ID: " + e.getMessage());
        }
    }

    private static void updateBugField(String bugID, int fieldIndex, String newValue) {
        FileHandler fileHandler = new FileHandler(BUGS_FILE);
        try {
            String fileContent = fileHandler.readFile();
            StringBuilder updatedContent = new StringBuilder();
            boolean updated = false;

            for (String line : fileContent.split("\n")) {
                String[] fields = line.split(",");
                if (fields[0].equals(bugID)) {
                    if (fieldIndex >= fields.length) {
                        System.out.println(fields.length);
                        System.out.println("Error: Field index out of bounds.");
                        return;
                    }
                    String originalLine = line;
                    fields[fieldIndex] = newValue;
                    String updatedLine = String.join(",", fields);
                    updated = true;

                    boolean isUpdated = fileHandler.update(originalLine, updatedLine);
                    if (isUpdated) {
                        break;
                    } else {
                        System.out.println("Failed to update the bug in the file.");
                    }
                    break;
                }
                updatedContent.append(line).append("\n");
            }

            if (!updated) {
                System.out.println("Bug with ID " + bugID + " not found.");
            }
        } catch (Exception e) {
            System.out.println("Error updating bug field: " + e.getMessage());
        }
    }
}