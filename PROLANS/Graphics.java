package PROLANS;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Graphics extends JPanel implements ActionListener {

    static final int WIDTH = 650;
    static final int HEIGHT = 650;
    static final int TICK_SIZE = 30;
    static final int border_size = (WIDTH * HEIGHT) / (TICK_SIZE * TICK_SIZE);

    final Font font = new Font("Arial", Font.BOLD, 30);

    int[] snakePosX = new int[border_size];
    int[] snakePosY = new int[border_size];
    int snakeLength;

    Food food;
    int foodEaten;

    char direction = 'R';
    boolean isMoving = false;
    final Timer timer = new Timer(200, this);

    public Graphics() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (isMoving) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                            if (direction != 'D') {
                                direction = 'A';
                            }
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (direction != 'A') {
                                direction = 'D';
                            }
                            break;
                        case KeyEvent.VK_UP:
                            if (direction != 'S') {
                                direction = 'W';
                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            if (direction != 'W') {
                                direction = 'S';
                            }
                            break;
                    }
                } else {
                    start();
                }
            }
        });

        start();
    }

    protected void start() {
        snakePosX = new int[border_size];
        snakePosY = new int[border_size];
        snakeLength = 5;
        foodEaten = 0;
        direction = 'R';
        isMoving = true;
        spawnFood();
        timer.start();
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        if (isMoving) {
            g.setColor(Color.RED);
            g.fillOval(food.getPosX(), food.getPosY(), TICK_SIZE, TICK_SIZE);

            g.setColor(Color.ORANGE);
            for (int i = 0; i < snakeLength; i++) {
                g.fillRect(snakePosX[i], snakePosY[i], TICK_SIZE, TICK_SIZE);
            }
        } else {
            String playAgain = String.format("Press any key to play again!");
            String scoreText = String.format("Score: %d", foodEaten);
            g.setColor(Color.WHITE);
            g.setFont(font);
            g.drawString(playAgain, (WIDTH - getFontMetrics(g.getFont()).stringWidth(scoreText)) / 4, HEIGHT / 3);
            g.drawString(scoreText, (WIDTH - getFontMetrics(g.getFont()).stringWidth(scoreText)) / 2, HEIGHT / 2);
        }
    }

    protected void move() {
        for (int i = snakeLength; i > 0; i--) {
            snakePosX[i] = snakePosX[i-1];
            snakePosY[i] = snakePosY[i-1];
        }

        switch (direction) {
            /**case 'U' -> snakePosY[0] -= TICK_SIZE;
            case 'D' -> snakePosY[0] += TICK_SIZE;
            case 'L' -> snakePosX[0] -= TICK_SIZE;
            case 'R' -> snakePosX[0] += TICK_SIZE; */
            case 'W':
                snakePosY[0] -= TICK_SIZE;
                break;
            case 'S':
                snakePosY[0] += TICK_SIZE;
                break;
            case 'A':
                snakePosX[0] -= TICK_SIZE;
                break;
            case 'D':
                snakePosX[0] += TICK_SIZE;
                break;
        }
    }

    protected void spawnFood() {
        food = new Food();
    }

    protected void eatFood() {
        if ((snakePosX[0] == food.getPosX()) && (snakePosY[0] == food.getPosY())) {
            snakeLength++;
            foodEaten++;
            spawnFood();
        }
    }

    protected void collisionTests() {
        for (int i = snakeLength; i > 0; i--) {
            if ((snakePosX[0] == snakePosX[i]) && (snakePosY[0] == snakePosY[i])) {
                isMoving = false;
                break;
            }
        }

        if (snakePosX[0] < 0 || snakePosX[0] > WIDTH - TICK_SIZE || snakePosY[0] < 0 || snakePosY[0] > HEIGHT - TICK_SIZE) {
            isMoving = false;
        }

        if (!isMoving) {
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isMoving) {
            move();
            collisionTests();
            eatFood();
        }

        repaint();
    }
}
