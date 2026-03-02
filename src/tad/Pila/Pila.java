package tad.Pila;

import tad.Lista.Nodo;

public class Pila<T> implements IPila<T> {

    private Nodo inicio;
    private int cantidadElementos;

    public Pila() {
        this.inicio = null;
        this.cantidadElementos = 0;
    }

    @Override
    public void push(T nuevo) {
        Nodo nodo = new Nodo();
        nodo.setDato(nuevo);
        nodo.setSiguiente(inicio);

        inicio = nodo;
        cantidadElementos++;
    }

    @Override
    public void pop() {
        if (esVacia()) {
            return;
        }
        Nodo<T> penultimoAgregado = inicio.getSiguiente();
        inicio = penultimoAgregado;
        cantidadElementos--;
    }

    @Override
    public Nodo<T> top() {
        if (esVacia()) {
            return null;
        }

        return inicio;
    }

    @Override
    public int CantidadElementos() {
        return cantidadElementos;
    }

    @Override
    public boolean esVacia() {
        return inicio == null && cantidadElementos == 0;
    }

}
