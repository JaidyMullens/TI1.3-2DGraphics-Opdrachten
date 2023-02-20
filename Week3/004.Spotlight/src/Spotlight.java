import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Spotlight extends Application {
    private ResizableCanvas canvas;
    private double shapeXPos = 200;
    private double shapeYPos = 200;
    private RenderableShape shape = null;
    private Shape vorm = null;
    @Override
    public void start(Stage stage) throws Exception
    {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());



        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now)
            {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Spotlight");
        stage.show();
        draw(g2d);

        canvas.setOnMouseMoved(e -> {
            this.shapeXPos = e.getX();
            this.shapeYPos = e.getY();
        });
    }


    public void draw(FXGraphics2D graphics) {

        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        Shape ellipse = new Ellipse2D.Double(shapeXPos - 125, shapeYPos - 125, 250, 250);
        graphics.setClip(ellipse);

        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            int ranNumFirst = r.nextInt(100000 - 1) + 1;
            int ranNumSecond = r.nextInt(100000 - 1) + 1;
            graphics.setPaint(Color.getHSBColor(r.nextFloat(), 1, 1));
            graphics.drawLine(ranNumFirst % (int) canvas.getWidth(), ranNumFirst % (int) canvas.getHeight(), ranNumSecond % (int) canvas.getWidth(), ranNumSecond % (int) canvas.getHeight());
        }

        graphics.setClip(null);
    }

    public void update(double deltaTime)
    {

    }

    public static void main(String[] args)
    {
        launch(Spotlight.class);
    }

}
