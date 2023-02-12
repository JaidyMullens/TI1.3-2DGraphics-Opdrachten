import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class BlockDrag extends Application {
    private ResizableCanvas canvas;
    private ArrayList<Renderable> blocks = new ArrayList<>();
    private Point2D mousePos = new Point2D.Double();
    private Renderable selectedBlock = null;
    private double dragX = 0;
    private double dragY = 0;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Block Dragging");
        primaryStage.show();

        canvas.setOnMousePressed(e -> mousePressed(e));
        canvas.setOnMouseReleased(e -> mouseReleased(e));
        canvas.setOnMouseDragged(e -> mouseDragged(e));

        init();

        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        for (Renderable block : blocks) {
            graphics.setColor(block.getColor());
            graphics.fill(block.getTransformedShape());
            block.draw(graphics);
        }
    }


    public static void main(String[] args)
    {
        launch(BlockDrag.class);
    }

    public void init(){

        for (int i = 1; i < 15; i++) {
            int x = (int) ((Math.random() * 1820));
            int y = (int) ((Math.random() * 980));
            Color color = (Color.getHSBColor((float) (Math.random() * 256), 0.7f, 1));

            Renderable block = new Renderable(
                    new Rectangle2D.Double(-50, -50, 100, 100),
                    new Point2D.Double(x, y), 0f, 1f, color);

            blocks.add(block);
        }
    }

    private void mousePressed(MouseEvent e)
    {

        double xPos = e.getX();
        double yPos = e.getY();
        mousePos = new Point2D.Double(xPos, yPos);

        for (Renderable block : blocks) {

            if (block.getTransformedShape().contains(xPos, yPos)) {
                selectedBlock = block;
                dragX = mousePos.getX() - selectedBlock.getPosition().getX();
                dragY = mousePos.getY() - selectedBlock.getPosition().getY();
                break;
            }

        }

    }



    private void mouseReleased(MouseEvent e)
    {
        this.selectedBlock = null;
    }

    private void mouseDragged(MouseEvent e)
    {
        if (selectedBlock != null){
            selectedBlock.setPosition(new Point2D.Double(e.getX() + dragX, e.getY() + dragY));
        }
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));

    }

}
