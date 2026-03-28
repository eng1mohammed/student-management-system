import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        StudentManager manager = new StudentManager();

        // تجهيز ملف البيانات
        File file = new File("students.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("File created ✔");
            } catch (IOException e) {
                System.out.println("Error creating file!");
            }
        } else {
            System.out.println("File already exists ✔");
        }

        System.out.println("Data file ready ✔");

        // تحميل البيانات
        manager.loadFromFile();

        int choice;

        do {
            Menu.showMenu();
            choice = InputValidator.getInt(input, "Choose an option: ");

            switch (choice) {

                // ✅ إضافة طالب + حفظ + منع تكرار
                case 1:
                    String name = InputValidator.getString(input, "Enter name: ");
                    int age = InputValidator.getInt(input, "Enter age: ");
                    double gpa = InputValidator.getDouble(input, "Enter GPA: ");

                    Student s = new Student(name, age, gpa);

                    if (manager.addStudent(s)) {
                        manager.saveToFile();   // 🔥 مهم
                        System.out.println("Student added successfully!");
                    } else {
                        System.out.println("Name already exists!");
                    }
                    break;

                // عرض الطلاب
                case 2:
                    manager.displayStudents();
                    break;

                // بحث
                case 3:
                    String searchName = InputValidator.getString(input, "Enter name to search: ");
                    manager.searchStudent(searchName);
                    break;

                // حذف + حفظ
                case 4:
                    String deleteName = InputValidator.getString(input, "Enter name to delete: ");
                    manager.deleteStudent(deleteName);
                    manager.saveToFile(); // 🔥 مهم
                    break;

                // ترتيب
                case 5:
                    manager.sortByGpa();
                    System.out.println("Sorted successfully!");
                    break;

                // ✅ تحديث + حفظ
                case 6:
                    String updateName = InputValidator.getString(input, "Enter name to update: ");
                    int newAge = InputValidator.getInt(input, "Enter new age: ");
                    double newGpa = InputValidator.getDouble(input, "Enter new GPA: ");

                    manager.updateStudent(updateName, newAge, newGpa);
                    manager.saveToFile(); // 🔥 مهم
                    break;

                // خروج + حفظ نهائي
                case 7:
                    manager.saveToFile();
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 7);

        input.close();
    }
}