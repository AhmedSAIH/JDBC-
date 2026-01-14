package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class MainFrame extends JFrame {

    public MainFrame(Connection conn) {
        setTitle("User Management");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        UserForm form = new UserForm(conn);
        UserTable table = new UserTable(conn, form);

        add(form, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);

        setVisible(true);
    }
}
