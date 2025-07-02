import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    public static List<Iris> loadDataSet(String fileName) {

        List<Iris> dataSet = new ArrayList<>();

        try ( BufferedReader br = new BufferedReader(new FileReader(fileName)) ) {

            String line;
            while((line = br.readLine()) != null) {
                dataSet.add( extractIris(line, "\\t") );
            }

        } catch (IOException e) {
            System.out.println("Error while reading file.");
        }

        return dataSet;
    }


    public static Iris extractIris (String line, String regex) {

        String[] dataParts = line.split(regex);

        double[] attributes;
        String classification = null;

        try {
            //IF THE LAST ELEMENT IS CONDITIONAL ATTRIBUTE
            Double.parseDouble( dataParts[dataParts.length-1].replace(",",".") );
            attributes = new double[dataParts.length];

        } catch (NumberFormatException e) {
            //IF THE LAST ELEMENT IS DECISION ATTRIBUTE
            attributes = new double[dataParts.length-1];
            classification = dataParts[dataParts.length-1].trim();
        }

        for(int i = 0; i < attributes.length; i++) {
            attributes[i] = Double.parseDouble(dataParts[i].replace(",", "."));
        }

        return new Iris(attributes, classification);
    }


}
