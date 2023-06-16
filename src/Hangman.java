import java.util.ArrayList;
/**
 * Hangman represents the hangman picture that is slowly built.
 *
 * @author SAl0nKA
 * @version 4.1.2022
 */
public class Hangman {
    private Picture hangman;
    private ArrayList<Obdlznik> hiders;
    private int tryCounter;

    /** Constructor for Hangman. Hangman is created by showing a complete picture of hangman and then hiding
     * its parts which are then slowly revealed.*/
    public Hangman() {
        this.hangman = new Picture("source/hangman.png");
        this.hangman.setPosition((1000 - this.hangman.getWidth()) / 2, 200);
        this.hiders = new ArrayList<>();
        this.hiders.add(new Obdlznik(16 , 324, 394, 218, "white")); //zvisle
        this.hiders.add(new Obdlznik(182, 16, 410, 218, "white")); //vodorovne
        this.hiders.add(new Obdlznik(75, 75, 410, 234, "white")); //sibenicka
        this.hiders.add(new Obdlznik(17, 60, 575, 234, "white")); //slucka
        this.hiders.add(new Obdlznik(92, 90, 537, 294, "white")); //hlava
        this.hiders.add(new Obdlznik(20, 97, 574, 383, "white")); //telo
        this.hiders.add(new Obdlznik(65, 50, 590, 398, "white")); //prava ruka
        this.hiders.add(new Obdlznik(65, 50, 510, 400, "white")); //lava ruka
        this.hiders.add(new Obdlznik(100, 60, 582, 480, "white")); //prava noha
        this.hiders.add(new Obdlznik(100, 60, 482, 480, "white")); //lava noha
        this.tryCounter = 0;
    }

    /** Shows the picture of hangman.*/
    public void show() {
        this.hangman.show();
    }

    /** Resets the counter and hangman by showing the hiding rectangles back on screen.*/
    public void reset() {
        for (Obdlznik o: this.hiders) {
            o.show();
        }
        this.tryCounter = 0;
    }

    /** Increments the counter and shows next part of the hangman*/
    public void badGuess() {
        this.hiders.get(this.tryCounter).hide();
        this.tryCounter++;
    }
    /** Checks if the game was lost by using all guesses.
     * @return true if 10 were used, otherwise false*/
    public boolean gameLost() {
        return this.tryCounter == 10 ? true : false;
    }
}
