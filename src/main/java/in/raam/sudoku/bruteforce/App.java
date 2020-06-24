package in.raam.sudoku.bruteforce;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import in.raam.sudoku.bruteforce.models.Board;
import in.raam.sudoku.bruteforce.models.Value;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    private Board loadBoardFromFile() throws Exception {
        var board = new Board();
        var file = new File(getClass().getClassLoader().getResource("input96.sudoku").getFile());
        var lines = Files.readAllLines(Paths.get(file.getPath())).stream()
        .filter(line -> !line.startsWith("-")).collect(Collectors.toList()).toArray(new String[0]);
        for (int rowIndex = 0; rowIndex < board.rows.size(); rowIndex++) {
            var row = board.rows.get(rowIndex);
            var digits = lines[rowIndex].split("[\\s\\|]+");
            for (int colIndex = 0; colIndex < row.cells.size(); colIndex++) {
                var cell = row.cells.get(colIndex);
                cell.value = digits[colIndex].equals("*") ? null : Value.fromStr(digits[colIndex]);
                cell.solved = cell.value != null;
            }
        }
        return board;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button btn1=new Button("Solve");  
        StackPane root=new StackPane();  
        root.setMinSize(300.0, 300.0);
        root.getChildren().add(btn1);
        Scene scene=new Scene(root);  
        primaryStage.setScene(scene);  
        primaryStage.setTitle("Brute Force Sudoku Solver");  
        primaryStage.show();  
        var board = loadBoardFromFile();
        board.printBoardValues();
        new BruteForceSolver().solve(board);
        System.out.println("After solution");
        board.printBoardValues();
    }
}
