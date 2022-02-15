import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Objects;

public class TicTacToe extends JFrame implements ActionListener {
    final int WINDOW_WIDTH = 400;
    final int WINDOW_HEIGHT = 250;

    Font fontPawn = new Font("Calibri", Font.BOLD, 20);


    private final JMenuItem about, rule, newGame;

    private final JButton[] b = new JButton[9]; // bouton

    private final int[][] array = new int[3][3];
    private int current_players; // 1 => X and 2 => O

    private final int[] scores = new int[2]; //

    JLabel score_label = new JLabel(Arrays.toString(scores));
    JLabel current_player_label = new JLabel();

    ImageIcon iconWinnerGame = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/resources/winner.png")));
    ImageIcon iconLoserGame = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/resources/loser.png")));
    ImageIcon iconGame = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/resources/icon.png")));
    ImageIcon iconConcordia = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/resources/concordia.png")));
    ImageIcon iconError = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/resources/error.png")));


    /**
     * Constructor
     * Reset GUI windows with menu, board, and other items
     */
    public TicTacToe(){
        // Windows
        JFrame jf =new JFrame();
        jf.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        jf.setBackground(Color.LIGHT_GRAY);
        jf.setResizable(false); // not resizable
        jf.setLocationRelativeTo(null); // window centering
        jf.setTitle("TicTacToe");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // when the window closes, the program closes


        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());


        // MENU
        JMenuBar menu = new JMenuBar();

        JMenu newMenu = new JMenu("New");
        newGame = new JMenuItem("New Game");
        newGame.addActionListener(this);
        newMenu.add(newGame);
        menu.add(newMenu);

        JMenu others = new JMenu("Others");
        about = new JMenuItem("About");
        about.addActionListener(this);
        others.add(about);
        rule = new JMenuItem("Rule of the game");
        rule.addActionListener(this);
        others.add(rule);

        menu.add(others);
        setJMenuBar(menu);



        // Board Game
        JPanel plateau = new JPanel();
        plateau.setLayout(new GridLayout(3, 3));
        plateau.setSize(new Dimension());

        for(int i = 0; i < b.length; i++){
            b[i] = new JButton("");
            b[i].setPreferredSize(new Dimension(50,50));

            plateau.add(b[i]);
            b[i].addActionListener(this);
        }
        content.add(plateau);
        content.add(score_label);
        content.add(current_player_label);


        jf.add(content, BorderLayout.CENTER);
        jf.add(menu, BorderLayout.NORTH);

        jf.setVisible(true);

        newGame();
    }

    /**
     * Reverse current player and change display
     */
    private void toggleCurrent_players() {
        if (current_players == 1) {
            current_players = 2;
            current_player_label.setText("It's the turn of O");
        } else if (current_players == 2) {
            current_players = 1;
            current_player_label.setText("It's the turn of X");
        }

    }


    /**
     * Initialize game start settings
     */
    public void newGame(){
        // Reset scores
        Arrays.fill(scores, 0);

        // Player X always starts the game
        current_players = 1;
        current_player_label.setText("It's the turn of X");

        newPart();
    }

    /**
     * Initialize part start settings
     */
    private void newPart(){
        score_label.setText("The score is " + scores[0] + " (X) to " + scores[1] + " (O)");

        // Reset array
        for (int[] line : array)
            Arrays.fill(line, 0);

        // Reset buttons
        for(int i = 0; i < 9; i++){
            b[i].setText(null);
        }
    }


    /**
     * Checks if the game is over because a player has won
     * @return true if a player has won and false otherwise
     */
    public boolean hasWinner(){
        int[] players = {1, 2};

        for(int player : players) {

            // Check horizontal
            for (int[] line : array) {
                if ((line[0] == player) && (line[1] == player) && (line[2] == player)) {
                    return true;
                }
            }

            // Check vertical
            for (int column = 0; column < 3; column++) {
                if ((array[0][column] == player) && (array[1][column] == player) && (array[2][column] == player)) {
                    return true;
                }
            }

            // Check diagonal
            if ((array[0][0] == player) && (array[1][1] == player) && (array[2][2] == player)) {
                return true;
            } else if ((array[0][2] == player) && (array[1][1] == player) && (array[2][0] == player)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the game is over because the game board is full
     * @return true if the game board is full and false otherwise
     */
    public boolean isComplete(){
        for (int[] line : array) {
            for (int element : line) {
                if (element == 0) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    /**
     * Code to be executed when button is pressed goes here.
     */
    public void actionPerformed(ActionEvent e){

        // getSource - returns a reference to the object that generated this event.
        if(e.getSource() == newGame) {
            if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit the game \nand start a game against a friend?", "Leave the game ?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, iconGame) == JOptionPane.YES_OPTION)
                newGame();
        }
        if(e.getSource() == about) {
            String about_message = """
                    This is an L3 student java project from Concordia University.\s
                    © All rights reserved Pierrick Delrieu""";
            JOptionPane.showMessageDialog(null,
                    about_message, "About", JOptionPane.INFORMATION_MESSAGE, iconConcordia);
        }
        if(e.getSource() == rule) {
            String rules_message = """
                    Tic Tac Toe is a game of reflection practiced by two players on a\s
                    turn-by-turn basis and whose goal is to be the first to create an alignment\s
                    of three pawns vertically, horizontally or diagonally.\s
                                                
                    It's always X that starts.""";
            JOptionPane.showMessageDialog(null,
                    rules_message,
                    "Rules of TicTacToe", JOptionPane.INFORMATION_MESSAGE, iconGame);
        }




        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {

                // if a player presses a button (a box)
                if (e.getSource() == b[(3*i)+j]) {
                    if (array[i][j] == 0) {

                        // Filling the box
                        array[i][j] = current_players;

                        if (current_players == 1) {
                            b[(3 * i) + j].setText("X");
                            b[(3 * i) + j].setFont(fontPawn);
                            b[(3 * i) + j].setForeground(Color.decode("#0095B7"));

                        } else if (current_players == 2) {
                            b[(3 * i) + j].setText("O");
                            b[(3 * i) + j].setFont(fontPawn);
                            b[(3 * i) + j].setForeground(Color.decode("#DD6E00"));
                        }


                        // If the game is over
                        if (isComplete() || hasWinner()) {
                            if (hasWinner()) {
                                scores[current_players-1]++;
                                score_label.setText(Arrays.toString(scores));

                                if (current_players == 1)
                                    JOptionPane.showMessageDialog(null, "The winner is player X", "Winner !", JOptionPane.INFORMATION_MESSAGE, iconWinnerGame);
                                else
                                    JOptionPane.showMessageDialog(null, "The winner is player O", "Winner !", JOptionPane.INFORMATION_MESSAGE, iconWinnerGame);
                            } else {
                                JOptionPane.showMessageDialog(null, "2 losers !", "DRAW", JOptionPane.INFORMATION_MESSAGE, iconLoserGame);
                            }

                            toggleCurrent_players();

                            String winnerSoFar = "";
                            if (scores[0] > scores[1])
                                winnerSoFar = "For the moment it is X who wins";
                            else if (scores[0] < scores[1])
                                winnerSoFar = "For the moment it is O who wins";
                            else
                                winnerSoFar = "Tie, no winner yet";

                            String[] options = new String[] {"New Game", "New Part", "Quit"};
                            int response = JOptionPane.showOptionDialog(null,
                                    "The score is " + scores[0] + " (X) to " + scores[1] + " (O)" +
                                            "\n" + winnerSoFar,
                                    "What do you want to do ?",
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                    iconGame, options, null);

                            if (response == 0)
                                newGame();
                            else if (response == 1) {
                                newPart();
//                                toggleCurrent_players();
                            }
                            else
                                System.exit(0);

                        } else {
                            toggleCurrent_players();
                        }
                    } else
                        JOptionPane.showMessageDialog(null, "This case is already complete.\nChoose an other case!", "Erreur", JOptionPane.INFORMATION_MESSAGE, iconError);
                }

            }
        }
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TicTacToe();
            }
        });

    }
}