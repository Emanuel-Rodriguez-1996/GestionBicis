package tad.Cola;

import java.util.function.Function;
import tad.Lista.Nodo;

public class Cola<T> implements ICola<T> {

    private Nodo<T> inicio;
    private int cantidadElementos;

    public Cola() {
        inicio = null;
        cantidadElementos = 0;
    }

    @Override
    public void agregarAlFinal(T elemento) {
        Nodo<T> elementoAAgregar = new Nodo<T>();
        elementoAAgregar.setDato(elemento);

        if (inicio == null) {
            // si la cola esta vacia el nuevo nodo es el inicio
            inicio = elementoAAgregar;
        } else {
            Nodo<T> ultimoElemento = inicio;
            while (ultimoElemento.getSiguiente() != null) {
                ultimoElemento = ultimoElemento.getSiguiente();
            }
            ultimoElemento.setSiguiente(elementoAAgregar);
        }

        cantidadElementos++;
    }

    @Override
    public void eliminarPrimerElemento() {
        if (!esVacia()) {
            inicio = inicio.getSiguiente();
            cantidadElementos--;
        }
    }

    @Override
    public boolean esVacia() {
        return inicio == null;
    }

    @Override
    public int cantidadElementos() {
        return cantidadElementos;
    }

    @Override
    public boolean existeElemento(T elemento) {
        if (esVacia()) {
            return false;
        }
        Nodo aux = inicio;
        boolean existe = false;

        while ((aux != null) && (!existe)) {
            existe = aux.getDato().equals(elemento);
            aux = aux.getSiguiente();
        }
        return existe;
    }

    @Override
    public T obtenerElPrimero() {
        return inicio.getDato();
    }

    @Override
    public String mostrar(Function<T, String> formateador) {
        if (esVacia()) {
            return null;
        }
        
        String[] vectorElementos = new String[cantidadElementos];

        int indice = 0;
        Nodo<T> aux = inicio;
        
        while (indice < cantidadElementos) {
            T dato = aux.getDato();

            vectorElementos[indice] = formateador.apply(dato);
            
            aux = aux.getSiguiente();
            
            indice++;
        }
        
        return String.join("|", vectorElementos);
    }
}
