
 class Item {

    private int index;
    private int size;
    private int value;

    public Item(int index, int size, int value) {
        this.index = index;
        this.size = size;
        this.value = value;
    }

    public double ratio() {
        return (double) this.value / this.size;
    }

    public int getIndex() {
        return this.index;
    }

    public int getSize() {
        return this.size;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "[" + this.index + ", " + this.size + ", " + this.value + "]";
    }

}
