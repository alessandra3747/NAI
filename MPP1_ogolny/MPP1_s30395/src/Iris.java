import java.util.Arrays;

public class Iris {

    private double[] attributes;
    private String decision;

    public Iris(double[] attributes, String decision) {
        this.attributes = attributes;
        this.decision = decision;
    }

    public double[] getAttributes() {
        return attributes;
    }

    public String getDecision() {
        return decision;
    }

    @Override
    public String toString() {
        return Arrays.toString(attributes) + " " + decision;
    }
}
