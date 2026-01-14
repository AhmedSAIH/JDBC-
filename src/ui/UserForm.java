package ui;

import dao.UserDAO;
import model.User;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class UserForm extends JPanel {

    private final JTextField idField = new JTextField(5);
    private final JTextField nameField = new JTextField(10);
    private final JTextField emailField = new JTextField(10);
    private final UserDAO userDAO;
    private UserTable userTable;

    public UserForm(Connection conn) {
        this.userDAO = new UserDAO(conn);
        setLayout(new FlowLayout());

        add(new JLabel("ID:"));
        add(idField);
        idField.setEditable(false);

        add(new JLabel("Name:"));
        add(nameField);

        add(new JLabel("Email:"));
        add(emailField);

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");

        add(addBtn);
        add(updateBtn);

        addBtn.addActionListener(e -> addUser());
        updateBtn.addActionListener(e -> updateUser());
    }

    public void setUserTable(UserTable table) {
        this.userTable = table;
    }

    public void setFields(int id, String name, String email) {
        idField.setText(String.valueOf(id));
        nameField.setText(name);
        emailField.setText(email);
    }

    private void addUser() {
        try {
            userDAO.addUser(new User(nameField.getText(), emailField.getText()));
            JOptionPane.showMessageDialog(this, "User added");
            if (userTable != null) userTable.loadUsers();
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void updateUser() {
        try {
            int id = Integer.parseInt(idField.getText());
            userDAO.updateUser(new User(id, nameField.getText(), emailField.getText()));
            JOptionPane.showMessageDialog(this, "User updated");
            if (userTable != null) userTable.loadUsers();
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
    }
}
