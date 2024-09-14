package my.web.database;

import my.web.Shot;

import javax.persistence.*;
import java.util.List;

public class PersistenceOrm implements Orm {
    private final EntityManager manager;
    private List<Shot> shots;

    public PersistenceOrm() {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("db_con");
        manager = managerFactory.createEntityManager();
    }

    @Override
    public void createShot(Shot shot) {
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        manager.persist(shot);
        transaction.commit();
        transaction.begin();
    }

    @Override
    public List<Shot> getShots(String shooter) {
        TypedQuery<Shot> query = manager.createQuery("SELECT shots FROM shots WHERE shots.shooter = :shooter", Shot.class);
        query = query.setParameter("shooter", shooter);
        return query.getResultList();
    }

    @Override
    public void clearShots(String shooter) {
        TypedQuery<Shot> query = manager.createQuery("DELETE FROM shots WHERE shots.shooter = :shooter", Shot.class);
        query = query.setParameter("shooter", shooter);
        query.getResultList();
    }
}
