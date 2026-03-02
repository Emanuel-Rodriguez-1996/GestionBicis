package dominio;

import java.util.Objects;

public class Usuario implements Comparable<Usuario> {

    private String ci;
    private String nombre;

    private int cantidadAlquileres;

    public Usuario() {

    }

    public Usuario(String ci) {
        this.ci = ci;
    }

    public Usuario(String ci, String nombre) {
        this.ci = ci;
        this.nombre = nombre;
        this.cantidadAlquileres = 0;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadAlquileres() {
        return cantidadAlquileres;
    }

    public void sumarAlquiler() {
        this.cantidadAlquileres++;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Usuario)) {
            return false;
        }
        Usuario u = (Usuario) obj;
        return (this.ci.equals(u.getCi()));
    }

    @Override
    public String toString() {
        return nombre + "#" + ci;
    }

    public String toStringCampos(String... campos) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < campos.length; i++) {
            String campo = campos[i];

            switch (campo) {
                case "ci":
                    sb.append(ci);
                    break;
                case "nombre":
                    sb.append(nombre);
                    break;
            }

            if (i < campos.length - 1) {
                sb.append("#");
            }
        }
        return sb.toString();
    }

    @Override
    public int compareTo(Usuario o) {
        int resultado = this.nombre.compareTo(o.getNombre());
        return resultado == 0 ? this.ci.compareTo(o.getCi()) : resultado;
    }
}
