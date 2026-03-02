package tad.ListaO;

import tad.ListaO.NodoO;

public class NodoO<T extends Comparable> {

    private T dato;
    private NodoO siguiente;

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoO getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoO siguiente) {
        this.siguiente = siguiente;
    }
}
