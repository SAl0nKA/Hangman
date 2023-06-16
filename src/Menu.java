import javax.swing.JOptionPane;
import java.util.HashMap;
/**
 * Graphical menu for choosing provided options.
 *
 * @author SAl0nKA
 * @version 4.1.2022
 */
public class Menu {
    private HashMap<String, Picture> pics;
    private Obdlznik selector;
    private int choice;
    private final int posX = (1000 - 550) / 2;
    private final int posY = (800 - 130 * 3 - 120 * 2) / 2;


    /** Constuctor for Menu. Menu consist of pictures of an overlay, options and a single selector which represents the choice.*/
    public Menu() {
        this.pics = new HashMap<>();
        this.pics.put("overlay", new Picture("source/overlay.png"));
        this.pics.get("overlay").setPosition(0, 0);
        this.pics.put("back", new Picture("source/back.png"));
        this.pics.get("back").setPosition(this.posX, this.posY);
        this.pics.put("stats", new Picture("source/stats.png"));
        this.pics.get("stats").setPosition(this.posX, this.posY + 250);
        this.pics.put("exit", new Picture("source/exit.png"));
        this.pics.get("exit").setPosition(this.posX, this.posY + 500);

        this.selector = new Obdlznik(70, 70, this.posX - 50, this.posY + 30, "red");
        this.choice = 0;
    }

    /** Shows the menu on screen.*/
    public void showMenu() {
        this.pics.get("overlay").show();
        this.pics.get("back").show();
        this.pics.get("stats").show();
        this.pics.get("exit").show();
        this.selector.show();
    }

    /** Hides menu on screen.*/
    public void hideMenu() {
        for (Picture pic: this.pics.values()) {
            pic.hide();
        }
        this.selector.hide();
    }

    /** Confirms the current selection.
     * @param statistikar Stats object for getting formatted user stats.*/
    public void choose(Stats statistikar) {
        switch (this.choice) {
            case 0:
                this.hideMenu();
                break;
            case 1:
                JOptionPane.showMessageDialog(null, statistikar.getStats(), "Stats", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 2:
                statistikar.save();
                System.exit(0);
        }
    }

    /** Moves the selection up by one*/
    public void chooseUp() {
        if (this.choice == 0) {
            return;
        }
        this.choice--;
        this.selector.changePos(this.posX - 50, this.selector.getPosY() - 250);
    }

    /** Moves the selection down by one*/
    public void chooseDown() {
        if (this.choice == 2) {
            return;
        }
        this.choice++;
        this.selector.changePos(this.posX - 50, this.selector.getPosY() + 250);
    }
}
