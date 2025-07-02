import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    private static int CAPACITY;
    private static int LENGTH;

    public static void main(String[] args) {

        List<Dataset> datasets = loadDatasets("plecak.txt");

        int randomIndex = (int)(Math.random() * datasets.size());
        Dataset dataset = datasets.get(randomIndex);

        System.out.println("Chosen set: " + (randomIndex + 1));
        System.out.println("Capacity of the knapsack: " + CAPACITY);

        Result heuristic = Heuristic.solve(dataset.items, CAPACITY);
        System.out.println("\n--- Heuristic ---");
        heuristic.print();

        Result bruteForce = BruteForce.solve(dataset.items, CAPACITY);
        System.out.println("\n--- Brute Force ---");
        bruteForce.print();

    }


    private static class Dataset {
        protected int index;
        protected Item[] items;

        Dataset(int index, Item[] items) {
            this.index = index;
            this.items = items;
        }
    }


    private static List<Dataset> loadDatasets(String filename) {
        List<Dataset> datasets = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line = br.readLine();

            if (line != null && line.startsWith("length")) {
                String[] parts = line.split(",");
                LENGTH = Integer.parseInt(parts[0].replaceAll("[^0-9]", ""));
                CAPACITY = Integer.parseInt(parts[1].replaceAll("[^0-9]", ""));
            }

            int datasetIndex = 1;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("dataset")) {
                    String sizeLine = br.readLine();
                    String valLine = br.readLine();

                    int[] sizes = parseArray(sizeLine);
                    int[] values = parseArray(valLine);

                    Item[] items = new Item[LENGTH];
                    for (int i = 0; i < LENGTH; i++) {
                        items[i] = new Item(i + 1, sizes[i], values[i]);
                    }

                    datasets.add(new Dataset(datasetIndex++, items));
                }
            }
        } catch (IOException e) {
            System.out.println("Error while loading file: " + e.getMessage());
        }

        return datasets;
    }


    private static int[] parseArray(String line) {
        return Arrays.stream(line.substring(line.indexOf("{") + 1, line.indexOf("}")).split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

}
