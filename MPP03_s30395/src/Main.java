import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Main {

    public static final double LEARNING_RATE = 0.1;


    public static void main(String[] args) {

        File trainingDataDir = new File("./TrainingData");
        File testingDataDir = new File("./TestingData");


        //PERCEPTRONS AND THEIR LANGUAGES
        Map<String, Perceptron> perceptrons = new HashMap<>();

        //TRAINING DATA FOR EACH LANGUAGE
        Map<String, List<List<Double>>> trainingData = new HashMap<>();

        //LOADING DATA
        FileService.loadData(trainingDataDir, perceptrons, trainingData);

        //TRAINING PERCEPTRONS
        trainPerceptrons(trainingData, perceptrons);

        //TESTING PERCEPTRONS
        testPerceptrons(testingDataDir, perceptrons);

        SwingUtilities.invokeLater(() -> new LanguageClassifierGUI(perceptrons));
    }

    private static void trainPerceptrons(Map<String, List<List<Double>>> trainingData, Map<String, Perceptron> perceptrons) {

        boolean allCorrect;

        do {

            allCorrect = true;

            //FOR EACH PERCEPTRON WHOLE TRAINING DATA
            for (Perceptron perceptron : perceptrons.values()) {

                for (Map.Entry<String, List<List<Double>>> languageAndLettersProportions : trainingData.entrySet()) {

                    String language = languageAndLettersProportions.getKey();

                    for (List<Double> lettersProportions : languageAndLettersProportions.getValue()) {

                        int expectedOutput = perceptron.getLanguageName().equals(language) ? 1 : 0;

                        boolean isOutputCorrect = perceptron.learn(lettersProportions, expectedOutput);

                        if(!isOutputCorrect)
                            allCorrect = false;
                    }

                }

            }

        } while (!allCorrect);

    }


    private static void testPerceptrons(File testingDataDir, Map<String, Perceptron> perceptrons) {

        Map<String, String> correctAnswers = loadAnswerFile();

        int correctlyClassified = 0;
        int fileCounter = 0;

        for (File file : Objects.requireNonNull(testingDataDir.listFiles((d, name) -> name.endsWith(".txt")))) {

            List<Double> input = FileService.getLettersProportions(file);

            if(correctAnswers.get(file.getName()).equals(classify(input, perceptrons)))
                correctlyClassified++;

            fileCounter++;

        }

        double accuracy = (double) correctlyClassified / fileCounter * 100;

        System.out.println("Accuracy: " + accuracy + "%");

    }


    public static String classify(List<Double> input, Map<String, Perceptron> perceptrons) {

        String result = "Unrecognized language";

        //WHICH PERCEPTRON IS ACTIVE
        for (Map.Entry<String, Perceptron> languageAndPerceptron : perceptrons.entrySet()) {

            //RESULT IS THE LAST ACTIVATED PERCEPTRON OR PRIOR MESSAGE
            if(languageAndPerceptron.getValue().compute(input) == 1){
                result = languageAndPerceptron.getKey();
            }

        }

        return result;
    }



    private static Map<String, String> loadAnswerFile() {

        Map<String, String> correctAnswers = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("testingAnswers.txt"))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split("\t");

                String fileName = parts[0].trim();
                String answer = parts[1].trim();

                correctAnswers.put(fileName, answer);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return correctAnswers;
    }

}