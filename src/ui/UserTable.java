package ui;

import dao.UserDAO;
import model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class UserTable extends JPanel {

    private final UserDAO userDAO;
    private final DefaultTableModel model;
    private final JTable table;

    public UserTable(Connection conn, UserForm form) {
        this.userDAO = new UserDAO(conn);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"ID", "Name", "Email"}, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        JButton refreshBtn = new JButton("Refresh");
        JButton deleteBtn = new JButton("Delete");

        JPanel buttons = new JPanel();
        buttons.add(refreshBtn);
        buttons.add(deleteBtn);

        add(scroll, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        form.setUserTable(this);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int id = (int) model.getValueAt(row, 0);
                    String name = (String) model.getValueAt(row, 1);
                    String email = (String) model.getValueAt(row, 2);
                    form.setFields(id, name, email);
                }
            }
        });

        refreshBtn.addActionListener(e -> loadUsers());
        deleteBtn.addActionListener(e -> deleteUser());
        loadUsers();
    }

    public void loadUsers() {
        try {
            model.setRowCount(0);
            List<User> users = userDAO.getAllUsers();
            for (User u : users) {
                model.addRow(new Object[]{u.getId(), u.getName(), u.getEmail()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void deleteUser() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to delete");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        try {
            userDAO.deleteUser(id);
            loadUsers();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
