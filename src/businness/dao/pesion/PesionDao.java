package businness.dao.pesion;

import businness.dao.AppDao;

public interface PesionDao extends AppDao {
        int login(String username, String password); // return loginResult
    void logout();
}
