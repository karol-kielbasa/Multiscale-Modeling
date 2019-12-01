import java.awt.*;
import java.util.List;
import javax.swing.*;

public class MultiscaleModellingPanel extends JPanel implements Runnable {

    private static final int BLOCK_SIZE = 2;

    private Cells cells;

    private Thread simulation;

    private Neighbourhood neighbourhood;

    private volatile boolean stop = true;

    public MultiscaleModellingPanel() {
        cells = new Cells();
        neighbourhood = new MooreNeighbourhood();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCells(g);
    }

    private void drawCells(Graphics g) {
        cells.getCells().stream()
            .filter(Cell::isGrowing)
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
                .filter(cell -> cell.isGrowing() && !cell.isGrown())
                .forEach(cell -> neighbourhood.grow(cell, nextIterationCells));

            currentCells.clear();
            currentCells.addAll(nextIterationCells);
            repaint();

            try {
                Thread.sleep(100);
            } catch (InterruptedException ignore) {
            }
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
    }

    public void startSimulation() {
        simulation = new Thread(this);
        simulation.start();
        stop = false;
    }

    public void stopSimulation() {
        stop = true;
        simulation.interrupt();
    }
}
