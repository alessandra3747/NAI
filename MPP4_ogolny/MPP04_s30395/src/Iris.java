import java.util.Arrays;

public class Iris {

    private final double[] attributes;
    private final String classification;

    public Iris(double[] attributes, String classification) {
        this.attributes = attributes;
        this.classification = classification;
    }

    public double[] getAttributes() {
        return attributes;
    }

    public String getClassification() {
        return classification;
    }

    @Override
    public String toString() {
        return Arrays.toString(attributes) + " " + classification;
    }
}
