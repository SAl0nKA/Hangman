import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;


/**
 * Class Game is the primary class with the game logic. It operates in the singleton mode.
 *
 * At the start of the game the user is asked to enter their name.
 * The game called Hangman is played by guessing letters of a hidden word one at the time.
 * You have 10 seconds to guess a letter and 10 guesses to guess the word.
 * Every time you guess a correct letter that letter is then shown on screen.
 * Otherwise, when you guess a wrong letter a new part is added to the hangman.
 * Game ends either when the hidden word is guessed correctly or user runs out of guesses by building a complete hangman.
 * After that you can either start a new game or end the application. <br/><br/>
 *
 * By pressing the Escape key you can bring out a game menu and pause the game.
 * You can move the selection by pressing Up and Down arrow keys, confirm your choice by pressing Enter and close the menu
 * by selecting "BACK" or pressing Escape. <br/>
 * You can see your stats by selecting "STATS" or exit the game by selecting "EXIT".
 *
 *
 * @author SAl0nKA
 * @version 3.1.2022
 * */
public class Game {
    private ArrayList<String> allWords;
    private DvojcifernyDisplej timerDisplay;
    private Timer timer;
    private DisplayedWord displayedWord;
    private char[] usedLetters;
    private Hangman hangman;
    private Random generator;
    private Stats statistikar;
    private KeyboardHandler handlerOne;
    private GameState stateOfGame;
    private Menu menu;

    private static Game game;
    /** Public instance getter for mode singleton.
     * @return return new instance of Game if none exists*/
    public static Game getGame() {
        if (Game.game == null) {
            Game.game = new Game();
        }
        return Game.game;
    }
    /** Private constructor for class Game. Loads list of all words and new instances of used atributes.
     * When done it starts the game.*/
    private Game() {
        try {
            File words = new File("words.txt");
            Scanner reader = new Scanner(words);
            this.allWords = new ArrayList<>();

            while (reader.hasNextLine()) {
                this.allWords.add(reader.nextLine().toLowerCase());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error while opening file words.txt", "Specifid file not found", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.generator = new Random();
        this.statistikar = new Stats();
        this.usedLetters = new char[0];
        this.handlerOne = new KeyboardHandler(this);
        this.timer = new Timer();
        Platno.dajPlatno().addTimerListener(this.timer);
        this.menu = new Menu();
        this.hangman = new Hangman();
        this.start();
    }

    /** Main method to create and start the game.*/
    public static void main(String[] args) {
        Game gameska = getGame();
    }

    /** Prepares game for the first time play.*/
    private void start() {
        this.displayedWord = new DisplayedWord(this.randomWord());
        this.timerDisplay = new DvojcifernyDisplej(50, 10, 750, 100, 10);
        this.hangman.show();
        this.hangman.reset();
        this.timer.timerStart();
        this.stateOfGame = GameState.GAME_IN_PROGRESS;
    }

    /** Prepares the next game by reseting counters, hidden word and list of used letters.*/
    private void nextGame() {
        this.timerDisplay.showNumber(10);
        this.timerDisplay.unpanic();
        this.displayedWord.hideAll();
        this.displayedWord.setWord(this.randomWord());
        this.usedLetters = new char[0];
        this.hangman.reset();
        this.timer.timerStart();
        this.stateOfGame = GameState.GAME_IN_PROGRESS;
    }

    /** Is called when game is either won or lost.
     * @param win if true user's won games get increased in stats and is asked to start a new game<br/>
     * if false the hidden word is shown on screen, user's lost games get increased in stats and user is asked to start a new game*/
    private void endGame(boolean win) { //we're in the endgame now
        this.timer.timerStop();
        this.stateOfGame = GameState.END_OF_GAME;
        int opt;
        if (win) {
            //todo add taunts
            this.statistikar.increment(1);
            opt = this.ask("Start next game?", "You won!");
        } else {
            this.statistikar.increment(2);
            this.displayedWord.showAll();
            opt = this.ask("Play again?", "You lost :(");
        }

        if (opt == 0) {
            this.nextGame();
        } else {
            this.statistikar.save();
            System.exit(0);
        }
    }

    /** Instructions what to do when Timer event fires.*/
    private void timerTask() {
        if (this.stateOfGame != GameState.END_OF_GAME) {
            this.timerDisplay.add(-1);
        }
        if (this.timerDisplay.getValue() == 5) {
            this.timerDisplay.panic();
        } else if (this.timerDisplay.getValue() == 0) {
            //dojde cas
            if (this.hangman.gameLost()) {
                this.endGame(false);
                return;
            }
            this.timerDisplay.showNumber(10);
            this.timerDisplay.unpanic();
            this.hangman.badGuess();
        }
    }

    /** Recieves selected letter from keyboard handler. Letter is then converted into character and
     * checked against current hidden word.
     */
    public void recieveLetter(String letter) throws Exception {
        if (this.stateOfGame == GameState.END_OF_GAME) {
            return;
        }
        char c = letter.toCharArray()[0];

        this.timerDisplay.showNumber(10);
        this.timerDisplay.unpanic();
        if (!this.contains(c, this.usedLetters)) {
            this.usedLetters = this.append(c, this.usedLetters);

            if (this.displayedWord.containsLetter(c)) {
                this.statistikar.increment(3);
                if (this.displayedWord.isWin()) {
                    this.timer.timerStop();
                    this.endGame(true);
                }
                return;
            }
        }

        //either already used or wrong
        this.statistikar.increment(4);
        this.hangman.badGuess();

        if (this.hangman.gameLost()) {
            this.stateOfGame = GameState.END_OF_GAME;
            this.endGame(false);
        }
    }

    /** Generates a random word from list of all words.
     * @return random word*/
    private String randomWord() {
        return this.allWords.get(this.generator.nextInt(this.allWords.size()));
    }

    /** Checks given array for given element.
     * @return returns true if contains, otherwise returns false.*/
    private boolean contains(char element, char[] array) {
        for (char c : array) {
            if (element == c) {
                return true;
            }
        }
        return false;
    }

    /** Appends new character into already existing character array.
     * @param src character to be appended
     * @param dst array of character to append to
     * @return returns new instance of array with appended element*/
    private char[] append(char src, char[] dst) {
        dst = Arrays.copyOf(dst, dst.length + 1);
        dst[dst.length - 1] = src;
        return dst;
    }

    /** Shows a confirm dialog with provided message and title.
     * @return returns selected option.*/
    private int ask(String message, String title) {
        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
    }
    /** Class Timer implements ActionListener to function as a timer with 1-second delay*/
    private class Timer implements ActionListener {
        private long oldTick;
        private final long tickLength = 1000000000;
        private boolean stop = true;

        /** After set time has passed since last event a function to run timer is called.*/
        public void actionPerformed(ActionEvent event) {
            long newTick = System.nanoTime();
            if (newTick - this.oldTick >= this.tickLength || newTick < this.tickLength) {
                this.oldTick = (newTick / this.tickLength) * this.tickLength;

                if (!this.stop) {
                    Game.this.timerTask();
                }
            }
        }

        /** Pauses the timer*/
        public void timerStop() {
            this.stop = true;
        }

        /** Resumes the timer*/
        public void timerStart() {
            this.stop = false;
        }
    }

    /** Confirms the choice in the game menu. Doesn't work if the game isn't in the state MENU.*/
    public void choose() {
        if (this.stateOfGame != GameState.MENU) {
            return;
        }
        this.menu.choose(this.statistikar);
    }

    /** Moves the selection in the game menu up by one. Doesn't work if the game isn't in the state MENU.*/
    public void chooseUp() {
        if (this.stateOfGame != GameState.MENU) {
            return;
        }
        this.menu.chooseUp();
    }

    /** Moves the selection in the game menu down by one. Doesn't work if the game isn't in the state MENU.*/
    public void chooseDown() {
        if (this.stateOfGame != GameState.MENU) {
            return;
        }
        this.menu.chooseDown();
    }

    /** Either hides or shows the game menu. Works only if the state of game isn't END_OF_GAME.*/
    public void showHideMenu() {
        if (this.stateOfGame != GameState.MENU && this.stateOfGame != GameState.END_OF_GAME) {
            this.stateOfGame = GameState.MENU;
            this.timer.timerStop();
            this.menu.showMenu();
        } else if (this.stateOfGame != GameState.END_OF_GAME) {
            this.timer.timerStart();
            this.menu.hideMenu();
            this.stateOfGame = GameState.GAME_IN_PROGRESS;
        }
    }

    /** Holds values for different states of the game.*/
    private enum GameState {
        GAME_IN_PROGRESS(),
        END_OF_GAME(),
        MENU()
    }
}
