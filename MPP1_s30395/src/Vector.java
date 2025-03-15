public class Vector<T> {
    private double el1;
    private double el2;
    private double el3;
    private double el4;

    public Vector(double el1, double el2, double el3, double el4) {
        this.el1 = el1;
        this.el2 = el2;
        this.el3 = el3;
        this.el4 = el4;
    }


    public double getEl1() {
        return el1;
    }

    public double getEl2() {
        return el2;
    }

    public double getEl3() {
        return el3;
    }

    public double getEl4() {
        return el4;
    }


    @Override
    public String toString() {
        return el1 + ", " + el2 + ", " + el3 + ", " + el4;
    }
}
