import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class GradientPaintExercise extends Application {
    private ResizableCanvas canvas;
    private Point2D center = new Point2D.Double(0, 0);


    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("GradientPaint");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));

        canvas.setOnMouseMoved(event -> {
            center = new Point2D.Double(event.getX() / 2, event.getY() / 2);
            draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        });

    }


    public void draw(FXGraphics2D graphics)
    {
//        graphics.setTransform(new AffineTransform());
//        center = new Point2D.Double();
        float radius = 1000f;
        float[] fractions = {0.1f,0.5f,0.9f};
        Color[] colors = {Color.red, Color.yellow, Color.pink};

        RadialGradientPaint radialGradientPaint = new RadialGradientPaint(center, radius, center, fractions, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE);

//        RadialGradientPaint radialGradientPaint = new RadialGradientPaint(new Point2D.Double((double)canvas.getWidth() / 2, (double)canvas.getHeight() / 2),
//                30, 40, new float[2], new Color[2], MultipleGradientPaint.CycleMethod.NO_CYCLE);

        Rectangle2D rectangle = new Rectangle((int)canvas.getWidth(), (int)canvas.getHeight());
        graphics.setPaint(radialGradientPaint);
        graphics.fill(rectangle);
        graphics.draw(rectangle);
    }


    public static void main(String[] args)
    {
        launch(GradientPaintExercise.class);
    }

}
