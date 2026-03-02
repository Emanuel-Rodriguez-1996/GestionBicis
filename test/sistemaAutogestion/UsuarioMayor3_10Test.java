package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UsuarioMayor3_10Test {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
    }

    @Test
    public void usuarioMayorOk() {

        s.registrarUsuario("00000001", "Usuario1");
        s.registrarUsuario("00000002", "Usuario2");
        s.registrarEstacion("A01", "A", 10);

        s.registrarBicicleta("E00001", "ELECTRICA");
        s.registrarBicicleta("E00002", "ELECTRICA");

        s.asignarBicicletaAEstacion("E00001", "A01");
        s.asignarBicicletaAEstacion("E00002", "A01");

        //Sumamos alquileres, para eso sumamos y devolvemos, para el usuario uno sumamos 1 alquiler, para el 2 dos alquileres
        s.alquilarBicicleta("00000001", "A01");
        s.devolverBicicleta("00000001", "A01");

        s.alquilarBicicleta("00000002", "A01");
        s.devolverBicicleta("00000002", "A01");
        s.alquilarBicicleta("00000002", "A01");
        s.devolverBicicleta("00000002", "A01");

        retorno = s.usuarioMayor();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("00000002", retorno.getValorString());

        //Sumo un alquiler al primer usuario, para empatar en cantidad con el segundo, entonces se deberia mostrar el de cedula mas pequeña es decir el usuario 1
        s.alquilarBicicleta("00000001", "A01");
        s.devolverBicicleta("00000001", "A01");

        retorno = s.usuarioMayor();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("00000001", retorno.getValorString());
    }
}
