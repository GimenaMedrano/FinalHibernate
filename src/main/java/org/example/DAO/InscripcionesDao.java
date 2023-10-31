package org.example.DAO;

import org.example.DTO.InscripcionesClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class InscripcionesDao {
    private SessionFactory sessionFactory;

    public InscripcionesDao() {
        this.sessionFactory = sessionFactory;
    }

    public void guardarInscripcion(InscripcionesClass inscripcion) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(inscripcion);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public InscripcionesClass obtenerInscripcionPorId(int id) {
        Session session = sessionFactory.openSession();
        InscripcionesClass inscripcion = null;
        try {
            inscripcion = session.get(InscripcionesClass.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return inscripcion;
    }

    public void actualizarInscripcion(InscripcionesClass inscripcion) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(inscripcion);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void eliminarInscripcion(int inscripcion) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(inscripcion);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<InscripcionesClass> obtenerTodasLasInscripciones() {
        List<InscripcionesClass> inscripciones = null;
        Session session = sessionFactory.openSession();

        try {
            Query<InscripcionesClass> query = session.createQuery("FROM InscripcionesClass", InscripcionesClass.class);
            inscripciones = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return inscripciones;
    }
}

