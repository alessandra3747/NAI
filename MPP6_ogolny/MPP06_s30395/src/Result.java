import java.util.*;

public class Result {

    public List<Item> items = new ArrayList<>();
    public int totalSize = 0;
    public int totalValue = 0;
    public long checkedSets = 0;
    public double execTime = 0;

    public void add(Item item) {
        this.items.add(item);
        this.totalSize += item.getSize();
        this.totalValue += item.getValue();
    }

    public void print() {
        System.out.println("Knapsack items: " + Arrays.toString(this.items.toArray()));
        System.out.println("Value: " + this.totalValue);
        System.out.println("Size: " + this.totalSize);
        System.out.printf("Execution time: %.3f ms\n", this.execTime);

        if (this.checkedSets > 0)
            System.out.println("Amount of checked datasets: " + this.checkedSets);
    }

}
