package dominio;

public class Barrio implements Comparable<Barrio> {

    private String nombre;
    private int bicisDisponibles; //Es lo mismo que bicis ancladas 3.7
    private int capacidadTotalDelBarrio;

    public Barrio() {

    }

    public Barrio(String nombre) {
        this.nombre = nombre;
        this.bicisDisponibles = 0;
        this.capacidadTotalDelBarrio = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getBicisDisponibles() {
        return bicisDisponibles;
    }

    //Se le puede pasar negativo o positivo
    public void sumarACapacidadTotalDelBarrio(int cantidad) {
        capacidadTotalDelBarrio += cantidad;
    }

    public int getCapacidadTotalDelBarrio() {
        return this.capacidadTotalDelBarrio;
    }

    public void sumarBicicsDisponibles() {
        bicisDisponibles++;
    }

    public void restarBicicsDisponibles() {
        bicisDisponibles--;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Barrio b = (Barrio) obj;
        return b.getNombre().equals(this.nombre);
    }

    @Override
    public String toString() {
        double ocupacionPromedio = ((double) bicisDisponibles / capacidadTotalDelBarrio) * 100.0;
        int ocupacionRedondeada = (int) Math.round(ocupacionPromedio);
        return nombre + "#" + ocupacionRedondeada;
    }

    public String toStringCampos(String... campos) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < campos.length; i++) {
            String campo = campos[i];

            switch (campo) {
                case "nombre":
                    sb.append(nombre);
                    break;
                case "bicisDisponibles":
                    sb.append(bicisDisponibles);
                    break;
                case "capacidadTotalDelBarrio":
                    sb.append(capacidadTotalDelBarrio);
                    break;
            }

            if (i < campos.length - 1) {
                sb.append("#");
            }
        }
        return sb.toString();
    }

    @Override
    public int compareTo(Barrio o) {
        return this.nombre.compareTo(o.getNombre());
    }
}
