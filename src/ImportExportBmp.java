import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class ImportExportBmp implements ImportExport {

    public static final String format = "bmp";

    @Override
    public void exportFile(List<Cell> cells, File file) {
        file = validateFormatWhileExporting(file, format);
        System.out.println("Export started");
        BufferedImage bi = new BufferedImage(MultiscaleModellingGui.WIDTH - 6, MultiscaleModellingGui.HEIGHT - 50,
            BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        cells.forEach(cell -> {
            g.setColor(ColorCache.getColorById(cell.getId()));
            g.fillRect(MultiscaleModellingPanel.BLOCK_SIZE + (MultiscaleModellingPanel.BLOCK_SIZE * cell.x),
                MultiscaleModellingPanel.BLOCK_SIZE + (MultiscaleModellingPanel.BLOCK_SIZE * cell.y),
                MultiscaleModellingPanel.BLOCK_SIZE, MultiscaleModellingPanel.BLOCK_SIZE);
        });
        g.dispose();
        try {
            ImageIO.write(bi, "BMP", file);
        } catch (Exception e) {
            System.out.println("Error during exporting to bmp file");
        }
        System.out.println("Export finished successfully");
    }

    @Override
    public List<Cell> importFile(File file) {
        file = validateFormatWhileImporting(file, format);
        System.out.println("Import started");
        BufferedImage bi;
        List<Cell> cells = null;
        try {
            bi = ImageIO.read(file);
            cells = initCellsFromBmpImage(bi);
        } catch (Exception e) {
            System.out.println("Error occured durning import");
        }
        System.out.println("Import finished succesfully");
        return cells;
    }

    private List<Cell> initCellsFromBmpImage(BufferedImage bi) {
        List<Cell> cells = new ArrayList<>();
        int x = 0, y = 0;
        for (int i = MultiscaleModellingPanel.BLOCK_SIZE; i < bi.getHeight();
             i = i + MultiscaleModellingPanel.BLOCK_SIZE) {
            for (int j = MultiscaleModellingPanel.BLOCK_SIZE; j < bi.getWidth();
                 j = j + MultiscaleModellingPanel.BLOCK_SIZE) {
                Cell cell = initCell(bi, x, y, i, j);
                initColorCache(cell);
                cells.add(cell);
                x++;
            }
            x = 0;
            y++;
        }
        return cells;
    }

    private Cell initCell(BufferedImage bi, int x, int y, int i, int j) {
        Cell cell = new Cell(y, x);
        cell.setId(bi.getRGB(i, j));
        cell.setGrown(true);
        cell.setGrowing(true);
        return cell;
    }

    private void initColorCache(Cell cell) {
        Color color = ColorCache.getColorById(cell.getId());
        if (color == null) {
            ColorCache.addIdToCacheMap(cell.getId());
        }
    }

}
