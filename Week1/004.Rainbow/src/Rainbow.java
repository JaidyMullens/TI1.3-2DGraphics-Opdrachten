import java.awt.*;
import java.awt.geom.*;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Rainbow extends Application {
    Canvas canvas = new Canvas(1920, 1080);

    @Override
    public void start(Stage primaryStage) throws Exception {
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Rainbow");
        primaryStage.show();

    }
    
    
    public void draw(FXGraphics2D graphics) throws InterruptedException {

        double scale = 50.0;

        // Set origin to middle under
        graphics.translate(this.canvas.getWidth() / 2, this.canvas.getHeight() / 2);
        graphics.scale(1, -1);

//        for(int i = 0; i < 500; i++) {
//            graphics.setColor(Color.getHSBColor(i/500.0f, 1, 1));
//            graphics.drawLine(i, 10, i, 100);
//        }
        float x1 = 0f;
        float y1 = 0f;
        float x2 = 0f;
        float y2 = 0f;
//        float angle = 159;

//        for (float i = 0; i < 500f; i+= 0.1f) {
//            graphics.setColor(Color.getHSBColor(i/500f, 1, 1));
//            x1 = (float)(400 * Math.cos(i / angle));
//            y1 = (float)(400 * Math.sin(i / angle));
//            x2 = (float)(500 * Math.cos(i / angle));
//            y2 = (float)(500 * Math.sin(i / angle));
//            graphics.draw(new Line2D.Double(x1, y1, x2, y2));
//        }
//        graphics.translate(this.canvas.getWidth() / 0.5, this.canvas.getHeight() / 0.5);

        for (float i = 0; i < Math.PI; i+= 0.001f) {
            graphics.setColor(Color.getHSBColor(i/(float)Math.PI, 1, 1));
            x1 = (float)(400 * Math.cos(i));
            y1 = (float)(400 * Math.sin(i));
            x2 = (float)(500 * Math.cos(i));
            y2 = (float)(500 * Math.sin(i));
            graphics.draw(new Line2D.Double(x1, y1, x2, y2));
        }
    }
    
    
    
    public static void main(String[] args) {
        launch(Rainbow.class);
    }

}
