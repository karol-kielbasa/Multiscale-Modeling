import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ColorCache {

    private static Map<Integer, Color> map = new HashMap<Integer, Color>() {{
        put(-2, Color.BLACK);
        put(-3, Color.MAGENTA);

    }};

    public static void resetCacheMap() {
        map.clear();
        map.put(-2, Color.BLACK);
        map.put(-3, Color.MAGENTA);
    }

    public static void addIdToCacheMap(int id) {
        map.put(id, new Color((int) (Math.random() * 0x1000000)));
    }

    public static Color getColorById(int id) {
        return map.get(id);
    }

}
