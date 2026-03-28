import java.io.FileWriter;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;

public class StudentManager {

    private ArrayList<Student> students = new ArrayList<>();

    // ✅ Getter مهم جداً للـ GUI
    public ArrayList<Student> getStudents() {
        return students;
    }

    // إضافة طالب + منع تكرار
    public boolean addStudent(Student s) {
        for (Student st : students) {
            if (st.getName().equalsIgnoreCase(s.getName())) {
                System.out.println("Student with this name already exists!");
                return false;
            }
        }

        students.add(s);
        return true;
    }

    // عرض الطلاب
    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student s : students) {
            s.display();
        }
    }

    // بحث
    public void searchStudent(String name) {
        boolean found = false;

        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                s.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("Student not found.");
        }
    }

    // حذف
    public void deleteStudent(String name) {
        boolean removed = students.removeIf(s -> s.getName().equalsIgnoreCase(name));

        if (removed) {
            System.out.println("Student deleted.");
        } else {
            System.out.println("Student not found.");
        }
    }

    // تحديث
    public void updateStudent(String name, int newAge, double newGpa) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                s.setAge(newAge);
                s.setGpa(newGpa);
                System.out.println("Student updated successfully.");
                return;
            }
        }

        System.out.println("Student not found.");
    }

    // ترتيب
    public void sortByGpa() {
        students.sort(Comparator.comparingDouble(Student::getGpa).reversed());
        System.out.println("Students sorted by GPA (descending).");
    }

    // حفظ
    public void saveToFile() {
        try (FileWriter writer = new FileWriter("students.txt", false)) {

            for (Student s : students) {
                writer.write(s.toString() + "\n");
            }

            System.out.println("Data saved successfully!");

        } catch (Exception e) {
            System.out.println("Error saving data.");
        }
    }

    // تحميل
    public void loadFromFile() {
        try {
            File file = new File("students.txt");

            if (!file.exists()) return;

            Scanner reader = new Scanner(file);
            students.clear();

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(",");

                if (parts.length == 3) {
                    String name = parts[0];
                    int age = Integer.parseInt(parts[1]);
                    double gpa = Double.parseDouble(parts[2]);

                    students.add(new Student(name, age, gpa));
                }
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error loading data.");
        }
    }
}