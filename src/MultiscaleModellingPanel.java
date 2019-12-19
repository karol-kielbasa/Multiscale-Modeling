import java.awt.*;
import java.util.List;
import javax.swing.*;

public class MultiscaleModellingPanel extends JPanel implements Runnable {

    public static final int BLOCK_SIZE = 2;

    private Cells cells;

    private Thread simulation = new Thread(this);;

    private Neighbourhood neighbourhood;

    private volatile boolean stop = true;
    private boolean allGrown;

    public MultiscaleModellingPanel() {
    }

    public void init(int x, int y) {
        cells = new Cells(x, y);
        neighbourhood = new MooreNeighbourhood(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCells(g);
    }

    private void drawCells(Graphics g) {
        if (cells == null) {
            return;
        }
        cells.getCells().stream()
            .filter(cell -> cell.isGrowing() || cell.isDead())
            .forEach(cell -> drawCell(cell, g));
    }

    private void drawCell(Cell cell, Graphics g) {
        g.setColor(ColorCache.getColorById(cell.getId()));
        g.fillRect(BLOCK_SIZE + (BLOCK_SIZE * cell.x), BLOCK_SIZE + (BLOCK_SIZE * cell.y), BLOCK_SIZE,
            BLOCK_SIZE);
    }

    @Override
    public void run() {
        while (!stop) {
            List<Cell> currentCells = cells.getCells();
            List<Cell> nextIterationCells = cells.copyCells();

            currentCells.stream()
                .filter(cell -> cell.isGrowing() && !cell.isDead())
                .forEach(cell -> neighbourhood.grow(cell, nextIterationCells));

            currentCells.clear();
            currentCells.addAll(nextIterationCells);
            repaint();
            try {
                checkIfFinished();
                Thread.sleep(100);
            } catch (InterruptedException ignore) {
            }
        }

    }

    private void checkIfFinished() {
        allGrown = cells.getCells().stream().allMatch(cell -> cell.getId() != -1);
        if (allGrown) {
            stopSimulation();
        }
    }

    public List<Cell> getCells() {
        return cells.getCells();
    }

    public void setCells(List<Cell> c) {
        cells.setCells(c);
        repaint();
    }

    public void addRandomCell() {
        cells.addRandomCell();
        repaint();
    }

    public void resetSimulation() {
        stop = true;
        cells.resetCells();
        simulation.interrupt();
    }

    public void startSimulation() {

        simulation.start();
        stop = false;
    }

    public void stopSimulation() {
        stop = true;
        simulation.interrupt();
        System.out.println("Simulation stopped");
    }

    public void addRandomInclusion(String type, int inclusionSize) {
        checkIfFinished();
        if (allGrown) {
            cells.addInclusion(type, inclusionSize);
        } else {
            cells.addRandomInclusion(type, inclusionSize);
        }
        repaint();
    }
}
