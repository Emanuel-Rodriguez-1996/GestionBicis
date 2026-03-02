package dominio;

import tad.Cola.Cola;
import tad.Lista.Lista;
import tad.ListaO.ListaO;

public class Estacion {

    private String nombre;
    private Barrio barrio;
    private int capacidad;
    private int contadorBiciDisponible;
    private ListaO<Bicicleta> bicicletas; //ListaO
    private Cola<Usuario> usuariosEnEsperaAlquiler; //Usuarios esperando alquilar
    private Cola<Alquiler> enEsperaDeEntrega; //Usuarios esperando a devolver

    public Estacion(String nombre, Barrio barrio, int capacidad) {
        this.nombre = nombre;
        this.barrio = barrio;
        this.capacidad = capacidad;
        this.bicicletas = new ListaO();
        this.contadorBiciDisponible = this.bicicletas.cantElementos();
        this.usuariosEnEsperaAlquiler = new Cola();
        this.enEsperaDeEntrega = new Cola();
    }

    public Estacion(String nombre) {
        this.nombre = nombre;
        this.bicicletas = new ListaO();
        this.contadorBiciDisponible = this.bicicletas.cantElementos();
        this.usuariosEnEsperaAlquiler = new Cola();
        this.enEsperaDeEntrega = new Cola();
    }

    public Cola<Usuario> getUsuariosEnEsperaAlquiler() {
        return usuariosEnEsperaAlquiler;
    }

    public Cola<Alquiler> getEnEsperaDeEntrega() {
        return enEsperaDeEntrega;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Barrio getBarrio() {
        return barrio;
    }

    public void setBarrio(Barrio barrio) {
        this.barrio = barrio;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public ListaO<Bicicleta> getBicicletas() {
        return bicicletas;
    }

    public void setBicicletas(ListaO<Bicicleta> bicicletas) {
        this.bicicletas = bicicletas;
    }
    
     public int getContadorBiciDisponible() {
        return contadorBiciDisponible;
    }
    
    public void sumarBiciDisponible(){
        this.contadorBiciDisponible++;
    }
    
     public void restarBiciDisponible(){
        this.contadorBiciDisponible--;
    }

    public boolean hayBicicletasOColas() {
        return bicicletas.cantElementos() > 0
                || usuariosEnEsperaAlquiler.cantidadElementos() > 0
                || enEsperaDeEntrega.cantidadElementos() > 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Estacion)) {
            return false;
        }
        Estacion e = (Estacion) obj;
        return (this.nombre.equals(e.getNombre()));
    }

    @Override
    public String toString() {
        return "Nombre=" + nombre
                + " Barrio=" + barrio
                + " Capacidad=" + capacidad
                + " Bicicletas=" + bicicletas
                + "Usuario para Alquilar=" + usuariosEnEsperaAlquiler
                + "Usuario para Entregar=" + enEsperaDeEntrega;
    }
}
