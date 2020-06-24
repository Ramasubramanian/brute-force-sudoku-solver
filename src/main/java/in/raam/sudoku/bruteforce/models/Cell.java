package in.raam.sudoku.bruteforce.models;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Cell {
    public boolean solved = false;
    public Value value;
    public EnumSet<Value> possibilities = EnumSet.noneOf(Value.class);
    public Row row;
    public Column column;
    public Zone zone;
    public int rowIndex;
    public int columnIndex;

    public Cell(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s {%s}", rowIndex, columnIndex,
                Optional.ofNullable(zone).map(z -> z.index).orElse(-1), solved ? value.toString() : "x" );
    }

    public String toPossibility() {
        return solved ? value.toString() : possibilities.toString();
    }

    public String toValue() {
        var valueStr = solved ? value.toString() : "*";
        return columnIndex > 0 && columnIndex % 3 == 0 ? "| " + valueStr : valueStr;
    }

    public List<Cell> getRowSiblings() {
        return this.row.cells.stream().filter(c -> c != this).collect(Collectors.toList());
    }

    public List<Cell> getColumnSiblings() {
        return this.column.cells.stream().filter(c -> c != this).collect(Collectors.toList());
    }

    public List<Cell> getZoneSiblings() {
        return this.zone.cells.stream().filter(c -> c != this).collect(Collectors.toList());
    }
}