import java.io.IOException;

public interface Login {
    boolean login(String role,String email,String password) throws IOException;
}