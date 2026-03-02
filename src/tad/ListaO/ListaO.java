package tad.ListaO;

import java.util.function.Function;
import tad.Lista.Nodo;
import tad.ListaO.NodoO;

public class ListaO<T extends Comparable> implements IListaO<T> {

    private NodoO inicio;
    private int cantidadElementos;

    public ListaO() {
        inicio = null;
        cantidadElementos = 0;
    }

    @Override
    public boolean esVacia() {
        return inicio == null && cantidadElementos == 0;
    }

    @Override
    public void vaciar() {
        inicio = null;
        cantidadElementos = 0;
    }

    @Override
    public String mostrar(Function<T, String> formateador) {
        if (esVacia()) {
            return null;
        }

        String[] vectorElementos = new String[cantidadElementos];

        int indice = 0;
        NodoO<T> aux = inicio;

        while (indice < cantidadElementos) {
            T dato = aux.getDato();

            vectorElementos[indice] = formateador.apply(dato);

            aux = aux.getSiguiente();

            indice++;
        }

        return String.join("|", vectorElementos);
    }

    private void agregarInicio(T elemento) {
        NodoO<T> nuevoNodo = new NodoO<T>();
        nuevoNodo.setDato(elemento);
        nuevoNodo.setSiguiente(inicio);
        inicio = nuevoNodo;
        cantidadElementos++;
    }

    @Override
    public void agregarOrd(T dato) {

        NodoO<T> nuevo = new NodoO<>();
        nuevo.setDato(dato);

        // Caso lista vacía
        if (esVacia()) {
            nuevo.setSiguiente(null);
            inicio = nuevo;
            cantidadElementos++;
            return;
        }

        // Caso: insertar al inicio (dato es menor que el inicio)
        if (inicio.getDato().compareTo(dato) > 0) {
            nuevo.setSiguiente(inicio);
            inicio = nuevo;
            cantidadElementos++;
            return;
        }

        // Insertar en el medio o al final
        NodoO<T> actual = inicio;

        // Mientras el siguiente exista y el siguiente sea menor que dato
        while (actual.getSiguiente() != null
                && actual.getSiguiente().getDato().compareTo(dato) < 0) {

            actual = actual.getSiguiente();
        }

        // Insertar después de actual
        nuevo.setSiguiente(actual.getSiguiente());
        actual.setSiguiente(nuevo);
        cantidadElementos++;
    }

    @Override
    public void borrarElemento(T elemento) {
        if (esVacia()) {
            return;
        }

        if (!existeElemento(elemento)) {
            return;
        }

        if (cantidadElementos == 1) {
            inicio = null;
            cantidadElementos--;
            return;
        }

        NodoO<T> nActual = inicio;
        NodoO<T> nSiguiente = inicio.getSiguiente();

        //Hay un solo elemento y el actual es el elemento a borrar
        if (nSiguiente == null && nActual.getDato().equals(elemento)) {
            inicio = null;
            cantidadElementos--;
            return;
        }

        //Si hay mas de un elemento pero la acutal es el dato, el nuevo inicio es la siguiente
        if (nActual.getDato().equals(elemento)) {
            inicio = nSiguiente;
            cantidadElementos--;
            return;
        }

        while (nSiguiente != null && !nSiguiente.getDato().equals(elemento)) {
            nActual = nSiguiente;
            nSiguiente = nSiguiente.getSiguiente();
        }

        //Si nSiguiente es null, es el ultimo, solo necesito limpiar el siguiente del penultimo
        if (nSiguiente == null) {
            nActual.setSiguiente(null);
            cantidadElementos--;
            return;
        }

        nActual.setSiguiente(nSiguiente.getSiguiente());
        cantidadElementos--;
    }

    @Override
    public int cantElementos() {
        return cantidadElementos;
    }

    @Override
    public T obtenerElemento(T elemento) {
        if (esVacia()) {
            return null;
        }

        NodoO<T> nodoAux = inicio;
        boolean existe = false;
        T elementoExistente = null;

        while (nodoAux != null && !existe) {
            if (nodoAux.getDato().equals(elemento)) {
                existe = true;
                elementoExistente = nodoAux.getDato();
            } else {
                nodoAux = nodoAux.getSiguiente();
            }
        }

        return elementoExistente;
    }

    @Override
    public boolean existeElemento(T elemento) {
        if (esVacia()) {
            return false;
        }
        NodoO<T> nodoAux = inicio;
        boolean existe = false;
        while (nodoAux != null && !existe) {
            if (nodoAux.getDato().equals(elemento)) {
                existe = true;
            }
            nodoAux = nodoAux.getSiguiente();
        }

        return existe;
    }

    private void agregarFin(T elemento) {
        if (esVacia()) {
            agregarInicio(elemento);
            return;
        }

        NodoO<T> elementoNuevo = new NodoO<T>();
        elementoNuevo.setDato(elemento);
        NodoO<T> nodoAux = inicio;

        while (nodoAux.getSiguiente() != null) {
            nodoAux = nodoAux.getSiguiente();
        }

        nodoAux.setSiguiente(elementoNuevo);
        cantidadElementos++;
    }

    public IteradorListaO obtenerIterador() {
        return new IteradorListaO(inicio);
    }

    //Clase interna
    public class IteradorListaO {

        private NodoO<T> nodoAux;

        //Constructor con el primer elemento
        private IteradorListaO(NodoO<T> primero) {
            this.nodoAux = primero;
        }

        //Devuelve true si tiene siguiente, false si no tiene siguiente
        public boolean tieneSiguiente() {
            return nodoAux != null;
        }

        //Devuelve el elemento actual y setea en actual el siguiente
        public T obtenerActualYAvanzar() {
            T dato = nodoAux.getDato();
            nodoAux = nodoAux.getSiguiente();
            return dato;
        }
    }

}
