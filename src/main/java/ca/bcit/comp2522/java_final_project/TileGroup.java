package ca.bcit.comp2522.java_final_project;

import javafx.geometry.Point2D;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import java.util.HashMap;

public class TileGroup
{
    private static final HashMap<Integer, TileGroup> tileGroups;

    static {
        tileGroups = new HashMap<>();
    }



    private final int                groupID;
    private final boolean[][]        tileSignature;
    private final TileEntity[]       tiles;
    private final TileSideModifier[] sideModifiers;

    private boolean flipped;
    private Directions direction;


    private Rotate    groupRotation;
    private Translate groupTranslation;


    public TileGroup(boolean[][] tileSignature,
                     TileEntity[] tiles,
                     TileSideModifier[] sideModifiers)
    {
        this.tileSignature = tileSignature;
        this.tiles         = tiles;
        this.sideModifiers = sideModifiers;

        direction = Directions.UP;

        groupID = tileGroups.size() + 1;
        tileGroups.put(groupID, this);
        addGroupValueToTiles();
    }


    public void updatePositionOn(TileEntity tile)
    {
        for (TileEntity t: tiles)
        {
            if (t != tile)
            {
                final Point2D moveDirection = getTileVector(tile, t);

                t.moveTo(moveDirection.getX() * TileEntity.getGridSize() + tile.getX(),
                         moveDirection.getY() * TileEntity.getGridSize() + tile.getY());

            }

            t.setDirection(direction);

        }
    }

    public void updateRotationOn(TileEntity tile)
    {

    }

    public void snapAllTiles()
    {
        for (TileEntity tile: tiles) {
            tile.snapToGrid();
        }
    }

    private Point2D getTileVector(TileEntity tileStart,
                               TileEntity tileTarget)
    {
        final Point2D startPoint = getTileSignaturePosition(tileStart);
        final Point2D targetPoint = getTileSignaturePosition(tileTarget);

        if (startPoint == null || targetPoint == null)
        {
            return null;
        }

        final double angle = Math.atan2(targetPoint.getY() - startPoint.getY(), targetPoint.getX() - startPoint.getX()) + direction.value;
        final double distance = startPoint.distance(targetPoint);

        return new Point2D(distance * Math.cos(angle), distance * Math.sin(angle));
    }

    private Point2D getTileSignaturePosition(TileEntity tile)
    {
        int tileCount = 0;
        for (int row = 0; row < tileSignature.length; row++)
        {
            for (int col = 0; col < tileSignature[row].length; col++)
            {
                if (tileSignature[row][col])
                {
                    if (tile == tiles[tileCount]) {
                        return new Point2D(col, row);
                    }
                    tileCount++;
                }

            }
        }
        return null;
    }

    public void rotateLeft() {
        switch (direction) {
            case LEFT:
                direction = Directions.DOWN;
                break;
            case DOWN:
                direction = Directions.RIGHT;
                break;
            case RIGHT:
                direction = Directions.UP;
                break;
            case UP:
                direction = Directions.LEFT;
                break;
        }
    }

    public void rotateRight()
    {
        switch (direction) {
            case LEFT:
                direction = Directions.UP;
                break;
            case DOWN:
                direction = Directions.LEFT;
                break;
            case RIGHT:
                direction = Directions.DOWN;
                break;
            case UP:
                direction = Directions.RIGHT;
                break;
        }
    }

    private void addGroupValueToTiles()
    {
        for (TileEntity tile: tiles)
        {
            tile.setGroupValue(groupID);
        }
    }

    public static TileGroup getTileGroupFromID(final int groupID)
    {
        return tileGroups.get(groupID);
    }

    public void flip()
    {
        flipped = !flipped;
    }

}
