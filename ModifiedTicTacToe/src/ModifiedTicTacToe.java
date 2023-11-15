import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ModifiedTicTacToe extends Application {

    private static final int SIZE = 5;
    private static final int WINNING_COUNT = 5;

    private char[][] board = new char[SIZE][SIZE];
    private char currentPlayer = 'X';

    @Override
    public void start(Stage primaryStage) {
        initializeBoard();

        GridPane gridPane = createBoard();
        gridPane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(gridPane, 300, 300);

        primaryStage.setTitle("Modified TicTacToe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private GridPane createBoard() {
        GridPane gridPane = new GridPane();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Button btn = createButton(i, j);
                gridPane.add(btn, j, i);
            }
        }

        return gridPane;
    }

    private Button createButton(int row, int col) {
        Button btn = new Button();
        btn.setMinSize(60, 60);
        btn.setOnAction(e -> handleButtonClick(row, col, btn));
        return btn;
    }

    private void handleButtonClick(int row, int col, Button btn) {
        if (board[row][col] == ' ' && !isGameFinished()) {
            board[row][col] = currentPlayer;
            btn.setText(String.valueOf(currentPlayer));
            if (checkForWinner(row, col)) {
                showWinner();
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }
    }

    private boolean checkForWinner(int row, int col) {
        return checkRow(row) || checkColumn(col) || checkDiagonal(row, col);
    }

    private boolean checkRow(int row) {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == currentPlayer) {
                count++;
                if (count == WINNING_COUNT) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    private boolean checkColumn(int col) {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            if (board[i][col] == currentPlayer) {
                count++;
                if (count == WINNING_COUNT) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    private boolean checkDiagonal(int row, int col) {
        int count = 0;

        // Check main diagonal
        for (int i = 0; i < SIZE; i++) {
            if (row + i < SIZE && col + i < SIZE && board[row + i][col + i] == currentPlayer) {
                count++;
                if (count == WINNING_COUNT) {
                    return true;
                }
            } else {
                break;
            }
        }

        // Check anti-diagonal
        count = 0;
        for (int i = 0; i < SIZE; i++) {
            if (row - i >= 0 && col + i < SIZE && board[row - i][col + i] == currentPlayer) {
                count++;
                if (count == WINNING_COUNT) {
                    return true;
                }
            } else {
                break;
            }
        }

        return false;
    }

    private boolean isGameFinished() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private void showWinner() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("Player " + currentPlayer + " wins!");
    
        // Option to start a new game
        alert.setOnCloseRequest(event -> {
            initializeBoard();
            primaryStage.close();
            start(new Stage());
        });
    
        alert.showAndWait();
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}
