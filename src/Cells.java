import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.sun.org.apache.bcel.internal.generic.ARETURN;

public class Cells {

    public static final String CIRCLE = "Circle";
    public static final String SQUARE = "Square";
    public static final String DUAL = "Dual phase";
    public static final String SUB = "Substructure";
    private static int uniqueIndex = 0;
    private List<Integer> savedCells = new ArrayList<>();
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
        c.setDead(cell.isDead());
        c.setId(cell.getId());
        return c;
    }

    public void addRandomCell() {
        int i = generator.nextInt(xSize);
        int j = generator.nextInt(ySize);

        Cell cell = cells.get(i * ySize + j);
        if (cell.getId() != -1) {
            boolean found = false;
            while (!found) {
                i = generator.nextInt(xSize);
                j = generator.nextInt(ySize);
                cell = cells.get(i * ySize + j);
                if (cell.getId() == -1) {
                    found = true;
                }
            }
        }

        if (cell.getId() == -2) {
            System.out.println("Cell not added at x: " + i + " y: " + j + " reason: is inclusion");
            return;
        }
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
        clearSavedCellsId();
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public void addRandomInclusion(String type, int inclusionSize) {
        int x = generator.nextInt(xSize);
        int y = generator.nextInt(ySize);

        Cell cell = cells.get(x * ySize + y);
        if (cell.getId() == -2) {
            return;
        }
        if (inclusionSize == 1) {
            initInclusion(x, y);
            return;
        }

        chooseType(type, inclusionSize, x, y);

    }

    private void chooseType(String type, int inclusionSize, int x, int y) {
        if (Cells.SQUARE.equals(type)) {
            drawSquare(inclusionSize, x, y);
        } else {
            drawCircle(inclusionSize, x, y);
        }
    }

    private void drawCircle(int inclusionSize, int x, int y) {
        int r = inclusionSize;
        while (r > 0) {
            for (double i = 0; i < 360; i = i + 0.2) {
                double x1 = r * Math.cos(i * 3.14 / 180);
                double y1 = r * Math.sin(i * 3.14 / 180);
                initInclusion((int) Math.round(x + x1), (int) Math.round(y + y1));
            }
            r--;
        }
        initInclusion(x, y);
    }

    private void drawSquare(int inclusionSize, int x, int y) {
        for (int i = 0; i < inclusionSize; i++) {
            for (int j = 0; j < inclusionSize; j++) {
                initInclusion(x + i, y + j);
            }
        }
    }

    private Cell initInclusion(int x, int y) {
        Cell cell = getCellByXAndY(cells, x, y);
        if (cell == null || cell.getId() == -2) return null;
        cell.setId(-2);
        cell.setDead(true);
        System.out.println("Inclusion added at x: " + x + " y: " + y);
        return cell;
    }

    public void addInclusion(String type, int inclusionSize) {
        boolean found = false;
        int x = 0;
        int y = 0;
        while (!found) {
            x = generator.nextInt(xSize);
            y = generator.nextInt(ySize);
            Cell cell = getCellByXAndY(cells, x, y);
            Cell neighbourCell = getCellByXAndY(cells, x + 1, y);
            if (neighbourCell == null || cell == null) continue;
            if (neighbourCell.getId() != cell.getId() && !neighbourCell.isDead() && !cell.isDead()) {
                found = true;
            }
        }

        if (inclusionSize == 1) {
            initInclusion(x, y);
            return;
        }

        chooseType(type, inclusionSize, x, y);
    }

    public Cell getCellByXAndY(List<Cell> cells, int x, int y) {
        int index = x * ySize + y;
        if (index >= 0 && index < xSize * ySize) {
            Cell cellToGrow = cells.get(index);
            if (cellToGrow != null) {
                return cellToGrow;
            }
        }
        return null;
    }

    public void saveCellId(Integer id) {
        savedCells.add(id);
    }

    public void clearSavedCellsId() {
        savedCells.clear();
    }

    public void resetWithSelectedCells(String selected) {
        cells.forEach(cell -> {
            if (savedCells.contains(cell.getId())) {
                cell.setDead(true);
                cell.setGrowing(true);
                if (DUAL.equals(selected)) {
                    cell.setId(-3);
                }
            } else {
                cell.reset();
            }
        });

    }

    public double colorBoundaries(int gbSize) {
        cells.forEach(cell -> {
            Cell neighbourCell = getCellByXAndY(cells, cell.x + 1, cell.y);
            if (neighbourCell == null) {
                return;
            }
            if (neighbourCell.getId() != cell.getId() && !neighbourCell.isDead() && !cell.isDead()) {
                drawSquare(gbSize, cell.x, cell.y);
            }
            neighbourCell = getCellByXAndY(cells, cell.x, cell.y - 1);
            if (neighbourCell == null) {
                return;
            }
            if (neighbourCell.getId() != cell.getId() && !neighbourCell.isDead() && !cell.isDead()) {
                if (cell.y != 0) {
                    drawSquare(gbSize, cell.x, cell.y);
                }
            }
        });
        long deadCells = cells.stream().filter(Cell::isDead).count();
        return deadCells / cells.size() * 100;
    }

    public double colorSelectedBoundaries(int gbSize) {
        cells.forEach(cell -> {
            if (savedCells.contains(cell.getId())) {
                Cell neighbourCell = getCellByXAndY(cells, cell.x + 1, cell.y);
                if (neighbourCell == null) {
                    return;
                }
                if (neighbourCell.getId() != cell.getId() && !neighbourCell.isDead() && !cell.isDead()) {
                    drawSquare(gbSize, cell.x, cell.y);
                }
                neighbourCell = getCellByXAndY(cells, cell.x, cell.y - 1);
                if (neighbourCell == null) {
                    return;
                }
                if (neighbourCell.getId() != cell.getId() && !neighbourCell.isDead() && !cell.isDead()) {
                    if (cell.y != 0 && cell.y != ySize - 1) {
                        drawSquare(gbSize, cell.x, cell.y);
                    }
                }

                neighbourCell = getCellByXAndY(cells, cell.x, cell.y + 1);
                if (neighbourCell == null) {
                    return;
                }
                if (neighbourCell.getId() != cell.getId() && !neighbourCell.isDead() && !cell.isDead()) {
                    if (cell.y != 0 && cell.y != ySize - 1) {
                        drawSquare(gbSize, cell.x, cell.y);
                    }
                }
                neighbourCell = getCellByXAndY(cells, cell.x - 1, cell.y);
                if (neighbourCell == null) {
                    return;
                }
                if (neighbourCell.getId() != cell.getId() && !neighbourCell.isDead() && !cell.isDead()) {
                    if (cell.y != 0) {
                        drawSquare(gbSize, cell.x, cell.y);
                    }
                }
            }
        });
        long deadCells = cells.stream().filter(Cell::isDead).count();
        return (double) deadCells / cells.size() * 100;
    }

    public void clearWithBoundaries() {
        cells.forEach(cell -> {
            if (savedCells.contains(cell.getId()) || cell.getId() == -2) {
                if(!cell.isDead()){
                    cell.setId(-4);
                    cell.setDead(true);
                }
            } else {
                cell.reset();
            }
        });
    }

    public void clearWithAllBoundaries() {
        cells.forEach(cell -> {
            if (cell.getId() == -2) {
                if(!cell.isDead()){
                    cell.setId(-4);
                }
            } else {
                cell.reset();
            }
        });
    }
}
