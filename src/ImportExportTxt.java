import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class ImportExportTxt implements ImportExport {

    public static final String format = "txt";

    @Override
    public void exportFile(List<Cell> cells, File file) {
        file = validateFormatWhileExporting(file, format);
        System.out.println("Export started");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            for (Cell cell : cells) {
                String content = cell.toString();
                fileOutputStream.write(content.getBytes());
                fileOutputStream.write(System.getProperty("line.separator").getBytes());
            }
        } catch (IOException e) {
            System.out.println("Error occured durning export");
        }
        System.out.println("Export finished succesfully");
    }

    @Override
    public List<Cell> importFile(File file) {
        file = validateFormatWhileImporting(file, format);
        System.out.println("Import started");
        List<Cell> cells = null;
        try {
            List<String> list = Files.readAllLines(file.toPath());
            cells = list.stream()
                .map(line -> line.split(" "))
                .map(this::initCell)
                .collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println("Error occured durning import");
        }
        System.out.println("Import finished succesfully");
        return cells;
    }

    private Cell initCell(String[] line) {
        Cell cell = new Cell(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
        cell.setId(Integer.parseInt(line[2]));

        cell.setGrowing(Boolean.parseBoolean(line[3]));
        cell.setDead(Boolean.parseBoolean((line[4])));

        initColorCache(cell);
        return cell;
    }

    private void initColorCache(Cell cell) {
        Color color = ColorCache.getColorById(cell.getId());
        if (color == null) {
            ColorCache.addIdToCacheMap(cell.getId());
        }
    }
}
