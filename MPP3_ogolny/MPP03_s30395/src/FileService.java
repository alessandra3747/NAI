import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FileService {

    public static void loadData (File directory, Map<String, Perceptron> perceptrons, Map<String, List<List<Double>>> data) {

        for (File langDir : Objects.requireNonNull(directory.listFiles(File::isDirectory))) {

            String language = langDir.getName();

            perceptrons.put(language, new Perceptron(language, Main.LEARNING_RATE));

            data.put(language, new ArrayList<>());

            for (File file : Objects.requireNonNull(langDir.listFiles((d, name) -> name.endsWith(".txt")))) {
                data.get(language).add(FileService.getLettersProportions(file));
            }

        }
    }


    public static List<Double> getLettersProportions (File file) {

        List<Double> proportions = new ArrayList<>();

        List<Integer> latinLetters = getLatinLetters(file);

        for (int i = 97; i <= 122; i++) {
            double letterFrequency = Collections.frequency(latinLetters, i);
            double letterProportion = letterFrequency / latinLetters.size();

            proportions.add(letterProportion);
        }

        return proportions;
    }


    private static List<Integer> getLatinLetters(File file) {

        List<Integer> latinLetters = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

                line = line.toLowerCase();

                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);

                    if ((int) c >= 97 && (int) c <= 122) {
                        latinLetters.add((int) c);
                    }
                }

            }
        } catch (IOException e) {
            System.out.println("Error while parsing file: " + e.getMessage());
        }

        return latinLetters;
    }

}
