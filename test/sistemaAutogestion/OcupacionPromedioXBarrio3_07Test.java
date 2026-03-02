package sistemaAutogestion;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class OcupacionPromedioXBarrio3_07Test {

    private Retorno retorno;
    private final IObligatorio s = new Sistema();

    @Before
    public void setUp() {
        s.crearSistemaDeGestion();
    }

    @Test
    public void OcupacionPromedioXBarrioOk() {

        // Registrar estaciones
        s.registrarEstacion("B01", "B", 10);
        s.registrarEstacion("B02", "B", 4);
        s.registrarEstacion("D01", "D", 11);
        s.registrarEstacion("D02", "D", 5);
        s.registrarEstacion("C01", "C", 20);
        s.registrarEstacion("C02", "C", 8);
        s.registrarEstacion("A01", "A", 12);
        s.registrarEstacion("A02", "A", 5);

        // Registrar bicicletas (20 E, 25 M, 15 U = 60 total)
        int contador = 1;

        // eléctricas
        while (contador <= 20) {
            String codigo = String.format("E%05d", contador);
            s.registrarBicicleta(codigo, "ELECTRICA");
            contador++;
        }

        // mountain
        while (contador <= 45) {
            String codigo = String.format("M%05d", contador);
            s.registrarBicicleta(codigo, "MOUNTAIN");
            contador++;
        }

        // urbanas
        while (contador < 60) {
            String codigo = String.format("U%05d", contador);
            s.registrarBicicleta(codigo, "URBANA");
            contador++;
        }

        // A01 → 5 bicis
        s.asignarBicicletaAEstacion("E00001", "A01");
        s.asignarBicicletaAEstacion("E00002", "A01");
        s.asignarBicicletaAEstacion("M00021", "A01");
        s.asignarBicicletaAEstacion("U00046", "A01");
        s.asignarBicicletaAEstacion("U00047", "A01");

        // A02 → 3 bicis
        s.asignarBicicletaAEstacion("E00003", "A02");
        s.asignarBicicletaAEstacion("E00004", "A02");
        s.asignarBicicletaAEstacion("M00022", "A02");

        // B01 → 4 bicis
        s.asignarBicicletaAEstacion("E00005", "B01");
        s.asignarBicicletaAEstacion("E00006", "B01");
        s.asignarBicicletaAEstacion("M00023", "B01");
        s.asignarBicicletaAEstacion("U00048", "B01");

        // B02 → 2 bicis
        s.asignarBicicletaAEstacion("M00024", "B02");
        s.asignarBicicletaAEstacion("U00049", "B02");

        // C01 → 6 bicis
        s.asignarBicicletaAEstacion("E00007", "C01");
        s.asignarBicicletaAEstacion("E00008", "C01");
        s.asignarBicicletaAEstacion("M00025", "C01");
        s.asignarBicicletaAEstacion("M00026", "C01");
        s.asignarBicicletaAEstacion("U00050", "C01");
        s.asignarBicicletaAEstacion("U00051", "C01");

        // C02 → 3 bicis
        s.asignarBicicletaAEstacion("E00009", "C02");
        s.asignarBicicletaAEstacion("U00052", "C02");
        s.asignarBicicletaAEstacion("U00053", "C02");

        // D01 → 5 bicis
        s.asignarBicicletaAEstacion("E00010", "D01");
        s.asignarBicicletaAEstacion("E00011", "D01");
        s.asignarBicicletaAEstacion("M00027", "D01");        
        s.asignarBicicletaAEstacion("M00028", "D01");
        s.asignarBicicletaAEstacion("U00054", "D01");

        // D01 → 3 bicis
        s.asignarBicicletaAEstacion("E00012", "D02");
        s.asignarBicicletaAEstacion("M00029", "D02");
        s.asignarBicicletaAEstacion("U00055", "D02");

        //Barrio A => capacidad total de 17 => 8 anclajes ocupados => (8 / 17) * 100 = 47
        //Barrio B => capacidad total de 14 => 6 anclajes ocupados => (6 / 14) * 100 = 43
        //Barrio C => capacidad total de 28 => 9 anclajes ocupados => (9 / 28) * 100 = 32
        //Barrio D => capacidad total de 16 => 8 anclajes ocupados => (8 / 16) * 100 = 32

        // acá probás tu funcionalidad real
        retorno = s.ocupacionPromedioXBarrio();

        assertEquals("A#47|B#43|C#32|D#50", retorno.getValorString());
    }
}
