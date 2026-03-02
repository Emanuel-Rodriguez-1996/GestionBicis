package sistemaAutogestion;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ListarBicicletasDeEstacion3_05Test {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
    }

    @Test
    public void ListarBicicletasDeEstacionOk() {
        // Registrar estaciones
        s.registrarEstacion("A01", "A", 12);

        s.registrarBicicleta("U00001", "URBANA");
        s.registrarBicicleta("M00001", "MOUNTAIN");
        s.registrarBicicleta("E00002", "ELECTRICA");
        s.registrarBicicleta("E00001", "ELECTRICA");

        s.asignarBicicletaAEstacion("M00001", "A01");
        s.asignarBicicletaAEstacion("U00001", "A01");
        s.asignarBicicletaAEstacion("E00002", "A01");
        s.asignarBicicletaAEstacion("E00001", "A01");

        retorno = s.listarBicicletasDeEstacion("A01");

        assertEquals("E00001|E00002|M00001|U00001", retorno.getValorString());
    }
    
    @Test
    public void ListarBicicletasDeEstacionError01() {
        //Listar bicis de estacion inexistente
        retorno = s.listarBicicletasDeEstacion("A01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }
}
