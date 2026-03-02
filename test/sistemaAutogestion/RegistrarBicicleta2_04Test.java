package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RegistrarBicicleta2_04Test {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
    }

    @Test
    public void registrarBicicletaError01() {
        //Error 1: Si alguno de los parámetros es null o vacío.
        retorno = s.registrarBicicleta(null, null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarBicicleta(null, "MOUNTAIN");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.registrarBicicleta("M00001", null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarBicicleta("", "ELECTRICA");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.registrarBicicleta("E00001", "");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarBicicleta("   ", "URBANA");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.registrarBicicleta("U00001", "   ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarBicicleta("", "MOUNTAIN");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
        retorno = s.registrarBicicleta("M00001", "");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void registrarBicicletaError02() {
        //Formato incorrecto de código
        retorno = s.registrarBicicleta("M1", "MOUNTAIN");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarBicicleta("M0001", "MOUNTAIN");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarBicicleta("M10101010", "MOUNTAIN");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void registrarBicicletaError03() {
        //Error 3: El tipo no está dentro de los permitidos
        retorno = s.registrarBicicleta("M00001", "MONTAÑA");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());

        retorno = s.registrarBicicleta("U00001", "e");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    @Test
    public void registrarBicicletaError04() {
        //Error 3: Ya existe bici con ese código
        s.registrarBicicleta("M00001", "MOUNTAIN");
        retorno = s.registrarBicicleta("M00001", "MOUNTAIN");
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());
    }

    @Test
    public void registrarBicicletaOk01() {
        //Ok 1: Si pudo registrar la bicicleta
        retorno = s.registrarBicicleta("E00001", "ELECTRICA");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        //Verifico que la bici este en deposito
        retorno = s.listarBicisEnDeposito();
        assertEquals("E00001#ELECTRICA#Disponible", retorno.getValorString());

        retorno = s.registrarBicicleta("M00002", "mountain");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        //Verifico que la bici este en deposito
        retorno = s.listarBicisEnDeposito();
        assertEquals("E00001#ELECTRICA#Disponible|M00002#MOUNTAIN#Disponible", retorno.getValorString());

        retorno = s.registrarBicicleta("U00003", "UrbAna");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        //Verifico que la bici este en deposito
        retorno = s.listarBicisEnDeposito();
        assertEquals("E00001#ELECTRICA#Disponible|M00002#MOUNTAIN#Disponible|U00003#URBANA#Disponible", retorno.getValorString());

        retorno = s.registrarBicicleta("E00004", "Electrica");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        //Verifico que la bici este en deposito
        retorno = s.listarBicisEnDeposito();
        assertEquals("E00001#ELECTRICA#Disponible|M00002#MOUNTAIN#Disponible|U00003#URBANA#Disponible|E00004#ELECTRICA#Disponible", retorno.getValorString());
    }
}
