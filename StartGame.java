import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class StartGame extends JFrame {
    private JLabel statusbar;

    public StartGame() {

        initUI();
    }

    private void initUI() {

        statusbar = new JLabel("0", SwingConstants.CENTER);
        add(statusbar, BorderLayout.SOUTH);

        var board = new GameBoard(this);
        add(board);
        board.start();

        setTitle("Tetris");
        setSize(300, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    JLabel getStatusBar() {
        return statusbar;
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var game = new StartGame();
            game.setVisible(true);
        });
    }
}
