package udistrital.avanzada.mascotasexoticas.control;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import udistrital.avanzada.mascotasexoticas.modelo.MascotaVO;
import udistrital.avanzada.mascotasexoticas.modelo.DAO.ICRUDMascota;
import udistrital.avanzada.mascotasexoticas.modelo.conexion.ISerializacionService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Clase de pruebas unitarias para la clase {@link ControlMascota}.
 *
 * Esta clase valida el comportamiento de la lógica de negocio sin necesidad de conectarse a
 * una base de datos real ni a servicios externos. Para lograr esto se utilizan objetos simulados
 * (mocks), lo que permite aislar el código de producción y verificar que las interacciones con
 * las dependencias sean correctas.
 *
 *  Tecnologías utilizadas:
 * - **JUnit 4**: Framework principal para la ejecución de pruebas unitarias en Java.
 * - **Mockito**: Biblioteca para crear mocks de interfaces y clases, facilitando pruebas aisladas.
 * - **Byte Buddy** (byte-buddy-1.10.22.jar): Biblioteca utilizada internamente por Mockito para
 *   generar dinámicamente las clases proxy en tiempo de ejecución.
 * - **Objenesis** (objenesis-3.2.jar): Biblioteca que permite instanciar objetos sin llamar
 *   directamente a sus constructores. Mockito la usa para crear mocks incluso de clases que
 *   no tienen constructores públicos.
 *
 * Justificación del uso de las librerías:
 * - Con Mockito se evitan dependencias reales, permitiendo probar únicamente la lógica
 *   de la clase `ControlMascota`.
 * - Byte Buddy y Objenesis son requeridas por Mockito para crear los mocks de forma eficiente
 *   y flexible, sin necesidad de modificar el código original.
 *
 *  Escenarios probados:
 * - Adición de una nueva mascota cuando no existe previamente.
 * - Lanzamiento de excepción si se intenta agregar una mascota duplicada.
 * - Modificación de una mascota existente.
 * - Eliminación de una mascota por apodo.
 * - Listado de todas las mascotas registradas.
 * - Serialización de mascotas sin alimento.
 * - Guardado del estado de las mascotas mediante un servicio de serialización.
 *
 * Resultado esperado:
 * Cada prueba debe validar tanto el resultado como la interacción con los mocks,
 * asegurando que la lógica de negocio funcione correctamente y que los métodos
 * de las dependencias se llamen con los parámetros esperados.
 *
 * Importante (Proyecto con Ant):
 * Como este proyecto no utiliza Maven, es necesario **agregar manualmente** las librerías .jar
 * al classpath para que las pruebas se ejecuten correctamente:
 *   - `junit-4.x.jar`
 *   - `mockito-core-3.12.4.jar`
 *   - `byte-buddy-1.10.22.jar`
 *   - `objenesis-3.2.jar`
 *
 * Esto se puede hacer desde:
 *   `Propiedades del proyecto > Librerías > Añadir JAR/Carpeta` en NetBeans.
 *
 * @author 
 * @version 1.0
 * @since 2025-10-15
 */

public class ControlMascotaTest {

    private ICRUDMascota mascotaDAOMock;
    private ISerializacionService serializacionMock;
    private ControlMascota controlMascota;

    @Before
    public void setUp() {
        mascotaDAOMock = Mockito.mock(ICRUDMascota.class);
        serializacionMock = Mockito.mock(ISerializacionService.class);
        controlMascota = new ControlMascota(mascotaDAOMock, serializacionMock);
    }

    @Test
    public void testAdicionarMascota_CuandoNoExiste_DeberiaAgregar() {
        MascotaVO mascota = new MascotaVO("Luna", "Ave", "Psittacidae", "Hembra", "Loro", "Herbívoro", "Lunita");
        when(mascotaDAOMock.consultarPorApodo("Lunita")).thenReturn(new ArrayList<>());
        when(mascotaDAOMock.adicionarMascota(mascota)).thenReturn(true);

        boolean resultado = controlMascota.adicionarMascota(mascota);

        assertTrue(resultado);
        verify(mascotaDAOMock).adicionarMascota(mascota);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAdicionarMascota_CuandoYaExiste_DeberiaLanzarExcepcion() {
        MascotaVO mascota = new MascotaVO("Luna", "Ave", "Psittacidae", "Hembra", "Loro", "Herbívoro", "Lunita");
        List<MascotaVO> existentes = new ArrayList<>();
        existentes.add(mascota);
        when(mascotaDAOMock.consultarPorApodo("Lunita")).thenReturn(existentes);

        controlMascota.adicionarMascota(mascota);
    }

    @Test
    public void testModificarMascota_CuandoExiste_DeberiaModificar() {
        MascotaVO mascota = new MascotaVO("Luna", "Ave", "Psittacidae", "Hembra", "Loro", "Herbívoro", "Lunita");
        List<MascotaVO> existentes = new ArrayList<>();
        existentes.add(mascota);

        when(mascotaDAOMock.consultarPorApodo("Lunita")).thenReturn(existentes);
        when(mascotaDAOMock.modificarMascota(mascota)).thenReturn(true);

        boolean resultado = controlMascota.modificarMascota(mascota);

        assertTrue(resultado);
        verify(mascotaDAOMock).modificarMascota(mascota);
    }

    @Test
    public void testEliminarMascota_DeberiaLlamarDAO() {
        when(mascotaDAOMock.eliminarMascota("Lunita")).thenReturn(true);

        boolean resultado = controlMascota.eliminarMascota("Lunita");

        assertTrue(resultado);
        verify(mascotaDAOMock).eliminarMascota("Lunita");
    }

    @Test
    public void testListarTodasMascotas_DeberiaRetornarLista() {
        List<MascotaVO> lista = new ArrayList<>();
        lista.add(new MascotaVO("Luna", "Ave", "Psittacidae", "Hembra", "Loro", "Herbívoro", "Lunita"));
        when(mascotaDAOMock.listarTodasMascotas()).thenReturn(lista);

        List<MascotaVO> resultado = controlMascota.listarTodasMascotas();

        assertEquals(1, resultado.size());
        assertEquals("Luna", resultado.get(0).getNombre());
    }

    @Test
    public void testSerializarMascotasSinAlimento_DeberiaLlamarServicio() throws Exception {
        when(mascotaDAOMock.listarTodasMascotas()).thenReturn(new ArrayList<>());
        doNothing().when(serializacionMock).serializarSinAlimento(anyList(), anyString());

        boolean resultado = controlMascota.serializarMascotasSinAlimento("archivo.ser");

        assertTrue(resultado);
        verify(serializacionMock).serializarSinAlimento(anyList(), eq("archivo.ser"));
    }

    @Test
    public void testGuardarEstadoMascotas_DeberiaLlamarServicio() throws Exception {
        when(mascotaDAOMock.listarTodasMascotas()).thenReturn(new ArrayList<>());
        doNothing().when(serializacionMock).guardarEstadoRandomAccess(anyList(), anyString());

        boolean resultado = controlMascota.guardarEstadoMascotas("estado.dat");

        assertTrue(resultado);
        verify(serializacionMock).guardarEstadoRandomAccess(anyList(), eq("estado.dat"));
    }
}
