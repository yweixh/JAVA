package PROLANS;

import javax.swing.*;

public class Prolans_Game extends JFrame{
    public Prolans_Game() {
        this.add(new Graphics());
        this.setName("Snake");
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
