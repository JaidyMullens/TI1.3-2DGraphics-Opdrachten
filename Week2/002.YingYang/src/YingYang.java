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
        g2d.setColor(Color.black);
        Area outerCircle = new Area(new Ellipse2D.Double(100, 100, 400, 400));
        g2d.draw(outerCircle);

        Area ying = new Area(new Ellipse2D.Double(200, 100, 200, 200));
        Area yang = new Area(new Ellipse2D.Double(200, 300, 200, 200));
        Area rectangle = new Area(new Rectangle2D.Double(300, 100,200,400));
        rectangle.intersect(outerCircle);
        rectangle.subtract(ying);
        g2d.fill(rectangle);
        g2d.draw(rectangle);
        outerCircle.add(ying);
        g2d.draw(yang);
        g2d.fill(yang);

        g2d.setColor(Color.white);
        Area yangHead = new Area(new Ellipse2D.Double(275, 380, 50, 50));
        g2d.fill(yangHead);
        g2d.draw(yangHead);

        g2d.setColor(Color.black);
        Area yingHead = new Area(new Ellipse2D.Double(275, 175, 50, 50));
        g2d.fill(yingHead);

    }

    public static void main(String[] args)
    {
        launch(YingYang.class);
    }

}
