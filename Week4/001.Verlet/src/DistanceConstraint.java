import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class DistanceConstraint implements Constraint {

    private double distance;
    private Particle a;
    private Particle b;

    private double appliedForce;

    public DistanceConstraint(Particle a, Particle b) {
        this(a, b, a.getPosition().distance(b.getPosition()));
    }

    public DistanceConstraint(Particle a, Particle b, double distance) {
        this.a = a;
        this.b = b;
        this.distance = distance;
    }

    @Override
    public void satisfy() {

        double currentDistance = a.getPosition().distance(b.getPosition());
        double adjustmentDistance = (currentDistance - distance) / 2;

        Point2D BA = new Point2D.Double(b.getPosition().getX() - a.getPosition().getX(), b.getPosition().getY() - a.getPosition().getY());
        double length = BA.distance(0, 0);
        if (length > 0.0001) // We kunnen alleen corrigeren als we een richting hebben
        {
            BA = new Point2D.Double(BA.getX() / length, BA.getY() / length);
        } else {
            BA = new Point2D.Double(1, 0);
        }

        a.setPosition(new Point2D.Double(a.getPosition().getX() + BA.getX() * adjustmentDistance,
                a.getPosition().getY() + BA.getY() * adjustmentDistance));
        b.setPosition(new Point2D.Double(b.getPosition().getX() - BA.getX() * adjustmentDistance,
                b.getPosition().getY() - BA.getY() * adjustmentDistance));


        setAppliedForce(currentDistance - adjustmentDistance);
        System.out.println(appliedForce);
    }

    public void setAppliedForce(double force){
        double targetLength = distance;
        double forceFraction = force / targetLength;

        this.appliedForce = forceFraction;
    }

    @Override
    public void draw(FXGraphics2D g2d) {
        // Set the color based on the appliedForce
        Color color = Color.GREEN; // default color

        if (appliedForce > 1.30 )
        {
            // Very heavy force on constraint
            color = Color.RED.darker();
        }
        if (appliedForce > 1.10 && appliedForce < 1.30) {
             // Big force on constraint
            color = Color.red;
        } else if (appliedForce < 1.09 && appliedForce > 1.03) {
             // Small force on constraint (hanging)
            color = Color.orange;
        }
        else if ( appliedForce < 1.03)
        {
            // Little to no force on constraint
            color = Color.green;
        }

        g2d.setColor(color);
        g2d.draw(new Line2D.Double(a.getPosition(), b.getPosition()));
    }
}
