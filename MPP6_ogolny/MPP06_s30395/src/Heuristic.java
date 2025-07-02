import java.util.Arrays;
import java.util.Comparator;

public class Heuristic {

    public static Result solve(Item[] items, int capacity) {

        Item[] sortedItems = Arrays.stream(items)
                .sorted(Comparator.comparingDouble(Item::ratio).reversed())
                .toArray(Item[]::new);

        Result result = new Result();

        long start = System.nanoTime();

        for (Item item : sortedItems) {
            if (result.totalSize + item.getSize() <= capacity) {
                result.add(item);
            }
        }

        long end = System.nanoTime();

        result.execTime = (end - start) / 1_000_000.0;

        return result;
    }

}
