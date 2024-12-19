import java.io.IOException;
import java.util.*;
public class Developer extends User {
    //    private static FileHandler fileHandler;
    private static String filePath = "files/bugs.txt";

    public Developer(String id, String email, String password) {
        super(id, email, password);
//        this.fileHandler = new FileHandler(filePath);
    }

    @Override
    public void DisplayMenu() throws IOException {
        System.out.println("    <<Developer Menu>>    ");
        Scanner input = new Scanner(System.in);

        int choice = getValidatedIntInput(input, "1. View assigned bugs\n2. Change status of bug\n3. Log out\nEnter your choice: ");

        String devid = getValidatedInput(input, "Enter your ID: ");

        switch (choice) {
            case 1:
                System.out.println("Assigned bugs for developer " + devid + ":");
                Developer.viewAssignedBugs(devid);
                break;

            case 2:
                String bugidstat = getValidatedInput(input, "Enter bug ID you want to change the status of: ");
                String status = getValidatedInput(input, "Enter the status of the bug you want to change (open/closed): ");
                String idtest = getValidatedInput(input, "Enter the ID of the tester who assigned the bug " + bugidstat + ": ");
                Developer.updateBugStatus(bugidstat, status, idtest, devid);
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

    public static void viewAssignedBugs(String devid) {
        FileHandler f = new FileHandler(filePath);
        try {
            String allBugs = f.readFile();
            String[] bugs = allBugs.split("\\R");

            System.out.println("Assigned bugs for developer " + devid + ":");

            for (String bug : bugs) {
                String[] bugData = bug.split(",\\s*");
                if (bugData.length > 10) {
                    String assignedDevId = bugData[bugData.length - 2].trim();
                    if (assignedDevId.equals(devid.trim())) {
                        System.out.println("Bug ID: " + bugData[0].trim());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error while reading bug data: " + e.getMessage());
        }
    }


    //    public static void updateBugStatus(String bugID, String newStatus, String testerId, String devid) {
//        FileHandler f = new FileHandler(filePath);
//        try {
//            String allBugs = f.readFile();
//            String[] bugs = allBugs.split("\n");
//            boolean bugUpdated = false;
//
//            for (String bug : bugs) {
//                String[] bugData = bug.split(",");
//
//                if (bugData.length > 9 && bugData[0].equals(bugID) && bugData[9].equals(devid)) {
//                    bugData[7] = newStatus;
//                    bugData[8] = testerId;
//
//                    String updatedBug = String.join(",", bugData);
//
//                    boolean updated = f.update(bug, updatedBug);
//                    if (updated) {
//                        bugUpdated = true;
//                        System.out.println("Bug '" + bugID + "' has been updated to status: " + newStatus);
//                        if (newStatus.equalsIgnoreCase("Closed")) {
//                            System.out.println("Dear Tester '" + testerId + "': Bug '" + bugID + "' has been successfully resolved.");
//                        }
//                    } else {
//                        System.out.println("Failed to update the bug in the file.");
//                    }
//                    break;
//                }
//            }
//
//            if (!bugUpdated) {
//                System.out.println("Bug '" + bugID + "' not found or not assigned to you.");
//            }
//        } catch (Exception e) {
//            System.out.println("Error while updating bug status: " + e.getMessage());
//        }
//    }
//}
    public static void updateBugStatus(String bugID, String newStatus, String testerId, String developerId) {
        FileHandler f = new FileHandler(filePath);
        try {
            String allBugs = f.readFile();
            String[] bugs = allBugs.split("\n");
            boolean bugUpdated = false;

            for (String bug : bugs) {
                String[] bugData = bug.split(",");

                if (bugData.length > 9 && bugData[0].equals(bugID) && bugData[9].equals(developerId)) {
                    bugData[7] = newStatus;
                    bugData[8] = testerId;

                    String updatedBug = String.join(",", bugData);

                    boolean updated = f.update(bug, updatedBug);
                    if (updated) {
                        bugUpdated = true;
                        System.out.println("Bug '" + bugID + "' has been updated to status: " + newStatus);

                        // Compose the email message
                        String emailMessage = "From: DeveloperSystem@bugtracking.com\n"
                                + "To: Tester_" + testerId + "@company.com\n"
                                + "Subject: Bug Status Update\n"
                                + "\n"
                                + "Dear Tester " + testerId + ",\n\n"
                                + "The bug with the following details has been updated:\n"
                                + "Bug ID: " + bugID + "\n"
                                + "New Status: " + newStatus + "\n\n"
                                + "Please review the changes and follow up if necessary.\n\n"
                                + "Best Regards,\n"
                                + "Bug Tracking System";

                        // Print the email
                        System.out.println(emailMessage);

                        if (newStatus.equalsIgnoreCase("Closed")) {
                            System.out.println("Dear Tester '" + testerId + "': Bug '" + bugID + "' has been successfully resolved.");
                        }
                    } else {
                        System.out.println("Failed to update the bug in the file.");
                    }
                    break;
                }
            }

            if (!bugUpdated) {
                System.out.println("Bug '" + bugID + "' not found or not assigned to you.");
            }
        } catch (Exception e) {
            System.out.println("Error while updating bug status: " + e.getMessage());
        }
    }
}