package my.web;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "shots")
public class Shot implements Serializable {
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic(optional = false)
    @Column(name = "x", nullable = false)
    private float x;

    @Basic(optional = false)
    @Column(name = "y", nullable = false)
    private float y;

    @Basic(optional = false)
    @Column(name = "radius", nullable = false)
    private float radius;

    @Basic(optional = false)
    @Column(name = "hit", nullable = false)
    private boolean hit;

    @Basic(optional = false)
    @Column(name = "date", nullable = false)
    private Date date;

    @Basic(optional = false)
    @Column(name = "execution_time", nullable = false)
    private long executionTime;

    @Basic(optional = false)
    @Column(name = "shooter", nullable = false)
    private String shooter;

    public Shot(float x, float y, float radius, boolean hit, Date date, long executionTime, String shooter) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.hit = hit;
        this.date = date;
        this.executionTime = executionTime;
        this.shooter = shooter;
    }

    public Shot(int id, float x, float y, float radius, boolean hit, Date date, long executionTime, String shooter) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.hit = hit;
        this.date = date;
        this.executionTime = executionTime;
        this.shooter = shooter;
    }

    public Shot() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    public boolean getHit() {
        return hit;
    }

    public Date getDate() {
        return date;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public String getShooter() {
        return shooter;
    }

    public void setShooter(String shooter) {
        this.shooter = shooter;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Shot) obj;
        return Float.floatToIntBits(this.x) == Float.floatToIntBits(that.x) && Float.floatToIntBits(this.y) == Float.floatToIntBits(that.y) && Float.floatToIntBits(this.radius) == Float.floatToIntBits(that.radius) && this.hit == that.hit && Objects.equals(this.date, that.date) && this.executionTime == that.executionTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, radius, hit, date, executionTime);
    }

    @Override
    public String toString() {
        return "Shot[" + "x=" + x + ", " + "y=" + y + ", " + "radius=" + radius + ", " + "hit=" + hit + ", " + "date=" + date + ", " + "executionTime=" + executionTime + ']';
    }
}
