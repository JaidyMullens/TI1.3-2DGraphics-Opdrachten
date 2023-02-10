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

public class Colors extends Application {
    private ResizableCanvas canvas;
    private Color[] colors = new Color[13];
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Colors");
        primaryStage.show();

        colors[0] = Color.black;
        colors[1] = Color.blue;
        colors[2] = Color.cyan;
        colors[3] = Color.darkGray;
        colors[4] = Color.gray;
        colors[5] = Color.green;
        colors[6] = Color.lightGray;
        colors[7] = Color.magenta;
        colors[8] = Color.orange;
        colors[9] = Color.pink;
        colors[10] = Color.red;
        colors[11] = Color.white;
        colors[12] = Color.yellow;

    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        for (int i = 0; i < colors.length; i++) {
            graphics.setColor(Color.black);
            Area rectangle = new Area(new Rectangle2D.Double(50 + (i*70), 50, 50, 50));
            graphics.draw(rectangle);

            graphics.setColor(colors[i]);
            graphics.fill(rectangle);
        }

    }

    public static void main(String[] args)
    {
        launch(Colors.class);
    }

}
