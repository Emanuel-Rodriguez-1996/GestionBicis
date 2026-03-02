package tad.ListaO;

import java.util.function.Function;
import tad.ListaO.NodoO;

public interface IListaO <T extends Comparable> {
    
    public boolean esVacia();

    public void vaciar();

    public String mostrar(Function<T, String> formateador);

    public void agregarOrd(T elemento);
    
    public void borrarElemento(T elemento);

    public int cantElementos();

    public T obtenerElemento(T elemento);

    public boolean existeElemento(T elemento);
}
