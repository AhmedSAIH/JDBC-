import db.DBConnection;
import ui.MainFrame;
import javax.swing.*;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Connection conn = DBConnection.getConnection();
                new MainFrame(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }); 
    }
}
