package sistemaAutogestion;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class DeshacerUltimosRetiros2_11Test {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
    }

    @Test
    public void DeshacerUltimosRetirosOk() {
        //Creo el usuario
        s.registrarUsuario("00000001", "Usuario01");
        s.registrarUsuario("00000002", "Usuario02");
        s.registrarUsuario("00000003", "Usuario03");
        s.registrarUsuario("00000004", "Usuario04");
        s.registrarUsuario("00000005", "Usuario05");

        //Creo la estacion
        s.registrarEstacion("E01", "E", 3);
        s.registrarEstacion("A01", "A", 3);

        //Creo la bici
        s.registrarBicicleta("E00001", "ELECTRICA");
        s.registrarBicicleta("E00002", "ELECTRICA");
        s.registrarBicicleta("U00001", "URBANA");
        s.registrarBicicleta("U00002", "URBANA");
        s.registrarBicicleta("M00001", "MOUNTAIN");
        s.registrarBicicleta("M00002", "MOUNTAIN");

        //Asigno la bici a la estacion
        s.asignarBicicletaAEstacion("E00001", "E01");
        s.asignarBicicletaAEstacion("E00002", "E01");
        s.asignarBicicletaAEstacion("U00001", "E01");
        s.asignarBicicletaAEstacion("U00002", "A01");
        s.asignarBicicletaAEstacion("M00001", "A01");
        s.asignarBicicletaAEstacion("M00002", "A01");

        //Alquilo la bici
        s.alquilarBicicleta("00000001", "E01");
        s.alquilarBicicleta("00000002", "E01");
        s.alquilarBicicleta("00000003", "E01");
        s.alquilarBicicleta("00000004", "A01");
        s.alquilarBicicleta("00000005", "A01");

        //La forma en que toma las bicis al alquilar es la primera en estado disponible.
        //Pero las bicis en la ListaO de estacion se ordenan alfabeticamente, por lo que estan ordenadas de esta manera
        //Se espera del metodo: codigoBici#cedula#estacionOrigen|....
        //Al deshacer los 3 ultimos alquileres me deberia devolver: "M00002#00000005#A01|M00001#00000004#A01|U00001#00000003#E01"
        retorno = s.deshacerUltimosRetiros(3);
        assertEquals("M00002#00000005#A01|M00001#00000004#A01|U00001#00000003#E01", retorno.getValorString());       
        
        retorno = s.listarBicicletasDeEstacion("A01");
        assertEquals("M00001|M00002|U00002", retorno.getValorString());
        retorno = s.listarBicicletasDeEstacion("E01");
        assertEquals("U00001", retorno.getValorString());
        
        //Deshacer los n ultimos retiros aunque la esstacion este llena
        //Al deshacer los ultimos 3 alquileres, la estacion E01 me quedo con una sola bici disponible, voy a tomar 2 de las bicis de la otra estacion y llenar la estacion E01
        s.asignarBicicletaAEstacion("M00001", "E01");
        s.asignarBicicletaAEstacion("M00002", "E01");
        
        //Una vez asignadas, las alquilo todas por el usuario 3, 4 y 5
        s.alquilarBicicleta("00000003", "E01");
        s.alquilarBicicleta("00000004", "E01");
        s.alquilarBicicleta("00000005", "E01");
        
        retorno = s.listarBicicletasDeEstacion("E01");
        assertEquals(null, retorno.getValorString());

        //Ahora deberia tener 5 alquileres de los usuarios 1, 2, 3, 4 y 5 entonces deshago todos los alquileres
        //Al intentar devolver los ultimos dos no hay lugar, entonces quedan alquiladas
        //"U00001#00000005#A01|M00002#00000004#A01|M00001#00000003#A01"
        
        retorno = s.deshacerUltimosRetiros(5);
        assertEquals("U00001#00000005#E01|M00002#00000004#E01|M00001#00000003#E01", retorno.getValorString());
        
        retorno = s.listarBicicletasDeEstacion("E01");
        assertEquals("M00001|M00002|U00001", retorno.getValorString());
    }

    @Test
    public void DeshacerUltimosRetirosError01() {
        retorno = s.deshacerUltimosRetiros(0);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.deshacerUltimosRetiros(-2);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    //No hay bicicletas alquiladas
    @Test
    public void DeshacerUltimosRetirosError02() {
        retorno = s.deshacerUltimosRetiros(2);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    //La cantidad a deshacer es mayor que la cantidad de bicicletas alquiladas
    @Test
    public void DeshacerUltimosRetirosError03() {
        //Creo el usuario
        s.registrarUsuario("00000001", "Usuario01");

        //Creo la estacion
        s.registrarEstacion("E01", "E", 5);

        //Creo la bici
        s.registrarBicicleta("E00001", "ELECTRICA");

        //Asigno la bici a la estacion
        s.asignarBicicletaAEstacion("E00001", "E01");

        //Alquilo la bici
        s.alquilarBicicleta("00000001", "E01");

        retorno = s.deshacerUltimosRetiros(2);
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }
}
