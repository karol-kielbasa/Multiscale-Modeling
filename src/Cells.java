import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Cells {

    private static int uniqueIndex = 0;
    private List<Cell> cells;

    private int xSize;
    private int ySize;

    private Random generator = new Random();

    public Cells(int x, int y) {
        this.xSize = x;
        this.ySize = y;
        this.cells = initCells();

    }

    public List<Cell> copyCells() {
        return cells.stream()
            .map(this::initCopy)
            .collect(Collectors.toList());
    }

    private Cell initCopy(Cell cell) {
        Cell c = new Cell(cell.x, cell.y);
        c.setGrowing(cell.isGrowing());
        c.setGrown(cell.isGrown());
        c.setId(cell.getId());
        return c;
    }

    public void addRandomCell() {
        int i = generator.nextInt(xSize);
        int j = generator.nextInt(ySize);

        Cell cell = cells.get(i * ySize  + j);
        cell.setId(++uniqueIndex);
        cell.setGrowing(true);
        ColorCache.addIdToCacheMap(cell.getId());

        System.out.println("Cell added at x: " + i + " y: " + j + " with id: " + uniqueIndex);
    }

    private List<Cell> initCells() {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                cells.add(new Cell(i, j));
            }
        }
        return cells;
    }

    public void resetCells() {
        ColorCache.resetCacheMap();
        cells.clear();
        uniqueIndex = 0;
        cells = initCells();
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }
}
