package org.example.app;

import javax.swing.*;

import org.example.DAO.UsuarioDAO;
import org.example.Interfaz.loginForm;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = null;

        try {
            // Intentar abrir la sesión
            session = sessionFactory.openSession();
            // Mostrar un mensaje cuando la sesión se abre con éxito
            System.out.println("Sesión abierta con éxito.");

            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Login");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Crear una instancia de UsuarioDAO pasando el SessionFactory
                UsuarioDAO usuarioDAO = new UsuarioDAO(sessionFactory);

                // Crear el panel de inicio de sesión pasando el UsuarioDAO
                JPanel panel = new loginForm(usuarioDAO).PanelPrincipal;

                frame.add(panel);

                frame.setSize(400, 300);
                frame.setVisible(true);
            });
        } catch (Exception e) {
            // Capturar excepciones en caso de error al abrir la sesión
            System.err.println("Error al abrir la sesión: " + e.getMessage());
        } finally {
            // Cerrar la sesión de Hibernate en el bloque finally
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}


