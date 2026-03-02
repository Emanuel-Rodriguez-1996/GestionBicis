package tad.Lista;

import java.util.function.Function;

public interface ILista<T> {

    public boolean esVacia();

    public void vaciar();

    public String mostrar(Function<T, String> formateador);
    
    public String mostrarRecursivo(Function<T, String> formateador);

    public void agregarInicio(T n);

    public void agregarFinal(T n);

    public void borrarInicio();// no esta

    public void borrarFin();// no esta

    public void borrarElemento(T n);// modificado

    public int cantElementos();

    public T obtenerElemento(T n);// modificado

    public boolean existeElemento(T n);
}
