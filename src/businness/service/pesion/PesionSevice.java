package businness.service.pesion;

import businness.service.AppSevice;

public interface PesionSevice extends AppSevice {
    int login(String username, String password); // return loginResult
    void logout();
}
