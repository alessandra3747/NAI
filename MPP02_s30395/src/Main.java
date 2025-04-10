import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        //Wczytanie danych
        List<Iris> trainingSet = loadDataSet("iris_training.txt");
        List<Iris> testSet = loadDataSet("iris_test.txt");

        //Stworzenie perceptronu, nauka i testowanie go
        Perceptron perceptron = new Perceptron(0.1, 10);

        perceptron.learn(trainingSet);

        int correct = 0;
        for (Iris iris : testSet) {
            if (perceptron.compute(iris.getAttributes()) == iris.getDecision()) {
                correct++;
            }
        }

        System.out.println("Poprawnie zaklasyfikowane: " + correct + " / " + testSet.size());
        System.out.println("Dokładność: " + (100.0 * correct / testSet.size()) + "%");

        perceptron.printWeights();


        //Wczytanie dowolnego wektora atrybutow i zaklasyfikowanie go
        System.out.println("\n\nPodaj wektory atrybutów w formacie atrybutów oddzielonych pojedynczą spacją.");
        System.out.println("Pusta linia zakończy działanie programu.");

        Scanner sc = new Scanner(System.in);

        String inputVector;
        while(!(inputVector = sc.nextLine().trim()).isEmpty()) {
            try {
                if(perceptron.compute(extractIris(inputVector, " ").getAttributes()) == 0)
                    System.out.println("To nie jest iris-setosa");
                else
                    System.out.println("To jest iris-setosa");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        sc.close();

    }


    private static List<Iris> loadDataSet(String fileName) {

        List<Iris> resultData = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;
            while((line = br.readLine()) != null) {
                resultData.add(extractIris(line, "\\t"));
            }

        } catch (IOException e) {
            System.out.println("Błąd wczytując dane.");
        }

        return resultData;
    }

    public static Iris extractIris (String line, String regex) {

        String[] dataParts = line.split(regex);

        double[] attributes;
        String decision = null;

        try{
            //jesli ostatni element to atrybut warunkowy
            Double.parseDouble( dataParts[dataParts.length-1].replace(",",".") );
            attributes = new double[dataParts.length];

        } catch(NumberFormatException e) {
            //jesli ostatni element to atrybut decyzyjny
            attributes = new double[dataParts.length-1];
            decision = dataParts[dataParts.length-1].trim();
        }

        for(int i = 0; i < attributes.length; i++) {
            attributes[i] = Double.parseDouble(dataParts[i].replace(",", "."));
        }

        return new Iris(attributes, decision);
    }
}