import java.awt.*;

public class Cell extends Point {

    private int id;

    private boolean growing;
    private boolean grown;

    public Cell(int x, int y) {
        super(x, y);
        this.id = -1;
        this.growing = false;
        this.grown = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isGrowing() {
        return growing;
    }

    public void setGrowing(boolean growing) {
        this.growing = growing;
    }

    public boolean isGrown() {
        return grown;
    }

    public void setGrown(boolean grown) {
        this.grown = grown;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + id + " " + growing + " " + grown;
    }
}
