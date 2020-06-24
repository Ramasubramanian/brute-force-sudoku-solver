package in.raam.sudoku.bruteforce.models;

import java.util.ArrayList;
import java.util.List;

public class Zone {
    public List<Cell> cells = new ArrayList<>();

    public final int index;

    public Zone(int index) {
        this.index = index;
    }

    public void addCell(Cell cell) {
        this.cells.add(cell);
        cell.zone = this;
    }
}