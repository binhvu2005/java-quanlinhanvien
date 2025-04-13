package businness.service.pesion;


import businness.dao.pesion.PesionDao;
import businness.dao.pesion.PesionDaoImp;

public class PesionSeviceImp implements PesionSevice {
    private final PesionDao pesionDao = new PesionDaoImp();
    @Override
    public int login(String username, String password) {
        return pesionDao.login(username, password);
    }
    @Override
    public void logout() {
        pesionDao.logout();
    }
}
