package sistemaAutogestion;

import dominio.Estacion;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EliminarEstacion2_07Test {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
    }

    @Test
    public void eliminarEstacionOk() {
        s.registrarEstacion("A01", "A", 5);
        //Si listar estacion no me da error 1 quiere decir que la misma existe
        retorno = s.listarBicicletasDeEstacion("A01");
        assertNotEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        
        
        retorno = s.eliminarEstacion("A01");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        
        //Si listar estacion me da error 1 quiere decir que la misma no existe entonces se eliminó
        retorno = s.listarBicicletasDeEstacion("A01");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void eliminarEstacionError1() {
        //Parametro null/vacio
        retorno = s.eliminarEstacion("");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.eliminarEstacion("   ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.eliminarEstacion(null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void eliminarEstacionError2() {
        s.registrarEstacion("A01", "A", 5);
        retorno = s.eliminarEstacion("ZZZ");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void eliminarEstacionError3() {
        s.registrarUsuario("00000001", "Usuario01");
        s.registrarUsuario("00000002", "Usuario02");
        s.registrarUsuario("00000003", "Usuario03");
        s.registrarUsuario("00000004", "Usuario04");

        s.registrarEstacion("A01", "A", 1);
        s.registrarBicicleta("E00001", "ELECTRICA");
        s.asignarBicicletaAEstacion("E00001", "A01");

        //Intento eliminar una estacion con bicis disponibles
        retorno = s.eliminarEstacion("A01");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());

        //Alquilo la bicicleta
        s.alquilarBicicleta("00000001", "A01");
        //Pongo al otro usuario en lista de espera para alquilar
        s.alquilarBicicleta("00000002", "A01");
        
        //Intento borrar la estacion con cola de espera para alquilar
        retorno = s.eliminarEstacion("A01");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
        
        s.registrarBicicleta("E00002", "ELECTRICA");
        s.asignarBicicletaAEstacion("E00002", "A01");
        //Al devolver la bici del usuario 1 se le asigna al usuario 2 que esta esperando alquilar
        s.devolverBicicleta("00000001", "A01");
        //Devuelvo el alquiler del usuario 2 para generar la cola en espera de entrega.
        s.devolverBicicleta("00000002", "A01");
        
        //Intento borrar la estacion con cola de espera para entregar
        retorno = s.eliminarEstacion("A01");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());       
        
        //Intento borrar con ambas colas llenas, tanto de espera para alquilar como espera para entregar y lista de bicicleta
        s.alquilarBicicleta("00000003", "A01");
        s.alquilarBicicleta("00000004", "A01");
        
        s.registrarBicicleta("E00003", "ELECTRICA");
        s.asignarBicicletaAEstacion("E00003", "A01");

        retorno = s.eliminarEstacion("A01");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());       
    }
}
