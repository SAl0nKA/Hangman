import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.awt.event.KeyAdapter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * KeyboardHandler extends KeyAdapter to send values of pressed keys to the Game instance which are then processed.
 *
 * @author SAl0nKA
 * @version 4.1.2022
 */
public class KeyboardHandler extends KeyAdapter {
    private Object game;
    private final Pattern pattern;

    /** Constructor for KeyboardHandler. While creating it registers itself as KeyListener to Platno.
     * @param object Object of the Game class to send signals to.*/
    public KeyboardHandler(Object object) {
        this.game = object;
        Platno.dajPlatno().addKeyListener(this);
        this.pattern = Pattern.compile("^[a-zA-Z]$", Pattern.MULTILINE);
    }

    /** Reacts to a pressed key and then checks if the KeyChar matches the predefined pattern of lowercase and uppercase letters.
     * If not it checks if either Up and Down arrow keys, Escape or Enter were pressed.*/
    public void keyPressed(KeyEvent event) {
        String keyValue = String.valueOf(event.getKeyChar()).toLowerCase();
        Matcher matcher = this.pattern.matcher(keyValue);
        if (matcher.find()) {
            this.invokeMethod("recieveLetter", String.class, keyValue);
        } else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.invokeMethod("showHideMenu", null);
        } else if (event.getKeyCode() == KeyEvent.VK_ENTER) {
            this.invokeMethod("choose", null);
        } else if (event.getKeyCode() == KeyEvent.VK_UP) {
            this.invokeMethod("chooseUp", null);
        } else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
            this.invokeMethod("chooseDown", null);
        }
    }

    /** Invokes a method on the Game object.
     * @param methodName Name of the method to invoke
     * @param parameterType Types of method arguments
     * @param args Arguments to invoke the method with*/
    private void invokeMethod(String methodName, Class parameterType, Object... args) {
        try {
            if (this.game != null) {
                if (parameterType != null) {
                    Method sprava = this.game.getClass().getMethod(methodName, parameterType);
                    sprava.invoke(this.game, args);
                } else {
                    Method sprava = this.game.getClass().getMethod(methodName);
                    sprava.invoke(this.game);
                }
            }
        } catch (SecurityException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
