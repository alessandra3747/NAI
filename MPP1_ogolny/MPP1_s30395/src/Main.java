import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        //wczytanie danych
        List<Iris> trainingSet = loadDataSet("iris_training.txt");
        List<Iris> testSet = loadDataSet("iris_test.txt");


        //wczytanie parametru k
        Scanner sc = new Scanner(System.in);

        System.out.print("Podaj wartość parametru k: "); 
        int k = sc.nextInt();

        while(k <= 0) {
            System.out.print("Parametr k musi być większy od 0. Podaj poprawną wartość parametru k: ");
            k = sc.nextInt();
        }


        //usuniecie znaku nowej linii
        sc.nextLine();


        //obliczenie poprawnosci algorytmu
        int correctlyClassified = 0;

        for (Iris iris : testSet) {

            String decision = classify(iris, trainingSet, k);
            if (decision.equals(iris.getDecision()))
                correctlyClassified++;

        }

        double accuracy = (double) correctlyClassified / testSet.size() * 100;

        System.out.println("Ilość poprawnie zaklasyfikowanych przykładów: " + correctlyClassified);
        System.out.println("Dokładność algorytmu k-NN: " + accuracy + "%\n");



        //wczytanie dowolnego wektora atrybutow i zaklasyfikowanie go
        System.out.println("Podaj wektory atrybutów w formacie atrybutów oddzielonych pojedynczą spacją.");
        System.out.println("Pusta linia zakończy działanie programu.");

        String inputVector;
        while(!(inputVector = sc.nextLine().trim()).isEmpty()) {
            try {
                System.out.println(classify(extractIris(inputVector, " "), trainingSet, k));
            } catch (Exception e) {
                System.out.println("Niepoprawny format atrybutów");
            }
        }

        sc.close();
    }


    private static String classify(Iris testIris, List<Iris> trainingSet, int k) {

        Map<Iris, Double> neighbours = new HashMap<>();

        for (Iris trainingIris : trainingSet)
            neighbours.put(trainingIris, euclideanDistance(testIris, trainingIris));


        neighbours = neighbours.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(k)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


        Map<String, Integer> neighbourFrequency = new HashMap<>();

        for(Map.Entry<Iris, Double> neighbour : neighbours.entrySet()) {

            String neighbourDecision = neighbour.getKey().getDecision();

            neighbourFrequency.put(neighbourDecision, neighbourFrequency.getOrDefault(neighbourDecision, 1) + 1);

        }
        return Collections.max(neighbourFrequency.entrySet(), Map.Entry.comparingByValue()).getKey();
    }


    private static Double euclideanDistance(Iris iris1, Iris iris2) {
        double distance = 0;

        if(iris1.getAttributes().length != iris2.getAttributes().length){
            throw new IllegalArgumentException("Argumenty mają różne wymiary.");
        }

        double sum = 0;

        for(int i = 0; i < iris1.getAttributes().length; i++) {
            sum += Math.pow(iris1.getAttributes()[i] - iris2.getAttributes()[i], 2);
        }

        distance = Math.sqrt(sum);

        return distance;
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