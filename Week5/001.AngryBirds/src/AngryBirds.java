
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class AngryBirds extends Application {

    private ResizableCanvas canvas;
    private World world;
    private MousePicker mousePicker;
    private Camera camera;
    private boolean debugSelected = false;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();

        // Add debug button
        javafx.scene.control.CheckBox showDebug = new CheckBox("Show debug");
        showDebug.setOnAction(e -> {
            debugSelected = showDebug.isSelected();
        });
        mainPane.setTop(showDebug);

        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        camera = new Camera(canvas, g -> draw(g), g2d);
        mousePicker = new MousePicker(canvas);

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1) {
                    last = now;
                }
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane, 1920, 1080));
        stage.setTitle("Angry Birds");
        stage.show();
        draw(g2d);
    }

    public void init() {
        world = new World();
        world.setGravity(new Vector2(0, -9.8));

        Body floor = new Body();
        floor.addFixture(Geometry.createRectangle(20, 1));
        floor.getTransform().setTranslation(0, -.5);
        floor.setMass(MassType.INFINITE);
        world.addBody(floor);

        Body ball = new Body();
        ball.addFixture(Geometry.createCircle(1));
        ball.getTransform().setTranslation(new Vector2(0,2));
        ball.setMass(MassType.NORMAL);
        ball.getFixture(0).setRestitution(.250);
        ball.applyImpulse(-20);
        world.addBody(ball);



    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.clearRect(0,0, 1920, 1080);
        graphics.setColor(Color.white);

        graphics.setTransform(camera.getTransform((int)canvas.getWidth(), (int)canvas.getHeight()));
        graphics.scale(1,-1);

        DebugDraw.draw(graphics, world, 100);
        for (GameObject go : gameObjects) {
            go.draw(graphics);
        }

        if (debugSelected) {
            graphics.setColor(Color.blue);
            DebugDraw.draw(graphics, world, 100);
        }

//        graphics.setTransform(originalTransform);
    }

//    double lastFrame = 60;
    public void update(double deltaTime) {
        mousePicker.update(world, camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()), 100);
//        if (lastFrame > 1/60)
//        {
            world.update(deltaTime);
//            lastFrame -= deltaTime;
//        }
    }

    public static void main(String[] args) {
        launch(AngryBirds.class);
    }

}
