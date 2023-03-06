import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.sun.scenario.effect.LinearConvolveCoreEffect;
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

public class Screensaver extends Application {
    private ResizableCanvas canvas;
    private Line line_1;
    private Line line_2;
    private Line line_3;
    private Line line_4;
    private static double lineVelocity = 2;

    private ArrayList<Line> lines = new ArrayList<>();
    private ArrayList<Line> previousLines = new ArrayList<>();
    private Color lineColor;

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
        stage.setTitle("Screensaver");
        stage.show();
        g2d.setColor(Color.cyan);
        draw(g2d);


        canvas.setOnMouseClicked(e ->{
            Random random = new Random();
            double ranVal = 1 + random.nextDouble() * (360 - 1);
            Color ranColor = Color.getHSBColor((float)ranVal, 1, 1);
            g2d.setBackground(ranColor);
            lineColor = getComplementary(ranColor);
        });
    }

    public static Color getComplementary(Color color) {
        return new Color(255 - color.getRed(), 255 - color.getGreen(),
                255 - color.getBlue());
    }

    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.setColor(lineColor);

        for (Line line : lines) {

            line.draw(graphics);
        }

        for (Line previousLine : previousLines) {

            previousLine.draw(graphics);
        }

    }

    public void init()
    {
        line_1 = new Line(200, 100, 500, 100, -1, -1, -1, 1, lineVelocity);
        line_2 = new Line(200, 300, 500, 300, 1, 1, 1, -1, lineVelocity);
        line_3 = new Line(500, 300, 500, 100, 1, -1, -1, 1, lineVelocity);
        line_4 = new Line(200, 300,200, 100,  1, 1, -1, -1, lineVelocity);

        lines.add(line_1);
        lines.add(line_2);
        lines.add(line_3);
        lines.add(line_4);

    }

    public void update(double deltaTime)
    {
        double xBoundMin = canvas.getLayoutBounds().getMinX();
        double xBoundMax = canvas.getLayoutBounds().getMaxX();
        double yBoundMin = canvas.getLayoutBounds().getMinY();
        double yBoundMax = canvas.getLayoutBounds().getMaxY();

        for (Line line : lines) {

            line.setStartX(line.getStartX() + line.xStartDirection);
            line.setStartY(line.getStartY() + line.yStartDirection);

            line.setEndX(line.getEndX() + line.xEndDirection);
            line.setEndY(line.getEndY() + line.yEndDirection);

            // X-Start bounds
            if(line.getStartX() < xBoundMin || line.getStartX() > xBoundMax)
                line.xStartDirection = -line.xStartDirection;

            // Y-Start bounds
            if(line.getStartY() < yBoundMin || line.getStartY() > yBoundMax)
                line.yStartDirection = -line.yStartDirection;

            //X-End bounds
            if((line.getEndX() < xBoundMin) || (line.getEndX() > xBoundMax))
                line.xEndDirection = -line.xEndDirection;

            // Y-End bounds
            if ((line.getEndY() < yBoundMin || line.getEndY() > yBoundMax))
                line.yEndDirection = -line.yEndDirection;
        }

        previousLines.add(new Line(line_1.getStartX(), line_1.getStartY(), line_1.getEndX(), line_1.getEndY(),
                line_1.xStartDirection, line_1.yStartDirection, line_1.xEndDirection, line_1.yEndDirection, lineVelocity));

        previousLines.add(new Line(line_2.getStartX(), line_2.getStartY(), line_2.getEndX(), line_2.getEndY(),
                line_2.xStartDirection, line_2.yStartDirection, line_2.xEndDirection, line_2.yEndDirection, lineVelocity));

        previousLines.add(new Line(line_3.getStartX(), line_3.getStartY(), line_3.getEndX(), line_3.getEndY(),
                line_3.xStartDirection, line_3.yStartDirection, line_3.xEndDirection, line_3.yEndDirection, lineVelocity));

        previousLines.add(new Line(line_4.getStartX(), line_4.getStartY(), line_4.getEndX(), line_4.getEndY(),
                line_4.xStartDirection, line_4.yStartDirection, line_4.xEndDirection, line_4.yEndDirection, lineVelocity));

        if (previousLines.size() > 350) {
            for (int i = 0; i < 4; i++) {
                previousLines.remove(i);
            }
        }
    }

    public static void main(String[] args)
    {
        launch(Screensaver.class);
    }

}
