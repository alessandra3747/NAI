import java.util.List;

class Perceptron {

    private final String languageName;
    private double[] weights;
    private double threshold;
    private final double learningRate;


    public Perceptron(String languageName, double learningRate) {
        this.languageName = languageName;
        this.learningRate = learningRate;

        initializeWeightsAndThreshold();
    }



    //IF DECISION IS CORRECT RETURNS TRUE ELSE FALSE AND ADJUSTS WEIGHTS AND THRESHOLD
    public boolean learn(List<Double> lettersProportions, int expectedOutput) {

        int output = compute(lettersProportions);

        if(output != expectedOutput) {

            for (int i = 0; i < weights.length; i++)
                weights[i] += learningRate * (expectedOutput - output) * lettersProportions.get(i);

            threshold -= learningRate * (expectedOutput - output);

            return false;
        }

        return true;
    }


    public int compute(List<Double> inputs) {

        if(inputs.size() != weights.length) {
            throw new IllegalArgumentException("Input array size does not match weights array size");
        }

        double sum = 0.0;

        for (int i = 0; i < inputs.size(); i++) {
            sum += inputs.get(i) * weights[i];
        }

        return sum >= threshold ? 1 : 0;
    }



    private void initializeWeightsAndThreshold() {
        this.weights = new double[26];

        for (int i = 0; i < weights.length; i++) {
            weights[i] = Math.random()*10 - 5; //-5..5
        }

        this.threshold = Math.random()*10 - 5; //-5..5
    }


    public String getLanguageName() {
        return languageName;
    }

}