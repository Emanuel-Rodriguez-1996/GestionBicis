package dominio;

public class Alquiler implements Comparable<Alquiler> {

    private Bicicleta bicicleta;
    private Usuario usuario;
    private int alquilerId;
    private static int contadorId = 0;

    public Alquiler(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Alquiler(Bicicleta bicicleta) {
        this.bicicleta = bicicleta;
    }

    public Alquiler(Bicicleta bicicleta, Usuario usuario) {
        this.alquilerId = this.contadorId++;
        this.bicicleta = bicicleta;
        this.usuario = usuario;
    }

    public Bicicleta getBicicleta() {
        return bicicleta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setBicileta(Bicicleta bicileta) {
        this.bicicleta = bicileta;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int compareTo(Alquiler a) {
        return Integer.compare(a.alquilerId, this.alquilerId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Alquiler a = (Alquiler) obj;
        
        boolean usuarioIgual = (usuario != null && usuario.equals(a.getUsuario()));
        boolean bicicletaIgual = (bicicleta != null && bicicleta.equals(a.getBicicleta()));
        boolean sonIguales;
        
        if(a.getUsuario() != null && a.getBicicleta() == null){
            sonIguales = usuarioIgual;
        }else if(a.getUsuario() == null && a.getBicicleta() != null){
            sonIguales = bicicletaIgual;
        }else if (a.getUsuario() != null && a.getBicicleta() != null){
            sonIguales = usuarioIgual && bicicletaIgual;
        }else{
            sonIguales = false;
        }

        return sonIguales;
    }
    
    @Override
    public String toString(){
        return bicicleta.getCodigo() + "#" + usuario.getCi() + "#" + bicicleta.getEstacion().getNombre();
    }
}
