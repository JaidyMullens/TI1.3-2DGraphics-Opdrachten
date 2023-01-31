import java.awt.*;
import java.awt.geom.*;
import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Spirograph extends Application {
    private TextField v1;
    private TextField v2;
    private TextField v3;
    private TextField v4;
    private Canvas canvas = new Canvas(1920, 1080);
    private Button b1 = new Button("Update");
    private Button btnClear = new Button("Clear Canvas");

    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox mainBox = new VBox();
        HBox topBar = new HBox();
        HBox bottomBar = new HBox();
        mainBox.getChildren().addAll(topBar, bottomBar);
        mainBox.getChildren().add(new Group(canvas));

        topBar.setPadding(new Insets(30,0,10, 0));
        topBar.getChildren().add(v1 = new TextField("300"));
        topBar.getChildren().add(v2 = new TextField("1"));
        topBar.getChildren().add(v3 = new TextField("300"));
        topBar.getChildren().add(v4 = new TextField("10"));
        topBar.getChildren().add(b1);

        bottomBar.getChildren().add(btnClear);

        primaryStage.setScene(new Scene(mainBox));
        primaryStage.setTitle("Spirograph");
        primaryStage.show();
        canvas.getGraphicsContext2D().translate(this.canvas.getWidth() / 2, this.canvas.getHeight() / 2);

        b1.setOnAction(event -> {
            draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        });

        btnClear.setOnAction(event -> {

            canvas.getGraphicsContext2D().clearRect(0, 0, this.canvas.getWidth() * 2, this.canvas.getHeight() * 2);
        });
    }


    float lastX = 0;
    float lastY = 0;
    public void draw(FXGraphics2D graphics) {

        // x = a × cos(b × φ) + c × cos(d × φ)
        double a = Double.parseDouble(v1.getText());
        double b = Double.parseDouble(v2.getText());
        double c = Double.parseDouble(v3.getText());
        double d = Double.parseDouble(v4.getText());


        for (float i = 0; i < Math.PI * Math.PI; i+= 0.00001f) {
            graphics.setColor(Color.getHSBColor(i/(float)Math.PI, 1, 1));
            float x = (float)(a * Math.cos(b * i) + c * Math.cos(d * i));
            float y = (float)(a * Math.sin(b * i) + c * Math.sin(d * i));

            graphics.draw(new Line2D.Double(x, y, x, y));

        }
    }

    public static void main(String[] args) {
        launch(Spirograph.class);
    }

}
