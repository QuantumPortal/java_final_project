package ca.bcit.comp2522.java_final_project;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
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

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        Screen      screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        Group root  = new Group();
        Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight());


        Rectangle rect = new Rectangle(50,75);
        rect.setFill(new Color(0.6,0.6,0.6,0.9));

        TileEntity tileEntity = new TileEntity(
                1000,
                1000,
                25,
                25,
                rect
        );

        tileEntity.addToGroup(root);

        Circle centerDot = new Circle();

        centerDot.setRadius(20);
        centerDot.setFill(Color.RED);
        centerDot.setCenterX(bounds.getMaxX()/2);
        centerDot.setCenterY(bounds.getMaxY()/2);

        root.getChildren().add(centerDot);

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

        centerDot.setOnMouseReleased(event -> {
            centerDot.setCenterX(event.getX());
            centerDot.setCenterY(event.getY());
            centerDot.setFill(Color.PINK);
            centerDot.setCenterX(Math.round(centerDot.getCenterX()/50) * 50.0);
            centerDot.setCenterY(Math.round(centerDot.getCenterY()/50) * 50.0);
        });


        centerDot.setOnMouseDragged(event -> {
            centerDot.setCenterX(event.getX());
            centerDot.setCenterY(event.getY());
        });




        Duration      interval = Duration.millis(5);
        AtomicInteger counter = new AtomicInteger();
        KeyFrame frame    = new KeyFrame(interval, actionEvent -> {

            long deltaTime = System.currentTimeMillis() - curTime;


            tileEntity.setRotateAxis(centerDot.getCenterX(),
                                     centerDot.getCenterY());

            tileEntity.moveTo(centerDot.getCenterX(),
                              centerDot.getCenterY());

            //tileEntity.addRotate(deltaTime/10f);


            counter.set(counter.get() + 1);
            curTime = System.currentTimeMillis();
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