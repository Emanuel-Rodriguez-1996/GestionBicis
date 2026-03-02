package sistemaAutogestion;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class DevolverBicicleta2_10Test {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
    }

    @Test
    public void DevolverBicicletaOk() {
        //Registro el usuario
        retorno = s.registrarUsuario("00000001", "Usuario 01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        retorno = s.registrarUsuario("00000002", "Usuario 02");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        //Creo la bicicleta
        retorno = s.registrarBicicleta("E00001", "Electrica");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        retorno = s.registrarBicicleta("E00002", "Electrica");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        //Creo la estacion
        retorno = s.registrarEstacion("A01", "A", 3);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        retorno = s.registrarEstacion("B01", "B", 3);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        //Asigno la bicicleta a la estacion
        retorno = s.asignarBicicletaAEstacion("E00001", "A01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        retorno = s.asignarBicicletaAEstacion("E00002", "A01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        //Verifico las bicicletas de la estacion
        retorno = s.listarBicicletasDeEstacion("A01");
        assertEquals("E00001|E00002", retorno.getValorString());

        //Alquilo la bicicleta
        retorno = s.alquilarBicicleta("00000001", "A01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        retorno = s.alquilarBicicleta("00000002", "A01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        //Verifico las bicicletas de la estacion
        retorno = s.listarBicicletasDeEstacion("A01");
        assertEquals(null, retorno.getValorString());

        //Devuelvo la bicicleta a la misma estacion
        retorno = s.devolverBicicleta("00000001", "A01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        //Verifico las bicicletas de la estacion
        retorno = s.listarBicicletasDeEstacion("A01");
        assertEquals("E00001", retorno.getValorString());

        //Devuelvo la bicicleta a otra estacion
        retorno = s.devolverBicicleta("00000002", "B01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        //Verifico las bicicletas de la estacion
        retorno = s.listarBicicletasDeEstacion("B01");
        assertEquals("E00002", retorno.getValorString());
    }

    @Test
    public void DevolverBicicletaError01() {
        //Error 1: Si alguno de los parámetros es null o vacío.
        //Retorno devolverBicicleta(String cedula, String nombreEstacionDestino);
        retorno = s.devolverBicicleta(null, null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.devolverBicicleta("", "");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.devolverBicicleta("    ", "    ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.devolverBicicleta(null, "E01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.devolverBicicleta("00000001", null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.devolverBicicleta("", "E01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.devolverBicicleta("00000001", "");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.devolverBicicleta("   ", "E01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.devolverBicicleta("00000001", "   ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void DevolverBicicletaError02() {
        //Intento devolver una bicicleta con un usuario inexistente
        retorno = s.devolverBicicleta("33333333", "A01");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        //Intento devolver la bicicleta con un usuario el cual no tiene bicicleta alquilada
        retorno = s.devolverBicicleta("00000001", "A01");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void DevolverBicicletaError03() {
        //Registro el usuario
        retorno = s.registrarUsuario("00000001", "Usuario 01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        //Creo la bicicleta
        retorno = s.registrarBicicleta("E00001", "Electrica");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        //Creo la estacion
        retorno = s.registrarEstacion("A01", "A", 3);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        //Asigno la bicicleta a la estacion
        retorno = s.asignarBicicletaAEstacion("E00001", "A01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        //Alquilo la bicicleta
        retorno = s.alquilarBicicleta("00000001", "A01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        //Intento devolverla a una estacion inexistente
        retorno = s.devolverBicicleta("00000001", "B01");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }
}
