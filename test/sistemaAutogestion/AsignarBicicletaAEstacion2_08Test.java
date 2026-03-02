package sistemaAutogestion;

import dominio.TipoBicicleta;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class AsignarBicicletaAEstacion2_08Test {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
    }

    @Test
    public void AsignarBicicletaAEstacionOk() {
        //Registro bici
        retorno = s.registrarBicicleta("E00001", "Electrica");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        //Registro estacion
        retorno = s.registrarEstacion("E01", "E", 5);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        
        //Cuando registro una estacion, tendria que sumarse correctamente la capacidad al barrio
        
        //Asigno bici a estacion
        retorno = s.asignarBicicletaAEstacion("E00001", "E01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        
        retorno = s.listarBicicletasDeEstacion("E01");
        assertEquals("E00001", retorno.getValorString());
    }
    
    @Test
    public void AsignarBicicletaAEstacionError01() {
        //Error 1: Si alguno de los parámetros es null o vacío.
        //Retorno asignarBicicletaAEstacion(String codigo, String nombreEstacion); 
        retorno = s.asignarBicicletaAEstacion(null, null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.asignarBicicletaAEstacion("", "");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.asignarBicicletaAEstacion("    ", "    ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.asignarBicicletaAEstacion(null, "E01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.asignarBicicletaAEstacion("E00001", null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.asignarBicicletaAEstacion("", "E01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.asignarBicicletaAEstacion("E00001", "");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.asignarBicicletaAEstacion("   ", "E01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.asignarBicicletaAEstacion("E00001", "   ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void AsignarBicicletaAEstacionError02() {
        //Registro 3 bicis
        s.registrarBicicleta("E00001", "Electrica");
        s.registrarBicicleta("E00002", "Electrica");
        s.registrarBicicleta("E00003", "Electrica");

        //Registro 2 estaciones
        s.registrarEstacion("A01", "A", 3);
        s.registrarEstacion("B01", "B", 3);

        //La 3 la asigno a una estacion Y la alquilo
        retorno = s.asignarBicicletaAEstacion("E00003", "A01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        //La 1 la marco en Mantenimiento
        retorno = s.marcarEnMantenimiento("E00001", "Pinchazo");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        //Asigno una bicicleta inexistente a una estacion
        retorno = s.asignarBicicletaAEstacion("EEEEEE", "A01");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        //Asigno una bici en mantenimiento (no disponible) a la estacion
        retorno = s.asignarBicicletaAEstacion("E00001", "A01");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void AsignarBicicletaAEstacionError03() {
        //Creo una bici
        s.registrarBicicleta("E00001", "Electrica");

        //Creo una estacion
        s.registrarEstacion("A01", "A", 3);

        //Asigno una bicicleta a una estacion inexistente
        retorno = s.asignarBicicletaAEstacion("E00001", "A20");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    @Test
    public void AsignarBicicletaAEstacionError04() {
        //Creo una bici
        s.registrarBicicleta("E00001", "Electrica");
        s.registrarBicicleta("E00002", "Electrica");
        s.registrarBicicleta("E00003", "Electrica");
        s.registrarBicicleta("E00004", "Electrica");

        //Creo una estacion
        s.registrarEstacion("A01", "A", 3);

        //Asigno las 3 bicicletas a la estacion de capacidad 3
        retorno = s.asignarBicicletaAEstacion("E00001", "A01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        retorno = s.asignarBicicletaAEstacion("E00002", "A01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        retorno = s.asignarBicicletaAEstacion("E00003", "A01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        //Asigno una bicicleta a una estacion sin anclajes libres
        retorno = s.asignarBicicletaAEstacion("E00004", "A01");
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());
    }
}
