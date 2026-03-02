package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UsuariosEnEspera3_09Test {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
    }

    @Test
    public void UsuariosEnEsperaError01() {
        retorno = s.registrarEstacion("A01", "A", 1);

        retorno = s.registrarBicicleta("E00001", "ELECTRICA");
        
        retorno = s.asignarBicicletaAEstacion("E00001", "A01");

        s.registrarUsuario("00000001", "Prueba01");
        s.registrarUsuario("00000002", "Prueba02");
        s.registrarUsuario("00000003", "Prueba03");
        s.registrarUsuario("00000004", "Prueba04");

        s.alquilarBicicleta("00000001", "A01");
        s.alquilarBicicleta("00000002", "A01");
        s.alquilarBicicleta("00000003", "A01");
        s.alquilarBicicleta("00000004", "A01");

        retorno = s.usuariosEnEspera("         ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        
        retorno = s.usuariosEnEspera("");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }
    
        @Test
    public void UsuariosEnEsperaError02() {
        retorno = s.registrarEstacion("A01", "A", 1);

        retorno = s.registrarBicicleta("E00001", "ELECTRICA");
        
        retorno = s.asignarBicicletaAEstacion("E00001", "A");

        s.registrarUsuario("00000001", "Prueba01");
        s.registrarUsuario("00000002", "Prueba02");

        s.alquilarBicicleta("00000001", "A");
        s.alquilarBicicleta("00000002", "A");

        retorno = s.usuariosEnEspera("BBBB");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }
    
    @Test
    public void UsuariosEnEsperaOk() {
        retorno = s.registrarEstacion("A01", "A", 1);

        retorno = s.registrarBicicleta("E00001", "ELECTRICA");
        
        retorno = s.asignarBicicletaAEstacion("E00001", "A01");

        s.registrarUsuario("00000001", "Prueba01");
        s.registrarUsuario("00000002", "Prueba02");
        s.registrarUsuario("00000003", "Prueba03");
        s.registrarUsuario("00000004", "Prueba04");

        s.alquilarBicicleta("00000001", "A01");
        s.alquilarBicicleta("00000002", "A01");
        s.alquilarBicicleta("00000003", "A01");
        s.alquilarBicicleta("00000004", "A01");

        retorno = s.usuariosEnEspera("A01");
        assertEquals("00000002|00000003|00000004", retorno.getValorString());
    }
}
