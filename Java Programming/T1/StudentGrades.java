import java.util.ArrayList;
import java.util.Scanner;

public class StudentGrades {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Double> grades = new ArrayList<>();
        
        System.out.println("Enter student grades. Type -1 to stop:");

        // Input student grades
        while (true) {
            System.out.print("Enter grade: ");
            double grade = scanner.nextDouble();
            if (grade == -1) {
                break;
            }
            grades.add(grade);
        }

        // Check if grades were entered
        if (grades.isEmpty()) {
            System.out.println("No grades were entered.");
        } else {
            // Calculate average, highest, and lowest grades
            double total = 0;
            double highest = grades.get(0);
            double lowest = grades.get(0);

            for (double grade : grades) {
                total += grade;
                if (grade > highest) {
                    highest = grade;
                }
                if (grade < lowest) {
                    lowest = grade;
                }
            }

            double average = total / grades.size();

            // Display results
            System.out.println("Number of students: " + grades.size());
            System.out.printf("Average grade: %.2f%n", average);
            System.out.printf("Highest grade: %.2f%n", highest);
            System.out.printf("Lowest grade: %.2f%n", lowest);
        }

        scanner.close();
    }
}
