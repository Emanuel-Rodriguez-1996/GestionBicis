package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RepararBicicleta2_06Test {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
    }

    @Test
    public void RepararBicicletaOk() {
        retorno = s.registrarBicicleta("E00001", "Electrica");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        retorno = s.marcarEnMantenimiento("E00001", "Esta hecha pedazos");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        
        //Verifico que la bici este en estado en mantenimiento
        retorno = s.listarBicisEnDeposito();
        assertEquals("E00001#ELECTRICA#Mantenimiento", retorno.getValorString());

        retorno = s.repararBicicleta("E00001");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        
        //Verifico que la bici este en estado en disponible
        retorno = s.listarBicisEnDeposito();
        assertEquals("E00001#ELECTRICA#Disponible", retorno.getValorString());
    }

    @Test
    public void RepararBicicletaErro01() {
        //Si alguno de los parámetros es null o vacío
        s.registrarBicicleta("BEle01", "Electrica");
        retorno = s.repararBicicleta(null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.repararBicicleta("");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.repararBicicleta(" ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    public void RepararBicicletaErro02() {
        //Bici inexistente
        s.registrarBicicleta("BEle01", "Electrica");
        retorno = s.repararBicicleta("BMou99");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    public void RepararBicicletaErro03() {
        //bici no se encuentra en mantenimiento (estado por defecto al registrar => EnDeposito)
        s.registrarBicicleta("BEle01", "Electrica");
        retorno = s.repararBicicleta("BEle01");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }
}
