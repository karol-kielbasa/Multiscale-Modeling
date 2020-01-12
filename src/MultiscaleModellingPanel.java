import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.*;

public class MultiscaleModellingPanel extends JPanel implements Runnable, MouseListener {

    public static final int BLOCK_SIZE = 2;

    private Cells cells;

    private Thread simulation;

    private Neighbourhood neighbourhood;

    private volatile boolean stop = true;
    private boolean allGrown;

    public MultiscaleModellingPanel() {
        addMouseListener(this);
    }

    public void init(int x, int y, int percentage) {
        cells = new Cells(x, y);
        neighbourhood = new MooreNeighbourhood(x, y, percentage);
        simulation = new Thread(this);
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
        allGrown = false;
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

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getPoint().x / MultiscaleModellingPanel.BLOCK_SIZE - 1;
        int y = e.getPoint().y / MultiscaleModellingPanel.BLOCK_SIZE - 1;
        Cell c = cells.getCellByXAndY(cells.getCells(), x, y);
        cells.saveCellId(c.getId());
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void resetWithSelected(String selected) {
        allGrown = false;
        cells.resetWithSelectedCells(selected);
    }

    public double colorBounaries(int cellsToAdd) {
        return cells.colorBoundaries(cellsToAdd);
    }

    public double colorSelectedBounaries(int cellsToAdd) {
       return cells.colorSelectedBoundaries(cellsToAdd);
    }

    public void clearWithBoundaries() {
        cells.clearWithBoundaries();
    }

    public void clearWithAllBoundaries() {
        cells.clearWithAllBoundaries();
    }
}
