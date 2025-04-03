package ca.bcit.comp2522.java_final_project;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.HashMap;

public class TileEntity
{
    private static double GRID_SIZE = 50;
    private static final HashMap<Integer, TileEntity> tileEntities;

    static {
        tileEntities = new HashMap<>();
    }

    private final int  entityID;
    private Point2D position;
    private Point2D offset;
    private double angle;

    private Rectangle rectangle;
    private Translate positionTransform;
    private Rotate    rotationTransform;

    private boolean    flipped;
    private Directions direction;

    private int groupID;

    public TileEntity(double x,
                      double y,
                      double offsetX,
                      double offsetY,
                      double angle,
                      Rectangle rectangle)
    {
        this.position = new Point2D(x, y);
        this.offset = new Point2D(offsetX, offsetY);
        this.rectangle = rectangle;
        this.angle       = angle;
        flipped        = false;
        direction      = Directions.UP;


        this.entityID = tileEntities.size() + 1;
        tileEntities.put(entityID, this);

        setUpTransformations();
        setUpDragAndDrop();
        updatePosition();
        updatePivot();

    }

    private void setUpTransformations() {
        positionTransform = new Translate();
        rotationTransform = new Rotate();

        rectangle.getTransforms().addAll(rotationTransform,
                                         positionTransform);
    }

    public void moveTo(double x,
                     double y)
    {
        position = new Point2D(x, y);

        updatePosition();
        updatePivot();
    }

    public void setAngle(final double angle)
    {
        this.angle = angle;
        updateRotate();
    }

    public void addAngle(final double angle) {
        this.angle += angle;
        updateRotate();
    }

    public double getAngle() {
        return angle;
    }

    public Rectangle getRectangle()
    {
        return rectangle;
    }

    public void setPivot(final double x,
                         final double y)
    {
        rotationTransform.setPivotX(x);
        rotationTransform.setPivotY(y);
    }

    public void updatePivot() {
        rotationTransform.setPivotX(position.getX());
        rotationTransform.setPivotY(position.getY());
    }

    public void updatePosition()
    {
        positionTransform.setX(position.getX() - offset.getX());
        positionTransform.setY(position.getY() - offset.getY());
    }

    public void addToGroup(final Group group) {
        group.getChildren().add(rectangle);
    }

    public void snapToGrid()
    {
        position = new Point2D( Math.round(position.getX()/GRID_SIZE) * GRID_SIZE,
                                Math.round(position.getY()/GRID_SIZE) * GRID_SIZE);

        updatePosition();
        updatePivot();
    }

    public double getX()
    {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public static double getGridSize()
    {
        return GRID_SIZE;
    }

    public void setUpDragAndDrop()
    {

        final DragAndDropDelta delta = new DragAndDropDelta();
        rectangle.setOnMousePressed(mouseEvent -> {
            delta.x = position.getX() - mouseEvent.getSceneX();
            delta.y = position.getY() - mouseEvent.getSceneY();


            final TileGroup tileGroup = TileGroup.getTileGroupFromID(groupID);
            if (tileGroup != null)
            {
                HelloApplication.activeGroup = groupID;
            }
            HelloApplication.activeEntity = entityID;
        });

        rectangle.setOnMouseDragged(mouseEvent -> {
            moveTo(delta.x + mouseEvent.getSceneX(), delta.y + mouseEvent.getSceneY());
            if (groupID != 0)
            {
                final TileGroup tileGroup = TileGroup.getTileGroupFromID(groupID);
                if (tileGroup != null)
                {
                    tileGroup.updatePositionOn(this);
                }
                else
                {
                    groupID = 0;
                }
            }


        });

        rectangle.setOnMouseReleased(mouseEvent -> {
            if (groupID != 0)
            {
                final TileGroup tileGroup = TileGroup.getTileGroupFromID(groupID);
                if (tileGroup != null)
                {
                    tileGroup.snapAllTiles();

                }
                else
                {
                    groupID = 0;
                }
                HelloApplication.activeGroup = 0;
            }
            else
            {
                snapToGrid();
            }
            HelloApplication.activeEntity = 0;
        });
    }

    public void setDirection(final Directions direction)
    {
        this.direction = direction;
        updateRotate();
    }

    private void updateRotate()
    {
        angle %= 360;
        rotationTransform.setAngle(angle + Math.toDegrees(direction.value));
    }

    public void setGroupValue(final int groupID)
    {
        this.groupID = groupID;
    }

    public static TileEntity getTileEntityByID(final int entityID)
    {
        return tileEntities.get(entityID);
    }

    private class DragAndDropDelta {public double x; public double y;}

}
