import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class FadingImage extends Application {
    private ResizableCanvas canvas;
    private BufferedImage imgTheRock;
    private BufferedImage imgTheCow;
    private static float fadeSpeed = 0.007f;
    private float transparency = 0.0f;
    private float bufferTime = 0f;
    boolean bufferIsOn = false;

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        init();

        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
		if(last == -1)
                    last = now;
		update((now - last) / 1000000000.0);
		last = now;
		draw(g2d);
            }
        }.start();
        
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Fading image");
        stage.show();
        draw(g2d);
    }
    
    
    public void draw(FXGraphics2D graphics) {
//        graphics.setTransform(new AffineTransform());
//        graphics.setBackground(Color.white);
//        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());

        AffineTransform transpartentImg = new AffineTransform();
        AffineTransform backgroundImg = new AffineTransform();
//        transpartentImg.translate(transpartentImg.getTranslateX() + 500, transpartentImg.getTranslateY());

        graphics.drawImage(imgTheCow, backgroundImg,null);



        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));

        graphics.drawImage(imgTheRock, transpartentImg,null);


    }
    

    public void update(double deltaTime) {

        if (!bufferIsOn) {
            transparency += fadeSpeed;

            if (transparency > 0.95f) {
                bufferIsOn = true;
                fadeSpeed *= -1;
            }

            if (transparency <= 0f)
            {
                System.out.println(transparency);
                System.out.println("The rock is only visible");
                fadeSpeed *= -1;
                bufferIsOn = true;
            }
        }
        else{
            // Buffer is on
         bufferTime += 0.1f;
         if (bufferTime >= 8f)
         {
             bufferIsOn = false;
             bufferTime = 0;
         }
        }

    }

    public void init() throws IOException {
        transparency = 0.15f;

        imgTheRock = ImageIO.read(getClass().getResource("images/rock.jpg"));
        imgTheRock.getScaledInstance(300, 900, Image.SCALE_SMOOTH);

        imgTheCow = ImageIO.read(getClass().getResource("images/cow.jpg"));
//        imgTheCow.getScaledInstance(900, 900, Image.SCALE_SMOOTH);

    }

    public static void main(String[] args) {
        launch(FadingImage.class);
    }

}
