package ca.bcit.comp2522.java_final_project;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class TileEntity
{
    private double x;
    private double y;
    private double offsetX;
    private double offsetY;

    private Rectangle rectangle;
    private Translate position;
    private Rotate    rotation;

    private boolean    flipped;
    private Directions direction;


    public TileEntity(double x,
                      double y,
                      double offsetX,
                      double offsetY,
                      Rectangle rectangle) {
        this.x = x;
        this.y = y;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.rectangle = rectangle;
        flipped = false;
        direction = Directions.UP;

        setUp();
        updatePosition();

    }

    private void setUp() {
        position = new Translate();
        rotation = new Rotate();

        rectangle.getTransforms().addAll(position, rotation);
    }

    public void moveTo(double x,
                     double y)
    {
        this.x = x;
        this.y = y;

        updatePosition();
    }

    public void setRotate(final double angle)
    {
        rotation.setAngle(angle);
    }

    public void addRotate(final double angle) {
        rotation.setAngle(rotation.getAngle() + angle);
    }

    public double getRotate() {
        return rotation.getAngle();
    }

    public Rectangle getRectangle()
    {
        return rectangle;
    }

    public void setRotateAxis(final double x,
                                final double y)
    {
        rotation.setPivotX(x);
        rotation.setPivotY(y);
    }

    public void updatePosition()
    {
        position.setX(x-offsetX);
        position.setY(y-offsetY);
    }

    public void addToGroup(final Group group) {
        group.getChildren().add(rectangle);
    }

}
