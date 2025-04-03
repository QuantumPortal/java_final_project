package ca.bcit.comp2522.java_final_project;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.css.Rect;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloApplication extends Application
{
    private static long curTime = System.currentTimeMillis();
    public static int activeGroup;
    public static int activeEntity;

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        Screen      screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        Group root  = new Group();
        Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight());


        Rectangle rect = new Rectangle(50,50);
        rect.setFill(new Color(0.6,0.4,0.6,0.9));
        TileEntity tileEntity1 = new TileEntity(
                1000,
                1000,
                25,
                25,
                0,
                rect
        );

        Rectangle rect1 = new Rectangle(50,50);
        rect1.setFill(new Color(0.6,0.6,0.4,0.9));
        TileEntity tileEntity2 = new TileEntity(
                900,
                300,
                25,
                25,
                0,
                rect1
        );

        Rectangle rect2 = new Rectangle(50,75);
        rect2.setFill(new Color(0.4,0.6,0.6,0.9));
        TileEntity tileEntity3 = new TileEntity(
                600,
                200,
                25,
                25,
                0,
                rect2
        );

        Rectangle rect3 = new Rectangle(50,75);
        rect3.setFill(new Color(0.3,0.7,0.2,0.9));
        TileEntity tileEntity4 = new TileEntity(
                300,
                400,
                25,
                25,
                0,
                rect3
        );

        Rectangle rect4 = new Rectangle(50,75);
        rect4.setFill(new Color(0.7,0.3,0.5,0.9));
        TileEntity tileEntity5 = new TileEntity(
                400,
                500,
                25,
                25,
                0,
                rect4
        );

        tileEntity1.addToGroup(root);
        tileEntity2.addToGroup(root);
        tileEntity3.addToGroup(root);
        tileEntity4.addToGroup(root);
        tileEntity5.addToGroup(root);


        primaryStage.setScene(scene);
        primaryStage.show();

        for (int i = 0; i < bounds.getMaxX()/50; i++) {
            Rectangle gridLine = new Rectangle();
            gridLine.setWidth(3);
            gridLine.setHeight(bounds.getMaxY());
            gridLine.setFill(new Color(0.39, 0.39, 0.99, 0.7));
            gridLine.setX(25 + 50*i);
            gridLine.setY(0);

            root.getChildren().add(gridLine);

        }

        for (int i = 0; i < bounds.getMaxY()/50; i++) {
            Rectangle gridLine = new Rectangle();
            gridLine.setWidth(bounds.getMaxX());
            gridLine.setHeight(3);
            gridLine.setFill(new Color(0.39, 0.39, 0.99, 0.7));
            gridLine.setX(0);
            gridLine.setY(25 + 50*i);

            root.getChildren().add(gridLine);

        }


        boolean[][] a = {
                {true, true},
                {true, false}
        };

        TileEntity[] tiles = {tileEntity1,
                              tileEntity2,
                              tileEntity3,};

        TileGroup tg = new TileGroup(a, tiles, null);


        boolean[][] b = {
                {true, true}
        };
        TileEntity[] tiles2 = {tileEntity4,tileEntity5};
        TileGroup tg2 = new TileGroup(b, tiles2, null);


        scene.setOnKeyPressed(event -> {
            if (activeGroup == 0 || activeEntity == 0) {
                return;
            }
            final TileGroup activeTileGroup = TileGroup.getTileGroupFromID(activeGroup);
            final TileEntity activeTileEntity = TileEntity.getTileEntityByID(activeEntity);
            if (event.getCode() == KeyCode.E) {
                activeTileGroup.rotateRight();
                activeTileGroup.updatePositionOn(activeTileEntity);
            }
            else if (event.getCode() == KeyCode.Q) {
                activeTileGroup.rotateLeft();
                activeTileGroup.updatePositionOn(activeTileEntity);
            }
            else if (event.getCode() == KeyCode.F) {
                activeTileGroup.flip();
            }
            event.consume();
        });



        Duration      interval = Duration.millis(5);
        AtomicInteger counter = new AtomicInteger();
        KeyFrame frame    = new KeyFrame(interval, actionEvent -> {
            long deltaTime = System.currentTimeMillis() - curTime;

            //counter.set(counter.get() + 1);
        });
        Timeline timeline = new Timeline(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();



    }

    public static void main(String[] args)
    {
        launch();
    }
}