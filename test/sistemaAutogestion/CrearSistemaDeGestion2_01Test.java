package sistemaAutogestion;


import org.junit.Test;
import static org.junit.Assert.*;


public class CrearSistemaDeGestion2_01Test {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Test
    public void testCrearSistemaDeGestion() {
        retorno = s.crearSistemaDeGestion();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }
    
}
