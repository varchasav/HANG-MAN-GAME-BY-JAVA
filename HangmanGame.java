import javax.swing.*;
import java.awt.*;

import java.util.HashSet;

public class HangmanGame extends JFrame {
    private String[] words = {"JAVA", "SWING", "PROGRAM", "DEVELOPER", "HANGMAN"};
    private String selectedWord;
    private HashSet<Character> guessedLetters = new HashSet<>();
    private int wrongGuesses = 0;
    private final int MAX_WRONG = 6;

    private JLabel wordLabel;
    private JLabel messageLabel;
    private JLabel wrongGuessesLabel;
    private JPanel lettersPanel;

    public HangmanGame() {
        selectedWord = words[(int)(Math.random() * words.length)];

        setTitle("Hangman Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        wordLabel = new JLabel(getMaskedWord(), SwingConstants.CENTER);
        wordLabel.setFont(new Font("Monospaced", Font.BOLD, 24));

        messageLabel = new JLabel("Guess a letter!", SwingConstants.CENTER);
        wrongGuessesLabel = new JLabel("Wrong guesses: 0/" + MAX_WRONG, SwingConstants.CENTER);

        lettersPanel = new JPanel(new GridLayout(3, 9));
        for (char c = 'A'; c <= 'Z'; c++) {
            JButton btn = new JButton(String.valueOf(c));
            btn.addActionListener(e -> handleGuess(c, btn));
            lettersPanel.add(btn);
        }

        setLayout(new BorderLayout());
        add(wordLabel, BorderLayout.NORTH);
        add(lettersPanel, BorderLayout.CENTER);
        add(wrongGuessesLabel, BorderLayout.SOUTH);
        add(messageLabel, BorderLayout.PAGE_END);

        setVisible(true);
    }

    private void handleGuess(char letter, JButton btn) {
        btn.setEnabled(false);

        if (selectedWord.indexOf(letter) >= 0) {
            guessedLetters.add(letter);
            wordLabel.setText(getMaskedWord());
            if (isWordGuessed()) {
                messageLabel.setText("You win!");
                disableAllButtons();
            }
        } else {
            wrongGuesses++;
            wrongGuessesLabel.setText("Wrong guesses: " + wrongGuesses + "/" + MAX_WRONG);
            if (wrongGuesses >= MAX_WRONG) {
                wordLabel.setText(selectedWord);
                messageLabel.setText("You lost!");
                disableAllButtons();
            }
        }
    }

    private String getMaskedWord() {
        StringBuilder sb = new StringBuilder();
        for (char c : selectedWord.toCharArray()) {
            if (guessedLetters.contains(c)) {
                sb.append(c).append(" ");
            } else {
                sb.append("_ ");
            }
        }
        return sb.toString().trim();
    }

    private boolean isWordGuessed() {
        for (char c : selectedWord.toCharArray()) {
            if (!guessedLetters.contains(c)) return false;
        }
        return true;
    }

    private void disableAllButtons() {
        for (Component comp : lettersPanel.getComponents()) {
            if (comp instanceof JButton) {
                comp.setEnabled(false);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HangmanGame::new);
    }
}