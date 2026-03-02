package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ObtenerUsuario3_01Test {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
    }

    @Test
    public void obtenerUsuarioOk() {
        s.registrarUsuario("00000001", "A");
        retorno = s.obtenerUsuario("00000001");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("A#00000001", retorno.getValorString());
    }

    @Test
    public void obtenerUsuarioError01() {
        this.retorno = s.registrarUsuario(null, null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        this.retorno = s.registrarUsuario("12345678", "");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        this.retorno = s.registrarUsuario("", "Usuario01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        this.retorno = s.registrarUsuario("12345678", "   ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        this.retorno = s.registrarUsuario("   ", "Usuario01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        this.retorno = s.registrarUsuario("12345678", null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        this.retorno = s.registrarUsuario(null, "Usuario01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void obtenerUsuarioError02() {

        retorno = s.obtenerUsuario("1234567");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.obtenerUsuario("123456789");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void obtenerUsuarioError03() {

        retorno = s.obtenerUsuario("87654321");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());

        s.registrarUsuario("12345678", "Usuario01");
        retorno = s.obtenerUsuario("99999999");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

}
