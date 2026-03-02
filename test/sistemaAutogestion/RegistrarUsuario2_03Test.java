package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RegistrarUsuario2_03Test {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
    }

    @Test
    public void registrarUsuarioError01() {
        //Error 1: Si alguno de los parámetros es null o vacío.
        retorno = s.registrarUsuario(null, null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarUsuario(null, "Usuario01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.registrarUsuario("00000001", null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarUsuario("", "Usuario01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.registrarUsuario("00000001", "");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarUsuario("   ", "Usuario01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.registrarUsuario("00000001", "   ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarUsuario("", "Usuario01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.registrarUsuario("00000001", "");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void registrarUsuarioError02() {
        //Error 2: formato cédula inválido
        retorno = s.registrarUsuario("00", "Usuario01");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarUsuario("0000000", "Usuario01");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarUsuario("000000010", "Usuario01");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarUsuario("AAAAAAA1", "Usuario01");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarUsuario("AAAAAAAA", "Usuario01");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarUsuario("0000000A", "Usuario01");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void registrarUsuarioError03() {
        //Error 3: ya existe usuario con esa cédula
        s.registrarUsuario("00000001", "Usuario01");
        retorno = s.registrarUsuario("00000001", "Usuario01");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    @Test
    public void registrarUsuarioOk01() {
        //Ok 1: Si pudo registrar el usuario en el sistema.
        retorno = s.registrarUsuario("00000001", "Usuario01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        
        retorno = s.listarUsuarios();
        assertEquals("Usuario01#00000001", retorno.getValorString());

        retorno = s.registrarUsuario("00000002", "Usuario02");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        
        retorno = s.listarUsuarios();
        assertEquals("Usuario01#00000001|Usuario02#00000002", retorno.getValorString());
    }
}
