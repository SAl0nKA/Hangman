/**
 * DvojcifernyDisplej is used to show numbers from 0 to 99.
 *
 * @author SAl0nKA
 * @version 4.1.2022
 */
public class DvojcifernyDisplej {
    private SSD ssdA;
    private SSD ssdB;
    private int currentValue;

    /** Constructor for DvojcifernyDisplej
     * @param length Length of segment
     * @param width Width of segment
     * @param x X coordinate of upper left corner
     * @param y Y coordinate of upper left corner
     * @param value Number to show*/
    public DvojcifernyDisplej(int length, int width, int x, int y, int value) {
        this.ssdA = new SSD(length, width, x, y);
        this.ssdB = new SSD(length, width, x + 3 * width + length, y);
        this.showNumber(value);
    }

    /** Shows number in range 0 - 99.
     * @param number Number to show*/
    public void showNumber(int number) {
        if (number > 99 || number < 0) {
            return;
        }
        
        this.ssdA.showDigit((number - (number % 10)) / 10);
        this.ssdB.showDigit(number % 10);
        this.currentValue = number;
    }

    /** Adds value to current value and shows it.
     * @param value How much to add, can be either negative or positive*/
    public void add(int value) {
        this.currentValue += value;
        this.showNumber(this.currentValue);
    }

    /** Returns current value.
     * @return Current value*/
    public int getValue() {
        return this.currentValue;
    }

    /** Turns all segments red.*/
    public void panic() {
        this.ssdA.changeColor("red");
        this.ssdB.changeColor("red");
    }

    /** Turns all segments green.*/
    public void unpanic() {
        this.ssdA.changeColor("green");
        this.ssdB.changeColor("green");
    }
}
