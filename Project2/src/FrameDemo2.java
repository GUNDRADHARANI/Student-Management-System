import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class FrameDemo2 extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FrameDemo2 frame = new FrameDemo2();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public FrameDemo2() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 500);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("STUDENT DETAILS");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel.setBounds(109, 30, 227, 29);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(155, 80, 96, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("ID:");
        lblNewLabel_1.setBounds(110, 83, 35, 14);
        contentPane.add(lblNewLabel_1);

        JButton fetchButton = new JButton("FETCH");
        fetchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = textField.getText();
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "12345");
                    Statement st = con.createStatement();
                    String sql = "select * from stu_table where stu_id = '" + id + "'";
                    ResultSet rs = st.executeQuery(sql);

                    String[] columns = {"stu_id", "stu_name", "stu_class", "stu_marks", "stu_gender"};
                    model = new DefaultTableModel(columns, 0);

                    while (rs.next()) {
                        Object[] row = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)};
                        model.addRow(row);
                    }

                    table = new JTable(model);
                    scrollPane = new JScrollPane(table);
                    contentPane.add(scrollPane);
                    scrollPane.setBounds(10, 150, 620, 300);
                    contentPane.revalidate();
                    contentPane.repaint();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        fetchButton.setBounds(129, 120, 122, 29);
        contentPane.add(fetchButton);
    }
}
