package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AlquilarBicicleta2_09Test {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
    }

    @Test
    public void AlquilarBicicletaError01() {
        //Error 1: Si alguno de los parámetros es null o vacío.
        retorno = s.alquilarBicicleta(null, null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.alquilarBicicleta("", "");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.alquilarBicicleta("    ", "    ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.alquilarBicicleta(null, "E01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.alquilarBicicleta("00000001", null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.alquilarBicicleta("", "E01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.alquilarBicicleta("00000001", "");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.alquilarBicicleta("   ", "E01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.alquilarBicicleta("00000001", "   ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void AlquilarBicicletaError02() {
        //Intento alquilar una bici con un usuario inexistente
        retorno = s.alquilarBicicleta("00000001", "E01");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void AlquilarBicicletaError03() {
        //Intento alquilar en una estacion inexistente
        s.registrarUsuario("00000001", "Usuario01");
        retorno = s.alquilarBicicleta("00000001", "E01");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    @Test
    public void AlquilarBicicletaOk() {
        s.registrarUsuario("00000001", "Usuario01");
        s.registrarEstacion("A01", "A", 5);
        s.registrarBicicleta("E00001", "ELECTRICA");
        s.asignarBicicletaAEstacion("E00001", "A01");
        
        retorno = s.alquilarBicicleta("00000001", "A01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        
        //Si deshago los 1 ultimos alquileres, me deberia dar el que acabo de alquilar
        //Si puedo deshacer el alquiler quiere decir que la bicicleta se alquilo correctamente
        retorno = s.deshacerUltimosRetiros(1);
        assertEquals("E00001#00000001#A01", retorno.getValorString());
    }

}
