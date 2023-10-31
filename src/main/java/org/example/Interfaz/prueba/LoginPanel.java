package org.example.Interfaz.prueba;

import org.example.DAO.InscripcionesDao;
import org.example.DAO.UsuarioDAO;
import org.example.DTO.UsuariosClass;
import org.example.Interfaz.InscripcionPanel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginPanel extends JPanel {
    private JTextField user;
    private JPasswordField password;
    private JButton bottonLogin;
    private UsuarioDAO usuarioDAO;
    private JFrame mainFrame;


    public LoginPanel(final UsuarioDAO usuarioDAO, final JFrame mainFrame) {
        this.usuarioDAO = usuarioDAO;
        this.mainFrame = mainFrame;;


        setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Usuario:");
        user = new JTextField();
        JLabel passwordLabel = new JLabel("Contraseña:");
        password = new JPasswordField();
        bottonLogin = new JButton("Iniciar Sesión");

        add(usernameLabel);
        add(user);
        add(passwordLabel);
        add(password);
        add(new JPanel()); // Espacio vacío
        add(bottonLogin);

        bottonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = user.getText();
                char[] passwordChars = password.getPassword();
                String passwordStr = new String(passwordChars);

                UsuariosClass usuario = usuarioDAO.obtenerUsuarioPorNombre(username);

                if (usuario != null && usuario.getContraseña().equals(passwordStr)) {
                    // Crea un nuevo JFrame para tu InscripcionPanel
                    JFrame inscripcionFrame = new JFrame("Panel de Inscripción");
                    inscripcionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    // Crea una instancia de InscripcionPanel y agrégala al nuevo JFrame
                    InscripcionPanel inscripcionPanel = new InscripcionPanel(new InscripcionesDao());
                    inscripcionFrame.add(inscripcionPanel.InscripcionPanel);

                    // Ajusta el tamaño del JFrame y hazlo visible
                    inscripcionFrame.pack();
                    inscripcionFrame.setVisible(true);

                    // Cierra el JFrame actual si es necesario
                    mainFrame.dispose();




                } else {
                    JOptionPane.showMessageDialog(LoginPanel.this, "Credenciales incorrectas");
                }

                // Limpia los campos de usuario y contraseña
                user.setText("");
                password.setText("");
            }
        });
    }
}

