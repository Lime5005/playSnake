package juinSnake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class LPanel extends JPanel implements KeyListener, ActionListener {
    ImageIcon title = new ImageIcon("title.jpeg");
    ImageIcon body = new ImageIcon("body.png");
    ImageIcon food = new ImageIcon("food.png");
    ImageIcon down = new ImageIcon("down.png");
    ImageIcon up = new ImageIcon("up.png");
    ImageIcon left = new ImageIcon("left.png");
    ImageIcon right = new ImageIcon("right.png");

    int len;
    int score;
    int[] snakeX = new int[750];
    int[] snakeY = new int[750];

    String direction = "R";
    boolean isStarted = false;
    boolean isFailed = false;

    // Import Timer. Want faster, delay number smaller. In millisecond
    Timer timer = new Timer(200, this);

    // Set a food at a random place.
    int foodX;
    int foodY;
    Random random = new Random();

    public LPanel(){
        initSnake();
        this.setFocusable(true); // To get the key board Event
        this.addKeyListener(this);
        timer.start(); // Continue the timer
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); // g is my pencil
        this.setBackground(Color.CYAN);

        title.paintIcon(this, g, 25, 11);

        g.fillRect(25, 75, 850, 600);

        // Draw the length and the score.
        g.setColor(Color.WHITE);
        g.drawString("Length: " + len, 730, 35);
        g.drawString("Score: " + score, 730, 55);

        switch (direction) {
            case "R" -> right.paintIcon(this, g, snakeX[0], snakeY[0]);
            case "L" -> left.paintIcon(this, g, snakeX[0], snakeY[0]);
            case "U" -> up.paintIcon(this, g, snakeX[0], snakeY[0]);
            case "D" -> down.paintIcon(this, g, snakeX[0], snakeY[0]);
        }

        for (int i = 1; i < len; i++) {
            body.paintIcon(this, g, snakeX[i], snakeY[i]);
        }

        // Paint the food randomly.
        food.paintIcon(this, g, foodX, foodY);

        if (!isStarted) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", Font.BOLD, 40));
            g.drawString("Press space to START", 250, 600);
        }

        if (isFailed) {
            g.setColor(Color.RED);
            g.setFont(new Font("arial", Font.BOLD, 40));
            g.drawString("Failed: Press space to RESTART", 150, 600);
        }

    }

    /**
     * At the beginning, the snake is shaped and the food is set.
     */
    public void initSnake() {
        len = 3;
        score = 0;
        direction = "R";
        snakeX[0] = 100;
        snakeY[0] = 100;
        snakeX[1] = 75;
        snakeY[1] = 100;
        snakeX[2] = 50;
        snakeY[2] = 100;

        // Put the food in a scope not outside of the canvas.
        foodX = 25 + 25 * random.nextInt(34); // From 0 to 33 block. 25 * 34 = 850.
        foodY = 75 + 25 * random.nextInt(24); // 24 * 25 = 600.

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            // Restart if failed.
            if (isFailed) {
                isFailed = false;
                initSnake();
            }

            isStarted = !isStarted;

            repaint();
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            direction = "R";
        } else if (keyCode == KeyEvent.VK_LEFT) {
            direction = "L";
        } else if (keyCode == KeyEvent.VK_UP) {
            direction = "U";
        } else if (keyCode == KeyEvent.VK_DOWN) {
            direction = "D";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (isStarted && !isFailed) {
            // Move the body one step forward:
            for (int i = len - 1; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }

            // How to move:
            switch (direction) {
                case "R" -> {
                    // Move the head right:
                    snakeX[0] = snakeX[0] + 25;
                    if (snakeX[0] > 850) snakeX[0] = 25; // Don't let it out of the canvas.
                }
                case "L" -> {
                    snakeX[0] = snakeX[0] - 25;
                    if (snakeX[0] < 25) snakeX[0] = 850;
                }
                case "D" -> {
                    snakeY[0] = snakeY[0] + 25;
                    if (snakeY[0] > 650) snakeY[0] = 75;
                }
                case "U" -> {
                    snakeY[0] = snakeY[0] - 25;
                    if (snakeY[0] < 75) snakeY[0] = 650;
                }
            }

            // If the snake head covered the food, the snake grow.
            if (snakeX[0] == foodX && snakeY[0] == foodY) {
                len ++;
                score += 10;
                // Get a new food.
                foodX = 25 + 25 * random.nextInt(34); // From 0 to 33 block. 25 * 34 = 850.
                foodY = 75 + 25 * random.nextInt(24); // 24 * 25 = 600.
            }

            // Judge if failed: if snake head touched it's body, it's dead.
            for (int i = 1; i < len; i++) {
                if (snakeX[i] == snakeX[0] && snakeY[i] == snakeY[0]) {
                    isFailed = true;
                    break;
                }
            }

            repaint();
        }

        timer.start();
    }
}
