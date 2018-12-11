package aoc2018.day11;

public class Grid {

    private final int serial;
    private final int size;
    private final int[][] grid;

    public Grid(final int serial) {
        this(serial, 300);
    }
    
    public Grid(final int serial,
                final int size) {
        this.serial = serial;
        this.size = size;

        this.grid = makeGrid();
    }

    public int getPowerLevel(int x, int y) {
        return this.grid[y][x];
    }
    
    private int[][] makeGrid() {
        int[][] grid = new int[size+1][size+1];
        for (int y = 1; y <= size; y++) {
            grid[y] = makeGridRow(y);
        }
        return grid;
    }
    private int[] makeGridRow(int y) {
        int[] row = new int[size+1];
        for (int x = 1; x <= size; x++) {
            row[x] = powerLevel(x, y);
        }
        return row;
    }

    private int powerLevel(int x, int y) {
        int rackId = 10 + x;
        return ((rackId * y + this.serial) * rackId / 100) % 10 - 5;
    }  
}
