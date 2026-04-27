package com.contratacion.model;

public class Aspirante {
    private int id;
    private String nombreCompleto;
    private String cedula;
    private String nivelEstudios; // variable para enum
    private String turnoPreferencia; // variable de ENUM
    private String idiomas; // variable para el SET
    private String habilidadesTecnicas; // valor para el SET

    // contructor
    public Aspirante (int id,String nombreCompleto, String cedula, String nivelEstudios,String turnoPreferencia,String idiomas, String habilidadesTecnicas) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.cedula = cedula;
        this.nivelEstudios = nivelEstudios;
        this.turnoPreferencia = turnoPreferencia;
        this.idiomas = idiomas;
        this.habilidadesTecnicas = habilidadesTecnicas;
    }
    // creacion de getters = obtener datos
    public int getid() {
        return id;
    }

    public String getnombreCompleto() {
        return nombreCompleto;
    }

    public String getcedula() {
        return cedula;
    }

    public String getnivelEstudios() {
        return nivelEstudios;
    }

    public String getturnoPreferencia() {
        return turnoPreferencia;
    }

    public String getidiomas() {
        return idiomas;
    }

    public String gethabilidadesTecnicas() {
        return habilidadesTecnicas;
    }


}

