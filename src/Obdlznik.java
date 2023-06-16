import java.awt.Rectangle;

/**
 * A rectangle to draw on screen.
 * @author  Michael Kolling and David J. Barnes
 * Upravil SAl0nKA
 * @version 4.1.2022
 */
public class Obdlznik {
    private final int sideA;
    private final int sideB;
    private int posX;
    private int posY;
    private String color;
    private boolean isVisible;

    /**
     * Creates a new rectangle with provided arguments.
     * @param posX X coordinate of the upper left corner
     * @param posY Y coordinate of the upper left corner
     * @param sideA Length of side A, usually width.
     * @param sideB Length of side B, usually height.
     * @param color Color of the rectangle
     */
    public Obdlznik(int sideA, int sideB, int posX, int posY, String color) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.posX = posX;
        this.posY = posY;
        this.color = color;
        this.isVisible = false;
    }

    /** Shows the rectangle on screen.*/
    public void show() {
        this.isVisible = true;
        this.draw();
    }

    /** Hides the rectangle on screen.*/
    public void hide() {
        this.remove();
        this.isVisible = false;
    }

    /** Changes position to new coordinates.
     * @param upperLeftX New upper left X coordinate.
     * @param upperLeftY New upper left Y coordinate.*/
    public void changePos(int upperLeftX, int upperLeftY) {
        boolean drawn = this.isVisible;
        this.remove();
        this.posX = upperLeftX;
        this.posY = upperLeftY;
        if (drawn) {
            this.draw();
        }
    }

    /** Changes the color of the rectangle to provided color.
     * @param newColor Possible colors are:<br/>
     * "red"<br/>
     * "yellow"<br/>
     * "blue"<br/>
     * "green"<br/>
     * "magenta"<br/>
     * "black"<br/>
     * "white"<br/>
     * "brown"<br/>
     * */
    public void changeColor(String newColor) {
        this.color = newColor;
        this.draw();
    }

    /** Returns current Y coordinate.*/
    public int getPosY() {
        return this.posY;
    }

    /** Draw the square with current specifications on screen.*/
    private void draw() {
        if (this.isVisible) {
            Platno.dajPlatno().draw(this, this.color, new Rectangle(this.posX, this.posY, this.sideA, this.sideB));
        }
    }

    /** Erase the square on screen.*/
    private void remove() {
        if (this.isVisible) {
            Platno canvas = Platno.dajPlatno();
            canvas.erase(this);
        }
    }
}
