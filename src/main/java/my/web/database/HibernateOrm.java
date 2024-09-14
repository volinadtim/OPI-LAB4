package my.web.database;

import my.web.Shot;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.*;
import java.util.List;

public class HibernateOrm implements Orm {
    private final EntityManager manager;
    private List<Shot> shots;

    public HibernateOrm() {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        manager = (EntityManager) sessionFactory.openSession();
    }

    @Override
    public void createShot(Shot shot) {
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        manager.persist(shot);
        transaction.commit();
    }

    @Override
    public List<Shot> getShots(String shooter) {
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            TypedQuery<Shot> query = manager.createQuery("SELECT p FROM Shot p WHERE p.shooter = :shooter", Shot.class);
            query = query.setParameter("shooter", shooter);
            transaction.commit();
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e);
            transaction.rollback();
        }
        return null;
    }

    @Override
    public void clearShots(String shooter) {
        TypedQuery<Shot> query = manager.createQuery("DELETE FROM Shot WHERE shots.shooter = :shooter", Shot.class);
        query = query.setParameter("shooter", shooter);
        query.getResultList();
    }
}
