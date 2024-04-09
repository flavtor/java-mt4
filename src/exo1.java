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

public class Calculateur {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Calculateur <nombre1> <nombre2> <opérateur>");
            return;
        }
        
        try {
            double num1 = Double.parseDouble(args[0]);
            double num2 = Double.parseDouble(args[1]);
            String operator = args[2];
            
            Operation operation = getOperation(operator);
            if (operation == null) {
                System.out.println("Opérateur non valide.");
                return;
            }
            
            double result = operation.calculer(num1, num2);
            System.out.println("Résultat : " + result);
        } catch (NumberFormatException e) {
            System.out.println("Les deux premiers paramètres doivent être des nombres.");
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
