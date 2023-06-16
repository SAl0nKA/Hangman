/**
 * Letter consists of picture of the letter and a line under it.
 *
 * @author SAl0nKA
 * @version 4.1.2022
 */
public class Letter {
    private Picture letterPic;
    private Obdlznik underline;
    private char value;

    /** Constructor for Letter takes in a single letter and finds corresponding picture of the letter provided.
     * @param letter A letter which the instance represents
     * @param posX X coordinate of the upper left corner
     * @param posY Y coordinate of the upper left corner*/
    public Letter(int posX, int posY, char letter) {
        this.value = letter;
        this.letterPic = new Picture("./source/" + letter + ".png");
        this.letterPic.setPosition(posX, posY - 70);
        this.underline = new Obdlznik(60, 10, posX, posY + 10, "black");
        this.underline.show();
    }

    /** Returns letter which the instance represents.
     * @return A letter*/
    public char getValue() {
        return this.value;
    }

    /** Shows the picture on screen.*/
    public void show() {
        this.letterPic.show();
//        this.underline.show();
    }

    /** Hides the picture.*/
    public void hide() {
        this.letterPic.hide();
        this.underline.hide();
    }

    /** Checks if the picture is visible.
     * @return True if yes, false if no*/
    public boolean isShown() {
        return this.letterPic.isVisible();
    }
}
