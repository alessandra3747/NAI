import javax.swing.*;
import java.awt.*;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class LanguageClassifierGUI extends JFrame {

    private JTextArea textArea;
    private JButton classifyButton;
    private Map<String, Perceptron> perceptrons;


    public LanguageClassifierGUI(Map<String, Perceptron> perceptrons) {

        super("Language Classifier");

        this.perceptrons = perceptrons;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(800, 600));
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());


        textArea = new JTextArea(10, 40);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);


        JScrollPane scrollPane = new JScrollPane(textArea);


        this.add(scrollPane, BorderLayout.CENTER);


        classifyButton = new JButton("Classify Language");

        classifyButton.addActionListener(e -> {

            String inputText = textArea.getText().trim();

            if (!inputText.isEmpty()) {
                File tempFile = createTempFile(inputText);

                if (tempFile != null) {

                    List<Double> input = FileService.getLettersProportions(tempFile);
                    String result = Main.classify(input, perceptrons);

                    JOptionPane.showMessageDialog(LanguageClassifierGUI.this,
                            "Classified Language: " + result,
                            "Result",
                            JOptionPane.INFORMATION_MESSAGE);

                    tempFile.delete();

                }

            }  else {
                JOptionPane.showMessageDialog(LanguageClassifierGUI.this,
                        "Please enter some text.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        this.add(scrollPane, BorderLayout.CENTER);
        this.add(classifyButton, BorderLayout.SOUTH);
        this.pack();

    }

    private File createTempFile(String inputText) {
        try {
            File tempFile = File.createTempFile("user_input", ".txt");
            tempFile.deleteOnExit();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                writer.write(inputText);
            }

            return tempFile;

        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

}
