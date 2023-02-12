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

public class Mirror extends Application {
    ResizableCanvas canvas;
    double x = 0;
    double y = 0;
    double rc = 2.5;
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Mirror");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        graphics.translate(canvas.getWidth()/2, canvas.getHeight() / 2);
        graphics.scale(1, -1);


        // x-Axis
        graphics.setColor(Color.blue);
        graphics.drawLine(-(int)(canvas.getWidth()/2), 0, (int)(canvas.getWidth()/2), 0);


        // y-Axis
        graphics.setColor(Color.red);
        graphics.drawLine(0, (int)(canvas.getHeight() / 2), 0, -(int)(canvas.getHeight()/2));



        graphics.setColor(Color.black);

        for (int i = -300; i < 300; i++) {
            x = i;
            y = rc * x;
            Line2D line = new Line2D.Double(0, 0, x, y);
            graphics.draw(line);
        }

        Shape square = new Rectangle2D.Double(-50, 150, 100, 100);
        graphics.draw(square);

        AffineTransform mirroredSquare = new AffineTransform();
        mirroredSquare.concatenate(new AffineTransform(
                (2/(1+Math.pow(rc, 2)))-1,
                (2*rc)/(1+Math.pow(rc, 2)),
                (2*rc)/(1+Math.pow(rc, 2)),
                (2*Math.pow(rc,2)/(1+Math.pow(rc, 2))) - 1,0,0));
        graphics.draw( mirroredSquare.createTransformedShape(square));
    }


    public static void main(String[] args)
    {
        launch(Mirror.class);
    }

}
