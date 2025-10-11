/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.mascotasexoticas.modelo;

/**
 *
 * @author sarit
 */


/**
 * Representa los atributos biológicos de un animal exótico.
 * Esta clase es utilizada como base para construir una mascota exótica.
 */
public class AnimalVO {

    /** Nombre común del animal. */
    protected String nombre;

    /** Clasificación taxonómica (mamífero, reptil, etc.). */
    protected String clasificacion;

    /** Familia biológica del animal. */
    protected String familia;

    /** Género biológico del animal. */
    protected String genero;

    /** Especie biológica del animal. */
    protected String especie;

    /** Tipo de alimento principal que consume el animal. */
    protected String alimento;

    /**
     * Constructor completo que inicializa todos los atributos del animal.
     * @param nombre Nombre común.
     * @param clasificacion Clasificación taxonómica.
     * @param familia Familia biológica.
     * @param genero Género biológico.
     * @param especie Especie biológica.
     * @param alimento Tipo de alimento principal.
     */
    public AnimalVO(String nombre, String clasificacion, String familia,
                    String genero, String especie, String alimento) {
        this.nombre = nombre;
        this.clasificacion = clasificacion;
        this.familia = familia;
        this.genero = genero;
        this.especie = especie;
        this.alimento = alimento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getAlimento() {
        return alimento;
    }

    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }
}