import java.io.File;
import java.util.List;

public interface ImportExport {

    void exportFile(List<Cell> cells, File file);

    List<Cell> importFile(File file);

    default File validateFormatWhileExporting(File file, String format){
        String filename = file.toString();
        if (!filename.endsWith("." + format)){
            file = new File(file.toString() + "." + format);
        }
        return file;
    }

    default File validateFormatWhileImporting(File file, String format){
        String filename = file.toString();
        if (!filename.endsWith("." + format)){
            throw new RuntimeException("Wrong format");
        }
        return file;
    }
}
