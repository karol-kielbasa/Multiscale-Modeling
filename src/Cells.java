import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Cells {

    private static final int SIZE = 300;
    private static int uniqueIndex = 0;
    private List<Cell> cells;

    private Random generator = new Random();

    public Cells() {
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
        int i = generator.nextInt(300);
        int j = generator.nextInt(300);

        Cell cell = cells.get(i * SIZE + j);
        cell.setId(++uniqueIndex);
        cell.setGrowing(true);
        ColorCache.addIdToCacheMap(cell.getId());

        System.out.println("Cell added at x:" + i + "y:" + j + " with id: " + uniqueIndex);
    }

    private List<Cell> initCells() {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
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
