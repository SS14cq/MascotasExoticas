/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.mascotasexoticas.modelo;

/**
 * <p>
 * Representa una mascota exótica, incluyendo su apodo y los atributos biológicos heredados de {@link AnimalVO}.
 * Esta clase permite distinguir entre diferentes individuos de una misma especie mediante un nombre personalizado.
 * </p>
 *
 * <p>
 * Extiende la clase {@link AnimalVO}, por lo que también incluye información taxonómica como nombre común,
 * clasificación, familia, género, especie y tipo de alimento.
 * </p>
 * @author Sofia
 * @version 1.0
 * @since 12-10-2024
 */
public class MascotaVO extends AnimalVO {

    /** Apodo personalizado de la mascota. */
    private String apodo;

    /**
     * Crea una nueva instancia de {@code MascotaVO} con todos los atributos biológicos
     * y un apodo personalizado.
     *
     * @param nombre Nombre común del animal.
     * @param clasificacion Clasificación taxonómica (ej. mamífero, reptil, ave).
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
     * Crea una nueva instancia de {@code MascotaVO} a partir de un objeto {@link AnimalVO}
     * y un apodo personalizado.
     *
     * @param animal Objeto {@link AnimalVO} que contiene la información biológica base.
     * @param apodo Apodo de la mascota.
     */
    public MascotaVO(AnimalVO animal, String apodo) {
        super(animal.getNombre(), animal.getClasificacion(), animal.getFamilia(),
              animal.getGenero(), animal.getEspecie(), animal.getAlimento());
        this.apodo = apodo;
    }

    /**
     * Obtiene el apodo personalizado de la mascota.
     *
     * @return apodo de la mascota.
     */
    public String getApodo() {
        return apodo;
    }

    /**
     * Establece el apodo personalizado de la mascota.
     *
     * @param apodo nuevo apodo de la mascota.
     */
    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    /**
     * Devuelve una representación textual legible de la mascota exótica,
     * incluyendo tanto su apodo como sus atributos biológicos heredados.
     *
     * @return cadena de texto con los datos completos de la mascota.
     */
    @Override
    public String toString() {
        return String.format(
            "Mascota: %s (%s)%nClasificación: %s%nFamilia: %s%nGénero: %s%nEspecie: %s%nAlimento: %s",
            getNombre(), apodo, getClasificacion(), getFamilia(), getGenero(), getEspecie(), getAlimento()
        );
    }
}