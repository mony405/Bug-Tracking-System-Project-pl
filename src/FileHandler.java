import java.io.*;
import java.util.*;
public class FileHandler {
    protected String path;
    private FileWriter fw;
    private Scanner fr;

    public FileHandler(String path) {
        this.path = path;
    }

    public String readLine(String ID) throws IOException {
        String content;
        this.fr = new Scanner(new File(path));
        while (fr.hasNextLine()) {
            content = fr.nextLine();
            if (content.startsWith(ID))
                return content;
        }
        fr.close();
        return "Nothing";
    }

    public boolean delete(String s) {
        try {
            StringBuilder content = new StringBuilder();
            String temp;
            boolean flag = false;
            this.fr = new Scanner(new File(path));
            while (fr.hasNextLine()) {
                temp = fr.nextLine();
                if (!temp.equals(s)) {
                    content.append(temp).append("\n");
                } else
                    flag = true;
            }
            fr.close();
            this.fw = new FileWriter(path, false);
            fw.write(content.toString());
            fw.close();
            return flag;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean update(String line, String updatedRow) {
        try {
            StringBuilder content = new StringBuilder();
            String temp;
            this.fr = new Scanner(new File(path));
            while (fr.hasNextLine()) {
                temp = fr.nextLine();
                if (!temp.equals(line)) {
                    content.append(temp).append("\n");
                } else {
                    content.append(updatedRow).append("\n");
                }
            }
            fr.close();
            this.fw = new FileWriter(path, false);
            fw.write(content.toString());
            fw.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean userExists(String userId) throws IOException {
        this.fr = new Scanner(new File(path));
        while (fr.hasNextLine()) {
            String line = fr.nextLine();
            if (line.startsWith(userId)) {
                fr.close();
                return true; // User found
            }
        }
        fr.close();
        return false; // User not found
    }

    public boolean append(String line)  {
        try {
            this.fw = new FileWriter(path, true);
            fw.append(line).append("\n");
            fw.close();
        } catch (IOException e) {
//            System.out.println("An error occurred." + e.getMessage());
            return false ;
        }
        return true;
    }

    public String readFile() {
        StringBuilder content = new StringBuilder();
        try {
            this.fr = new Scanner(new File(path));
            while (fr.hasNextLine()) {
                content.append("\n").append(fr.nextLine());
            }
            fr.close();
        } catch (FileNotFoundException e) {
            return "error";
        }
        return content.toString();
    }

    public boolean emailExists(String email) throws IOException {
        this.fr = new Scanner(new File(path));
        while (fr.hasNextLine()) {
            String line = fr.nextLine();
            String[] columns = line.split(","); // Assuming CSV format
            if (columns.length > 1 && columns[1].trim().equals(email)) {
                fr.close();
                return true; // Email found in the second column
            }
        }
        fr.close();
        return false; // Email not found
    }

    public boolean passwordExists(String password) throws IOException {
        this.fr = new Scanner(new File(path));
        while (fr.hasNextLine()) {
            String line = fr.nextLine();
            String[] columns = line.split(","); // Assuming CSV format
            if (columns.length > 2 && columns[2].trim().equals(password)) {
                fr.close();
                return true; // Password found in the third column
            }
        }
        fr.close();
        return false; // Password not found
    }

    private void ensureFileExists() {
        try {
            File file = new File(path); // Convert path to File object
            if (!file.exists()) {
                if (file.getParentFile() != null && !file.getParentFile().exists()) {
                    file.getParentFile().mkdirs(); // Create directories if necessary
                }
                file.createNewFile(); // Create file
                System.out.println("File created at: " + path);
            } else {
                System.out.println("File already exists at: " + path);
            }
        } catch (IOException e) {
            System.out.println("Error ensuring file existence: " + e.getMessage());
            e.printStackTrace();
        }
    }


}