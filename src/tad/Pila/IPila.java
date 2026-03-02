package tad.Pila;

import tad.Lista.Nodo;

public interface IPila<T> {

    public void push(T nuevo);

    public void pop();

    public Nodo<T> top();
    
    public int CantidadElementos();

    public boolean esVacia();
}
