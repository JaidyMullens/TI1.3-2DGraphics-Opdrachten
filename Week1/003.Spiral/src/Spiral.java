import java.awt.*;
import java.awt.geom.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Spiral extends Application {

    Canvas canvas = new Canvas(1920, 1080);


    @Override
    public void start(Stage primaryStage) throws Exception {
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Spiral");
        primaryStage.show();
    }
    
    
    public void draw(FXGraphics2D graphics) {
        // Set origin to middle
        graphics.translate(this.canvas.getWidth() / 2, this.canvas.getHeight() / 2);
        graphics.scale(1, -1);

        graphics.setColor(Color.red);
        graphics.drawLine(0, 0, 1000, 0);
        graphics.setColor(Color.green);
        graphics.drawLine(0, 0, 0, 1000);
        graphics.setColor(Color.black);

        float n = 0.4f;

        double resolution = 0.1;
        double scale = 50.0;
        double lastY = 0;
        double lastX = 0;

        for (double r = 0; r < 100; r += resolution) {
            float x = (float)(n * r * Math.cos(r));
            float y = (float)(n * r * Math.sin(r));
            graphics.draw(new Line2D.Double(x * scale, y * scale, lastX * scale, lastY * scale));
            lastY = y;
            lastX = x;
        }
        graphics.setColor(Color.blue);
        n = 0.1f;

        for (double r = 0; r < 100; r += resolution) {
            float x = (float)(n * r * Math.cos(r));
            float y = (float)(n * r * Math.sin(r));
            graphics.draw(new Line2D.Double(x * scale, y * scale, lastX * scale, lastY * scale));
            lastY = y;
            lastX = x;
        }

        n = 0.05f;
        graphics.setColor(Color.pink);
        for (double r = 0; r < 100; r += resolution) {
            float x = (float)(n * r * Math.cos(r));
            float y = (float)(n * r * Math.sin(r));
            graphics.draw(new Line2D.Double(x * scale, y * scale, lastX * scale, lastY * scale));
            lastY = y;
            lastX = x;
        }
    }
    
    
    
    public static void main(String[] args) {
        launch(Spiral.class);
    }

}
