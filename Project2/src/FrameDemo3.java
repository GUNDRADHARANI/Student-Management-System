import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FrameDemo3 extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JCheckBox chckbxId;
    private JCheckBox chckbxName;
    private JCheckBox chckbxClass;
    private JCheckBox chckbxMarks;
    private JCheckBox chckbxGender;
    private JTable table;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FrameDemo3 frame = new FrameDemo3();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public FrameDemo3() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("STUDENT DETAILS");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel.setBounds(250, 10, 200, 30);
        contentPane.add(lblNewLabel);

        JLabel lblId = new JLabel("ID:");
        lblId.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblId.setBounds(20, 60, 40, 20);
        contentPane.add(lblId);

        textField = new JTextField();
        textField.setBounds(60, 60, 100, 25);
        contentPane.add(textField);
        textField.setColumns(10);

        JLabel lblDetails = new JLabel("DETAILS:");
        lblDetails.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblDetails.setBounds(180, 60, 100, 20);
        contentPane.add(lblDetails);

        chckbxId = new JCheckBox("ID");
        chckbxId.setBounds(260, 60, 70, 25);
        contentPane.add(chckbxId);

        chckbxName = new JCheckBox("Name");
        chckbxName.setBounds(330, 60, 70, 25);
        contentPane.add(chckbxName);

        chckbxClass = new JCheckBox("Class");
        chckbxClass.setBounds(400, 60, 70, 25);
        contentPane.add(chckbxClass);

        chckbxMarks = new JCheckBox("Marks");
        chckbxMarks.setBounds(470, 60, 70, 25);
        contentPane.add(chckbxMarks);

        chckbxGender = new JCheckBox("Gender");
        chckbxGender.setBounds(540, 60, 80, 25);
        contentPane.add(chckbxGender);

        JButton btnFetch = new JButton("FETCH");
        btnFetch.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnFetch.setBounds(280, 100, 100, 30);
        contentPane.add(btnFetch);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 150, 640, 300);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        btnFetch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fetchStudentData();
            }
        });
    }

    private void fetchStudentData() {
        String stuId = textField.getText().trim();
        if (stuId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder queryBuilder = new StringBuilder("SELECT ");
        DefaultTableModel model = new DefaultTableModel();

        // Dynamically build the query and add columns
        if (chckbxId.isSelected()) {
            queryBuilder.append("stu_id, ");
            model.addColumn("ID");
        }
        if (chckbxName.isSelected()) {
            queryBuilder.append("stu_name, ");
            model.addColumn("Name");
        }
        if (chckbxClass.isSelected()) {
            queryBuilder.append("stu_class, ");
            model.addColumn("Class");
        }
        if (chckbxMarks.isSelected()) {
            queryBuilder.append("stu_marks, ");
            model.addColumn("Marks");
        }
        if (chckbxGender.isSelected()) {
            queryBuilder.append("stu_gender, ");
            model.addColumn("Gender");
        }

        String query = queryBuilder.toString();

        // Remove trailing comma
        if (query.endsWith(", ")) {
            query = query.substring(0, query.length() - 2);
        }

        query += " FROM stu_table WHERE stu_id = ?";

        if (model.getColumnCount() == 0) {
            JOptionPane.showMessageDialog(this, "Please select at least one column", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "12345");
            PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setString(1, stuId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[model.getColumnCount()];
                int columnIndex = 0;

                if (chckbxId.isSelected()) {
                    row[columnIndex++] = rs.getString("stu_id");
                }
                if (chckbxName.isSelected()) {
                    row[columnIndex++] = rs.getString("stu_name");
                }
                if (chckbxClass.isSelected()) {
                    row[columnIndex++] = rs.getString("stu_class");
                }
                if (chckbxMarks.isSelected()) {
                    row[columnIndex++] = rs.getInt("stu_marks");
                }
                if (chckbxGender.isSelected()) {
                    row[columnIndex++] = rs.getString("stu_gender");
                }

                model.addRow(row);
            }

            table.setModel(model);

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No data found for the given ID", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data from the database", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
