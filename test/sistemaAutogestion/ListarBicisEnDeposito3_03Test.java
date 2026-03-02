package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ListarBicisEnDeposito3_03Test {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
    }

    @Test
    public void ListarBicisEnDepositoVacio() {
        retorno = s.listarBicisEnDeposito();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("", retorno.getValorString());
    }

    @Test
    public void listarBicisIngresoSoloUnaBici() {
        s.registrarBicicleta("BEle01", "ELECTRICA");
        retorno = s.listarBicisEnDeposito();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("BEle01#ELECTRICA#Disponible", retorno.getValorString());
    }

    @Test
    public void ListarBicisEnDepositoOk() {
        // Registramos algunos tipos de bicicletas
        s.registrarBicicleta("BEle01", "ELECTRICA");
        s.registrarBicicleta("BUrb02", "URBANA");
        s.registrarBicicleta("BMou03", "MOUNTAIN");

        retorno = s.listarBicisEnDeposito();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        // Verificamos el contenido del string
        String esperado = "BEle01#ELECTRICA#Disponible|BUrb02#URBANA#Disponible|BMou03#MOUNTAIN#Disponible";
        assertEquals(esperado, retorno.getValorString());
    }

    @Test
    public void ListarBicisEnDepositoUnaEnMantenimientoOk() {
        // Registramos algunos tipos de bicicletas
        s.registrarBicicleta("BEle01", "ELECTRICA");
        retorno = s.listarBicisEnDeposito();
        assertEquals("BEle01#ELECTRICA#Disponible", retorno.getValorString());
        
        s.registrarBicicleta("BUrb02", "URBANA");
        retorno = s.listarBicisEnDeposito();
        assertEquals("BEle01#ELECTRICA#Disponible|BUrb02#URBANA#Disponible", retorno.getValorString());
        
        s.registrarBicicleta("BMou03", "MOUNTAIN");

        s.marcarEnMantenimiento("BUrb02", "Motivo");

        retorno = s.listarBicisEnDeposito();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        // Verificamos el contenido del string
        String esperado = "BEle01#ELECTRICA#Disponible|BUrb02#URBANA#Mantenimiento|BMou03#MOUNTAIN#Disponible";
        assertEquals(esperado, retorno.getValorString());
    }
}
