import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Class responsible for managing stats of players. Stats are then saved in a JSON file.
 *
 * @author SAl0nKA
 * @version 4.1.2022
 */
public class Stats {
    private final String filename = "stats.json";
    private ArrayList<Player> players;
    private String currentPlayerName;

    /** Constructor for Stats. At first user is prompted to enter their name. If the input dialog is closed the application exits.*/
    public Stats() {
        this.currentPlayerName = "";
        this.players = new ArrayList<>();
        this.load();

        while (this.currentPlayerName.equals("")) {
            this.currentPlayerName = JOptionPane.showInputDialog(null, "Enter your name", "", JOptionPane.QUESTION_MESSAGE);
            if (this.currentPlayerName == null) {
                System.exit(0);
            }
        }

        if (this.getPlayer() == null) {
            this.players.add(new Player(this.currentPlayerName, 0, 0, 0, 0));
        }
    }

    /** Saves current values for players into JSON file.*/
    public void save() {
        JSONArray jArr = new JSONArray();
        for (Player player:this.players) {
            jArr.put(player.createJsonObject());
        }

        JSONObject jsonPlayers = new JSONObject();
        jsonPlayers.put("players", jArr);

        try {
            FileWriter writer = new FileWriter(this.filename);
            writer.write(jsonPlayers.toString(3));
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Saving failed!", "Save status", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /** Loads JSON file and parses it into a list of Player objects. If no file was found it creates a new one.*/
    private void load() {
        try {
            File f = new File(this.filename);
            if (!f.createNewFile()) {
                String content = Files.readString(Path.of(this.filename));
                JSONObject json = new JSONObject(content);
                JSONArray jsArr = json.getJSONArray("players");

                for (int i = 0; i < jsArr.length(); i++) {
                    this.players.add((this.parsePlayer(jsArr.getJSONObject(i)))); //single player
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error while opening file", "Load status", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1);
        }
    }

    /** Returns formatted stats of current player.*/
    public String getStats() {
        return this.getPlayer().toString();
    }

    /** Increments selected counter by one.
     * @param n Represents which field should be incremented. <br/>
     * 1 - wonGames<br/>
     * 2 - lostGames<br/>
     * 3 - goodGuesses<br/>
     * 4 - badGuesses<br/>
     * **/
    public void increment(int n) {
        switch (n) {
            case 1:
                this.getPlayer().wonGames++;
                break;
            case 2:
                this.getPlayer().lostGames++;
                break;
            case 3:
                this.getPlayer().goodGuesses++;
                break;
            case 4:
                this.getPlayer().badGuesses++;
                break;
        }
    }

    /** Gets values from provided JSON object a returns a Player object with obtained values.*/
    private Player parsePlayer(JSONObject player) {
        String name = player.getString("name");
        int wonGames = player.getInt("wonGames");
        int lostGames = player.getInt("lostGames");
        int goodGuesses = player.getInt("goodGuesses");
        int badGuesses = player.getInt("badGuesses");
        return new Player(name, wonGames, lostGames, goodGuesses, badGuesses);
    }

    /** Returns player based on the name of current player.*/
    private Player getPlayer() {
        for (Player player:this.players) {
            if (player.name.equals(this.currentPlayerName)) {
                return player;
            }
        }
        return null;
    }

    /** Player holds values for different stats.*/
    private class Player {
        private final String name;
        private int wonGames;
        private int lostGames;
        private int goodGuesses;
        private int badGuesses;

        /** Constructor for Player object.*/
        Player(String name, int wonGames, int lostGames, int goodGuesses, int badGuesses) {
            this.name = name;
            this.wonGames = wonGames;
            this.lostGames = lostGames;
            this.goodGuesses = goodGuesses;
            this.badGuesses = badGuesses;
        }

        /** Creates JSONObject from current values of attributes.*/
        public JSONObject createJsonObject() {
            JSONObject object = new JSONObject();
            object.put("name", this.name);
            object.put("wonGames", this.wonGames);
            object.put("lostGames", this.lostGames);
            object.put("goodGuesses",  this.goodGuesses);
            object.put("badGuesses", this.badGuesses);
            return object;
        }

        /** Formats attribute values into readable string.*/
        public String toString() {
            return String.format("Player name: %s\nWon games: %d\nLost games: %d\nGood guesses: %d\nBad guesses: %d",
                    this.name, this.wonGames, this.lostGames, this.goodGuesses, this.badGuesses);
        }
    }
}
