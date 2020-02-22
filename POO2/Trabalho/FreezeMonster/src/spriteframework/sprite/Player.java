package spriteframework.sprite;

import javax.swing.ImageIcon;

import spriteframework.Commons;

import java.awt.Image;
import java.awt.event.KeyEvent;

public class Player extends Sprite {

    private int width;
    private int height;
    private int wichGame;

    public void setWichGame(int wichGame) {
        this.wichGame = wichGame;
    }

    public int getWichGame() {
        return wichGame;
    }

    public Player(int game) {
        if(game == 1) loadImageIcon();
        else if(game == 2) loadImage();

        setWichGame(game);

		getImageDimensions();
		resetState();
    }

    protected void loadImageIcon() {
        ImageIcon ii = new ImageIcon("images/player.png");
        width = ii.getImage().getWidth(null);
        setImage(ii.getImage());
    }

    protected void loadImage() {
        ImageIcon ii = new ImageIcon("images/freezemonster/woody.png");

        Image scaledImage = ii.getImage().getScaledInstance(30, 50, Image.SCALE_SMOOTH);

        width = scaledImage.getWidth(null);
        height = scaledImage.getHeight(null);

        setImage(scaledImage);
    }
    
    public void act() {

        x += dx;

        if (x <= 2) {

            x = 2;
        }

        if (x >= Commons.BOARD_WIDTH - 2 * width) {

            x = Commons.BOARD_WIDTH - 2 * width;
        }
    }

    public void act_2d() {

        x += dx;
        y += dy;

        if (x <= 2) {

            x = 2;
        }

        if (x >= Commons.BOARD_WIDTH - 2 * width) {

            x = Commons.BOARD_WIDTH - 2 * width;
        }

        if (y <= 2) {

            y = 2;
        }

        if (y >= Commons.BOARD_HEIGHT - 90) {

            y = Commons.BOARD_HEIGHT - 90;
        }
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 2;
        }

        if (key == KeyEvent.VK_UP) {

            dy = -2;
        }

        if (key == KeyEvent.VK_DOWN) {

            dy = 2;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {

            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {

            dy = 0;
        }
    }
    private void resetState() {

        setX(Commons.INIT_PLAYER_X);
        setY(Commons.INIT_PLAYER_Y);
    }
}
