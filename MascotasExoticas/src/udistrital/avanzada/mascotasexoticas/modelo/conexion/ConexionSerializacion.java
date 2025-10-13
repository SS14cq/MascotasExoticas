package udistrital.avanzada.mascotasexoticas.modelo.conexion;

import udistrital.avanzada.mascotasexoticas.modelo.MascotaVO;
import java.io.*;
import java.util.List;

/**
 * <b>Clase ConexionSerializacion</b><br>
 * Implementa la interfaz {@link ISerializacionService} para proporcionar métodos
 * relacionados con la serialización de objetos y el manejo de archivos. <br>
 * Permite:
 * <ul>
 *   <li>Serializar objetos {@link MascotaVO} sin incluir el tipo de alimento.</li>
 *   <li>Guardar información de las mascotas en un archivo de acceso aleatorio (RandomAccessFile).</li>
 * </ul>
 *
 * <p>Esta clase forma parte de la capa de acceso a datos y se utiliza
 * para persistir información en archivos cuando no se usa una base de datos.</p>
 *
 * @author Sofia
 * @version 1.0
 * @since 12-10-2025
 */
public class ConexionSerializacion implements ISerializacionService {

    /**
     * Serializa la información de una lista de mascotas a un archivo,
     * excluyendo el tipo de alimento.
     *
     * <p>Este método convierte cada objeto {@link MascotaVO} en un arreglo de Strings
     * y lo escribe en un archivo utilizando {@link ObjectOutputStream}.</p>
     *
     * @param mascotas Lista de mascotas a serializar.
     * @param rutaArchivo Ruta absoluta o relativa del archivo destino.
     * @throws IllegalArgumentException Si la lista es nula o está vacía.
     * @throws Exception Si ocurre un error al escribir en el archivo.
     */
    @Override
    public void serializarSinAlimento(List<MascotaVO> mascotas, String rutaArchivo) throws Exception {
        if (mascotas == null || mascotas.isEmpty()) {
            throw new IllegalArgumentException("No hay mascotas para serializar");
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            for (MascotaVO mascota : mascotas) {
                String[] datosMascota = {
                    mascota.getNombre(),
                    mascota.getApodo(),
                    mascota.getClasificacion(),
                    mascota.getFamilia(),
                    mascota.getGenero(),
                    mascota.getEspecie()
                };
                oos.writeObject(datosMascota);
            }
        }
    }

    /**
     * Guarda la información completa de las mascotas en un archivo de acceso aleatorio,
     * incluyendo el tipo de alimento.
     *
     * <p>Los campos se almacenan separados por el carácter “|” y cada registro en una nueva línea.
     * Si el archivo ya existe, se sobrescribe completamente.</p>
     *
     * @param mascotas Lista de mascotas a guardar.
     * @param rutaArchivo Ruta absoluta o relativa del archivo destino.
     * @throws IllegalArgumentException Si la lista de mascotas es nula.
     * @throws Exception Si ocurre un error al escribir en el archivo.
     */
    @Override
    public void guardarEstadoRandomAccess(List<MascotaVO> mascotas, String rutaArchivo) throws Exception {
        if (mascotas == null) {
            throw new IllegalArgumentException("Lista de mascotas no puede ser nula");
        }

        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            // Limpia el contenido previo
            raf.setLength(0);

            for (MascotaVO mascota : mascotas) {
                String linea = String.format("%s|%s|%s|%s|%s|%s|%s%n",
                    escapePipe(mascota.getNombre()),
                    escapePipe(mascota.getApodo()),
                    escapePipe(mascota.getClasificacion()),
                    escapePipe(mascota.getFamilia()),
                    escapePipe(mascota.getGenero()),
                    escapePipe(mascota.getEspecie()),
                    escapePipe(mascota.getAlimento())
                );
                raf.writeBytes(linea);
            }
        }
    }

    /**
     * Escapa el carácter pipe (“|”) en una cadena de texto para evitar conflictos
     * con el formato de almacenamiento de datos.
     *
     * @param texto Texto a procesar.
     * @return Texto con los caracteres pipe escapados. Si es nulo, retorna cadena vacía.
     */
    private String escapePipe(String texto) {
        if (texto == null) return "";
        return texto.replace("|", "\\|");
    }
}
