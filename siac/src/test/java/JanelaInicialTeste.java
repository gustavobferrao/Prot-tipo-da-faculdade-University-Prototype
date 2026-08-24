import view.JanelaSiac;

import javax.swing.*;

public class JanelaInicialTeste {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JanelaSiac().setVisible(true);
                });
    }
}
