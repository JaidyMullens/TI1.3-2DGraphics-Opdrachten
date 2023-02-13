import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Rainbow extends Application {
    private ResizableCanvas canvas;
    private Color[] colors = new Color[13];
    @Override
    public void start(Stage stage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Rainbow");
        stage.show();


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

//        Shape regenboog = null;
        ArrayList<Shape> letters = new ArrayList<>();
        int xPos = 0;
        int yPos = 0;
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.translate(canvas.getWidth()/2, canvas.getHeight()/2);

        Font font = new Font("Arial", Font.PLAIN, 50);
        String regenboog = "Regenboog";
        char[] letterArr = regenboog.toCharArray();

        for (int i = 0; i < regenboog.toCharArray().length; i++) {
            letters.add(font.createGlyphVector(graphics.getFontRenderContext(), String.valueOf(letterArr[i])).getOutline());
        }
        double degrees = Math.PI/letters.size();
        for (int i = 0; i < letters.size(); i++) {

            Shape letter = letters.get(i);
            AffineTransform curveLetter = new AffineTransform();
            curveLetter.rotate(degrees * i - (Math.PI / 2));
            yPos = -150;
            curveLetter.translate(xPos, yPos);
            graphics.setColor(colors[i]);
            graphics.fill(curveLetter.createTransformedShape(letter));

        }

    }


    public static void main(String[] args)
    {
        launch(Rainbow.class);
    }

}
