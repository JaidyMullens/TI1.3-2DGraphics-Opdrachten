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

public class YingYang extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Ying Yang");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D g2d)
    {
        Shape outerCircle = new Ellipse2D.Double(100, 100, 400, 400);
        g2d.draw(outerCircle);

        Shape topEye = new Ellipse2D.Double(275, 150, 50, 50);
        Shape bottomEye = new Ellipse2D.Double(275, 380, 50, 50);

        Area topEyeArea = new Area(topEye);
        Area bottomEyeArea = new Area(bottomEye);

        g2d.draw(topEyeArea);
        g2d.draw(bottomEyeArea);



        Area yingHead = new Area(new Ellipse2D.Double(200, 100, 200, 200));
        Area yangHead = new Area(new Ellipse2D.Double(200, 300, 200, 200));
        g2d.draw(yangHead);

        Path2D yangPath = new Path2D.Double();
//        yangPath.curveTo(300, 300, ,300, 300);

        Area yang = new Area();




//        Area yang = new Area(new Ellipse2D.Double(100, 100, 400, 400));
//        yang.subtract(yingHead);
//        g2d.fill(yang);


    }


    public static void main(String[] args)
    {
        launch(YingYang.class);
    }

}
