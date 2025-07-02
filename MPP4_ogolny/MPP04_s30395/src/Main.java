import java.util.*;

public class Main {

    public static void main(String[] args) {


        List<Iris> trainingDataSet = FileService.loadDataSet("iris_training.txt");
        List<Iris> testDataSet = FileService.loadDataSet("iris_test.txt");


        Classifier classifier = new Classifier(trainingDataSet);


        List<String> classifications = classifier.getClassifications();


        int size = classifications.size();
        int[][] confusionMatrix = new int[size][size];

        int correct = 0;

        for (Iris iris : testDataSet) {

            String correctClassification = iris.getClassification();
            String predictedClassification = classifier.classify(iris);

            int correctIndex = classifications.indexOf(correctClassification);
            int predictedIndex = classifications.indexOf(predictedClassification);

            confusionMatrix[correctIndex][predictedIndex]++;

            if (correctClassification.equals(predictedClassification)) {
                correct++;
            } else {
                System.out.print("\u001B[31m");
            }

            System.out.println("------------------------------------------------");
            System.out.println(correctClassification + " classified as: " + predictedClassification);
            System.out.println("------------------------------------------------\n");

            System.out.print("\u001B[0m");
        }


        double accuracy = (double) correct / testDataSet.size();

        System.out.printf("\nAccuracy: %.2f%%\n", accuracy * 100);
        System.out.println("\nConfusion Matrix:");
        System.out.printf("%20s", "");

        for (String label : classifications) {
            System.out.printf("%20s", label);
        }

        System.out.println();

        for (int i = 0; i < size; i++) {
            System.out.printf("%20s", classifications.get(i));

            for (int j = 0; j < size; j++) {
                System.out.printf("%20d", confusionMatrix[i][j]);
            }

            System.out.println();
        }


        //USER INPUT
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\nENTER ATTRIBUTES SEPARATED WITH SPACES.");
        System.out.println("EMPTY LINE ENDS THE PROGRAM.");

        String inputVector;
        while(!(inputVector = sc.nextLine().trim()).isEmpty()) {
            try {
                System.out.println(classifier.classify(FileService.extractIris(inputVector, " ")));
            } catch (Exception e) {
                System.out.println("INPUT FORMAT ERROR");
            }
        }

    }
}