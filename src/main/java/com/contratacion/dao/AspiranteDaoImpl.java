package com.contratacion.dao;

import com.contratacion.db.ConexionBD;
import com.contratacion.model.Aspirante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AspiranteDaoImpl implements AspiranteDAO {
    private static final String SQL_INSERT ="""
            INSERT INTO aspirantes
            (nombre_completo,cedula,nivel_estudios,
            turno_preferencia,idiomas,habilidades_tecnicas)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    @Override
    public boolean insertar(Aspirante a) {
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {

            ps.setString(1, a.getnombreCompleto());
            ps.setString(2, a.getcedula());
            ps.setString(3, a.getnivelEstudios());
            ps.setString(4, a.getturnoPreferencia());
            ps.setString(5, a.getidiomas());
            ps.setString(6, a.gethabilidadesTecnicas());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar el aspirante: " +
                    e.getSQLState());
            return false;
        }
    }
    @Override
    public List<Aspirante> listarTodos() {
        List<Aspirante> lista = new ArrayList<>();
        String SQL = "SELECT * FROM aspirantes";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SQL);
             java.sql.ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Aspirante(
                        rs.getInt("id"),
                        rs.getString("nombre_completo"),
                        rs.getString("cedula"),
                        rs.getString("nivel_estudios"),
                        rs.getString("turno_preferencia"),
                        rs.getString("idiomas"),
                        rs.getString("habilidades_tecnicas")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar aspirantes: " + e.getMessage());
        }
        return lista;
    }

}
