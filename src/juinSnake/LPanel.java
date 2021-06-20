package juinSnake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LPanel extends JPanel implements KeyListener {
    ImageIcon title = new ImageIcon("title.jpeg");
    ImageIcon body = new ImageIcon("body.png");
    ImageIcon food = new ImageIcon("food.png");
    ImageIcon down = new ImageIcon("down.png");
    ImageIcon up = new ImageIcon("up.png");
    ImageIcon left = new ImageIcon("left.png");
    ImageIcon right = new ImageIcon("right.png");

    int len;
    int[] snakeX = new int[750];
    int[] snakeY = new int[750];

    String direction = "D";
    boolean isStarted = false;

    public LPanel(){
        initSnake();
        this.setFocusable(true); // To get the key board Event
        this.addKeyListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); // g is my pencil
        this.setBackground(Color.CYAN);

        title.paintIcon(this, g, 25, 11);

        g.fillRect(25, 75, 850, 600);

       /* right.paintIcon(this, g, 100, 100);
        body.paintIcon(this, g, 75, 100);
        body.paintIcon(this, g, 50, 100); // See what it looks like first */

        //right.paintIcon(this, g, snakeX[0], snakeY[0]); // Snake head to right first

        switch (direction) {
            case "R" -> right.paintIcon(this, g, snakeX[0], snakeY[0]);
            case "L" -> left.paintIcon(this, g, snakeX[0], snakeY[0]);
            case "U" -> up.paintIcon(this, g, snakeX[0], snakeY[0]);
            case "D" -> down.paintIcon(this, g, snakeX[0], snakeY[0]);
        }

        for (int i = 1; i < len; i++) {
            body.paintIcon(this, g, snakeX[i], snakeY[i]);
        }

        if (!isStarted) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", Font.BOLD, 40));
            g.drawString("Press space to START", 300, 300);
        }

    }

    public void initSnake() {
        len = 3;
        snakeX[0] = 100;
        snakeY[0] = 100;
        snakeX[1] = 75;
        snakeY[1] = 100;
        snakeX[2] = 50;
        snakeY[2] = 100;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            isStarted = !isStarted;
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
