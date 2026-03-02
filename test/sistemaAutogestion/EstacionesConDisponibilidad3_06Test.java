package sistemaAutogestion;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class EstacionesConDisponibilidad3_06Test {

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
        s.registrarEstacion("A02", "A", 12);
        s.registrarEstacion("A03", "A", 12);
        s.registrarEstacion("A04", "A", 12);

        s.registrarBicicleta("U00001", "URBANA");
        s.registrarBicicleta("M00001", "MOUNTAIN");
        s.registrarBicicleta("E00001", "ELECTRICA");
        s.registrarBicicleta("U00002", "URBANA");
        s.registrarBicicleta("M00002", "MOUNTAIN");
        s.registrarBicicleta("E00002", "ELECTRICA");
        s.registrarBicicleta("U00003", "URBANA");
        s.registrarBicicleta("M00003", "MOUNTAIN");
        s.registrarBicicleta("E00003", "ELECTRICA");
        s.registrarBicicleta("U00004", "URBANA");
        s.registrarBicicleta("M00004", "MOUNTAIN");
        s.registrarBicicleta("E00004", "ELECTRICA");
        s.registrarBicicleta("U00005", "URBANA");
        s.registrarBicicleta("M00005", "MOUNTAIN");
        s.registrarBicicleta("E00005", "ELECTRICA");
        s.registrarBicicleta("U00006", "URBANA");
        s.registrarBicicleta("M00006", "MOUNTAIN");
        s.registrarBicicleta("E00006", "ELECTRICA");

        s.asignarBicicletaAEstacion("M00001", "A02");
        s.asignarBicicletaAEstacion("U00001", "A02");
        s.asignarBicicletaAEstacion("E00001", "A02");
        s.asignarBicicletaAEstacion("M00002", "A02");
        s.asignarBicicletaAEstacion("U00002", "A02");
        s.asignarBicicletaAEstacion("E00002", "A02");
        s.asignarBicicletaAEstacion("M00003", "A03");
        s.asignarBicicletaAEstacion("U00003", "A03");
        s.asignarBicicletaAEstacion("E00003", "A03");
        s.asignarBicicletaAEstacion("M00004", "A03");
        s.asignarBicicletaAEstacion("U00004", "A03");
        s.asignarBicicletaAEstacion("E00004", "A03");

        retorno = s.estacionesConDisponibilidad(5);
        assertEquals(2, retorno.getValorEntero());

        s.asignarBicicletaAEstacion("M00003", "A02");
        s.asignarBicicletaAEstacion("U00003", "A02");
        s.asignarBicicletaAEstacion("E00003", "A02");
        s.asignarBicicletaAEstacion("M00004", "A02");
        s.asignarBicicletaAEstacion("U00004", "A02");
        s.asignarBicicletaAEstacion("E00004", "A02");

        retorno = s.estacionesConDisponibilidad(11);
        assertEquals(1, retorno.getValorEntero());
    }

    @Test
    public void ListarBicicletasDeEstacionError01() {
        retorno = s.estacionesConDisponibilidad(1);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.estacionesConDisponibilidad(0);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.estacionesConDisponibilidad(-1);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }
}
