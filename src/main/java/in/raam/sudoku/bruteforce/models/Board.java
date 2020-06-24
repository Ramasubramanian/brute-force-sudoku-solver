package in.raam.sudoku.bruteforce.models;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    public List<Row> rows;
    public List<Column> columns;
    public List<Zone> zones;
    public List<Cell> cells;

    public Board() {
        rows = IntStream.range(0, 9).mapToObj(__ -> new Row()).collect(Collectors.toList());
        columns = IntStream.range(0, 9).mapToObj(__ -> new Column()).collect(Collectors.toList());
        zones = IntStream.range(0, 9).mapToObj(index -> new Zone(index)).collect(Collectors.toList());
        cells = IntStream.range(0,81).mapToObj(index -> new Cell(index / 9, index % 9)).collect(Collectors.toList());
        cells.get(0).value = Value._1;
        cells.get(0).solved = true;
        //link cells to rows
        cells.stream().forEach(cell -> rows.get(cell.rowIndex).addCell(cell));
        //link cells to columns
        cells.stream().forEach(cell -> columns.get(cell.columnIndex).addCell(cell));
        //link all zones
        cells.stream().forEach(cell -> assignZones(cell));
    }

    private void assignZones(Cell cell) {
        int row = cell.rowIndex;
        int column = cell.columnIndex;
        int zoneIndex = -1;
        if(row <= 2 && column <= 2) {
            zoneIndex = 0;
        } else if(row <= 2 && column >= 3 && column <= 5) {
            zoneIndex = 1;
        } else if(row <= 2 && column >= 6) {
            zoneIndex = 2;
        } else if(row >= 3 && row <= 5 && column <= 2) {
            zoneIndex = 3;
        } else if(row >= 3 && row <= 5 && column >= 3 && column <= 5) {
            zoneIndex = 4;
        } else if(row >= 3 && row <= 5 && column >= 6) {
            zoneIndex = 5;
        } else if(row >= 6 && column <= 2) {
            zoneIndex = 6;
        } else if(row >= 6 && column >= 3 && column <= 5) {
            zoneIndex = 7;
        } else if(row >= 6 && column >= 6) {
            zoneIndex = 8;
        }
        zones.get(zoneIndex).addCell(cell);
    }

    public void printBoard() {
        rows.stream().forEach(row -> System.out.println(row.cells.stream().map(Cell::toString).collect(Collectors.joining(" | "))));
    }

    public void printBoardPossibilities() {
        rows.stream().forEach(row -> System.out.println(row.cells.stream().map(cell -> cell.toPossibility()).collect(Collectors.joining(" | "))));
    }

    public void printBoardValues() {
        rows.stream().forEach(row -> {
            int rowIndex = row.cells.get(0).rowIndex;
            if(rowIndex > 0 && rowIndex % 3 == 0) {
                System.out.println("------+-------+------");
            }
            System.out.println(row.cells.stream().map(Cell::toValue).collect(Collectors.joining(" "))); 
        });
    }
}