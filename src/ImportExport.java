import java.util.List;

public interface ImportExport {

    void exportFile(List<Cell> cells);

    List<Cell> importFile();
}
