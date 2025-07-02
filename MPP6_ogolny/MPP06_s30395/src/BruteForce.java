import java.util.ArrayList;

public class BruteForce {

    public static Result solve(Item[] items, int capacity) {

        int n = items.length;
        Result bestResult = new Result();

        long start = System.nanoTime();

        for (int mask = 0; mask < (1 << n); mask++) {
            
            bestResult.checkedSets++;

            int totalSize = 0;
            int totalValue = 0;

            Result currentResult = new Result();

            for (int i = 0; i < n; i++) {

                if ((mask & (1 << i)) != 0) {
                    totalSize += items[i].getSize();
                    totalValue += items[i].getValue();

                    if (totalSize > capacity)
                        break;

                    currentResult.items.add(items[i]);
                }

            }

            currentResult.totalSize = totalSize;
            currentResult.totalValue = totalValue;

            if (totalSize <= capacity && totalValue > bestResult.totalValue) {
                bestResult.items = new ArrayList<>(currentResult.items);
                bestResult.totalSize = totalSize;
                bestResult.totalValue = totalValue;
            }

        }

        long end = System.nanoTime();
        bestResult.execTime = (end - start) / 1_000_000.0;

        return bestResult;
    }

}
