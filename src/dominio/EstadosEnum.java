package dominio;

public enum EstadosEnum {
    Disponible,
    EnMantenimiento,
    //EnDeposito,
    EnAlquiler;

    public static String estadosEnumTranslate(EstadosEnum estado) {
        switch (estado) {
            case Disponible:
                return "Disponible";
            case EnMantenimiento:
                return "Mantenimiento";
            //case EnDeposito:
            //    return "Deposito";
            case EnAlquiler:
                return "Alquilada";
            default:
                throw new AssertionError("Estado desconocido: " + estado);
        }
    }
}
