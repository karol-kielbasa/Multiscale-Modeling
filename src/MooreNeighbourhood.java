import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MooreNeighbourhood implements Neighbourhood {

    private int xSize;
    private int ySize;

    private Random generator = new Random();

    public MooreNeighbourhood(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
    }

    @Override
    public void grow(Cell cell, List<Cell> cells) {
        int x = cell.x;
        int y = cell.y;

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

        if (cellToGrow == null || cellToGrow.getId()!= -1 || cellToGrow.isDead()) {
            return;
        }
        Cell neighbour1 = null;
        Cell neighbour2 = null;
        Cell neighbour3 = null;
        Cell neighbour4 = null;
        Cell neighbour5 = null;
        Cell neighbour6 = null;
        Cell neighbour7 = null;
        Cell neighbour8 = null;

        if (y == 0 && x == 0) {
            neighbour1 = getCellByXAndY(cells, x + 1, y);
            countColor(neighboursColors, neighbour1);

            neighbour3 = getCellByXAndY(cells, x, y + 1);
            countColor(neighboursColors, neighbour3);

            neighbour5 = getCellByXAndY(cells, x + 1, y + 1);
            countColor(neighboursColors, neighbour5);
        } else if (y == 0 && x == xSize - 1) {
            neighbour2 = getCellByXAndY(cells, x - 1, y);
            countColor(neighboursColors, neighbour2);

            neighbour8 = getCellByXAndY(cells, x - 1, y + 1);
            countColor(neighboursColors, neighbour8);

            neighbour3 = getCellByXAndY(cells, x, y + 1);
            countColor(neighboursColors, neighbour3);
        } else if (y == 0) {
            neighbour1 = getCellByXAndY(cells, x - 1, y);
            countColor(neighboursColors, neighbour1);

            neighbour2 = getCellByXAndY(cells, x - 1, y + 1);
            countColor(neighboursColors, neighbour2);

            neighbour4 = getCellByXAndY(cells, x, y + 1);
            countColor(neighboursColors, neighbour4);

            neighbour6 = getCellByXAndY(cells, x + 1, y + 1);
            countColor(neighboursColors, neighbour6);

            neighbour7 = getCellByXAndY(cells, x + 1, y);
            countColor(neighboursColors, neighbour7);

        } else if (y == ySize - 1 && x != 0 && x != xSize - 1) {
            neighbour1 = getCellByXAndY(cells, x + 1, y);
            countColor(neighboursColors, neighbour1);

            neighbour2 = getCellByXAndY(cells, x - 1, y);
            countColor(neighboursColors, neighbour2);

            neighbour4 = getCellByXAndY(cells, x, y - 1);
            countColor(neighboursColors, neighbour4);

            neighbour6 = getCellByXAndY(cells, x + 1, y - 1);
            countColor(neighboursColors, neighbour6);

            neighbour7 = getCellByXAndY(cells, x - 1, y - 1);
            countColor(neighboursColors, neighbour7);

        } else if (y == ySize - 1 && x == xSize - 1) {
            neighbour4 = getCellByXAndY(cells, x, y - 1);
            countColor(neighboursColors, neighbour4);

            neighbour2 = getCellByXAndY(cells, x - 1, y);
            countColor(neighboursColors, neighbour2);

            neighbour7 = getCellByXAndY(cells, x - 1, y - 1);
            countColor(neighboursColors, neighbour7);

        } else if (y == ySize - 1 && x == 0) {
            neighbour1 = getCellByXAndY(cells, x + 1, y);
            countColor(neighboursColors, neighbour1);

            neighbour6 = getCellByXAndY(cells, x + 1, y - 1);
            countColor(neighboursColors, neighbour6);

            neighbour4 = getCellByXAndY(cells, x, y - 1);
            countColor(neighboursColors, neighbour4);

        } else {
            neighbour1 = getCellByXAndY(cells, x + 1, y);
            countColor(neighboursColors, neighbour1);

            neighbour2 = getCellByXAndY(cells, x - 1, y);
            countColor(neighboursColors, neighbour2);

            neighbour3 = getCellByXAndY(cells, x, y + 1);
            countColor(neighboursColors, neighbour3);

            neighbour4 = getCellByXAndY(cells, x, y - 1);
            countColor(neighboursColors, neighbour4);

            neighbour5 = getCellByXAndY(cells, x + 1, y + 1);
            countColor(neighboursColors, neighbour5);

            neighbour6 = getCellByXAndY(cells, x + 1, y - 1);
            countColor(neighboursColors, neighbour6);

            neighbour7 = getCellByXAndY(cells, x - 1, y - 1);
            countColor(neighboursColors, neighbour7);

            neighbour8 = getCellByXAndY(cells, x - 1, y + 1);
            countColor(neighboursColors, neighbour8);
        }

        applyRuleOne(neighboursColors, cellToGrow);
        applyRuleTwo(neighboursColors, cellToGrow, neighbour1, neighbour2, neighbour3, neighbour4);
        applyRuleThree(neighboursColors, cellToGrow, neighbour5, neighbour6, neighbour7, neighbour8);
        applyRuleFour(neighboursColors, cellToGrow);
    }

    private void applyRuleFour(HashMap<Integer, Integer> neighboursColors, Cell cellToGrow) {
        neighboursColors.entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .ifPresent(id -> {
                int rand = generator.nextInt(100);
                if (rand < 10) {
                    cellToGrow.setId(id);
                    cellToGrow.setGrowing(true);
                }
            });
    }

    private void applyRuleThree(HashMap<Integer, Integer> neighboursColors, Cell cellToGrow, Cell neighbour5,
                                Cell neighbour6, Cell neighbour7, Cell neighbour8) {
        neighboursColors.entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .filter(v -> v.getValue() == 3 || v.getValue() == 4)
            .map(Map.Entry::getKey)
            .ifPresent(id -> {
                if (neighbour8 != null && id.equals(neighbour8.getId()) &&
                    neighbour5 != null && id.equals(neighbour5.getId()) &&
                    neighbour6 != null && id.equals(neighbour6.getId())) {
                    cellToGrow.setId(id);
                    cellToGrow.setGrowing(true);
                    return;
                }
                if (neighbour5 != null && id.equals(neighbour5.getId()) &&
                    neighbour6 != null && id.equals(neighbour6.getId()) &&
                    neighbour7 != null && id.equals(neighbour7.getId())) {
                    cellToGrow.setId(id);
                    cellToGrow.setGrowing(true);
                    return;
                }
                if (neighbour6 != null && id.equals(neighbour6.getId()) &&
                    neighbour7 != null && id.equals(neighbour7.getId()) &&
                    neighbour8 != null && id.equals(neighbour8.getId())) {
                    cellToGrow.setId(id);
                    cellToGrow.setGrowing(true);
                    return;
                }
                if (neighbour8 != null && id.equals(neighbour8.getId()) &&
                    neighbour5 != null && id.equals(neighbour5.getId()) &&
                    neighbour7 != null && id.equals(neighbour7.getId())) {
                    cellToGrow.setId(id);
                    cellToGrow.setGrowing(true);
                    return;
                }
            });
    }

    private void applyRuleTwo(HashMap<Integer, Integer> neighboursColors, Cell cellToGrow, Cell neighbour1,
                              Cell neighbour2, Cell neighbour3, Cell neighbour4) {
        neighboursColors.entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .filter(v -> v.getValue() == 3 || v.getValue() == 4)
            .map(Map.Entry::getKey)
            .ifPresent(id -> {
                if (neighbour1 != null && id.equals(neighbour1.getId()) &&
                    neighbour3 != null && id.equals(neighbour3.getId()) &&
                    neighbour4 != null && id.equals(neighbour4.getId())) {
                    cellToGrow.setId(id);
                    cellToGrow.setGrowing(true);
                    return;
                }
                if (neighbour1 != null && id.equals(neighbour1.getId()) &&
                    neighbour3 != null && id.equals(neighbour3.getId()) &&
                    neighbour2 != null && id.equals(neighbour2.getId())) {
                    cellToGrow.setId(id);
                    cellToGrow.setGrowing(true);
                    return;
                }
                if (neighbour2 != null && id.equals(neighbour2.getId()) &&
                    neighbour3 != null && id.equals(neighbour3.getId()) &&
                    neighbour4 != null && id.equals(neighbour4.getId())) {
                    cellToGrow.setId(id);
                    cellToGrow.setGrowing(true);
                    return;
                }
                if (neighbour1 != null && id.equals(neighbour1.getId()) &&
                    neighbour2 != null && id.equals(neighbour2.getId()) &&
                    neighbour4 != null && id.equals(neighbour4.getId())) {
                    cellToGrow.setId(id);
                    cellToGrow.setGrowing(true);
                    return;
                }

            });
    }

    private void applyRuleOne(HashMap<Integer, Integer> neighboursColors, Cell cellToGrow) {
        neighboursColors.entrySet()
            .stream()
            .filter(v -> v.getValue() >= 5)
            .findAny().map(Map.Entry::getKey)
            .ifPresent(id -> {
                cellToGrow.setId(id);
                cellToGrow.setGrowing(true);
            });
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

}
