import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import java.awt.geom.Line2D;

public class House extends Application {
    private Canvas canvas;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.canvas = new Canvas(600, 800);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Hello Line");
        primaryStage.show();
    }

    public void draw(FXGraphics2D graphics) {
//        graphics.draw(new Line2D.Double(200, 100, 500, 200));
        drawRoof(graphics);
        drawFoundation(graphics);
        drawDoor(graphics);
        drawWindow(graphics);
    }


    public void drawRoof(FXGraphics2D graphics){
        // Left part
        graphics.draw(new Line2D.Double(300, 100, 150, 300));

        // Right part
        graphics.draw(new Line2D.Double(300, 100, 450, 300));

    }

    public void drawFoundation(FXGraphics2D graphics){

        // Left wall
        graphics.draw(new Line2D.Double(150, 300, 150, 500));

        // Right wall
        graphics.draw(new Line2D.Double(450, 300, 450, 500));

        // Floor
        graphics.draw(new Line2D.Double(450, 500, 150, 500));


    }

    public void drawDoor(FXGraphics2D graphics){

        // Left side
        graphics.draw(new Line2D.Double(180, 500, 180, 400));


        // Right side
        graphics.draw(new Line2D.Double(240, 500, 240, 400));


        // Top side
        graphics.draw(new Line2D.Double(180, 400, 240, 400));

    }


    public void drawWindow(FXGraphics2D graphics){

        // Left side
        graphics.draw(new Line2D.Double(280, 450, 280, 340));

        // Right side
        graphics.draw(new Line2D.Double(410, 450, 410, 340));

        // Top side
        graphics.draw(new Line2D.Double(410, 340, 280, 340));

        // Bottom side
        graphics.draw(new Line2D.Double(410, 450, 280, 450));


    }

    public static void main(String[] args) {
        launch(House.class);
    }
}
