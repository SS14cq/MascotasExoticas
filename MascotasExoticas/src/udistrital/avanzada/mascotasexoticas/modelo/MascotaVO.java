/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.mascotasexoticas.modelo;

/**
 * Representa una mascota exótica, incluyendo su apodo y los atributos biológicos heredados de AnimalVO.
 */
public class MascotaVO extends AnimalVO {

    /** Apodo personalizado de la mascota. */
    private String apodo;

    /**
     * Constructor completo que inicializa todos los atributos de la mascota.
     * @param nombre Nombre común del animal.
     * @param clasificacion Clasificación taxonómica.
     * @param familia Familia biológica.
     * @param genero Género biológico.
     * @param especie Especie biológica.
     * @param alimento Tipo de alimento principal.
     * @param apodo Apodo de la mascota.
     */
    public MascotaVO(String nombre, String clasificacion, String familia,
                     String genero, String especie, String alimento, String apodo) {
        super(nombre, clasificacion, familia, genero, especie, alimento);
        this.apodo = apodo;
    }

    /**
     * Constructor alternativo que recibe un AnimalVO y el apodo.
     * @param animal Objeto AnimalVO con los atributos biológicos.
     * @param apodo Apodo de la mascota.
     */
    public MascotaVO(AnimalVO animal, String apodo) {
        super(animal.getNombre(), animal.getClasificacion(), animal.getFamilia(),
              animal.getGenero(), animal.getEspecie(), animal.getAlimento());
        this.apodo = apodo;
    }

    /**
     * Devuelve el apodo de la mascota.
     * @return Apodo.
     */
    public String getApodo() {
        return apodo;
    }

    /**
     * Establece el apodo de la mascota.
     * @param apodo Nuevo apodo.
     */
    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    /**
     * Devuelve una representación textual de la mascota.
     * @return Cadena con los datos de la mascota.
     */
    @Override
    public String toString() {
        return String.format(
            "Mascota: %s (%s)\nClasificación: %s\nFamilia: %s\nGénero: %s\nEspecie: %s\nAlimento: %s",
            getNombre(), apodo, getClasificacion(), getFamilia(), getGenero(), getEspecie(), getAlimento()
        );
    }
}