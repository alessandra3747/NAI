import java.util.Arrays;

public class Iris {

    private double[] attributes;
    private int decision;

    public Iris(double[] attributes, String decision) {
        this.attributes = attributes;
        if(decision != null)
            this.decision = decision.equals("Iris-setosa") ? 1 : 0;
    }

    public double[] getAttributes() {
        return attributes;
    }

    public int getDecision() {
        return decision;
    }

    @Override
    public String toString() {
        return Arrays.toString(attributes) + " " + decision;
    }
}
