import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;

public class FrameDemo1 extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private static DefaultTableModel tableModel;

    // Sample student data
    private static final String[] columnNames = { "ID", "Name", "Class", "Marks", "Gender" };
    private static Object[][] studentData = {
        { "1", "Dharani", "5", "99", "female" },
        { "2", "Navya", "6", "91", "female" },
        { "3", "Mani", "7", "89", "female" },
        { "4", "Ramesh", "8", "79", "male" },
        { "5", "Chandra", "9", "88", "male" }
    };

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                FrameDemo1 frame = new FrameDemo1();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public FrameDemo1() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("STUDENT MANAGEMENT SYSTEM");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitle.setBounds(250, 20, 300, 30);
        contentPane.add(lblTitle);

        JButton btnDisplay = new JButton("DISPLAY");
        btnDisplay.setBounds(50, 80, 150, 30);
        contentPane.add(btnDisplay);

        JButton btnInsert = new JButton("INSERT");
        btnInsert.setBounds(50, 140, 150, 30);
        contentPane.add(btnInsert);

        JButton btnDelete = new JButton("DELETE");
        btnDelete.setBounds(50, 200, 150, 30);
        contentPane.add(btnDelete);

        JButton btnUpdate = new JButton("UPDATE");
        btnUpdate.setBounds(50, 260, 150, 30);
        contentPane.add(btnUpdate);

        // Table to display data in the main frame
        JTable table = new JTable(FrameDemo1.getTableModel());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(250, 80, 500, 300);
        contentPane.add(scrollPane);

        btnDisplay.addActionListener(e -> openDisplayFrame());
        btnInsert.addActionListener(e -> openInsertFrame());
        btnDelete.addActionListener(e -> openDeleteFrame(table));
        btnUpdate.addActionListener(e -> openUpdateFrame(table));
    }

    private void openDisplayFrame() {
        DisplayFrame displayFrame = new DisplayFrame();
        displayFrame.setVisible(true);
    }

    private void openInsertFrame() {
        InsertFrame insertFrame = new InsertFrame();
        insertFrame.setVisible(true);
    }

    private void openDeleteFrame(JTable table) {
        DeleteFrame deleteFrame = new DeleteFrame(table);
        deleteFrame.setVisible(true);
    }

    private void openUpdateFrame(JTable table) {
        UpdateFrame updateFrame = new UpdateFrame(table);
        updateFrame.setVisible(true);
    }

    // Initialize the table model
    static {
        tableModel = new DefaultTableModel(studentData, columnNames);
    }

    public static DefaultTableModel getTableModel() {
        return tableModel;
    }
}

// Frame for displaying data
class DisplayFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public DisplayFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblDisplay = new JLabel("STUDENT DATA");
        lblDisplay.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblDisplay.setBounds(200, 20, 200, 30);
        contentPane.add(lblDisplay);

        JTable table = new JTable(FrameDemo1.getTableModel());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 70, 500, 200);
        contentPane.add(scrollPane);
    }
}

// Frame for inserting data
class InsertFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public InsertFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblInsert = new JLabel("INSERT STUDENT DETAILS");
        lblInsert.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblInsert.setBounds(150, 20, 300, 30);
        contentPane.add(lblInsert);

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField classField = new JTextField();
        JTextField marksField = new JTextField();
        JTextField genderField = new JTextField();

        JLabel[] labels = { new JLabel("ID:"), new JLabel("Name:"), new JLabel("Class:"), new JLabel("Marks:"), new JLabel("Gender:") };
        JTextField[] fields = { idField, nameField, classField, marksField, genderField };

        for (int i = 0; i < labels.length; i++) {
            labels[i].setBounds(50, 70 + i * 40, 100, 25);
            fields[i].setBounds(150, 70 + i * 40, 150, 25);
            contentPane.add(labels[i]);
            contentPane.add(fields[i]);
        }

        JButton btnAdd = new JButton("ADD");
        btnAdd.setBounds(150, 300, 100, 25);
        contentPane.add(btnAdd);

        btnAdd.addActionListener(e -> {
            String[] newRow = {
                idField.getText(),
                nameField.getText(),
                classField.getText(),
                marksField.getText(),
                genderField.getText()
            };
            FrameDemo1.getTableModel().addRow(newRow);
            JOptionPane.showMessageDialog(this, "Student inserted successfully!");
        });
    }
}

// Frame for deleting data
class DeleteFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable table;

    public DeleteFrame(JTable table) {
        this.table = table;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 200);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblDelete = new JLabel("DELETE STUDENT");
        lblDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblDelete.setBounds(120, 20, 200, 30);
        contentPane.add(lblDelete);

        JButton btnDelete = new JButton("DELETE");
        btnDelete.setBounds(150, 80, 100, 30);
        contentPane.add(btnDelete);

        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                FrameDemo1.getTableModel().removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a student to delete!");
            }
        });
    }
}

// Frame for updating data
class UpdateFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable table;

    public UpdateFrame(JTable table) {
        this.table = table;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblUpdate = new JLabel("UPDATE STUDENT DETAILS");
        lblUpdate.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblUpdate.setBounds(150, 20, 300, 30);
        contentPane.add(lblUpdate);

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField classField = new JTextField();
        JTextField marksField = new JTextField();
        JTextField genderField = new JTextField();

        JLabel[] labels = { new JLabel("ID:"), new JLabel("Name:"), new JLabel("Class:"), new JLabel("Marks:"), new JLabel("Gender:") };
        JTextField[] fields = { idField, nameField, classField, marksField, genderField };

        for (int i = 0; i < labels.length; i++) {
            labels[i].setBounds(50, 70 + i * 40, 100, 25);
            fields[i].setBounds(150, 70 + i * 40, 150, 25);
            contentPane.add(labels[i]);
            contentPane.add(fields[i]);
        }

        JButton btnUpdate = new JButton("UPDATE");
        btnUpdate.setBounds(150, 300, 100, 25);
        contentPane.add(btnUpdate);

        btnUpdate.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                FrameDemo1.getTableModel().setValueAt(idField.getText(), selectedRow, 0);
                FrameDemo1.getTableModel().setValueAt(nameField.getText(), selectedRow, 1);
                FrameDemo1.getTableModel().setValueAt(classField.getText(), selectedRow, 2);
                FrameDemo1.getTableModel().setValueAt(marksField.getText(), selectedRow, 3);
                FrameDemo1.getTableModel().setValueAt(genderField.getText(), selectedRow, 4);
                JOptionPane.showMessageDialog(this, "Student updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a student to update!");
            }
        });
    }
}
