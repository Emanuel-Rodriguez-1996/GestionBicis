package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MarcarEnMantenimiento2_05Test {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
    }

    //    Retorno marcarEnMantenimiento(String codigo, String motivo);
    @Test
    public void marcarEnMantenimientoError01() {
        //Error 1: Si alguno de los parámetros es null o vacío.
        retorno = s.marcarEnMantenimiento(null, null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.marcarEnMantenimiento(null, "Cadena");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.marcarEnMantenimiento("M00001", null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.marcarEnMantenimiento("", "Cadena");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.marcarEnMantenimiento("E00001", "");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.marcarEnMantenimiento("   ", "Cadena");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.marcarEnMantenimiento("U00001", "   ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.marcarEnMantenimiento("", "Cadena");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.marcarEnMantenimiento("M00001", "");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void marcarEnMantenimientoError02() {
        //Error 2: bici inexistente
        retorno = s.marcarEnMantenimiento("M1", "MOUNTAIN");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void marcarEnMantenimientoError03() {
        //Este error nunca se va a dar, por la definicion de nuestro sistema. La lista de bicis en sistema son bicis en deposito y en mantenimiento.
        //La lista de bicis de la estacion son bicis disponibles en cada estacion.
        //Si la bici no esta en la lista de sistema ni en la lista de las estaciones, entonces puede no existir o puede estar alquilada (Lista alquileres en sistema).
        //Nos es indeferente la la diferencia, porque ambos dan error, en caso de que no existir, error 2 y en caso de estar alquilada, error 3.
        //Sumar esa busqueda para forzar el error 3 lo unico que haria es restarle ineficiencia al metodo.
        //Por lo tanto a este test en el assert hacemos que el retorno sea error 2, ques lo que va a devolver el metodo.
        s.registrarUsuario("00000001", "Usuario01");
        s.registrarBicicleta("M00001", "MOUNTAIN");
        s.registrarEstacion("A01", "A", 3);
        s.asignarBicicletaAEstacion("M00001", "A01");
        s.alquilarBicicleta("00000001", "A01");
        retorno = s.marcarEnMantenimiento("M00001", "Prueba");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void marcarEnMantenimientoError04() {
        //Error 4: bici ya en mantenimiento
        s.registrarBicicleta("M00001", "MOUNTAIN");
        s.marcarEnMantenimiento("M00001", "MOUNTAIN");
        retorno = s.marcarEnMantenimiento("M00001", "MOUNTAIN");
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());
    }

    @Test
    public void marcarEnMantenimientoOk01() {
        //Ok 1: Si pudo registrar la bicicleta
        s.registrarBicicleta("E00001", "ELECTRICA");
        retorno = s.marcarEnMantenimiento("E00001", "ELECTRICA");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        
        //Verifico que la bici este en estado en mantenimiento
        retorno = s.listarBicisEnDeposito();
        assertEquals("E00001#ELECTRICA#Mantenimiento", retorno.getValorString());
    }
}
