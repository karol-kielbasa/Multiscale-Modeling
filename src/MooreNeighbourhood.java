import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MooreNeighbourhood implements Neighbourhood {

    private int xSize;
    private int ySize;

    public MooreNeighbourhood(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
    }

    @Override
    public void grow(Cell cell, List<Cell> cells) {
        int x = cell.x;
        int y = cell.y;

        cells.get(x * ySize + y).setGrown(true);

        if (y == 0 && x == 0) {
            countNeighboursColorAndChangeCellState(cells, x + 1, y);
            countNeighboursColorAndChangeCellState(cells, x + 1, y + 1);
            countNeighboursColorAndChangeCellState(cells, x, y + 1);
        } else if (y == 0 && x == xSize - 1) {
            countNeighboursColorAndChangeCellState(cells, x - 1, y);
            countNeighboursColorAndChangeCellState(cells, x - 1, y + 1);
            countNeighboursColorAndChangeCellState(cells, x, y + 1);
        } else if (y == 0) {
            countNeighboursColorAndChangeCellState(cells, x - 1, y);
            countNeighboursColorAndChangeCellState(cells, x - 1, y + 1);
            countNeighboursColorAndChangeCellState(cells, x, y + 1);
            countNeighboursColorAndChangeCellState(cells, x + 1, y + 1);
            countNeighboursColorAndChangeCellState(cells, x + 1, y);
        } else if (y == ySize - 1 && x != 0 && x != xSize - 1) {
            countNeighboursColorAndChangeCellState(cells, x - 1, y);
            countNeighboursColorAndChangeCellState(cells, x - 1, y - 1);
            countNeighboursColorAndChangeCellState(cells, x, y - 1);
            countNeighboursColorAndChangeCellState(cells, x + 1, y - 1);
            countNeighboursColorAndChangeCellState(cells, x + 1, y);
        } else if (y == ySize - 1 && x == xSize - 1) {
            countNeighboursColorAndChangeCellState(cells, x, y - 1);
            countNeighboursColorAndChangeCellState(cells, x - 1, y);
            countNeighboursColorAndChangeCellState(cells, x - 1, y - 1);
        } else if (y == ySize - 1 && x == 0) {
            countNeighboursColorAndChangeCellState(cells, x + 1, y);
            countNeighboursColorAndChangeCellState(cells, x + 1, y - 1);
            countNeighboursColorAndChangeCellState(cells, x, y - 1);
        } else {
            countNeighboursColorAndChangeCellState(cells, x + 1, y);
            countNeighboursColorAndChangeCellState(cells, x - 1, y);
            countNeighboursColorAndChangeCellState(cells, x, y + 1);
            countNeighboursColorAndChangeCellState(cells, x, y - 1);
            countNeighboursColorAndChangeCellState(cells, x + 1, y + 1);
            countNeighboursColorAndChangeCellState(cells, x - 1, y - 1);
            countNeighboursColorAndChangeCellState(cells, x + 1, y - 1);
            countNeighboursColorAndChangeCellState(cells, x - 1, y + 1);
        }

    }

    @Override
    public void countNeighboursColorAndChangeCellState(List<Cell> cells, int x, int y) {
        HashMap<Integer, Integer> neighboursColors = new HashMap<>();

        Cell cellToGrow = getCellByXAndY(cells, x, y);

        if (cellToGrow == null || cellToGrow.isGrown()) {
            return;
        }

        if (y == 0 && x == 0) {
            Cell neighbour1 = getCellByXAndY(cells, x + 1, y);
            countColor(neighboursColors, neighbour1);

            Cell neighbour3 = getCellByXAndY(cells, x, y + 1);
            countColor(neighboursColors, neighbour3);

            Cell neighbour5 = getCellByXAndY(cells, x + 1, y + 1);
            countColor(neighboursColors, neighbour5);
        } else if (y == 0 && x == xSize - 1) {
            Cell neighbour2 = getCellByXAndY(cells, x - 1, y);
            countColor(neighboursColors, neighbour2);

            Cell neighbour8 = getCellByXAndY(cells, x - 1, y + 1);
            countColor(neighboursColors, neighbour8);

            Cell neighbour3 = getCellByXAndY(cells, x, y + 1);
            countColor(neighboursColors, neighbour3);
        } else if (y == 0) {
            Cell neighbour1 = getCellByXAndY(cells, x - 1, y);
            countColor(neighboursColors, neighbour1);

            Cell neighbour2 = getCellByXAndY(cells, x - 1, y +1);
            countColor(neighboursColors, neighbour2);

            Cell neighbour4 = getCellByXAndY(cells, x, y + 1);
            countColor(neighboursColors, neighbour4);

            Cell neighbour6 = getCellByXAndY(cells, x + 1, y + 1);
            countColor(neighboursColors, neighbour6);

            Cell neighbour7 = getCellByXAndY(cells, x + 1, y);
            countColor(neighboursColors, neighbour7);

        } else if (y == ySize - 1 && x != 0 && x != xSize - 1) {
            Cell neighbour1 = getCellByXAndY(cells, x + 1, y);
            countColor(neighboursColors, neighbour1);

            Cell neighbour2 = getCellByXAndY(cells, x - 1, y);
            countColor(neighboursColors, neighbour2);

            Cell neighbour4 = getCellByXAndY(cells, x, y - 1);
            countColor(neighboursColors, neighbour4);

            Cell neighbour6 = getCellByXAndY(cells, x + 1, y - 1);
            countColor(neighboursColors, neighbour6);

            Cell neighbour7 = getCellByXAndY(cells, x - 1, y - 1);
            countColor(neighboursColors, neighbour7);


        } else if (y == ySize - 1 && x == xSize - 1) {
            Cell neighbour4 = getCellByXAndY(cells, x, y - 1);
            countColor(neighboursColors, neighbour4);

            Cell neighbour2 = getCellByXAndY(cells, x - 1, y);
            countColor(neighboursColors, neighbour2);

            Cell neighbour7 = getCellByXAndY(cells, x - 1, y - 1);
            countColor(neighboursColors, neighbour7);

        } else if (y == ySize - 1 && x == 0) {
            Cell neighbour1 = getCellByXAndY(cells, x + 1, y);
            countColor(neighboursColors, neighbour1);

            Cell neighbour6 = getCellByXAndY(cells, x + 1, y - 1);
            countColor(neighboursColors, neighbour6);

            Cell neighbour4 = getCellByXAndY(cells, x, y - 1);
            countColor(neighboursColors, neighbour4);

        } else {
            Cell neighbour1 = getCellByXAndY(cells, x + 1, y);
            countColor(neighboursColors, neighbour1);

            Cell neighbour2 = getCellByXAndY(cells, x - 1, y);
            countColor(neighboursColors, neighbour2);

            Cell neighbour3 = getCellByXAndY(cells, x, y + 1);
            countColor(neighboursColors, neighbour3);

            Cell neighbour4 = getCellByXAndY(cells, x, y - 1);
            countColor(neighboursColors, neighbour4);

            Cell neighbour5 = getCellByXAndY(cells, x + 1, y + 1);
            countColor(neighboursColors, neighbour5);

            Cell neighbour6 = getCellByXAndY(cells, x + 1, y - 1);
            countColor(neighboursColors, neighbour6);

            Cell neighbour7 = getCellByXAndY(cells, x - 1, y - 1);
            countColor(neighboursColors, neighbour7);

            Cell neighbour8 = getCellByXAndY(cells, x - 1, y + 1);
            countColor(neighboursColors, neighbour8);
        }

        neighboursColors.entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .ifPresent(id -> {
                cellToGrow.setId(id);
                cellToGrow.setGrowing(true);
            });

    }

    public Cell getCellByXAndY(List<Cell> cells, int x, int y) {
        int index = x * ySize + y;
        if (index >= 0 && index < xSize*ySize) {
            Cell cellToGrow = cells.get(index);
            if (cellToGrow != null) {
                return cellToGrow;
            }
        }
        return null;
    }

}
