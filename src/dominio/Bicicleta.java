package dominio;

public class Bicicleta implements Comparable<Bicicleta> {

    private String codigo;
    private TipoBicicleta tipo;
    private EstadosEnum estado;
    private Estacion estacion;

    public Bicicleta() {

    }

    public Bicicleta(String codigo) {
        this.codigo = codigo;
    }

    public Bicicleta(EstadosEnum estado) {
        this.estado = estado;
    }

    public Bicicleta(String codigo, TipoBicicleta tipo, EstadosEnum estado) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.estado = estado;
        this.estacion = null;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public TipoBicicleta getTipo() {
        return tipo;
    }

    public void setTipo(TipoBicicleta tipo) {
        this.tipo = tipo;
    }

    public EstadosEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadosEnum estado) {
        this.estado = estado;
    }

    public Estacion getEstacion() {
        return this.estacion;
    }

    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;
    }

    @Override
    public String toString() {
        //return codigo + "#" + tipo.getNombre() + "#" + EstadosEnum.estadosEnumTranslate(estado);
        //Cambiamos el to string para responder al metodo listarBicicletasDeEstacion, ya que el metodo listarBicisEnDeposito debe ser implementado recursivo
        return codigo;
    }

    public String toStringCampos(String... campos) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < campos.length; i++) {
            String campo = campos[i];

            switch (campo) {
                case "codigo":
                    sb.append(codigo);
                    break;
                case "tipo":
                    sb.append(tipo.getNombre());
                    break;
                case "estado":
                    sb.append(EstadosEnum.estadosEnumTranslate(estado));
                    break;
                case "estacion":
                    sb.append(estacion.getNombre());
                    break;
            }

            if (i < campos.length - 1) {
                sb.append("#");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Bicicleta b = (Bicicleta) obj;

        boolean codIgual = (codigo != null && codigo.equals(b.getCodigo()));
        boolean estIgual = (estado != null && estado == b.getEstado());
        boolean sonIguales;

        //Verifico que viene el el objeto por parametro, para saber el criterio por el cual me piden que busque.
        if (b.getCodigo() != null && b.getEstado() == null) {
            sonIguales = codIgual;
        } else if (b.getCodigo() == null && b.getEstado() != null) {
            sonIguales = estIgual;
        } else if (b.getCodigo() != null && b.getEstado() != null) {
            sonIguales = codIgual && estIgual;
        } else {
            sonIguales = false;
        }

        return sonIguales;
    }

    @Override
    public int hashCode() {
        return codigo != null ? codigo.hashCode() : 0;
    }

    @Override
    public int compareTo(Bicicleta o) {
        return this.codigo.compareTo(o.getCodigo());
    }
}
