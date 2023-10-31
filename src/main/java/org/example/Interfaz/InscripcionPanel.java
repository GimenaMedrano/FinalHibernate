package org.example.Interfaz;

import org.example.DAO.InscripcionesDao;
import org.example.DTO.InscripcionesClass;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class InscripcionPanel {

    private JTable table1;
    private JButton Crear;
    private JButton Borrar;
    private JButton Actualizar;
    private JButton Leer;
    private JTextField IDinscripcion;
    private JTextField FechaInscripcion;
    private JLabel FechaIns;
    public JPanel InscripcionPanel;
    private JTextField Id_Curso;
    private JLabel test2;

    private DefaultTableModel tableModel;
    private InscripcionesDao inscripcionesDao;

    public InscripcionPanel(InscripcionesDao inscripcionesDao) {
        this.inscripcionesDao = inscripcionesDao;

        // Configura la tabla
        String[] columnNames = {"ID Inscripción", "ID Estudiante", "ID Curso", "Fecha Inscripción"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table1.setModel(tableModel);

        Crear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearInscripcion();
            }
        });

        Borrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarInscripcion();
            }
        });

        Actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarInscripcion();
            }
        });

        Leer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leerInscripciones();
            }
        });
    }

    private void crearInscripcion() {
        try {
            int idEstudiante = getIntFromTextField(IDinscripcion);
            int idCurso = getIntFromTextField(Id_Curso);
            Date fechaInscripcion = getDateFromTextField(FechaInscripcion);

            InscripcionesClass inscripcion = new InscripcionesClass();
            inscripcion.setIdEstudiante(idEstudiante);
            inscripcion.setIdCurso(idCurso);
            inscripcion.setFechaInscripcion(fechaInscripcion);

            inscripcionesDao.guardarInscripcion(inscripcion);
            actualizarTabla();
        } catch (Exception ex) {
            mostrarError("Error al crear la inscripción: " + ex.getMessage());
        }
    }

    private void borrarInscripcion() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            int idInscripcion = (int) tableModel.getValueAt(selectedRow, 0);
            inscripcionesDao.eliminarInscripcion(idInscripcion);
            actualizarTabla();
        }
    }

    private void actualizarInscripcion() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            int idInscripcion = (int) tableModel.getValueAt(selectedRow, 0);
            int idEstudiante = getIntFromTextField(IDinscripcion);
            int idCurso = getIntFromTextField(Id_Curso);
            Date fechaInscripcion = getDateFromTextField(FechaInscripcion);

            InscripcionesClass inscripcion = inscripcionesDao.obtenerInscripcionPorId(idInscripcion);
            if (inscripcion != null) {
                inscripcion.setIdEstudiante(idEstudiante);
                inscripcion.setIdCurso(idCurso);
                inscripcion.setFechaInscripcion(fechaInscripcion);

                inscripcionesDao.actualizarInscripcion(inscripcion);
                actualizarTabla();
            }
        }
    }

    private void leerInscripciones() {
        actualizarTabla();
    }

    private void actualizarTabla() {
        try {
            List<InscripcionesClass> inscripciones = inscripcionesDao.obtenerTodasLasInscripciones();
            limpiarTabla();
            for (InscripcionesClass inscripcion : inscripciones) {
                agregarFilaATabla(inscripcion);
            }
        } catch (Exception ex) {
            mostrarError("Error al leer inscripciones: " + ex.getMessage());
        }
    }

    private void limpiarTabla() {
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }
    }

    private void agregarFilaATabla(InscripcionesClass inscripcion) {
        tableModel.addRow(new Object[]{inscripcion.getIdInscripcion(), inscripcion.getIdEstudiante(), inscripcion.getIdCurso(), inscripcion.getFechaInscripcion()});
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(InscripcionPanel, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private int getIntFromTextField(JTextField textField) {
        return Integer.parseInt(textField.getText());
    }

    private Date getDateFromTextField(JTextField textField) {
        String fechaStr = textField.getText();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(fechaStr);
            return new Date(parsedDate.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
