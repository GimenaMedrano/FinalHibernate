package org.example.DAO;

import jakarta.persistence.Query;
import org.example.DTO.UsuariosClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UsuarioDAO {
    private SessionFactory sessionFactory;

    public UsuarioDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UsuariosClass obtenerUsuarioPorNombre(String nombreUsuario) {
        Session session = sessionFactory.openSession();
        UsuariosClass usuario = null;
        try {
            String hql = "FROM UsuariosClass WHERE nombreUsuario = :nombre";
            Query query = session.createQuery(hql, UsuariosClass.class);
            query.setParameter("nombre", nombreUsuario);
            usuario = (UsuariosClass) ((org.hibernate.query.Query<?>) query).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return usuario;
    }
}
