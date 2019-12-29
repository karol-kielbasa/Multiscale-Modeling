import java.util.HashMap;
import java.util.List;

public interface Neighbourhood {

    void grow(Cell cell, List<Cell> nextIterationCells);

    void countNeighboursColorAndChangeCellState(List<Cell> cells, int x, int y);

    default void countColor(HashMap<Integer, Integer> neighboursColors, Cell neighbour) {
        if(neighbour == null || neighbour.getId() == -2 || neighbour.getId() == -3 || neighbour.isDead()) {
            return;
        }
        if (neighbour.getId() != -1) {
            Integer counter = neighboursColors.get(neighbour.getId());
            if (counter == null) {
                neighboursColors.put(neighbour.getId(), 1);
            } else {
                neighboursColors.put(neighbour.getId(), ++counter);
            }
        }
    }
}
