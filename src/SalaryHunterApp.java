import javax.swing.*;

public class SalaryHunterApp {
    public static void main(String[] args) {
        // Ensure the GUI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create and show the LoginFrame
                new DatabaseLoginFrame().setVisible(true);
            }
        });
    }
}
