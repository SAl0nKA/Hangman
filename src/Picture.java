import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import java.awt.geom.AffineTransform;

/**
 * A picture to load from a file and show on screen.
 *
 * @author SAl0nKA
 * @version 4.1.2022
 */
public class Picture {
    private int posX;
    private int posY;
    private boolean isVisible;
    private BufferedImage picture;

    /** Constructor for Picture.
     * @param path Path to picture.*/
    public Picture(String path) {
        try {
            this.picture = ImageIO.read(new File(path));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Specified file not found.", "Load status", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }  
        this.isVisible = false;
    }

    /** Draws the picture on canvas.*/
    private void draw() {
        if (this.isVisible) {
            Platno canvas = Platno.dajPlatno();
        
            AffineTransform at = new AffineTransform();
            at.translate(this.posX + this.picture.getWidth() / 2, this.posY + this.picture.getHeight() / 2);
            at.translate(-this.picture.getWidth() / 2, -this.picture.getHeight() / 2);
        
            canvas.draw(this, this.picture, at);
//            canvas.wait(10);
        }
    }

    /** Returns width of current picture.*/
    public int getWidth() {
        return this.picture.getWidth();
    }

    /** Shows the picture on screen.*/
    public void show() {      
        this.isVisible = true;
        this.draw();
    }

    /** Hides the picture on screen.*/
    public void hide() {       
        this.erase();
        this.isVisible = false;
    }

    /** Erases the picture from canvas.*/
    private void erase() {
        if (this.isVisible) {
            Platno canvas = Platno.dajPlatno();
            canvas.erase(this);
        }
    }

    /** Moves the picture to new postion
     * @param posX X coordinate of the upper left corner
     * @param posY Y coordinate of the upper left corner*/
    public void setPosition(int posX, int posY) {
        this.erase();
        this.posX = posX;
        this.posY = posY;
        if (this.isVisible) {
            this.draw();
        }
    }

    /** Checks if the picture is visible.
     * @return true if yes, false if no*/
    public boolean isVisible() {
        return this.isVisible;
    }
}
