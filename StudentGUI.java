import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentGUI {

    public static void main(String[] args) {

        StudentManager manager = new StudentManager();
        manager.loadFromFile();

        JFrame frame = new JFrame("Student Management System");
        frame.setSize(450, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);

        String[] columns = {"Name", "Age", "GPA"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        // تحميل البيانات
        for (Student s : manager.getStudents()) {
            tableModel.addRow(new Object[]{
                    s.getName(),
                    s.getAge(),
                    s.getGpa()
            });
        }

        JTable table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 220, 390, 150);
        panel.add(scroll);

        JTextField name = new JTextField();
        JTextField age = new JTextField();
        JTextField gpa = new JTextField();

        name.setBounds(100, 20, 150, 25);
        age.setBounds(100, 60, 150, 25);
        gpa.setBounds(100, 100, 150, 25);

        panel.add(new JLabel("Name:")).setBounds(20, 20, 80, 25);
        panel.add(new JLabel("Age:")).setBounds(20, 60, 80, 25);
        panel.add(new JLabel("GPA:")).setBounds(20, 100, 80, 25);

        panel.add(name);
        panel.add(age);
        panel.add(gpa);

        //  تعبئة الحقول عند اختيار صف
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();

            if (row != -1) {
                name.setText(tableModel.getValueAt(row, 0).toString());
                age.setText(tableModel.getValueAt(row, 1).toString());
                gpa.setText(tableModel.getValueAt(row, 2).toString());
            }
        });

        // ADD
        JButton add = new JButton("Add");
        add.setBounds(20, 150, 80, 30);

        add.addActionListener(e -> {
            try {
                String n = name.getText().trim();
                int a = Integer.parseInt(age.getText().trim());
                double g = Double.parseDouble(gpa.getText().trim());

                if (g < 0 || g > 100) {
                    JOptionPane.showMessageDialog(frame, "GPA must be 0-100");
                    return;
                }

                if (manager.addStudent(new Student(n, a, g))) {
                    manager.saveToFile();
                    tableModel.addRow(new Object[]{n, a, g});

                    //  تفريغ الحقول
                    name.setText("");
                    age.setText("");
                    gpa.setText("");

                } else {
                    JOptionPane.showMessageDialog(frame, "Name already exists!");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input!");
            }
        });

        // DELETE
        JButton delete = new JButton("Delete");
        delete.setBounds(120, 150, 80, 30);

        delete.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(frame, "Select a student first!");
                return;
            }

            String n = tableModel.getValueAt(row, 0).toString();

            manager.deleteStudent(n);
            manager.saveToFile();
            tableModel.removeRow(row);
            //  تفريغ الحقول
        name.setText("");
        age.setText("");
        gpa.setText("");
        });

        // UPDATE
        JButton update = new JButton("Update");
        update.setBounds(220, 150, 80, 30);

        update.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(frame, "Select a student first!");
                return;
            }

            try {
                String oldName = tableModel.getValueAt(row, 0).toString();

                String n = name.getText().trim();
                int a = Integer.parseInt(age.getText().trim());
                double g = Double.parseDouble(gpa.getText().trim());

                manager.updateStudent(oldName, a, g);
                manager.saveToFile();

                tableModel.setValueAt(n, row, 0);
                tableModel.setValueAt(a, row, 1);
                tableModel.setValueAt(g, row, 2);

                JOptionPane.showMessageDialog(frame, "Updated successfully!");
                // تفريغ الحقول
                name.setText("");
                age.setText("");
                gpa.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input!");
            }
        });

        panel.add(add);
        panel.add(delete);
        panel.add(update);

        frame.setVisible(true);
    }
}