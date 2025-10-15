
package udistrital.avanzada.mascotasexoticas.modelo;

/**
 * <p>
 * Representa los atributos biológicos de un animal exótico.
 * Esta clase se utiliza como base para la creación de objetos {@link MascotaVO},
 * que asocian a un animal con información adicional específica de una mascota.
 * </p>
 *
 * <p>
 * Contiene información taxonómica y biológica como nombre común,
 * clasificación, familia, género, especie y tipo de alimentación.
 * </p>
 *
 * <p><b>Ejemplo de uso:</b></p>
 * <pre>
 *     AnimalVO animal = new AnimalVO("Iguana verde", "Reptil", "Iguanidae",
 *                                    "Iguana", "Iguana iguana", "Herbívoro");
 * </pre>
 *
 * @author Sara
 * @version 1.0
 * @since 2024
 */
public class AnimalVO {

    /** Nombre común del animal. */
    protected String nombre;

    /** Clasificación taxonómica (ej. mamífero, reptil, ave, etc.). */
    protected String clasificacion;

    /** Familia biológica a la que pertenece el animal. */
    protected String familia;

    /** Género biológico del animal. */
    protected String genero;

    /** Especie biológica del animal. */
    protected String especie;

    /** Tipo de alimento principal que consume (ej. carnívoro, herbívoro, omnívoro). */
    protected String alimento;

    /**
     * Crea un nuevo objeto {@code AnimalVO} con todos sus atributos inicializados.
     *
     * @param nombre Nombre común del animal.
     * @param clasificacion Clasificación taxonómica (ej. mamífero, reptil).
     * @param familia Familia biológica.
     * @param genero Género biológico.
     * @param especie Especie biológica.
     * @param alimento Tipo de alimento principal que consume.
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

    /**
     * Obtiene el nombre común del animal.
     * @return nombre común.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre común del animal.
     * @param nombre nombre común.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la clasificación taxonómica.
     * @return clasificación taxonómica.
     */
    public String getClasificacion() {
        return clasificacion;
    }

    /**
     * Establece la clasificación taxonómica.
     * @param clasificacion clasificación taxonómica.
     */
    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    /**
     * Obtiene la familia biológica.
     * @return familia biológica.
     */
    public String getFamilia() {
        return familia;
    }

    /**
     * Establece la familia biológica.
     * @param familia familia biológica.
     */
    public void setFamilia(String familia) {
        this.familia = familia;
    }

    /**
     * Obtiene el género biológico.
     * @return género biológico.
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Establece el género biológico.
     * @param genero género biológico.
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * Obtiene la especie biológica.
     * @return especie biológica.
     */
    public String getEspecie() {
        return especie;
    }

    /**
     * Establece la especie biológica.
     * @param especie especie biológica.
     */
    public void setEspecie(String especie) {
        this.especie = especie;
    }

    /**
     * Obtiene el tipo de alimento principal que consume el animal.
     * @return tipo de alimento.
     */
    public String getAlimento() {
        return alimento;
    }

    /**
     * Establece el tipo de alimento principal que consume el animal.
     * @param alimento tipo de alimento.
     */
    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }
}
