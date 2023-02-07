import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Moon extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Moon");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D g2d)
    {
        g2d.setTransform(new AffineTransform());
        g2d.setBackground(Color.white);


//        Shape rightSide = new QuadCurve2D.Double(100.0f, 100.0f,  500, 200, 100.0f, 400.0f);
//        g2d.draw(rightSide);
//
//        Shape leftSide = new QuadCurve2D.Double(100.0f, 400.0f,  300, 200, 100.0f, 100.0f);
//        g2d.draw(leftSide);

        GeneralPath path = new GeneralPath();
        path.moveTo(150, 100);
        path.curveTo(350, 140,350, 360,150, 400);
        path.moveTo(150, 400);
        path.curveTo(250,350,250, 150,150, 100);
//        path.closePath();
        g2d.setColor(Color.black);
        g2d.fill(path);
        g2d.draw(path);

//        GeneralPath path = new GeneralPath();
//        path.moveTo(100.0f, 400.0f);


//
//        GeneralPath path = new GeneralPath();
//        path.moveTo(100, 100);
//        path.lineTo(200,100);
//        path.lineTo(100,200);
//        path.closePath();
//
//        g2d.setColor(Color.green);
//        g2d.fill(path);
//        g2d.setColor(Color.black);
//        g2d.draw(path);

        //graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
    }


    public static void main(String[] args)
    {
        launch(Moon.class);
    }

}
