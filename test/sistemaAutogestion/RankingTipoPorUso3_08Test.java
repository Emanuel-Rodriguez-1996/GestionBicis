package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RankingTipoPorUso3_08Test {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
    }

    @Test
    public void testRankingTiposPorUso() {
        s.registrarEstacion("A01", "A", 5);

        s.registrarUsuario("00000001", "Usuario01");
        s.registrarUsuario("00000002", "Usuario02");
        s.registrarUsuario("00000003", "Usuario03");
        s.registrarUsuario("00000004", "Usuario04");
        s.registrarUsuario("00000005", "Usuario05");
        s.registrarUsuario("00000006", "Usuario06");
        s.registrarUsuario("00000007", "Usuario07");

        s.registrarBicicleta("E00001", "ELECTRICA");
        s.registrarBicicleta("E00002", "ELECTRICA");
        s.registrarBicicleta("U00001", "URBANA");
        s.registrarBicicleta("U00002", "URBANA");
        s.registrarBicicleta("M00001", "MOUNTAIN");
        s.registrarBicicleta("M00002", "MOUNTAIN");
        s.registrarBicicleta("M00003", "MOUNTAIN");

        s.asignarBicicletaAEstacion("E00001", "A01");
        s.asignarBicicletaAEstacion("U00001", "A01");
        s.asignarBicicletaAEstacion("U00002", "A01");

        retorno = s.alquilarBicicleta("00000001", "A01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        retorno = s.rankingTiposPorUso();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("ELECTRICA#1|MOUNTAIN#0|URBANA#0", retorno.getValorString());

        retorno = s.alquilarBicicleta("00000002", "A01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        retorno = s.alquilarBicicleta("00000003", "A01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        retorno = s.rankingTiposPorUso();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("URBANA#2|ELECTRICA#1|MOUNTAIN#0", retorno.getValorString());

        s.asignarBicicletaAEstacion("M00001", "A01");
        s.asignarBicicletaAEstacion("M00002", "A01");
        s.asignarBicicletaAEstacion("M00003", "A01");

        retorno = s.alquilarBicicleta("00000004", "A01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        retorno = s.alquilarBicicleta("00000005", "A01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        retorno = s.alquilarBicicleta("00000006", "A01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        retorno = s.rankingTiposPorUso();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("MOUNTAIN#3|URBANA#2|ELECTRICA#1", retorno.getValorString());

        s.asignarBicicletaAEstacion("E00002", "A01");

        retorno = s.alquilarBicicleta("00000007", "A01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        
        retorno = s.rankingTiposPorUso();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("MOUNTAIN#3|ELECTRICA#2|URBANA#2", retorno.getValorString());        
    }

}
