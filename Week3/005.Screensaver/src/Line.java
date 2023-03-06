import org.jfree.fx.FXGraphics2D;

import java.awt.geom.Line2D;

public class Line {

    private double startX;
    private double endX;
    private double startY;
    private double endY;

    private double previousStartX;
    private double previousStartY;
    private double previousEndX;
    private double previousEndY;

    public double xStartDirection = 0;
    public double yStartDirection = 0;
    public double xEndDirection = 0;
    public double yEndDirection = 0;

    public Line(double startX, double startY, double endX, double endY, double xStartDir, double yStartDir, double xEndDir, double yEndDir, double lineVelocity) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.xStartDirection = xStartDir * lineVelocity;
        this.xEndDirection = xEndDir * lineVelocity;
        this.yStartDirection = yStartDir * lineVelocity;
        this.yEndDirection = yEndDir * lineVelocity;
    }

    public double getPreviousEndX() {
        return previousEndX;
    }

    public void setPreviousEndX(double previousEndX) {
        this.previousEndX = previousEndX;
    }

    public double getPreviousEndY() {
        return previousEndY;
    }

    public void setPreviousEndY(double previousEndY) {
        this.previousEndY = previousEndY;
    }

    public void draw(FXGraphics2D g2d)
    {
        g2d.draw(new Line2D.Double(getStartX(), getStartY(), getEndX(), getEndY()));
    }

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    public double getPreviousStartX() {
        return previousStartX;
    }

    public double getPreviousStartY() {
        return previousStartY;
    }

    public void setPreviousStartX(double previousStartX) {
        this.previousStartX = previousStartX;
    }

    public void setPreviousStartY(double previousStartY) {
        this.previousStartX = previousStartY;
    }






}
