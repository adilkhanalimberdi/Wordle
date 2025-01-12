package com.example.wordle;

import java.net.URL;
import java.util.*;

import com.example.wordle.words.FiveLetterWord;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backspace, restart;

    @FXML
    private GridPane fieldGrid, firstRowKeyboard, secondRowKeyboard, thirdRowKeyboard;


    private int pointer = -1, m, border = 0;

    private final int n = 6;

    private boolean gameOver = false;

    private String[][] mat;

    private String answer = "PEACE";

    private final ArrayList<Button> field = new ArrayList<>(), keyboard = new ArrayList<>();

    private final Set<Integer> seen = new HashSet<>();

    private final Set<String> letters = new HashSet<>();


    @FXML
    void keyboardBackspace(ActionEvent event) {
        if (pointer < 0 || gameOver || pointer < border) return;
        field.get(pointer--).setText("");
    }

    @FXML
    void keyboardClicked(ActionEvent event) {
        if (pointer < -1 || pointer > field.size() - 2 || gameOver) return;

        pointer++;
        Button button = (Button) event.getSource();
        field.get(pointer).setText(button.getText());

        update();
    }

    @FXML
    void restartClicked() {
        answer = FiveLetterWord.randomWord();
        keyboardStyleInit();
        fieldStyleInit();
        pointer = -1;
        gameOver = false;
        seen.clear();
        restart.setVisible(false);
        border = 0;
    }

    void update() {
        mat = new String[n][m];
        for (int i = 0; i < field.size(); i++) {
            mat[i / m][i % m] = field.get(i).getText();
        }
        for (int i = 0; i < n; i++) {
            if (seen.contains(i))
                continue;

            int empty = 0;
            for (int j = 0; j < m; j++)
                if (mat[i][j].isEmpty())
                    empty++;
            if (empty != 0) break;

            color(i);
            check(i);
            seen.add(i);
        }
    }

    void color(int index) {
        border = (index + 1) * 5;
        for (int i = 0; i < m; i++) {
            String temp = Character.toString(answer.charAt(i));
            Button currentButton = field.get(index * 5 + i);
            if (mat[index][i].equals(temp))
                currentButton.setStyle("-fx-background-color: green;");
            else if (letters.contains(mat[index][i])) {
                currentButton.setStyle("-fx-background-color: yellow;");
            } else {
                currentButton.setStyle("-fx-background-color: grey;");
                disableButton(currentButton.getText());
            }
        }
    }

    void check(int index) {
        boolean isGameOver = true;
        for (Button btn : field) {
            if (btn.getText().isEmpty())
                isGameOver = false;
        }

        boolean win = true;
        for (int i = 0; i < m; i++) {
            if (!Character.toString(answer.charAt(i)).equals(mat[index][i]))
                win = false;
        }

        if (!win && !isGameOver)
            return;

        gameOver = true;
        Alert alert;
        if (win) {
            alert = new Alert(Alert.AlertType.INFORMATION, "You guessed the word!", ButtonType.OK);
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION, "You lose!", ButtonType.OK);
        }
        alert.showAndWait();
        restart.setVisible(true);
    }

    void disableButton(String s) {
        for (Button btn : keyboard) {
            if (btn.getText().equals(s)) {
                btn.setStyle("-fx-background-color: grey");
                btn.setDisable(true);
                btn.setOpacity(100);
            }
        }
    }

    void fieldStyleInit() {
        for (Button btn : field) {
            btn.setText("");
            btn.setStyle("-fx-background-color: white; -fx-border-color: grey; -fx-text-fill: black; -fx-border-radius: 5px; -fx-border-width: 0.5px;");
        }
    }

    void keyboardStyleInit() {
        for (Button btn : keyboard) {
            btn.setDisable(false);
            btn.setStyle("-fx-background-color: #c9c9c9; -fx-border-radius: 5px;");
        }
    }

    @FXML
    void initialize() {
        answer = FiveLetterWord.randomWord();
        backspace.setStyle("-fx-background-color: #c9c9c9; -fx-border-radius: 5px;");
        restart.setStyle("-fx-background-color: #c9c9c9; -fx-border-radius: 5px;");

        for (Node i : fieldGrid.getChildren()) {
            Button btn = (Button) i;
            field.add(btn);
        }
        fieldStyleInit();

        for (Node i : firstRowKeyboard.getChildren()) {
            Button btn = (Button) i;
            keyboard.add(btn);
        }

        for (Node i : secondRowKeyboard.getChildren()) {
            Button btn = (Button) i;
            keyboard.add(btn);
        }

        for (Node i : thirdRowKeyboard.getChildren()) {
            Button btn = (Button) i;
            keyboard.add(btn);
        }
        keyboardStyleInit();

        m = field.size() / 6;

        for (char i : answer.toCharArray())
            letters.add(Character.toString(i));
    }

}
