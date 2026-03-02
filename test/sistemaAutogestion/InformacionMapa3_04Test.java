package sistemaAutogestion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class InformacionMapa3_04Test {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
    }

    @Test
    public void InformacionMapaEj1() {
        String[][] mapa = {
            {"E1", null, "E6"},
            {null, "E2", "E3"},
            {null, "E4", "E5"}
        };

        retorno = s.informaciónMapa(mapa);
        assertEquals("3#columna|existe", retorno.getValorString());
    }

    @Test
    public void InformacionMapaEj2() {
        String[][] mapa = {
            {"E1", "E2", null},
            {null, "E3", "E4"},
            {null, null, null}
        };

        retorno = s.informaciónMapa(mapa);
        assertEquals("2#ambas|no existe", retorno.getValorString());

    }

    @Test
    public void InformacionMapaEj3() {

        String[][] mapa = {
            {"E1", "E2", null},
            {null, "E3", "E4"},
            {null, null, null}
        };

        retorno = s.informaciónMapa(mapa);
        assertEquals("2#ambas|no existe", retorno.getValorString());
    }

    @Test
    public void InformacionMapaEj4() {
        String[][] mapa = {
            {null, null, null, null, null, null},
            {null, null, null, "E3", null, null},
            {null, null, null, null, null, null},
            {"E1", null, null, null, "E5", null},
            {null, null, null, null, null, null},
            {null, null, "E2", null, "E6", null},
            {null, null, null, null, "E7", null},
            {null, null, null, "E4", null, null}
        };

        retorno = s.informaciónMapa(mapa);
        assertEquals("3#columna|existe", retorno.getValorString());
    }

    @Test
    public void InformacionMapaEj5() {
        String[][] mapa = {
            {null, null, null, null, null, null},
            {null, null, null, "E3", null, null},
            {null, null, null, null, null, null},
            {"E1", null, null, null, "E5", null},
            {null, null, null, null, null, null},
            {null, null, "E2", null, "E6", null},
            {null, null, null, null, null, null},
            {null, null, null, "E4", null, null}
        };

        retorno = s.informaciónMapa(mapa);
        assertEquals("2#ambas|existe", retorno.getValorString());
    }

    @Test
    public void InformacionMapaEj6() {
        String[][] mapa = {
            {null, null, null, null, null, null},
            {null, null, null, "E3", null, null},
            {null, null, null, null, null, null},
            {"E1", null, null, null, "E5", null},
            {null, null, null, null, null, null},
            {null, null, "E2", null, "E6", null},
            {null, "E7", null, null, null, null},
            {null, null, null, "E4", null, null}
        };

        retorno = s.informaciónMapa(mapa);
        assertEquals("2#ambas|no existe", retorno.getValorString());
    }

    @Test
    public void InformacionMapaEj7() {
        String[][] mapa = {
            {null, null, null, null, null, null},
            {null, null, null, null, null, null},
            {null, null, null, null, null, null},
            {null, null, null, null, null, null},
            {null, null, null, null, null, null},
            {null, null, null, null, null, null},
            {null, null, null, null, null, null},
            {null, null, null, null, null, null}
        };

        retorno = s.informaciónMapa(mapa);
        assertEquals("0#ambas|no existe", retorno.getValorString());
    }

    @Test
    public void InformacionMapaEj8() {
        String[][] mapa = {
            {"E1", "E2", "E3"},
            {"E21", "E22", "E23"},
            {"E31", "E32", "E33"},
            {"E41", "E42", "E43"},
            {"E51", "E52", "E53"},
            {"E61", "E62", "E63"}
        };

        retorno = s.informaciónMapa(mapa);
        assertEquals("6#columna|no existe", retorno.getValorString());
    }
    
        @Test
    public void InformacionMapaEj9() {
        String[][] mapa = {
            {"E1", "E2", "E3"},
            {"E21", "E22", "E23"},
            {"E31", "E32", "E33"}
        };

        retorno = s.informaciónMapa(mapa);
        assertEquals("3#ambas|no existe", retorno.getValorString());
    }
}
