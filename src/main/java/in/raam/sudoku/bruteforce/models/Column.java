package in.raam.sudoku.bruteforce.models;

import java.util.ArrayList;
import java.util.List;

public class Column {
    public List<Cell> cells = new ArrayList<>();

    public void addCell(Cell cell) {
        this.cells.add(cell);
        cell.column = this;
    }
}