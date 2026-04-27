package com.contratacion.dao;

import com.contratacion.model.Aspirante;

import java.util.List;

public interface AspiranteDAO {
    boolean insertar(Aspirante aspirante);
    List<Aspirante> listarTodos();

}
