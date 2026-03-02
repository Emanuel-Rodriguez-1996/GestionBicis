package tad.Cola;

import java.util.function.Function;

/*Las colas son listas con la restricción que, las inserciones se deben
realizar al final (back) y se debe eliminar desde el principio (front).*/
public interface ICola<T> {

    public boolean esVacia();

    public void agregarAlFinal(T elemento);// modificado (se agrega el caso en el que la cola este vacia)

    public T obtenerElPrimero();

    public void eliminarPrimerElemento();

    public int cantidadElementos();

    public boolean existeElemento(T elemento);//modificado (se agrega el caso en el que la cola este vacia)

    public String mostrar(Function<T, String> formateador);
}
