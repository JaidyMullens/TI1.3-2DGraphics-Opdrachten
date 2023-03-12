
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;

//import javafx.scene.Camera;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.joint.PinJoint;
import org.dyn4j.dynamics.joint.PulleyJoint;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import javax.imageio.ImageIO;

public class AngryBirds extends Application {

    private ResizableCanvas canvas;
    private World world;
    private MousePicker mousePicker;
    private Camera camera;
    private boolean debugSelected = false;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private Vector2 ropeLength;
    private PinJoint catapultJoint;
    private Body catapult;
    private Body ball;
    private double catapultForce;
    private boolean cataPultReleased = false;
    private BufferedImage background;

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

        try {
            background = ImageIO.read(getClass().getResourceAsStream("/img/sky_2.jpg"));

            for (String str : ImageIO.getReaderFormatNames()) {
                System.out.println(str);
            }

//            System.out.println(getClass().getResourceAsStream("/img/mountains.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        canvas.setOnMouseReleased(event -> {
            if (mousePicker.getBody() != null)
            {
                releaseCatapult();

            }
        });

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
        floor.addFixture(Geometry.createRectangle(100, 3));
        floor.getTransform().setTranslation(0, 0);
        floor.setMass(MassType.INFINITE);
        world.addBody(floor);
        gameObjects.add(new GameObject("/img/floor.jpg", floor, new Vector2(0, 247), 1.255));


        Body background = new Body();
        background.addFixture(Geometry.createRectangle(100, 100));
        background.getTransform().setTranslation(0, 100);
        background.setMass(MassType.INFINITE);

        ball = new Body();
        ball.addFixture(Geometry.createCircle(0.3));
        ball.getTransform().setTranslation(new Vector2(-4,4));
        ball.setMass(MassType.NORMAL);
        ball.getFixture(0).setRestitution(.250);
        world.addBody(ball);
        gameObjects.add(new GameObject("/img/steve_head.png", ball, new Vector2(0,0), 0.052));

        catapult = new Body();
        catapult.getTransform().setTranslation(new Vector2(-4,4));
        catapult.setMass(MassType.INFINITE);
        world.addBody(catapult);


        catapultJoint = new PinJoint(ball, catapult.getTransform().getTranslation(), 10, 0, 200);
        catapultJoint.setTarget(this.catapult.getTransform().getTranslation());
        catapultJoint.setCollisionAllowed(false);
        world.addJoint(catapultJoint);

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10-y; x++) {
                Body box = new Body();
                box.addFixture(Geometry.createRectangle(.25, .25));
                box.setMass(MassType.INFINITE);
                box.getTransform().setTranslation(5 + x * 0.25 + 0.125 * y, y*0.25);
                world.addBody(box);
            }
        }

//        catapultJoint = new PulleyJoint(catapult, ball, new Vector2(-4,4), ball.getWorldCenter(), new Vector2(-4,4), ball.getWorldCenter());
//        catapultJoint.setCollisionAllowed(false);
//        catapultJoint.setSlackEnabled(true);
//        catapultJoint.setLength(0.1);
//        world.addJoint(catapultJoint);


    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.clearRect(0,0, 1920, 1080);
        graphics.setColor(Color.white);

        graphics.setTransform(camera.getTransform((int)canvas.getWidth(), (int)canvas.getHeight()));
        graphics.scale(1,-1);
        AffineTransform imageTransform = new AffineTransform();
//        imageTransform.translate(1, -1);
//        imageTransform.transform(new Point2D.Double(1000,1000), new Point2D.Double(1000, 100));

        imageTransform.scale(1, -1);
        imageTransform.scale(4, 4);
        imageTransform.translate(-700, -600);
        graphics.drawImage(background,  imageTransform, null);
        graphics.setBackground(Color.getHSBColor(213, 44, 100));

        if (!cataPultReleased)
        {
            if (mousePicker.getBody() != null && mousePicker.mousePos != null) {
//                if (mousePicker.getBody().equals(ball)) {
//                    graphics.drawLine((int)mousePicker.mousePos.getX() / 100, (int)mousePicker.mousePos.getY() * -1, (int)catapult.getTransform().getTranslation().x * 100, (int)catapult.getTransform().getTranslation().y * 100);
                    graphics.drawLine((int)mousePicker.getBody().getWorldCenter().x * 100, (int)mousePicker.getBody().getWorldCenter().y * 100, (int)catapult.getTransform().getTranslation().x * 100, (int)catapult.getTransform().getTranslation().y * 100);
//                }
            }


        }

//        DebugDraw.draw(graphics, world, 100);
        for (GameObject go : gameObjects) {
            go.draw(graphics);
        }

        if (debugSelected) {
            graphics.setColor(Color.blue);
            DebugDraw.draw(graphics, world, 100);
        }

    }

    double totalFrameTime = 0;
    public void update(double deltaTime) {
        totalFrameTime += deltaTime;
//        catapultJoint.setLength(ballToJoint.difference());
        double ropeLength = calculateVectorLength(ball.getTransform().getTranslation(), new Vector2(catapultJoint.getAnchor1().x, catapultJoint.getAnchor1().y));
//        catapultJoint.setLength(ropeLength);
//
//        if (ropeLength > 0.001)
//            catapultJoint.setLength(0.001);

        if (cataPultReleased) {

            Vector2 ballToJoint = catapultJoint.getAnchor1().subtract(mousePicker.getBody().getTransform().getTranslation());
//            Vector2 ballToJoin = new Vectore
            double distance = ballToJoint.getMagnitude();
            double magnitude = Math.max(100, Math.min(distance * 10, 10000));
//            System.out.println(ball.applyForce(ballToJoint.multiply(magnitude)));
            world.removeJoint(catapultJoint);
            ball.applyImpulse(-0.002);
            ball.applyForce(ballToJoint.multiply(magnitude));
            mousePicker.mousePos = null;
            cataPultReleased = false;
//            cataPultReleased = false;
        }
        mousePicker.update(world, camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()), 100);
        world.update(deltaTime);

        totalFrameTime -= 1/60.0;
    }

    public double calculateVectorLength(Vector2 v1, Vector2 v2) {
        double dx = v2.x - v1.x;
        double dy = v2.y - v1.y;
        double length = Math.sqrt(dx * dx + dy * dy);
        return length;
    }


    public void releaseCatapult(){
        cataPultReleased = true;
        System.out.println("We komen hier");

    }

    public static void main(String[] args) {
        launch(AngryBirds.class);
    }

}
