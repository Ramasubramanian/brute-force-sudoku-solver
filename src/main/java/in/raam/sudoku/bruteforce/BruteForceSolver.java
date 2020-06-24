package in.raam.sudoku.bruteforce;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import in.raam.sudoku.bruteforce.models.*;

public class BruteForceSolver {
    private Set<Value> getExclusions(List<Cell> siblings) {
        return siblings.stream().filter(cell -> cell.solved).map(cell -> cell.value).collect(Collectors.toSet());
    }

    private void clearCellPossibilities(List<Cell> cells, Value value) {
        cells.stream().filter(cell -> !cell.solved).forEach(cell -> {
            cell.possibilities.remove(value);
        });
    }

    public Board solve(Board board) {
        long start = System.currentTimeMillis();
        int iterationCount = 0;
        long unsolvedCellCount = board.cells.stream().filter(cell -> !cell.solved).count();
        do {
            solveInternal(board);
            unsolvedCellCount = board.cells.stream().filter(cell -> !cell.solved).count();
            iterationCount++;
            System.out.println(">>>>>>>>>>>>>>>>>>>");
            board.printBoardPossibilities();
            board.printBoardValues();
        } while(unsolvedCellCount > 0);
        long end = System.currentTimeMillis();
        System.out.println(String.format("time: %s ms, iterations: %s", (end - start), iterationCount));
        return board;
    }

    public Board solveInternal(Board board) {
        board.cells.forEach(cell -> {
            if (cell.solved) {
                return;
            }
            var exclusions = getExclusions(cell.getRowSiblings());
            exclusions.addAll(getExclusions(cell.getColumnSiblings()));
            exclusions.addAll(getExclusions(cell.getZoneSiblings()));
            var possibilities = EnumSet.allOf(Value.class);
            possibilities.removeAll(exclusions);
            if (possibilities.size() == 1) {
                cell.value = possibilities.iterator().next();
                cell.solved = true;
                cell.possibilities = null;
                clearPossibilities(cell);
            } else {
                cell.possibilities = possibilities;
            }
        });
        board.rows.forEach(row -> solveRow(row));
        board.columns.forEach(column -> solveColumn(column));        
        board.zones.forEach(zone -> solveZone(zone));        
        return board;
    }

    private void solveCells(List<Cell> cells) {
        var countMap = cells.stream().filter(cell -> !cell.solved).map(cell -> cell.possibilities)
                .map(set -> new ArrayList<>(set)).flatMap(List::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        var singleOccurrencePossibilities = countMap.entrySet().stream().filter(entry -> entry.getValue() == 1)
                .map(entry -> entry.getKey()).collect(Collectors.toList());
        cells.stream().filter(cell -> !cell.solved).forEach(cell -> {
            cell.possibilities.stream().filter(possibility -> singleOccurrencePossibilities.contains(possibility))
            .findFirst()
            .ifPresent(value -> {
                cell.value = value;
                cell.solved = true;
                cell.possibilities = null;
                clearPossibilities(cell);
            });
        });        
    }

    private void solveRow(Row row) {
        solveCells(row.cells);
    }

    private void solveColumn(Column column) {
        solveCells(column.cells);
    }

    private void solveZone(Zone zone) {
        solveCells(zone.cells);
    }

    private void clearPossibilities(Cell cell) {
        clearCellPossibilities(cell.column.cells, cell.value);
        clearCellPossibilities(cell.row.cells, cell.value);
        clearCellPossibilities(cell.zone.cells, cell.value);
    }
}