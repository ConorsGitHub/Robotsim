package Robotsim;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.TextAlignment;

public class MyCanvas {
	//this class creates the canvas and holds all the draw functions 
	int xCanvasSize = 512;				// constants for relevant sizes
	int yCanvasSize = 512;
    GraphicsContext gc; 

    /**
     * @param g
     * @param cs
     */
    public MyCanvas(GraphicsContext g, int xcs, int ycs) {
    	gc = g;
    	xCanvasSize = xcs;
    	yCanvasSize = ycs;
    }
    /**
     * @return xsize
     */
    public int getXCanvasSize() {
    	return xCanvasSize;
    }
    /**   
     * @return ysize
     */
    public int getYCanvasSize() {
    	return yCanvasSize;
    }
    public void clearCanvas() {
		gc.clearRect(0,  0,  xCanvasSize,  yCanvasSize);		// clear canvas
    } 
	/**
     * @param i		image
     * @param x		xposition	in range 0..1
     * @param y
     * @param sz	size
     */
	public void drawIt (Image i, double x, double y, double sz) {
			// to draw centred at x,y, give top left position and x,y size
			// sizes/position in range 0..1, so scale to canvassize 
		gc.drawImage(i, xCanvasSize * (x - sz/2), yCanvasSize*(y - sz/2), xCanvasSize*sz, yCanvasSize*sz);
	}
	/** 
	 * @param c
	 * @return Color
	 */
	Color colFromChar (char c){
		Color ans = Color.BLACK;
		switch (c) {
		case 'y' :	ans = Color.YELLOW;
					break;
		case 'w' :	ans = Color.WHITE;
					break;
		case 'r' :	ans = Color.RED;
					break;
		case 'g' :	ans = Color.GREEN;
					break;
		case 'b' :	ans = Color.BLUE;
					break;
		case 'o' :	ans = Color.ORANGE;
					break;
		case 'x' : ans = Color.BLACK;
					break;
		}
		return ans;
	}
    private Color opaquered() {
        // Adjust the alpha component to make it slightly opaque (e.g., 0.8 for 80% opacity)
        return new Color(1.0, 0.0, 0.0, 0.7);
    }
	
	public void setFillColour (Color c) {
		gc.setFill(c);
	}
	/**
	 * @param x
	 * @param y
	 * @param rad
	 * @param col
	 */
	public void showCircle(double x, double y, double rad, char col) {
	 	setFillColour(colFromChar(col));									// set the fill colour
		gc.fillArc(x-rad, y-rad, rad*2, rad*2, 0, 360, ArcType.ROUND);	// fill circle
	}

	/**
	 * @param x
	 * @param y
	 * @param rad
	 */
	public void showCircle(double x, double y, double rad) {
		gc.fillArc(x-rad, y-rad, rad*2, rad*2, 0, 360, ArcType.ROUND);	// fill circle
	}
	public void showRectangle(double x,double y, double width, double height,char col) {
		setFillColour(colFromChar(col));
		gc.fillRect(x, y, width, height); // fills rectangle
	}
	public void Leftwheel(double x, double y, double rad, char col, double angle) {
        setFillColour(colFromChar(col));
        gc.setLineWidth(4.5);
        double wheelOffset = rad; 
        double leftWheelX = x + wheelOffset * Math.cos(Math.toRadians(angle + 90));
        double leftWheelY = y + wheelOffset * Math.sin(Math.toRadians(angle + 90));
        gc.strokeLine(leftWheelX, leftWheelY, leftWheelX + rad * Math.cos(Math.toRadians(angle)), leftWheelY + rad * Math.sin(Math.toRadians(angle)));
        
	}
	public void Rightwheel(double x, double y, double rad, char col, double angle) {
        setFillColour(colFromChar(col));
        gc.setLineWidth(4.5);
        double wheelOffset = rad; 
        double rightWheelX = x + wheelOffset * Math.cos(Math.toRadians(angle - 90));
        double rightWheelY = y + wheelOffset * Math.sin(Math.toRadians(angle - 90));
        gc.strokeLine(rightWheelX, rightWheelY, rightWheelX + rad * Math.cos(Math.toRadians(angle)), rightWheelY + rad * Math.sin(Math.toRadians(angle)));
        
	}

	public void showWhiskers(double x, double y, double rad, char col,double angle) {
        setFillColour(colFromChar(col));
        double whiskerLength = 50; 
        double leftWhiskerAngle = angle + 30;
        double rightWhiskerAngle = angle - 30;
        
        double leftEndX = x + whiskerLength * Math.cos(Math.toRadians(leftWhiskerAngle));
        double leftEndY = y + whiskerLength * Math.sin(Math.toRadians(leftWhiskerAngle));
        double rightEndX = x + whiskerLength * Math.cos(Math.toRadians(rightWhiskerAngle));
        double rightEndY = y + whiskerLength * Math.sin(Math.toRadians(rightWhiskerAngle));

        gc.setStroke(Color.BLACK); // Set the stroke color
        gc.setLineWidth(1.0); // Set the line width

        // Draw left whisker
        gc.strokeLine(x, y, leftEndX, leftEndY);

        // Draw right whisker
        gc.strokeLine(x, y, rightEndX, rightEndY);
    }
	public void showBeam(double x, double y, double rad, double angle, double range) {
			Color savedFill = (Color) gc.getFill();
			Color savedStroke = (Color) gc.getStroke();
			double savedLineWidth = gc.getLineWidth();
		    double sensorTipX = x + rad * Math.cos(Math.toRadians(angle)); // Touching the robot
		    double sensorTipY = y + rad * Math.sin(Math.toRadians(angle));
		    double sensorBaseLeftX = sensorTipX + range * Math.cos(Math.toRadians(angle + 30));
		    double sensorBaseLeftY = sensorTipY + range * Math.sin(Math.toRadians(angle + 30));
		    double sensorBaseRightX = sensorTipX + range * Math.cos(Math.toRadians(angle - 30));
		    double sensorBaseRightY = sensorTipY + range * Math.sin(Math.toRadians(angle - 30));

		    gc.setFill(opaquered()); // Set the fill color for the sensor
		    gc.setStroke(opaquered()); // Set the stroke color for the sensor
		    gc.setLineWidth(1.0); // Set the line width
		    // Draw the large triangular sensor
		    gc.beginPath();
		    gc.moveTo(sensorTipX, sensorTipY);
		    gc.lineTo(sensorBaseLeftX, sensorBaseLeftY);
		    gc.lineTo(sensorBaseRightX, sensorBaseRightY);
		    gc.closePath();
		    gc.fill(); // Fill the sensor shape
		    gc.stroke(); // Stroke the sensor shape

		    // Restore the saved state of the graphics context
		    gc.setFill(savedFill);
		    gc.setStroke(savedStroke);
		    gc.setLineWidth(savedLineWidth);
		}


	/**
	 * @param x
	 * @param y
	 * @param s
	 */
	public void showText (double x, double y, String s) {
		gc.setTextAlign(TextAlignment.CENTER);							// set horizontal alignment
		gc.setTextBaseline(VPos.CENTER);								// vertical
		gc.setFill(Color.WHITE);										// colour in white
		gc.fillText(s, x, y);						// print score as text
	}

	/**
	 * @param x
	 * @param y
	 * @param i
	 */
	public void showInt (double x, double y, int i) {
		showText (x, y, Integer.toString(i));
	}	
}
