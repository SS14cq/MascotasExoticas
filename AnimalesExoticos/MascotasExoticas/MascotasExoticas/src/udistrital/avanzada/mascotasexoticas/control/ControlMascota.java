package udistrital.avanzada.mascotasexoticas.control;

import udistrital.avanzada.mascotasexoticas.modelo.MascotaVO;
import udistrital.avanzada.mascotasexoticas.modelo.conexion.ISerializacionService;
import java.util.List;
import udistrital.avanzada.mascotasexoticas.modelo.DAO.ICRUDMascota;

/**
 * Controlador de la capa de negocio responsable de gestionar las operaciones 
 * relacionadas con las mascotas del sistema.
 * <p>
 * Esta clase actúa como intermediario entre el {@code ControlPrincipal} y la 
 * capa de persistencia (DAO), aplicando validaciones y reglas de negocio antes 
 * de delegar las operaciones al {@link ICRUDMascota}.
 * </p>
 *
 * <h2>Responsabilidades</h2>
 * <ul>
 *   <li>Validar los datos de las mascotas antes de ser enviadas al DAO.</li>
 *   <li>Coordinar operaciones CRUD (crear, leer, actualizar, eliminar) sobre mascotas.</li>
 *   <li>Delegar los procesos de serialización al servicio {@link ISerializacionService}.</li>
 *   <li>Prevenir duplicidades y garantizar la integridad de los datos.</li>
 * </ul>
 *
 * <h2>Principios aplicados</h2>
 * <ul>
 *   <li><b>SRP (Single Responsibility Principle):</b> esta clase solo se encarga
 *       de la lógica de negocio de las mascotas.</li>
 *   <li><b>DIP (Dependency Inversion Principle):</b> depende de abstracciones
 *       ({@link ICRUDMascota}, {@link ISerializacionService}), no de implementaciones concretas.</li>
 *   <li><b>OCP (Open/Closed Principle):</b> puede extenderse con nuevas validaciones
 *       o comportamientos sin modificar el código existente.</li>
 * </ul>
 * 
 * @author Steban
 * @version 1.0
 * @since 13/10/2025
 */
public class ControlMascota implements IControlMascota {

    /** DAO responsable del acceso a los datos de las mascotas. */
    private final ICRUDMascota mascotaDAO;

    /** Servicio encargado de la serialización y persistencia secundaria de mascotas. */
    private final ISerializacionService serializacionService;

    /**
     * Crea una instancia del controlador de mascotas.
     *
     * @param mascotaDAO Implementación concreta de la interfaz {@link ICRUDMascota}.
     * @param serializacionService Servicio de serialización e ingreso/salida de archivos.
     */
    public ControlMascota(ICRUDMascota mascotaDAO, ISerializacionService serializacionService) {
        this.mascotaDAO = mascotaDAO;
        this.serializacionService = serializacionService;
    }

    // -------------------------------------------------------------------------
    // MÉTODOS CRUD
    // -------------------------------------------------------------------------

    /**
     * Registra una nueva mascota en el sistema.
     * <p>
     * Antes de agregarla, valida que no exista una mascota con las mismas
     * características y apodo, garantizando integridad de datos.
     * </p>
     *
     * @param mascota Objeto {@link MascotaVO} a registrar.
     * @return {@code true} si la mascota fue registrada correctamente.
     * @throws IllegalArgumentException si ya existe una mascota idéntica.
     */
    @Override
    public boolean adicionarMascota(MascotaVO mascota) {
        if (existeMascotaExacta(mascota)) {
            throw new IllegalArgumentException(
                "Ya existe una mascota con las mismas características. Inserción rechazada."
            );
        }
        return mascotaDAO.adicionarMascota(mascota);
    }

    /**
     * Modifica los datos de una mascota existente.
     * <p>
     * La búsqueda se realiza por apodo, y solo se modifica si existe un registro previo.
     * </p>
     *
     * @param mascota Objeto {@link MascotaVO} con los datos actualizados.
     * @return {@code true} si la mascota fue modificada correctamente.
     * @throws IllegalArgumentException si no existe una mascota con el apodo indicado.
     */
    @Override
    public boolean modificarMascota(MascotaVO mascota) {
        List<MascotaVO> existentes = mascotaDAO.consultarPorApodo(mascota.getApodo());
        if (existentes.isEmpty()) {
            throw new IllegalArgumentException("No se encontró una mascota con ese apodo.");
        }
        return mascotaDAO.modificarMascota(mascota);
    }

    /**
     * Elimina una mascota del sistema por su apodo.
     *
     * @param apodo Apodo único de la mascota a eliminar.
     * @return {@code true} si la eliminación fue exitosa.
     */
    @Override
    public boolean eliminarMascota(String apodo) {
        return mascotaDAO.eliminarMascota(apodo);
    }

    /**
     * Obtiene todas las mascotas registradas en el sistema.
     *
     * @return Lista completa de objetos {@link MascotaVO}.
     */
    @Override
    public List<MascotaVO> listarTodasMascotas() {
        return mascotaDAO.listarTodasMascotas();
    }

    // -------------------------------------------------------------------------
    // MÉTODOS DE CONSULTA
    // -------------------------------------------------------------------------

    /**
     * Consulta las mascotas registradas por apodo.
     *
     * @param apodo Apodo a buscar.
     * @return Lista de mascotas que coinciden con el apodo indicado.
     */
    @Override
    public List<MascotaVO> consultarPorApodo(String apodo) {
        return mascotaDAO.consultarPorApodo(apodo);
    }

    /**
     * Consulta las mascotas registradas por clasificación biológica.
     *
     * @param clasificacion Clasificación de la mascota (ej. Mamífero, Ave, etc.)
     * @return Lista de mascotas que pertenecen a esa clasificación.
     */
    @Override
    public List<MascotaVO> consultarPorClasificacion(String clasificacion) {
        return mascotaDAO.consultarPorClasificacion(clasificacion);
    }

    /**
     * Consulta las mascotas registradas por familia biológica.
     *
     * @param familia Familia biológica (ej. Felidae, Canidae, etc.)
     * @return Lista de mascotas de la familia especificada.
     */
    @Override
    public List<MascotaVO> consultarPorFamilia(String familia) {
        return mascotaDAO.consultarPorFamilia(familia);
    }

    /**
     * Consulta las mascotas registradas por tipo de alimento.
     *
     * @param alimento Tipo de alimento (Herbívoro, Carnívoro, Omnívoro, etc.)
     * @return Lista de mascotas con el tipo de alimento indicado.
     */
    @Override
    public List<MascotaVO> consultarPorAlimento(String alimento) {
        return mascotaDAO.consultarPorAlimento(alimento);
    }

    // -------------------------------------------------------------------------
    // MÉTODOS DE SERIALIZACIÓN
    // -------------------------------------------------------------------------

    /**
     * Serializa todas las mascotas excluyendo el campo "alimento", 
     * cumpliendo con el requerimiento de la entidad IDPYBA.
     *
     * @param rutaArchivo Ruta completa del archivo de salida (.ser).
     * @return {@code true} si la serialización se realizó exitosamente.
     */
    @Override
    public boolean serializarMascotasSinAlimento(String rutaArchivo) {
        try {
            List<MascotaVO> mascotas = listarTodasMascotas();
            serializacionService.serializarSinAlimento(mascotas, rutaArchivo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Guarda el estado actual de las mascotas utilizando un archivo de acceso aleatorio.
     * <p>
     * Este método permite persistir el estado antes de cerrar la aplicación.
     * </p>
     *
     * @param rutaArchivo Ruta completa del archivo de estado.
     * @return {@code true} si el guardado fue exitoso.
     */
    @Override
    public boolean guardarEstadoMascotas(String rutaArchivo) {
        try {
            List<MascotaVO> mascotas = listarTodasMascotas();
            serializacionService.guardarEstadoRandomAccess(mascotas, rutaArchivo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // -------------------------------------------------------------------------
    // MÉTODOS DE VALIDACIÓN
    // -------------------------------------------------------------------------

    /**
     * Verifica si existe una mascota registrada con el apodo indicado.
     *
     * @param apodo Apodo a verificar.
     * @return {@code true} si existe una mascota con ese apodo.
     */
    @Override
    public boolean existeMascotaPorApodo(String apodo) {
        return !mascotaDAO.consultarPorApodo(apodo).isEmpty();
    }

    /**
     * Verifica si ya existe una mascota con exactamente las mismas características
     * que otra (nombre, clasificación, familia, género, especie, alimento y apodo).
     *
     * @param nueva Mascota a verificar.
     * @return {@code true} si existe una mascota idéntica.
     */
    private boolean existeMascotaExacta(MascotaVO nueva) {
        List<MascotaVO> existentes = mascotaDAO.consultarPorApodo(nueva.getApodo());
        return existentes.stream().anyMatch(existente ->
            existente.getNombre().equalsIgnoreCase(nueva.getNombre()) &&
            existente.getClasificacion().equalsIgnoreCase(nueva.getClasificacion()) &&
            existente.getFamilia().equalsIgnoreCase(nueva.getFamilia()) &&
            existente.getGenero().equalsIgnoreCase(nueva.getGenero()) &&
            existente.getEspecie().equalsIgnoreCase(nueva.getEspecie()) &&
            existente.getAlimento().equalsIgnoreCase(nueva.getAlimento())
        );
    }
}
