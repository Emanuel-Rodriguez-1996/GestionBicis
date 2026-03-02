package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ListarUsuarios3_02Test {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
    }

    @Test
    public void listarUsuariosVacio() {
        retorno = s.listarUsuarios();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(null, retorno.getValorString());
    }

    @Test
    public void listarUsuariosSoloUnUsuario() {
        s.registrarUsuario("00000001", "A");
        retorno = s.listarUsuarios();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("A#00000001", retorno.getValorString());
    }

    @Test
    public void listarUsuariosIngresoOrdenado() {
        s.registrarUsuario("00000001", "A");
        s.registrarUsuario("00000002", "B");
        s.registrarUsuario("00000003", "C");
        retorno = s.listarUsuarios();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("A#00000001|B#00000002|C#00000003", retorno.getValorString());
    }
    
    @Test
    public void listarUsuariosIngresoNombreRepetido() {
        s.registrarUsuario("00000001", "A");
        s.registrarUsuario("00000002", "B");
        s.registrarUsuario("00000003", "C");
        s.registrarUsuario("00000004", "C");
        s.registrarUsuario("00000005", "A");

        retorno = s.listarUsuarios();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("A#00000001|A#00000005|B#00000002|C#00000003|C#00000004", retorno.getValorString());
    }

    @Test
    public void listarUsuariosIngresoDesordenado() {
        s.registrarUsuario("00000001", "A");
        s.registrarUsuario("00000002", "C");
        s.registrarUsuario("00000003", "B");
        s.registrarUsuario("00000004", "H");
        s.registrarUsuario("00000005", "X");
        s.registrarUsuario("00000006", "S");
        s.registrarUsuario("00000007", "F");
        s.registrarUsuario("00000008", "O");
        s.registrarUsuario("00000009", "M");
        s.registrarUsuario("00000010", "N");

        retorno = s.listarUsuarios();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("A#00000001|B#00000003|C#00000002|F#00000007|H#00000004|M#00000009|N#00000010|O#00000008|S#00000006|X#00000005", retorno.getValorString());
    }

}
