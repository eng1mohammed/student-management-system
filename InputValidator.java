import java.util.Scanner;

public class InputValidator {

    public static int getInt(Scanner input, String message) {
        while (true) {
            try {
                System.out.print(message);
                int value = input.nextInt();
                input.nextLine();
                return value;
            } catch (Exception e) {
                System.out.println("Invalid input!");
                input.nextLine();
            }
        }
    }

    public static double getDouble(Scanner input, String message) {
    while (true) {
        try {
            System.out.print(message);

            String valueStr = input.nextLine()
                    .replace(",", ".")
                    .trim();

            double value = Double.parseDouble(valueStr);
            if (value >= 0 && value <= 100) {
    return value;
} else {
    System.out.println("GPA must be between 0 and 100!");
}
        } catch (Exception e) {
            System.out.println("Invalid input!");
        }
    }
}

    public static String getString(Scanner input, String message) {
        System.out.print(message);
        return input.nextLine();
    }
}