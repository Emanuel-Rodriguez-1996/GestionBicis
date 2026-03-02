package sistemaAutogestion;

//Agregar aquí nombres y números de estudiante de los integrantes del equipo
//Joaquin Gonzalez - 347334
//Emanuel Rodriguez - 306299
import dominio.Bicicleta;
import dominio.Barrio;
import dominio.Estacion;
import dominio.EstadosEnum;
import dominio.TipoBicicleta;
import dominio.Usuario;
import dominio.Alquiler;
import tad.ListaO.ListaO;
import tad.Lista.Lista;

public class Sistema implements IObligatorio {

    private ListaO<Barrio> barrios;
    private Lista<Estacion> estaciones;
    private ListaO<Usuario> usuarios;
    private ListaO<TipoBicicleta> tiposBicis;
    private ListaO<Alquiler> alquileresBici;
    private Lista<Bicicleta> bicicletas;

    @Override
    public Retorno crearSistemaDeGestion() {
        barrios = new ListaO<Barrio>();
        estaciones = new Lista<Estacion>();
        usuarios = new ListaO<Usuario>();
        tiposBicis = new ListaO<TipoBicicleta>();
        tiposBicis.agregarOrd(new TipoBicicleta("ELECTRICA"));
        tiposBicis.agregarOrd(new TipoBicicleta("MOUNTAIN"));
        tiposBicis.agregarOrd(new TipoBicicleta("URBANA"));
        alquileresBici = new ListaO<Alquiler>();
        bicicletas = new Lista<Bicicleta>();

        return Retorno.ok("El sistema se inicializo correctamente.");
    }

    @Override
    public Retorno registrarEstacion(String nombre, String barrio, int capacidad) {
        if (nombre == null || nombre.trim().isEmpty() || barrio == null || barrio.trim().isEmpty()) {
            return Retorno.error1();
        }

        if (capacidad <= 0) {
            return Retorno.error2();
        }

        // Verificar nombre de la estacion
        Estacion auxEstacion = new Estacion(nombre);
        Estacion estacionExistente = estaciones.obtenerElemento(auxEstacion);
        if (estacionExistente != null) {
            return Retorno.error3();
        }

        // Buscar o crear el barrio
        Barrio auxBarrio = new Barrio(barrio);
        Barrio barrioExistente = barrios.obtenerElemento(auxBarrio);

        if (barrioExistente == null) {
            barrios.agregarOrd(auxBarrio);
            auxEstacion.setBarrio(auxBarrio);
        } else {
            auxEstacion.setBarrio(barrioExistente);
        }

        //Sumamos al barrio la capacidad de la estacion.
        auxEstacion.getBarrio().sumarACapacidadTotalDelBarrio(capacidad);

        auxEstacion.setCapacidad(capacidad);
        estaciones.agregarFinal(auxEstacion);

        return Retorno.ok("Se registro la estacion con exito");
    }

    @Override
    public Retorno registrarUsuario(String cedula, String nombre) {
        if (cedula == null || cedula.trim().isEmpty() || nombre == null || nombre.trim().isEmpty()) {
            return Retorno.error1();
        }

        if (cedula.length() != 8) {
            return Retorno.error2();
        }

        for (int i = 0; i < cedula.length(); i++) {
            if (!Character.isDigit(cedula.charAt(i))) {
                return Retorno.error2();
            }
        }

        Usuario usu = new Usuario(cedula, nombre);

        if (usuarios.existeElemento(usu)) {
            return Retorno.error3();
        }

        usuarios.agregarOrd(usu);
        return Retorno.ok("Se registro el usuario con exito");
    }

    @Override
    public Retorno registrarBicicleta(String codigo, String tipo) {
        if (codigo == null || codigo.trim().isEmpty() || tipo == null || tipo.trim().isEmpty()) {
            return Retorno.error1();
        }
        if (codigo.length() != 6) {
            return Retorno.error2();
        }

        TipoBicicleta auxTipoBici = new TipoBicicleta(tipo);
        TipoBicicleta tipoBici = tiposBicis.obtenerElemento(auxTipoBici);
        if (tipoBici == null) {
            return Retorno.error3();
        }

        Bicicleta b = new Bicicleta(codigo);
        if (bicicletas.existeElemento(b)) {
            return Retorno.error4();
        }
        b.setEstado(EstadosEnum.Disponible);
        b.setTipo(tipoBici);
        //Cuando se agrega una bici siempre se agrega al final
        bicicletas.agregarFinal(b);
        return Retorno.ok("Se registro la bicicleta con exito");
    }

    /*--------MODIFICADO PARA BUSCAR EN ESTACIONES*/
    @Override
    public Retorno marcarEnMantenimiento(String codigo, String motivo) {

        if (codigo == null || codigo.trim().isEmpty()
                || motivo == null || motivo.trim().isEmpty()) {
            return Retorno.error1();
        }

        Bicicleta buscada = new Bicicleta(codigo);
        Bicicleta bici = bicicletas.obtenerElemento(buscada);
        Estacion estacionOrigen = null;

        //si no esta en deposito buscarla en estaciones
        if (bici == null) {
            Lista<Estacion>.IteradorLista itEstaciones = estaciones.obtenerIterador();

            while (itEstaciones.tieneSiguiente() && bici == null) {
                Estacion e = itEstaciones.obtenerActualYAvanzar();

                bici = e.getBicicletas().obtenerElemento(buscada);

                if (bici != null) {
                    estacionOrigen = e;
                }
            }
        }

        if (bici == null) {
            return Retorno.error2();
        }

        //Este error nunca se va a dar, por la definicion de nuestro sistema. La lista de bicis en sistema son bicis en deposito y en mantenimiento.
        //La lista de bicis de la estacion son bicis disponibles en cada estacion.
        //Si la bici no esta en la lista de sistema ni en la lista de las estaciones, entonces puede no existir o puede estar alquilada (Lista alquileres en sistema).
        //Nos es indeferente la la diferencia, porque ambos dan error, en caso de que no existir, error 2 y en caso de estar alquilada, error 3.
        //Sumar esa busqueda para forzar el error 3 lo unico que haria es restarle ineficiencia al metodo.
        if (bici.getEstado() == EstadosEnum.EnAlquiler) {
            return Retorno.error3();
        }

        if (bici.getEstado() == EstadosEnum.EnMantenimiento) {
            return Retorno.error4();
        }

        bici.setEstado(EstadosEnum.EnMantenimiento);

        //Si estaba en la estacion pasarla a deposito
        if (estacionOrigen != null) {
            estacionOrigen.getBicicletas().borrarElemento(bici);
            estacionOrigen.getBarrio().restarBicicsDisponibles();
            estacionOrigen.restarBiciDisponible();
            bicicletas.agregarFinal(bici);
        }

        return Retorno.ok("Se envio la bicicleta a mantenimiento");
    }

    @Override
    public Retorno repararBicicleta(String codigo) {

        if (codigo == null || codigo.trim().isEmpty()) {
            return Retorno.error1();
        }

        // Buscarla en la lista del sistema, ya que las bicis en mantenimiento solo pueden estar en esa lista!
        Bicicleta biciA = bicicletas.obtenerElemento(new Bicicleta(codigo));

        if (biciA == null) {
            return Retorno.error2();
        }

        // Si estaba en deposito verificar estado
        if (biciA.getEstado() != EstadosEnum.EnMantenimiento) {
            return Retorno.error3();
        }

        biciA.setEstado(EstadosEnum.Disponible);

        return Retorno.ok();
    }

    @Override
    public Retorno eliminarEstacion(String nombre) {
        // Validar parámetro
        if (nombre == null || nombre.trim().isEmpty()) {
            return Retorno.error1();
        }

        Estacion estacionExistente = estaciones.obtenerElemento(new Estacion(nombre));
        if (estacionExistente == null) {
            return Retorno.error2();
        }

        if (estacionExistente.hayBicicletasOColas()) {
            return Retorno.error3();
        }

        estaciones.borrarElemento(estacionExistente);
        return Retorno.ok("Se eliminó la estación");
    }

    @Override
    public Retorno asignarBicicletaAEstacion(String codigo, String nombreEstacion) {

        if (codigo == null || codigo.trim().isEmpty() || nombreEstacion == null || nombreEstacion.trim().isEmpty()) {
            return Retorno.error1();
        }

        Bicicleta biciA = bicicletas.obtenerElemento(new Bicicleta(codigo));
        Boolean biciEnEstacion = false;

        if (biciA == null) {
            Lista<Estacion>.IteradorLista itEstaciones = estaciones.obtenerIterador();
            while (itEstaciones.tieneSiguiente() && biciA == null) {
                Estacion e = itEstaciones.obtenerActualYAvanzar();
                biciA = e.getBicicletas().obtenerElemento(new Bicicleta(codigo));
            }
            biciEnEstacion = biciA != null;
        }

        if (biciA == null) {
            return Retorno.error2();
        }

        if (biciA.getEstado() != EstadosEnum.Disponible) {
            return Retorno.error2();
        }

        Estacion estacionDestino = estaciones.obtenerElemento(new Estacion(nombreEstacion));
        if (estacionDestino == null) {
            return Retorno.error3();
        }

        if (estacionDestino.getCapacidad() == estacionDestino.getBicicletas().cantElementos()) {
            return Retorno.error4();
        }

        if (biciEnEstacion) {
            Estacion estacionOrigen = biciA.getEstacion();
            estacionOrigen.getBicicletas().borrarElemento(biciA);

            //Si el barrio de la estacion de origen es distinto que la de destino, resto disponibilidad todal al origen y sumo al destino
            Barrio barrioOrigen = estacionOrigen.getBarrio();
            Barrio barrioDestino = estacionDestino.getBarrio();
            if (!barrioOrigen.equals(barrioDestino)) {
                barrioOrigen.restarBicicsDisponibles();
                barrioDestino.sumarBicicsDisponibles();

                //Resto bicis disponibles de la estacion origen
                estacionOrigen.restarBiciDisponible();
            }
        } else {
            bicicletas.borrarElemento(biciA);
            //Si no estaba en una estacion, simplemente sumo al barrio de destino
            estacionDestino.getBarrio().sumarBicicsDisponibles();
        }

        biciA.setEstacion(estacionDestino);
        estacionDestino.sumarBiciDisponible();
        estacionDestino.getBicicletas().agregarOrd(biciA);

        return Retorno.ok("Se asigno la bicicleta a la estacion");
    }

    @Override
    public Retorno alquilarBicicleta(String cedula, String nombreEstacion) {

        if (cedula == null || cedula.trim().isEmpty() || nombreEstacion == null || nombreEstacion.trim().isEmpty()) {
            return Retorno.error1();
        }

        Usuario usuarioExistente = usuarios.obtenerElemento(new Usuario(cedula));

        if (usuarioExistente == null) {
            return Retorno.error2();
        }

        Estacion estacionEncontrada = estaciones.obtenerElemento(new Estacion(nombreEstacion));

        if (estacionEncontrada == null) {
            return Retorno.error3();
        }

        //ToDo: Verificar si hay alguien esperando entregar, alquila esa.!!!!!!!!
        Bicicleta biciAuxDisponible = new Bicicleta(EstadosEnum.Disponible);
        Bicicleta bicicletaAAlquilar = estacionEncontrada.getBicicletas().obtenerElemento(biciAuxDisponible);
        //Bicicleta a alquilar puede ser null porque la lista bicicletas esta vacia o porque ninguna esta en estado disponible
        if (bicicletaAAlquilar == null) {
            //si no hay bici se agrega a la cola
            estacionEncontrada.getUsuariosEnEsperaAlquiler().agregarAlFinal(usuarioExistente);
        } else {
            //se setea el estado
            bicicletaAAlquilar.setEstado(EstadosEnum.EnAlquiler);

            //se suma cantidad de alquileres al tipo de bicicleta
            bicicletaAAlquilar.getTipo().sumarCantidadAlquileres();

            //Se suma alquiler al usuario
            usuarioExistente.sumarAlquiler();

            //Se resta las bicis disponibles en el barrio
            bicicletaAAlquilar.getEstacion().getBarrio().restarBicicsDisponibles();

            //Se resta bici disponible de la estacion
            bicicletaAAlquilar.getEstacion().restarBiciDisponible();

            //Se saca la bici de la lista de bicis (bicis ancladas/disponibles) de la estacion 
            estacionEncontrada.getBicicletas().borrarElemento(bicicletaAAlquilar);

            //se crea el alquiler y se agrega
            Alquiler nuevoAlquiler = new Alquiler(bicicletaAAlquilar, usuarioExistente);
            alquileresBici.agregarOrd(nuevoAlquiler);
        }

        return Retorno.ok("Se alquilo la bicicleta con exito");
    }

    @Override
    public Retorno devolverBicicleta(String cedula, String nombreEstacionDestino) {
        if (cedula == null || cedula.trim().isEmpty() || nombreEstacionDestino == null || nombreEstacionDestino.trim().isEmpty()) {
            return Retorno.error1();
        }

        Alquiler alquilerActivo = alquileresBici.obtenerElemento(new Alquiler(new Usuario(cedula)));
        if (alquilerActivo == null) {
            return Retorno.error2();
        }

        Estacion estacionDestino = estaciones.obtenerElemento(new Estacion(nombreEstacionDestino));
        if (estacionDestino == null) {
            return Retorno.error3();
        }

        Bicicleta biciADevolver = alquilerActivo.getBicicleta();

        //si hay usuarios en espera de alquiler en esa estacion, entregar automaticamente al primero que estaba esperando.
        if (!estacionDestino.getUsuariosEnEsperaAlquiler().esVacia()) {
            Usuario usuarioEnEspera = estacionDestino.getUsuariosEnEsperaAlquiler().obtenerElPrimero();
            //Borro el alquiler activo del usuario 
            alquileresBici.borrarElemento(alquilerActivo);
            //La bici a devolver, que va a ser alquilada, parte desde otra estacion, por lo tanto seteo la estacion destino a la bici
            biciADevolver.setEstacion(estacionDestino);

            //Creo un nuevo alquiler con el primer usuario en espera en la cola y lo agrego a los alquileres activos
            Alquiler nuevoAlquiler = new Alquiler(biciADevolver, usuarioEnEspera);
            alquileresBici.agregarOrd(nuevoAlquiler);

            //se suma cantidad de alquileres al tipo de bicicleta
            biciADevolver.getTipo().sumarCantidadAlquileres();

            //Sumo el contador de alquileres del usuario
            usuarioEnEspera.sumarAlquiler();
            
            //Saco el usuario en espera de la lista de usuarios en espera
            estacionDestino.getUsuariosEnEsperaAlquiler().eliminarPrimerElemento();
        } else {
            if (estacionDestino.getBicicletas().cantElementos() < estacionDestino.getCapacidad()) {
                //Si las bicicletas en estacion destino son menos que la capacidad, la puedo devolver
                biciADevolver.setEstado(EstadosEnum.Disponible);
                biciADevolver.setEstacion(estacionDestino);
                estacionDestino.getBicicletas().agregarOrd(biciADevolver);
                alquileresBici.borrarElemento(alquilerActivo);

                //Al devolver sumamos en el contador bicis disponibles de Estacion y al contador de bicis disponibles en el barrio
                estacionDestino.sumarBiciDisponible();
                estacionDestino.getBarrio().sumarBicicsDisponibles();
            } else {
                //Como no hay lugar lo mando a la cola de espera de entrega
                estacionDestino.getEnEsperaDeEntrega().agregarAlFinal(alquilerActivo);
            }
        }

        return Retorno.ok("La bicicleta fue devuelta con exito");
    }

    @Override
    public Retorno deshacerUltimosRetiros(int n) {
        if (n <= 0) {
            return Retorno.error1();
        }

        if (alquileresBici.esVacia()) {
            return Retorno.error2();
        }

        if (n > alquileresBici.cantElementos()) {
            return Retorno.error3();
        }

        if (alquileresBici.esVacia()) {
            return Retorno.ok("No hay alquileres para deshacer.");/*validacion no requerida*/
        }

        ListaO<Alquiler>.IteradorListaO itAlquileres = alquileresBici.obtenerIterador();
        ListaO<Alquiler> resultado = new ListaO<Alquiler>();
        int contadorEliminado = 0;
        
        while (itAlquileres.tieneSiguiente() && contadorEliminado < n) {
            Alquiler alqAux = itAlquileres.obtenerActualYAvanzar();
            Bicicleta bici = alqAux.getBicicleta();
            Estacion estacion = alqAux.getBicicleta().getEstacion();

            // Devolver la bici a la estacion si hay espacio
            if (estacion.getBicicletas().cantElementos() < estacion.getCapacidad()) {

                //La dejo en estado disponible
                bici.setEstado(EstadosEnum.Disponible);

                //La agrego a la lista de bicicleta de la estacion
                estacion.getBicicletas().agregarOrd(bici);

                //Sumamos disponibilidad para el barrio
                estacion.getBarrio().sumarBicicsDisponibles();

                //Sumamos disponibilidad para la estacion
                estacion.sumarBiciDisponible();
                
                //Agrego a la lista de resultado, las bicicletas eliminadas
                resultado.agregarOrd(alqAux);
            } else {
                // Si no hay espacio, poner en espera de entrega al usuario
                estacion.getEnEsperaDeEntrega().agregarAlFinal(alqAux);
            }
            contadorEliminado++;
        }

        return Retorno.ok(resultado.mostrar((Alquiler a) -> a.toString()));
    }

    @Override
    public Retorno obtenerUsuario(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return Retorno.error1();
        }

        if (cedula.length() != 8) {
            return Retorno.error2();
        }

        Usuario auxUsu = new Usuario();
        auxUsu.setCi(cedula);
        Usuario u = usuarios.obtenerElemento(auxUsu);

        if (u == null) {
            return Retorno.error3();
        }

        return Retorno.ok(u.toString());
    }

    @Override
    public Retorno listarUsuarios() {
        //Faltaria ordernar creciente por nombre

        String resultado = usuarios.mostrar((Usuario u) -> u.toString());

        return Retorno.ok(resultado);
    }

    @Override
    public Retorno listarBicisEnDeposito() {
        if (bicicletas.esVacia()) {
            return Retorno.ok("");
        }

        String resultado = bicicletas.mostrarRecursivo((Bicicleta b) -> b.toStringCampos("codigo", "tipo", "estado"));

        return Retorno.ok(resultado);
    }

    /*
        PRE: El mapa debe ser ingresado con null donde no haya estaciones y algun String donde si haya, por ejemplo:
        String[][] mapa = {
            {"E1", "E2", null},
            {null, "E3", "E4"},
            {null, null, null}
        };
        Un ejemplo invalido seria:
        String[][] mapa = {
                {"E1", "E2", "x"},
                {"x", "E3", "E4"},
                {"x", "x", "x"}
        };
     */
    @Override
    public Retorno informaciónMapa(String[][] mapa) {
        if (mapa == null || mapa.length == 0 || mapa[0].length == 0) {
            return Retorno.ok("0#ambas|NO");
        }

        int filas = mapa.length;
        int columnas = mapa[0].length;

        int maxFila = 0;
        int maxColumna = 0;
        String existeColCOnsecutivasAscendentes = "no existe";

        // Contar estaciones por fila
        for (int fila = 0; fila < filas; fila++) {
            int count = 0;
            for (int col = 0; col < columnas; col++) {
                if (mapa[fila][col] != null && !mapa[fila][col].isEmpty()) {
                    count++;
                }
            }
            if (count > maxFila) {
                maxFila = count;
            }
        }

        // Contar estaciones por columna
        int[] estacionesPorColumna = new int[columnas];
        for (int col = 0; col < columnas; col++) {
            int count = 0;
            for (int fila = 0; fila < filas; fila++) {
                if (mapa[fila][col] != null && !mapa[fila][col].isEmpty()) {
                    count++;
                }
            }
            estacionesPorColumna[col] = count;
            if (count > maxColumna) {
                maxColumna = count;
            }

            //verificar si hay columnas consecutivas con conteo ascendente
            if (col >= 2) { //Ya estoy al menos en la tercer columna para revisar las 3 previas
                int a = estacionesPorColumna[col - 2];
                int b = estacionesPorColumna[col - 1];
                int c = count;

                if (a < b && b < c) {
                    existeColCOnsecutivasAscendentes = "existe";
                }
            }
        }

        // Buscar si el maximo fue en fila, columna o ambas y setear el valor maximo
        String tipoMax = "";
        int maximo = 0;
        if ((maxFila == 0 && maxColumna == 0) || maxFila == maxColumna) {
            tipoMax = "ambas";
            maximo = maxFila;
        } else if (maxFila > maxColumna) {
            tipoMax = "fila";
            maximo = maxFila;
        } else if (maxFila < maxColumna) {
            tipoMax = "columna";
            maximo = maxColumna;
        }

        return Retorno.ok(maximo + "#" + tipoMax + "|" + existeColCOnsecutivasAscendentes);
    }

    @Override
    public Retorno listarBicicletasDeEstacion(String nombreEstacion) {
        Estacion estacion = estaciones.obtenerElemento(new Estacion(nombreEstacion));
        if(estacion == null){
            return Retorno.error1();
        }
        
        String bicisEnEstacion = estacion.getBicicletas().mostrar((Bicicleta b) -> b.toString());
        return Retorno.ok(bicisEnEstacion);
    }

    @Override
    public Retorno estacionesConDisponibilidad(int n) {
        if (n <= 1) {
            return Retorno.error1();
        }
        int cantidadEstaciones = 0;

        Lista<Estacion>.IteradorLista itEstaciones = estaciones.obtenerIterador();
        while (itEstaciones.tieneSiguiente()) {
            Estacion e = itEstaciones.obtenerActualYAvanzar();
            if (e.getContadorBiciDisponible() > n) {
                cantidadEstaciones++;
            }
        }

        return Retorno.ok(cantidadEstaciones);
    }

    @Override
    public Retorno ocupacionPromedioXBarrio() {
        String ocupacionPromedioBarrios = barrios.mostrar((Barrio b) -> b.toString());
        return Retorno.ok(ocupacionPromedioBarrios);
    }

    @Override
    public Retorno rankingTiposPorUso() {
        ListaO<TipoBicicleta>.IteradorListaO itTipoBicicletas = tiposBicis.obtenerIterador();
        ListaO<TipoBicicleta> tipoDeBicisOrdenado = new ListaO<TipoBicicleta>();

        while (itTipoBicicletas.tieneSiguiente()) {
            TipoBicicleta tbActual = itTipoBicicletas.obtenerActualYAvanzar();
            tipoDeBicisOrdenado.agregarOrd(tbActual);
        }

        String resultado = tipoDeBicisOrdenado.mostrar((TipoBicicleta tb) -> tb.toString());

        return Retorno.ok(resultado);
    }

    @Override
    public Retorno usuariosEnEspera(String nombreEstacion) {
        if (nombreEstacion.trim().isEmpty()) {
            return Retorno.error1();
        }

        Estacion estacion = estaciones.obtenerElemento(new Estacion(nombreEstacion));
        if (estacion == null) {
            return Retorno.error2();
        }

        return Retorno.ok(estacion.getUsuariosEnEsperaAlquiler().mostrar((Usuario u) -> u.toStringCampos("ci")));
    }

    @Override
    public Retorno usuarioMayor() {

        ListaO<Usuario>.IteradorListaO itUsuarios = usuarios.obtenerIterador();
        Usuario usuMayor = itUsuarios.obtenerActualYAvanzar();

        while (itUsuarios.tieneSiguiente()) {
            Usuario auxUsu = itUsuarios.obtenerActualYAvanzar();

            if (auxUsu.getCantidadAlquileres() > usuMayor.getCantidadAlquileres()) {
                usuMayor = auxUsu;
            } else if (auxUsu.getCantidadAlquileres() == usuMayor.getCantidadAlquileres()) {
                // En caso de empate, nos quedamos con el de CI menor
                if (usuMayor.getCi().compareTo(auxUsu.getCi()) > 0) {
                    usuMayor = auxUsu;
                }
            }
        }

        return Retorno.ok(usuMayor.getCi());
    }

}
