import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

interface Operation {
    double calculer(double num1, double num2);
}

class Addition implements Operation {
    @Override
    public double calculer(double num1, double num2) {
        return num1 + num2;
    }
}

class Soustraction implements Operation {
    @Override
    public double calculer(double num1, double num2) {
        return num1 - num2;
    }
}

class Multiplication implements Operation {
    @Override
    public double calculer(double num1, double num2) {
        return num1 * num2;
    }
}

public class TraitementFichiers {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java TraitementFichiers <chemin_dossier>");
            return;
        }

        String dossier = args[0];
        processFiles(dossier);
    }

    private static void processFiles(String dossier) {
        try {
            // Liste des fichiers *.op dans le dossier spécifié
            File folder = new File(dossier);
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".op"));

            if (files == null) {
                System.out.println("Dossier spécifié invalide.");
                return;
            }

            for (File file : files) {
                String outputFilename = file.getAbsolutePath().replace(".op", ".res");
                try (BufferedReader br = new BufferedReader(new FileReader(file));
                     BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilename))) {

                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(";");
                        if (parts.length != 3) {
                            bw.write("ERROR\n");
                            continue;
                        }

                        try {
                            double num1 = Double.parseDouble(parts[0]);
                            double num2 = Double.parseDouble(parts[1]);
                            String operator = parts[2];

                            Operation operation = getOperation(operator);
                            if (operation == null) {
                                bw.write("ERROR\n");
                                continue;
                            }

                            double result = operation.calculer(num1, num2);
                            bw.write(result + "\n");
                        } catch (NumberFormatException e) {
                            bw.write("ERROR\n");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Operation getOperation(String operator) {
        switch (operator) {
            case "+":
                return new Addition();
            case "-":
                return new Soustraction();
            case "*":
                return new Multiplication();
            default:
                return null;
        }
    }
}
