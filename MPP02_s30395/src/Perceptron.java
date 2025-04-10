
import java.util.List;

class Perceptron {

    private double[] weights;
    private double threshold;
    private final double learningRate;
    private final int maxEpochs;


    public Perceptron(double learningRate, int maxEpochs) {
        this.learningRate = learningRate;
        this.maxEpochs = maxEpochs;
    }


    public void learn(List<Iris> trainingSet) {

        //zależnie od ilości atrybutów wektor wag ma x składowych
        initializeWeightsAndThreshold(trainingSet.get(0).getAttributes().length);

        int output;
        int decision;

        for(int epoch = 0; epoch < maxEpochs; epoch++) {
            for (Iris iris : trainingSet) {
                double[] inputs = iris.getAttributes();
                decision = iris.getDecision();
                output = compute(inputs);

                if(output != decision) {
                    for (int i = 0; i < weights.length; i++) {
                        weights[i] += learningRate * (decision - output) * inputs[i];
                    }
                    threshold -= learningRate * (decision - output);
                }
            }

        }

    }

    public int compute(double[] inputs) {
        if(inputs.length != weights.length) {
            throw new IllegalArgumentException("Input array size does not match weights array size");
        }
        double sum = 0.0;
        for (int i = 0; i < inputs.length; i++) {
            sum += inputs[i] * weights[i];
        }

        return (sum >= threshold) ? 1 : 0;
    }


    public void printWeights() {
        System.out.print("Wagi po przetworzeniu: ");
        for (double weight : weights) {
            System.out.print(weight + " ");
        }
        System.out.println("\nPróg po przetworzeniu: " + threshold);
    }

    private void initializeWeightsAndThreshold(int n) {
        this.weights = new double[n];

        for (int i = 0; i < weights.length; i++) {
            weights[i] = Math.random()*10 - 5; //-5..5
        }

        this.threshold = Math.random()*10 - 5; //-5..5
    }

}
