package my.web.database;

import my.web.Shot;

import java.util.List;

public interface Orm {

    void createShot(Shot shot);

    List<Shot> getShots(String shooter);

    void clearShots(String shooter);
}
