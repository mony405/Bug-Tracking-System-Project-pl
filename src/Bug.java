
public class Bug {
    private String bugID;
    private String bugName;
    private String bugType;
    private String bugPriority;
    private String bugLevel;
    private String projectName;
    private String startDate;
    private Developer assignedDeveloper;
    private Tester reportedByTester;
    private String screenshotPath;
    private boolean closed;

    // Constructor
    public Bug(String bugID, String bugName, String bugType, String bugPriority,
               String bugLevel, String projectName, String startDate,
               Tester reportedByTester) {
        this.bugID = bugID;
        this.bugName = bugName;
        this.bugType = bugType;
        this.bugPriority = bugPriority;
        this.bugLevel = bugLevel;
        this.projectName = projectName;
        this.startDate = startDate;
        this.reportedByTester = reportedByTester;
        this.closed = false; // Default status is open
        this.screenshotPath = "NoScreenshotYet"; // Default value

    }

    // Getter and Setter methods
    public String getBugID() {
        return bugID;
    }

    public String getBugName() {
        return bugName;
    }

    public void setBugName(String bugName) {
        this.bugName = bugName;
    }

    public String getBugType() {
        return bugType;
    }

    public void setBugType(String bugType) {
        this.bugType = bugType;
    }

    public String getBugPriority() {
        return bugPriority;
    }

    public void setBugPriority(String bugPriority) {
        this.bugPriority = bugPriority;
    }

    public String getBugLevel() {
        return bugLevel;
    }

    public void setBugLevel(String bugLevel) {
        this.bugLevel = bugLevel;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Developer getAssignedDeveloper() {
        return assignedDeveloper;
    }

    public void assignDeveloper(Developer developer) {
        this.assignedDeveloper = developer;
    }

    public Tester getReportedByTester() {
        return reportedByTester;
    }

    public boolean isClosed() {
        return closed;
    }

    public void closeBug() {
        this.closed = true;
    }

    public String getScreenshotPath() {
        return screenshotPath;
    }

    public void setScreenshotPath(String screenshotPath) {
        this.screenshotPath = screenshotPath;
    }

    // Format Bug Details as CSV without getId methods
    public String toCSV() {
        return String.join(",",
                bugID,
                bugName,
                bugType,
                bugPriority,
                bugLevel,
                projectName,
                startDate,
                closed ? "Closed" : "Open",
                assignedDeveloper != null ? assignedDeveloper.toString() : "Unassigned",
                reportedByTester != null ? reportedByTester.toString() : "Unknown",
                screenshotPath
        );
    }

    // Override toString method
    @Override
    public String toString() {
        return "Bug ID: " + bugID + "\n" +
                "Bug Name: " + bugName + "\n" +
                "Bug Type: " + bugType + "\n" +
                "Bug Priority: " + bugPriority + "\n" +
                "Bug Level: " + bugLevel + "\n" +
                "Project Name: " + projectName + "\n" +
                "Start Date: " + startDate + "\n" +
                "Status: " + (closed ? "Closed" : "Open") + "\n" +
                "Assigned Developer: " + (assignedDeveloper != null ? assignedDeveloper.toString() : "Unassigned") + "\n" +
                "Reported By: " + (reportedByTester != null ? reportedByTester.toString() : "Unknown")+
                "Screenshot Path: " + screenshotPath;
    }
}