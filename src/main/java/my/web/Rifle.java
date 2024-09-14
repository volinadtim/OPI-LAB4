package my.web;

import my.web.area.TargetAreaCircle;
import my.web.area.TargetAreaRectangle;
import my.web.area.TargetAreaTriangle;
import my.web.database.HibernateOrm;
import my.web.database.Orm;
import my.web.database.JdbcOrm;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

// @ManagedBean()
// @SessionScoped()
public class Rifle {

    private final int updateTime = 9;
    private final Input coordinateXInput = new Input(InputType.pSlider, -5, 5, 0.1);
    private final Input coordinateYInput = new Input(InputType.inputText, -5, 3);
    private final Input radiusInput = new Input(InputType.commandButton, List.of(1d, 2d, 3d, 4d, 5d));

    private float coordinateX = 0;
    private float coordinateY = 0;

    private float radius = 0;
    private final Target target = new Target();
    final Orm orm = new HibernateOrm();

    {
        target.setQuadrant1(null);
        target.setQuadrant2(new TargetAreaRectangle(1f));
        target.setQuadrant3(new TargetAreaTriangle(0.5f, 0.5f));
        target.setQuadrant4(new TargetAreaCircle(1f));
    }

    public Target getTarget() {
        return target;
    }

    private List<Shot> shots;

    public Rifle() {
        shots = orm.getShots(getShooter());
    }


    public float getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(float coordinateX) {
        this.coordinateX = coordinateX;
    }

    public float getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(float coordinateY) {
        this.coordinateY = coordinateY;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public List<Shot> getShots() {
        return shots;
    }

    public void shoot() {
        if (!validate()) {
            // TODO: Raise error
            return;
        }
        var shot = createShot();
        shots.add(shot);
        orm.createShot(shot);
        shots = orm.getShots(getShooter());
    }

    public void shootSpontaneous() {
        var map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String x = map.get("x");
        String y = map.get("y");
        this.coordinateX = Float.parseFloat(x);
        this.coordinateY = Float.parseFloat(y);
        this.shoot();
    }

    public void clear() {
        shots.clear();
        orm.clearShots(getShooter());
    }

    public void validateCoordinateX(FacesContext context, UIComponent component, double value) throws ValidatorException {
        var error = coordinateXInput.validate(value);
        if (error != null) {
            throw new ValidatorException(new FacesMessage(error));
        }
    }

    public void validateCoordinateY(FacesContext context, UIComponent component, double value) throws ValidatorException {
        var error = coordinateYInput.validate(value);
        if (error != null) {
            throw new ValidatorException(new FacesMessage(error));
        }
    }

    public void validateRadius(FacesContext context, UIComponent component, double value) throws ValidatorException {
        var error = radiusInput.validate(value);
        if (error != null) {
            throw new ValidatorException(new FacesMessage(error));
        }
    }


    private boolean validate() {
        var resX = coordinateXInput.validate(coordinateX);
        var resY = coordinateYInput.validate(coordinateY);
        var resR = radiusInput.validate(radius);
        return resX == null && resY == null && resR == null;
    }

    private Shot createShot() {
        long startTime = System.nanoTime();
        Date now = new Date();

        var hit = this.containsCoordinate(coordinateX, coordinateY, radius);

        long elapsedTime = (System.nanoTime() - startTime) / 1000;
        var shooter = getShooter();
        return new Shot(coordinateX, coordinateY, radius, hit, now, elapsedTime, shooter);
    }

    private String getShooter() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return facesContext.getExternalContext().getSessionId(true);
    }

    private boolean containsCoordinate(float x, float y, float radius) {
        return target.containsCoordinate(x, y, radius);
    }

    public Input getCoordinateXInput() {
        return coordinateXInput;
    }

    public Input getCoordinateYInput() {
        return coordinateYInput;
    }

    public Input getRadiusInput() {
        return radiusInput;
    }

    public int getUpdateTime() {
        return updateTime;
    }
}
