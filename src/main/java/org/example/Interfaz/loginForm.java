package org.example.Interfaz;

import org.example.DAO.InscripcionesDao;
import org.example.DAO.UsuarioDAO;
import org.example.DTO.UsuariosClass;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginForm {
    
    public JPanel PanelPrincipal;
    private JTextField user;
    private JButton buttonLogin;
    private JPasswordField password;
    private UsuarioDAO usuarioDAO;

    public loginForm(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;

        buttonLogin.addActionListener(new ActionListener() {
            private InscripcionesDao inscripcionesDao;

            @Override
            public void actionPerformed(ActionEvent e) {
                String username = user.getText();
                char[] passwordChars = password.getPassword();
                String passwordStr = new String(passwordChars);

                // Verificar las credenciales del usuario
                UsuariosClass usuario = usuarioDAO.obtenerUsuarioPorNombre(username);

                if (usuario != null && ((UsuariosClass) usuario).getContraseña().equals(passwordStr)) {
                    // Las credenciales son correctas, así que mostramos el InscripcionPanel en una nueva ventana
                    JFrame frame = new JFrame("Panel de Inscripción");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setContentPane(new InscripcionPanel(inscripcionesDao).InscripcionPanel);
                    frame.pack();
                    frame.setVisible(true);

                    // Cierra la ventana actual de login
                    JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelPrincipal);
                    loginFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
                }

                // Limpia los campos de usuario y contraseña
                user.setText("");
                password.setText("");
            }
        });
    }
}
