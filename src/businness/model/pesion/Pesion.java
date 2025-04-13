package businness.model.pesion;

public class Pesion {
    private String Username;
    private String Password;
    private PesionStatus Status;

    public Pesion(String username, String password, PesionStatus status) {
        Username = username;
        Password = password;
        Status = status;
    }
    public Pesion(){

    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public PesionStatus getStatus() {
        return Status;
    }

    public void setStatus(PesionStatus status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "Pesion{" +
                "Username='" + Username + '\'' +
                ", Password='" + Password + '\'' +
                ", Status=" + Status +
                '}';
    }
}
