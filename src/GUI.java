import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.regex.Pattern;
public class GUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Bug Tracking System");

        // Create a VBox layout for the main menu
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        // Main Menu label
        Label mainMenuLabel = new Label(" Welcome to Bug Tracking System ");
        mainMenuLabel.setFont(new Font("Arial", 18));

        // Role buttons
        Button adminButton = new Button("Login as Admin");
        Button testerButton = new Button("Login as Tester");
        Button developerButton = new Button("Login as Developer");
        Button pmButton = new Button("Login as Project Manager");
        Button exitButton = new Button("Exit");

        // Set button widths
        double buttonWidth = 200;
        adminButton.setPrefWidth(buttonWidth);
        testerButton.setPrefWidth(buttonWidth);
        developerButton.setPrefWidth(buttonWidth);
        pmButton.setPrefWidth(buttonWidth);
        exitButton.setPrefWidth(buttonWidth);

        // Add components to the VBox
        vbox.getChildren().addAll(mainMenuLabel, adminButton, testerButton, developerButton, pmButton, exitButton);

        // Main scene
        Scene mainScene = new Scene(vbox, 400, 300);

        // Set the main scene
        primaryStage.setScene(mainScene);
        primaryStage.show();

        // Exit application
        exitButton.setOnAction(e -> System.exit(0));

        // Add role-specific login handlers
        adminButton.setOnAction(e -> showLoginView(primaryStage, "Admin"));
        testerButton.setOnAction(e -> showLoginView(primaryStage, "Tester"));
        developerButton.setOnAction(e -> showLoginView(primaryStage, "Developer"));
        pmButton.setOnAction(e -> showLoginView(primaryStage, "Project Manager"));
    }

    private void showLoginView(Stage primaryStage, String role) {
        // Create a GridPane layout for login
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        // Role label
        Label roleLabel = new Label("Login as " + role);
        roleLabel.setFont(new Font("Arial", 18));

        // Email field
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");

        // Password field
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        // Buttons
        Button loginButton = new Button("Login");
        Button backButton = new Button("Back");

        // Add components to the grid
        grid.add(roleLabel, 0, 0, 2, 1);
        grid.add(emailLabel, 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(loginButton, 0, 3);
        grid.add(backButton, 1, 3);

        // Create a new scene for the login view
        Scene loginScene = new Scene(grid, 400, 300);

        // Set the login scene
        primaryStage.setScene(loginScene);

        // Back button handler
        backButton.setOnAction(e -> start(primaryStage));

        // Login button handler
        loginButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            // Email validation
            if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", email)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
                return;
            }

            // Password validation
            if (password.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Password", "Password cannot be empty.");
                return;
            }

            // Mock user login (replace with actual login logic)
            User user = createUser(role, email, password);
            try {
                if (user.login(role, email, password)) {
                    showRoleSpecificView(primaryStage, role);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email or password.");
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private User createUser(String role, String email, String password) {
        return switch (role) {
            case "Admin" -> new Admin("1", email, password);
            case "Tester" -> new Tester("2", email, password);
            case "Developer" -> new Developer("3", email, password);
            case "Project Manager" -> new ProjectManager("4", email, password);
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }

    private void showRoleSpecificView(Stage primaryStage, String role) {
        switch (role) {
            case "Tester":
                showTesterMenu(primaryStage);
                break;
            case "Developer":
                showDeveloperMenu(primaryStage);
                break;
            case "Project Manager":
                showProjectManagerMenu(primaryStage);
                break;
            case "Admin":
                showAdminMenu(primaryStage); // Added Admin menu handling
                break;
            default:
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid role specified!");
                break;
        }
    }
//    admin menu---------------------------------------------------
    private void showAdminMenu(Stage primaryStage) {
        // Create a new stage for the Admin menu
        Stage adminStage = new Stage();
        adminStage.setTitle("Admin Menu");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label titleLabel = new Label("<< Admin Menu >>");
        titleLabel.setFont(new Font("Arial", 18));

        // Buttons for Admin actions
        Button viewAllBugsButton = new Button("View All Bugs");
        Button manageUsersButton = new Button("Manage Users");
        Button logoutButton = new Button("Log Out");

        viewAllBugsButton.setPrefWidth(250);
        manageUsersButton.setPrefWidth(250);
        logoutButton.setPrefWidth(250);

        // Add button actions
        viewAllBugsButton.setOnAction(e -> viewAllBugs());
        manageUsersButton.setOnAction(e -> manageUsers());
        logoutButton.setOnAction(e -> start(primaryStage)); // Redirects to the main menu

        // Add components to the layout
        layout.getChildren().addAll(titleLabel, viewAllBugsButton, manageUsersButton, logoutButton);

        // Set the scene
        Scene adScene = new Scene(layout, 400, 400);
        primaryStage.setScene(adScene);
    }
    private void viewAllBugs() {
        // Simulate viewing all bugs
        Stage bugsStage = new Stage();
        bugsStage.setTitle("All Bugs");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label titleLabel = new Label("All Bugs in the System");
        titleLabel.setFont(new Font("Arial", 16));

        Button fetchBugsButton = new Button("Fetch All Bugs");
        fetchBugsButton.setOnAction(e -> {
            // Call Admin.viewAllBugs() to fetch and display bugs
            String allBugs = Admin.viewallbugs(); // Simulate fetching bugs
            TextArea bugsTextArea = new TextArea(allBugs);
            bugsTextArea.setEditable(false); // Make it non-editable
            layout.getChildren().add(bugsTextArea);
            showAlert(Alert.AlertType.INFORMATION, "Success", "All bugs displayed.");
        });

        layout.getChildren().addAll(titleLabel, fetchBugsButton);

        Scene scene = new Scene(layout, 600, 400);
        bugsStage.setScene(scene);
        bugsStage.show();
    }
    private void manageUsers() {
        // Create a new stage for managing users
        Stage manageUsersStage = new Stage();
        manageUsersStage.setTitle("Manage Users");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label titleLabel = new Label("Manage Users (Add/Update/Delete)");
        titleLabel.setFont(new Font("Arial", 16));

        Button addUserButton = new Button("Add User");
        Button updateUserButton = new Button("Update User");
        Button deleteUserButton = new Button("Delete User");

        addUserButton.setPrefWidth(250);
        updateUserButton.setPrefWidth(250);
        deleteUserButton.setPrefWidth(250);

        // Button actions for user management
        addUserButton.setOnAction(e -> addUser());
        updateUserButton.setOnAction(e -> updateUser());
        deleteUserButton.setOnAction(e -> deleteUser());

        layout.getChildren().addAll(titleLabel, addUserButton, updateUserButton, deleteUserButton);

        Scene scene = new Scene(layout, 400, 300);
        manageUsersStage.setScene(scene);
        manageUsersStage.show();
    }
    private void addUser() {
        Stage addUserStage = new Stage();
        addUserStage.setTitle("Add User");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        TextField roleField = new TextField();
        roleField.setPromptText("Enter user role");

        TextField idField = new TextField();
        idField.setPromptText("Enter user ID");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter user email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter user password");

        Button addButton = new Button("Add User");
        addButton.setOnAction(e -> {
            String role = roleField.getText().trim();
            String id = idField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            if (role.isEmpty() || id.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "All fields must be filled.");
                return;
            }

            try {
                FileHandler fileHandler = new FileHandler("files/" + role.toUpperCase() + ".txt");
                if (fileHandler.userExists(id)) {
                    showAlert(Alert.AlertType.WARNING, "Warning", role + " " + id + " already exists.");
                } else {
                    Admin.addusers(role, id, email, password);
                    showAlert(Alert.AlertType.INFORMATION, "Success", role + " added successfully.");
                    addUserStage.close();
                }
            } catch (IOException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add user: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(roleField, idField, emailField, passwordField, addButton);
        Scene scene = new Scene(layout, 400, 300);
        addUserStage.setScene(scene);
        addUserStage.show();
    }
    private void updateUser() {
        Stage updateUserStage = new Stage();
        updateUserStage.setTitle("Update User");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        TextField roleField = new TextField();
        roleField.setPromptText("Enter user role (Tester/Developer/ProjectManager/Admin)");

        TextField idField = new TextField();
        idField.setPromptText("Enter current user ID");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter current user email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter current user password");

        TextField newIdField = new TextField();
        newIdField.setPromptText("Enter new user ID");

        TextField newEmailField = new TextField();
        newEmailField.setPromptText("Enter new user email");

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Enter new user password");

        Button updateButton = new Button("Update User");
        updateButton.setOnAction(e -> {
            String role = roleField.getText().trim();
            String id = idField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            String newId = newIdField.getText().trim();
            String newEmail = newEmailField.getText().trim();
            String newPassword = newPasswordField.getText().trim();

            if (role.isEmpty() || id.isEmpty() || email.isEmpty() || password.isEmpty() ||
                    newId.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "All fields must be filled.");
                return;
            }

            try {
                FileHandler fileHandler = new FileHandler("files/" + role.toUpperCase() + ".txt");
                if (!fileHandler.userExists(id)) {
                    showAlert(Alert.AlertType.WARNING, "Warning", "User " + id + " does not exist.");
                } else {
                    Admin.updateusers(role, id, email, password, newId, newEmail, newPassword);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "User updated successfully.");
                    updateUserStage.close();
                }
            } catch (IOException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update user.");
            }
        });

        layout.getChildren().addAll(roleField, idField, emailField, passwordField, newIdField, newEmailField, newPasswordField, updateButton);
        Scene scene = new Scene(layout, 400, 400);
        updateUserStage.setScene(scene);
        updateUserStage.show();
    }
    private void deleteUser() {
        Stage deleteUserStage = new Stage();
        deleteUserStage.setTitle("Delete User");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        TextField roleField = new TextField();
        roleField.setPromptText("Enter user role (Tester/Developer/ProjectManager/Admin)");

        TextField idField = new TextField();
        idField.setPromptText("Enter user ID");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter user email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter user password");

        Button deleteButton = new Button("Delete User");
        deleteButton.setOnAction(e -> {
            String role = roleField.getText().trim();
            String id = idField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            if (role.isEmpty() || id.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "All fields must be filled.");
                return;
            }

            try {
                FileHandler fileHandler = new FileHandler("files/" + role.toUpperCase() + ".txt");
                if (!fileHandler.userExists(id)) {
                    showAlert(Alert.AlertType.WARNING, "Warning", "User " + id + " does not exist.");
                } else {
                    Admin.deleteusers(role, id, email, password);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "User deleted successfully.");
                    deleteUserStage.close();
                }
            } catch (IOException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete user.");
            }
        });

        layout.getChildren().addAll(roleField, idField, emailField, passwordField, deleteButton);
        Scene scene = new Scene(layout, 400, 300);
        deleteUserStage.setScene(scene);
        deleteUserStage.show();
    }


    //    tester menu---------------------------------------------------
    private void showTesterMenu(Stage primaryStage) {
        // VBox layout for the tester menu
        VBox testerMenu = new VBox(10);
        testerMenu.setAlignment(Pos.CENTER);
        testerMenu.setPadding(new Insets(20));

        // Tester menu title
        Label titleLabel = new Label("<<Tester Menu>>");
        titleLabel.setFont(new Font("Arial", 18));

        // Menu buttons
        Button defineBugButton = new Button("Define Bug");
        Button assignBugButton = new Button("Assign Bug to Developer");
        Button attachScreenshotButton = new Button("Attach Screenshot of Bug");
        Button monitorBugsButton = new Button("Monitor Open and Closed Bugs");
        Button logoutButton = new Button("Log Out");

        // Set button widths
        double buttonWidth = 300;
        defineBugButton.setPrefWidth(buttonWidth);
        assignBugButton.setPrefWidth(buttonWidth);
        attachScreenshotButton.setPrefWidth(buttonWidth);
        monitorBugsButton.setPrefWidth(buttonWidth);
        logoutButton.setPrefWidth(buttonWidth);

        // Add buttons to the layout
        testerMenu.getChildren().addAll(titleLabel, defineBugButton, assignBugButton, attachScreenshotButton, monitorBugsButton, logoutButton);

        // Set the scene
        Scene testerScene = new Scene(testerMenu, 400, 400);
        primaryStage.setScene(testerScene);

        // Button actions
        defineBugButton.setOnAction(e -> defineBug(primaryStage));
        assignBugButton.setOnAction(e -> assignBugToDeveloper(primaryStage));
        attachScreenshotButton.setOnAction(e -> attachScreenshot(primaryStage));
        monitorBugsButton.setOnAction(e -> monitorBugs(primaryStage));
        logoutButton.setOnAction(e -> start(primaryStage)); // Log out and go back to main menu
    }

    private void defineBug(Stage primaryStage) {
        // Define a new bug form
        Stage defineBugStage = new Stage();
        defineBugStage.setTitle("Define Bug");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        // Input fields
        TextField bugNameField = new TextField();
        TextField bugTypeField = new TextField();
        TextField bugPriorityField = new TextField();
        TextField bugLevelField = new TextField();
        TextField projectNameField = new TextField();
        TextField bugDateField = new TextField();
        TextField testerIdField = new TextField();

        bugNameField.setPromptText("Bug Name");
        bugTypeField.setPromptText("Bug Type");
        bugPriorityField.setPromptText("Bug Priority");
        bugLevelField.setPromptText("Bug Level");
        projectNameField.setPromptText("Project Name");
        bugDateField.setPromptText("Bug Date");
        testerIdField.setPromptText("Your ID");

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String bugName = bugNameField.getText().trim();
            String bugType = bugTypeField.getText().trim();
            String bugPriority = bugPriorityField.getText().trim();
            String bugLevel = bugLevelField.getText().trim();
            String projectName = projectNameField.getText().trim();
            String bugDate = bugDateField.getText().trim();
            String testerId = testerIdField.getText().trim();

            if (bugName.isEmpty() || bugType.isEmpty() || bugPriority.isEmpty() || bugLevel.isEmpty() ||
                    projectName.isEmpty() || bugDate.isEmpty() || testerId.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "All fields must be filled.");
                return;
            }

            // Call Tester.addBug
            Tester.addBug(bugName, bugType, bugPriority, bugLevel, projectName, bugDate, testerId);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Bug added successfully.");
            defineBugStage.close();
        });


        // Layout for form
        VBox formLayout = new VBox(10,
                new Label("Enter Bug Details"),
                bugNameField, bugTypeField, bugPriorityField,
                bugLevelField, projectNameField,
                bugDateField, testerIdField, submitButton);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setPadding(new Insets(20));

        Scene scene = new Scene(formLayout, 400, 400);
        defineBugStage.setScene(scene);
        defineBugStage.show();
    }
    private void assignBugToDeveloper(Stage primaryStage) {
        // Stage for assigning a bug to a developer
        Stage assignBugStage = new Stage();
        assignBugStage.setTitle("Assign Bug to Developer");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        // Input fields
        TextField bugIdField = new TextField();
        TextField developerIdField = new TextField();

        bugIdField.setPromptText("Bug ID");
        developerIdField.setPromptText("Developer ID");

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String bugId = bugIdField.getText().trim();
            String developerId = developerIdField.getText().trim();

            if (bugId.isEmpty() || developerId.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "All fields must be filled.");
                return;
            }

            // Check if the Developer ID exists
            if (!Tester.isDeveloperIDExists(developerId)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Developer ID not found.");
                return;
            }

            // Assign bug to developer and capture the email content
            Tester.assignBugToDeveloper(bugId, developerId);

            // Compose the email message
            String emailMessage = "From: TesterSystem@bugtracking.com\n"
                    + "To: Developer_" + developerId + "@company.com\n"
                    + "Subject: New Bug Assignment\n"
                    + "\n"
                    + "Dear Developer " + developerId + ",\n\n"
                    + "You have been assigned a new bug.\n"
                    + "Bug ID: " + bugId + "\n"
                    + "Status: Assigned\n\n"
                    + "Please review and take appropriate actions.\n"
                    + "For further assistance, contact the Tester who assigned this bug.\n\n"
                    + "Best Regards,\n"
                    + "Bug Tracking System";

            // Show the email content in a new window
            showEmailWindow(emailMessage);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Bug assigned to developer successfully.");
            assignBugStage.close();
        });

        // Layout for the form
        VBox formLayout = new VBox(10,
                new Label("Assign Bug to Developer"),
                bugIdField, developerIdField, submitButton);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setPadding(new Insets(20));

        Scene scene = new Scene(formLayout, 400, 300);
        assignBugStage.setScene(scene);
        assignBugStage.show();
    }

    // Helper method to display the email content in a new window
    private void showEmailWindow(String emailMessage) {
        Stage emailStage = new Stage();
        emailStage.setTitle("Email Notification");

        TextArea emailTextArea = new TextArea(emailMessage);
        emailTextArea.setEditable(false);
        emailTextArea.setWrapText(true);

        VBox layout = new VBox(10, new Label("Email Content"), emailTextArea);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 500, 400);
        emailStage.setScene(scene);
        emailStage.show();
    }


    private void attachScreenshot(Stage primaryStage) {
        // Stage for attaching a screenshot to a bug
        Stage attachScreenshotStage = new Stage();
        attachScreenshotStage.setTitle("Attach Screenshot to Bug");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        // Input fields
        TextField bugIdField = new TextField();
        TextField screenshotPathField = new TextField();

        bugIdField.setPromptText("Bug ID");
        screenshotPathField.setPromptText("Screenshot Path");

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String bugId = bugIdField.getText().trim();
            String screenshotPath = screenshotPathField.getText().trim();

            if (bugId.isEmpty() || screenshotPath.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "All fields must be filled.");
                return;
            }

            // Call Tester.attachScreenshot
            Tester.attachScreenshot(bugId, screenshotPath);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Screenshot attached to bug successfully.");
            attachScreenshotStage.close();
        });

        // Layout for the form
        VBox formLayout = new VBox(10,
                new Label("Attach Screenshot to Bug"),
                bugIdField, screenshotPathField, submitButton);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setPadding(new Insets(20));

        Scene scene = new Scene(formLayout, 400, 300);
        attachScreenshotStage.setScene(scene);
        attachScreenshotStage.show();
    }

    private void monitorBugs(Stage primaryStage) {
        // Stage for monitoring open and closed bugs
        Stage monitorBugsStage = new Stage();
        monitorBugsStage.setTitle("Monitor Bugs");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label titleLabel = new Label("View Open/Closed Bugs");
        titleLabel.setFont(new Font("Arial", 16));

        Button viewOpenBugsButton = new Button("View Open Bugs");
        Button viewClosedBugsButton = new Button("View Closed Bugs");

        viewOpenBugsButton.setPrefWidth(200);
        viewClosedBugsButton.setPrefWidth(200);

        // Button actions
        viewOpenBugsButton.setOnAction(e -> {
            Tester.viewBugsByStatus("Open");
            showAlert(Alert.AlertType.INFORMATION, "Open Bugs", "Open bugs have been displayed in the console.");
        });

        viewClosedBugsButton.setOnAction(e -> {
            Tester.viewBugsByStatus("Closed");
            showAlert(Alert.AlertType.INFORMATION, "Closed Bugs", "Closed bugs have been displayed in the console.");
        });

        // Add components to the layout
        layout.getChildren().addAll(titleLabel, viewOpenBugsButton, viewClosedBugsButton);

        Scene scene = new Scene(layout, 400, 300);
        monitorBugsStage.setScene(scene);
        monitorBugsStage.show();
    }
//    developer menu---------------------------------------------
    private void showDeveloperMenu(Stage primaryStage) {
        // Create a new stage for the Developer menu
        Stage developerStage = new Stage();
        developerStage.setTitle("<<Developer Menu>>");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label titleLabel = new Label("<< Developer Menu >>");
        titleLabel.setFont(new Font("Arial", 18));

        // Buttons for Developer actions
        Button viewAssignedBugsButton = new Button("View Assigned Bugs");
        Button changeBugStatusButton = new Button("Change Status of Bug");
        Button logoutButton = new Button("Log Out");

        viewAssignedBugsButton.setPrefWidth(200);
        changeBugStatusButton.setPrefWidth(200);
        logoutButton.setPrefWidth(200);

        // Add button actions
        viewAssignedBugsButton.setOnAction(e -> viewAssignedBugs(primaryStage));
        changeBugStatusButton.setOnAction(e -> changeBugStatus(primaryStage));
        logoutButton.setOnAction(e -> start(primaryStage)); // Redirects to main menu

        // Add components to layout
        layout.getChildren().addAll(titleLabel, viewAssignedBugsButton, changeBugStatusButton, logoutButton);

        // Set the scene
        Scene devScene = new Scene(layout, 400, 400);
        primaryStage.setScene(devScene);
    }
    private void viewAssignedBugs(Stage primaryStage) {
        Stage viewBugsStage = new Stage();
        viewBugsStage.setTitle("View Assigned Bugs");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label titleLabel = new Label("View Assigned Bugs");
        titleLabel.setFont(new Font("Arial", 16));

        TextField developerIdField = new TextField();
        developerIdField.setPromptText("Enter Developer ID");

        Button viewButton = new Button("View");
        viewButton.setOnAction(e -> {
            String developerId = developerIdField.getText().trim();
            if (developerId.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Developer ID must not be empty.");
                return;
            }

            // Call Developer.viewAssignedBugs
            Developer.viewAssignedBugs(developerId);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Assigned bugs displayed in the console.");
            viewBugsStage.close();
        });

        layout.getChildren().addAll(titleLabel, developerIdField, viewButton);

        Scene scene = new Scene(layout, 400, 300);
        viewBugsStage.setScene(scene);
        viewBugsStage.show();
    }
    private void changeBugStatus(Stage primaryStage) {
        Stage changeStatusStage = new Stage();
        changeStatusStage.setTitle("Change Bug Status");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label titleLabel = new Label("Change Bug Status");
        titleLabel.setFont(new Font("Arial", 16));

        // Input fields
        TextField bugIdField = new TextField();
        bugIdField.setPromptText("Bug ID");

        TextField statusField = new TextField();
        statusField.setPromptText("Status (open/closed)");

        TextField testerIdField = new TextField();
        testerIdField.setPromptText("Tester ID");

        TextField developerIdField = new TextField();
        developerIdField.setPromptText("Developer ID");

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            String bugId = bugIdField.getText().trim();
            String status = statusField.getText().trim();
            String testerId = testerIdField.getText().trim();
            String developerId = developerIdField.getText().trim();

            if (bugId.isEmpty() || status.isEmpty() || testerId.isEmpty() || developerId.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "All fields must be filled.");
                return;
            }

            // Call Developer.updateBugStatus
            Developer.updateBugStatus(bugId, status, testerId, developerId);

            // Compose the email message for display
            String emailMessage = "From: DeveloperSystem@bugtracking.com\n"
                    + "To: Tester_" + testerId + "@company.com\n"
                    + "Subject: Bug Status Update\n"
                    + "\n"
                    + "Dear Tester " + testerId + ",\n\n"
                    + "The bug with the following details has been updated:\n"
                    + "Bug ID: " + bugId + "\n"
                    + "New Status: " + status + "\n\n"
                    + "Please review the changes and follow up if necessary.\n\n"
                    + "Best Regards,\n"
                    + "Bug Tracking System";

            // Show the email content in a new window
            showEmailWindow(emailMessage);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Bug status updated successfully.");
            changeStatusStage.close();
        });

        layout.getChildren().addAll(titleLabel, bugIdField, statusField, testerIdField, developerIdField, updateButton);

        Scene scene = new Scene(layout, 400, 400);
        changeStatusStage.setScene(scene);
        changeStatusStage.show();
    }

    // Helper method to display the email content in a new window
//    private void showEmailWindow(String emailMessage) {
//        Stage emailStage = new Stage();
//        emailStage.setTitle("Email Notification");
//
//        TextArea emailTextArea = new TextArea(emailMessage);
//        emailTextArea.setEditable(false);
//        emailTextArea.setWrapText(true);
//
//        VBox layout = new VBox(10, new Label("Email Content"), emailTextArea);
//        layout.setPadding(new Insets(20));
//        layout.setAlignment(Pos.CENTER);
//
//        Scene scene = new Scene(layout, 500, 400);
//        emailStage.setScene(scene);
//        emailStage.show();
//    }

    //    projectmanger menu-------------------------------------------------------------------------------
    private void showProjectManagerMenu(Stage primaryStage) {
        // Create a new stage for the Project Manager menu
        Stage projectManagerStage = new Stage();
        projectManagerStage.setTitle("<<Project Manager Menu>>");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label titleLabel = new Label("<< Project Manager Menu >>");
        titleLabel.setFont(new Font("Arial", 18));

        // Buttons for Project Manager actions
        Button checkDevPerformanceButton = new Button("Check Developer Performance");
        Button checkTesterPerformanceButton = new Button("Check Tester Performance");
        Button monitorBugsButton = new Button("Monitor Bugs");
        Button logoutButton = new Button("Log Out");

        checkDevPerformanceButton.setPrefWidth(250);
        checkTesterPerformanceButton.setPrefWidth(250);
        monitorBugsButton.setPrefWidth(250);
        logoutButton.setPrefWidth(250);

        // Add button actions
        checkDevPerformanceButton.setOnAction(e -> checkDeveloperPerformance());
        checkTesterPerformanceButton.setOnAction(e -> checkTesterPerformance());
        monitorBugsButton.setOnAction(e -> monitorBugs());
        logoutButton.setOnAction(e -> start(primaryStage)); // Redirects to the main menu


        // Add components to the layout
        layout.getChildren().addAll(titleLabel, checkDevPerformanceButton, checkTesterPerformanceButton, monitorBugsButton, logoutButton);

        // Set the scene
        Scene pmScene = new Scene(layout, 400, 400);
        primaryStage.setScene(pmScene);

    }
    private void checkDeveloperPerformance() {
        // Simulate checking developer performance
        Stage performanceStage = new Stage();
        performanceStage.setTitle("Developer Performance");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label titleLabel = new Label("Developer Performance");
        titleLabel.setFont(new Font("Arial", 16));

        Button fetchPerformanceButton = new Button("Fetch Performance");
        fetchPerformanceButton.setOnAction(e -> {
            // Call ProjectManager.checkDeveloperPerformance()
            ProjectManager.checkDeveloperPerformance();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Developer performance displayed in the console.");
            performanceStage.close();
        });

        layout.getChildren().addAll(titleLabel, fetchPerformanceButton);

        Scene scene = new Scene(layout, 400, 200);
        performanceStage.setScene(scene);
        performanceStage.show();
    }
    private void checkTesterPerformance() {
        // Simulate checking tester performance
        Stage performanceStage = new Stage();
        performanceStage.setTitle("Tester Performance");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label titleLabel = new Label("Tester Performance");
        titleLabel.setFont(new Font("Arial", 16));

        Button fetchPerformanceButton = new Button("Fetch Performance");
        fetchPerformanceButton.setOnAction(e -> {
            // Call ProjectManager.checkTesterPerformance()
            ProjectManager.checkTesterPerformance();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Tester performance displayed in the console.");
            performanceStage.close();
        });

        layout.getChildren().addAll(titleLabel, fetchPerformanceButton);

        Scene scene = new Scene(layout, 400, 200);
        performanceStage.setScene(scene);
        performanceStage.show();
    }
    private void monitorBugs() {
        // Simulate monitoring bugs
        Stage monitorStage = new Stage();
        monitorStage.setTitle("Monitor Bugs");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label titleLabel = new Label("Monitor Bugs");
        titleLabel.setFont(new Font("Arial", 16));

        Button closedBugsButton = new Button("View Bugs");

        closedBugsButton.setOnAction(e -> {
            // Call ProjectManager.monitorBugs() for closed bugs
            ProjectManager.monitorBugs();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Closed/Open bugs displayed in the console.");
        });

        layout.getChildren().addAll(titleLabel,closedBugsButton);

        Scene scene = new Scene(layout, 400, 300);
        monitorStage.setScene(scene);
        monitorStage.show();
    }
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}