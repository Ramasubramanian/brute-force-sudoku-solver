package in.raam.sudoku.bruteforce.models;

public enum Value {
    _1, _2, _3, _4, _5, _6, _7, _8, _9;

    @Override
    public String toString() {
        return super.toString().replaceAll("\\_", "");
    }

    public static Value fromStr(String s) {
        return Value.valueOf("_" + s);
    }
}