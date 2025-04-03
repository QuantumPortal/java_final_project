package ca.bcit.comp2522.java_final_project;

import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class TileGroup
{

    private TileEntity[]       tiles;
    private TileSideModifier[] sideModifiers;

    private boolean flipped;
    private Directions direction;
    private boolean[][] tileSignature;

    private Rotate    groupRotation;
    private Translate groupTranslation;


    public TileGroup(TileEntity[] tiles,
                     TileSideModifier[] sideModifiers)
    {
        this.tiles         = tiles;
        this.sideModifiers = sideModifiers;
    }


    public void updatePositionOn(TileEntity tile)
    {

    }

    public void updateRotationOn(TileEntity tile)
    {

    }


}
