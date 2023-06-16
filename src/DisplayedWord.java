import java.util.ArrayList;
/**
 * DisplayedWord is a word consisting of letters and their respective pictures which are shown on screen.
 * 
 * @author SAl0nKA
 * @version 4.1.2022
 */
public class DisplayedWord {
    private ArrayList<Letter> wordLetters;
    private final int startY = 700;

    /**
     * Constructor for DisplayedWord.
     * @param word A word to be made into list of letters and pictures.
     */
    public DisplayedWord(String word) {
        this.setWord(word);
    }

    /** Creates new list of Letters with provided word.
     * @param word A word to be made into new list.*/
    public void setWord(String word) {
        this.wordLetters = new ArrayList<Letter>();
        int fullLength = word.length() * 70; //60 na pismeno, 10 na medzeru
        Platno platno = Platno.dajPlatno();

        //nech je cele slovo zhruba v strede
        int startX =  (platno.getWidth() - fullLength) / 2;

        for (char letter : word.stripTrailing().toCharArray()) {
            if (letter == ' ') {
                startX += 70;
                continue;
            }
            this.wordLetters.add(new Letter(startX, this.startY, letter));

            //miesto pre dalsie pismeno
            startX += 70;
        }
    }

    /** Checks if current list of letters contains provided letter
     * @param guessedLetter Letter to check against current list
     * @return true if list contains letter, false if it doesn't*/
    public boolean containsLetter(char guessedLetter) {
        boolean contains = false;
        for (Letter letter : this.wordLetters) {
            if (letter.getValue() == guessedLetter) {
                letter.show();
                contains = true;
            }
        }
        return contains;
    }
    /** Shows all hidden letters on screen.*/
    public void showAll() {
        for (Letter letter : this.wordLetters) {
            letter.show();
        }
    }

    /** Hides all letters on screen.*/
    public void hideAll() {
        for (Letter letter : this.wordLetters) {
            letter.hide();
        }
    }

    /** Checks if the game was won by guessing all letters.
     * @return true if all letters are shown, false if not*/
    public boolean isWin() {
        for (Letter letter : this.wordLetters) {
            if (!letter.isShown()) {
                return false;
            }
        }
        return true;
    }
}
