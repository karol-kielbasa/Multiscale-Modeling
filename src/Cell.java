import java.awt.*;

public class Cell extends Point {

    private int id;

    private boolean growing;
    private boolean dead;

    public Cell(int x, int y) {
        super(x, y);
        this.id = -1;
        this.growing = false;
    }

    public void reset(){
        this.id = -1;
        this.growing = false;
        this.dead = false;
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

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + id + " " + growing + " " + dead;
    }
}
