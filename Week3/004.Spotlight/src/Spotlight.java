import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Spotlight extends Application {
    private ResizableCanvas canvas;

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
    }


    public void draw(FXGraphics2D graphics)
    {
//        graphics.setTransform(new AffineTransform());
//        graphics.setBackground(Color.white);
//        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        Shape shape = new Ellipse2D.Double(canvas.getWidth()/2-100, canvas.getHeight()/2-100, 200, 200);
        graphics.setColor(Color.black);
        graphics.draw(shape);
        graphics.setClip(shape);

//        graphics.drawLine((int)canvas.getWidth()/2-100, (int)canvas.getHeight()/2-100, 600, 200);
        Random r = new Random();
        for(int i = 0; i < 10; i++) {
            int ranNumFirst = r.nextInt(1000 - 1) + 1;
            int ranNumSecond = r.nextInt(1000 - 1) + 1;
//            int ranNum = r.nextInt();
            graphics.setPaint(Color.getHSBColor(r.nextFloat(),1,1));
//            graphics.drawLine(200, 0, 800, 500 + ranNum);
            graphics.drawLine(ranNumFirst % (int)canvas.getWidth(), ranNumFirst % (int)canvas.getHeight(), ranNumSecond % (int)canvas.getWidth(), ranNumSecond % (int)canvas.getHeight());
            //graphics.drawLine(new Line2D.Double((double)canvas.getWidth() + ranNum, (double)canvas.getHeight() + ranNum, (double)canvas.getWidth() + ranNum, (double)canvas.getHeight() + ranNum));


        }
    }

    public void init()
    {

    }

    public void update(double deltaTime)
    {

    }

    public static void main(String[] args)
    {
        launch(Spotlight.class);
    }

}
