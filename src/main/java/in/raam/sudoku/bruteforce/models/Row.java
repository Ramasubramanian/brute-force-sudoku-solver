package in.raam.sudoku.bruteforce.models;

import java.util.ArrayList;
import java.util.List;

public class Row {
    public List<Cell> cells = new ArrayList<>();

    public void addCell(Cell cell) {
        cells.add(cell);
        cell.row = this;
    }
}