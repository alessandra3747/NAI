import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Classifier {

    private final List<Iris> dataSet;
    private final List<String> classifications;


    public Classifier(List<Iris> dataSet) {

        this.dataSet = dataSet;

        this.classifications = dataSet.stream()
                .map(Iris::getClassification)
                .distinct()
                .toList();

    }


    public String classify (Iris iris) {

        double[] probabilities = classifications.stream()
                .mapToDouble(classification -> this.getProbability(new Iris(iris.getAttributes(), classification)))
                .toArray();

        int maxIndex = 0;
        for (int i = 0; i < classifications.size(); i++)
            if (probabilities[i] > probabilities[maxIndex])
                maxIndex = i;

        return classifications.get(maxIndex);

    }


    //P(C) * P(x1|C) * P(x2|C) * ...
    private double getProbability (Iris iris) {

        //IRISES WITH THE SAME CLASSIFICATION
        List<Iris> matchingIrises = dataSet.stream()
                .filter(i -> i.getClassification().equals(iris.getClassification()))
                .collect(Collectors.toCollection(ArrayList::new));


        List<Double> attributeProbabilities = computeAttributeProbabilities(matchingIrises, iris.getAttributes());

        //System.out.println("\t\tCLASS: " + matchingIrises.get(0).getClassification());

        smoothIfNeeded(matchingIrises.size(), attributeProbabilities);

        double result = (double) matchingIrises.size() / dataSet.size();

        for (double chance : attributeProbabilities)
            result *= chance;

        return result;
    }


    //P(xi|C)
    private List<Double> computeAttributeProbabilities(List<Iris> matchingIrises, double[] attributes) {

        List<Double> attributeProbabilities = new ArrayList<>(attributes.length);

        for (int i = 0; i < attributes.length; i++) {

            double atr = attributes[i];
            int tmpIndex = i;

            double count = matchingIrises.stream()
                    .filter(iris -> iris.getAttributes()[tmpIndex] == atr)
                    .count();

            attributeProbabilities.add(count / matchingIrises.size());

        }

        return attributeProbabilities;
    }



    private void smoothIfNeeded(double valueCount, List<Double> attributeProbabilities) {

        boolean isSmoothed = false;

        for (int i = 0; i < attributeProbabilities.size(); i++) {

            if (attributeProbabilities.get(i) == 0) {

                System.out.printf("Attribute %d: before smoothing = %.6f\n", i, attributeProbabilities.get(i));

                int tmpIndex = i;
                long diffValueCount = dataSet.stream()
                        .map(iris -> iris.getAttributes()[tmpIndex])
                        .distinct()
                        .count();

                double smoothedProbability = 1.0 / (valueCount + diffValueCount);

                attributeProbabilities.set(i, smoothedProbability);

                System.out.printf("Attribute %d: after smoothing = %.6f\n", i, smoothedProbability);

                isSmoothed = true;

            }

        }


        if (!isSmoothed) {

            System.out.printf("Attribute %d: before forced smoothing = %.6f\n", 0, attributeProbabilities.getFirst());

            long diffValueCount = dataSet.stream()
                    .map(iris -> iris.getAttributes()[0])
                    .distinct()
                    .count();

            double smoothedProbability = 1.0 / (valueCount + diffValueCount);

            attributeProbabilities.set(0, smoothedProbability);

            System.out.printf("Attribute %d: after forced smoothing = %.6f\n", 0, attributeProbabilities.getFirst());


        }

    }

    public List<String> getClassifications(){
        return classifications;
    }


}