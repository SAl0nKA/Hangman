/**
 * Segment of SSD consist of a single Rectangle.
 *
 * @author SAl0nKA
 * @version 4.1.2022
 */
public class Segment {
    private Obdlznik segment;

    /** Constructor for Segment. All segments are created with green color.
     * @param length Length of segment.
     * @param height Height of segment.
     * @param posX X coordinate of the upper left corner
     * @param posY Y coordinate of the upper left corner*/
    public Segment(int length, int height, int posX, int posY) {
        this.segment = new Obdlznik(length, height, posX, posY, "green");
    }

    /** Hides the segment on screen.*/
    public void lightDown() {
        this.segment.hide();
    }

    /** Shows the segment on screen.*/
    public void lightUp() {
        this.segment.show();
    }

    /** Changes the color of segment.*/
    public void changeColor(String color) {
        this.segment.changeColor(color);
    }
}
