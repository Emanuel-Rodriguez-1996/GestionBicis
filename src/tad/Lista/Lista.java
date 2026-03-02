package tad.Lista;

import java.util.function.Function;

public class Lista<T> implements ILista<T> {

    private Nodo inicio;
    private int cantidadElementos;

    public Lista() {
        inicio = null;
        cantidadElementos = 0;
    }

    @Override
    public boolean esVacia() {
        return inicio == null;
    }

    @Override
    public void agregarInicio(T n) {
        Nodo nodo = new Nodo();
        nodo.setDato(n);
        nodo.setSiguiente(inicio);

        inicio = nodo;
        cantidadElementos++;
    }

    @Override
    public void agregarFinal(T n) {
        if (esVacia()) {
            agregarInicio(n);
        } else {
            Nodo nuevoNodo = new Nodo();
            nuevoNodo.setDato(n);
            Nodo aux = inicio;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nuevoNodo);
            cantidadElementos++;
        }
    }

    @Override
    public void borrarInicio() {
        if (!esVacia()) {
            inicio = inicio.getSiguiente();
            cantidadElementos--;
        }
    }

    @Override
    public void borrarFin() {
        Nodo nodoActual = inicio;
        Nodo siguienteNodo = nodoActual.getSiguiente();
        while (siguienteNodo != null) {
            nodoActual = siguienteNodo;
            siguienteNodo.getSiguiente();
        }

        nodoActual.setSiguiente(null);
        cantidadElementos--;
    }

    @Override
    public void vaciar() {
        inicio = null;
        cantidadElementos = 0;
    }

    private String mostrarRecAux(Nodo<T> nodo, Function<T, String> formateador) {

        //No hay elementos
        if (nodo == null) {
            return "";
        }

        // Nodo actual
        String actual = formateador.apply(nodo.getDato());

        // Llamada recursiva
        String resto = mostrarRecAux(nodo.getSiguiente(), formateador);

        // Si no hay mas elementos devolvemos el actual sin concatenar
        if (resto.isEmpty()) {
            return actual;
        }

        // Unimos el string mediante |
        return actual + "|" + resto;
    }

    public String mostrarRecursivo(Function<T, String> formateador) {
        if (esVacia()) {
            return null;
        }
        return mostrarRecAux(inicio, formateador);
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

        Nodo<T> nActual = inicio;
        Nodo<T> nSiguiente = inicio.getSiguiente();

        if (nSiguiente == null && nActual.getDato().equals(elemento)) {
            inicio = null;
            cantidadElementos--;
            return;
        }

        while (nSiguiente != null && !nSiguiente.equals(elemento)) {
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

        Nodo aux = inicio;
        boolean existe = false;
        T elementoExiste = null;

        while (aux != null && !existe) {
            if (aux.getDato().equals(elemento)) {
                existe = true;
                elementoExiste = (T) aux.getDato();//con el (T) estoy casteando
            } else {
                aux = aux.getSiguiente();
            }
        }

        return elementoExiste;
    }

    @Override
    public boolean existeElemento(T elemento) {
        Nodo aux = inicio;
        boolean existe = false;

        while ((aux != null) && (!existe)) {
            existe = aux.getDato().equals(elemento);
            aux = aux.getSiguiente();
        }
        return existe;
    }

    public IteradorLista obtenerIterador() {
        return new IteradorLista(inicio);
    }

    // clase interna
    public class IteradorLista {

        private Nodo<T> nodoAux;

        //Constructor con el primer elemento
        private IteradorLista(Nodo<T> primero) {
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
