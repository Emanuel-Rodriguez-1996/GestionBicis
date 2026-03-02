package dominio;

import java.util.Objects;

public class TipoBicicleta implements Comparable<TipoBicicleta>{

    private String nombre;
    private int cantidadAlquileres;

    public TipoBicicleta(){
        
    }
    
    public TipoBicicleta(String nombre) {
        this.nombre = nombre;
        this.cantidadAlquileres = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadAlquileres() {
        return this.cantidadAlquileres;
    }

    public void sumarCantidadAlquileres() {
        this.cantidadAlquileres++;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TipoBicicleta tb = (TipoBicicleta) obj;
        return this.nombre.toUpperCase().equals(tb.getNombre().toUpperCase());
    }

    @Override
    public String toString() {
        return nombre + "#" + cantidadAlquileres;
    }

    @Override
    public int compareTo(TipoBicicleta tb) {
        int resultadoCompararCantidad = Integer.compare(tb.getCantidadAlquileres(), cantidadAlquileres);
        return resultadoCompararCantidad == 0 ? nombre.compareTo(tb.getNombre()) : resultadoCompararCantidad;
    }
}
