import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;


public class Campeonato {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        Equipo[] equipos=new Equipo[15];
        String archivoEquipos= "Equipos.txt";
        String archivoJugadores="Jugadores.txt";
        procesarListaEquipos(archivoEquipos, equipos);  
        procesarListaJugadores(archivoJugadores, equipos);
        Partido[][]fechas=generarFixture(equipos);
        Jugador [] todosLosJugadores=obtenerTodosLosJugadores(equipos);
        int opcion;
        do {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    mostrarFechas(fechas);
                    break;
                case 2:
                    cargarResultadosFecha(fechas);
                    break;
                case 3:
                    agregarNuevoJugadorUsuario(equipos, sc);
                    todosLosJugadores=obtenerTodosLosJugadores(equipos);
                    break;
                case 4:
                    ordenarEquiposSegunPosiciones(equipos);

                    verTablaPosiciones(equipos);
                    break;
                case 5:
                    verResultadosFecha(fechas, sc);
                    break;
                case 6:
                    
                    ordenarJugadoresSegunGoles(todosLosJugadores);
                    verGoleadores(todosLosJugadores);
                    break;
                case 7:
                    subMenuEstadisticas(sc, equipos, todosLosJugadores);
                    break;
                case 8:
                    subMenuJugadores(sc, equipos, todosLosJugadores);
                    
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente nuevamente.");
            }
        } while (opcion != 0);   
        
    }
    
    public static void mostrarMenu(){
        System.out.println("---------Menu Principal----------");
        System.out.println(" 1.  Mostrar Fixture ");
        System.out.println(" 2. Ingresar resultados de una fecha");
        System.out.println(" 3. Agregar nuevo jugador");
        System.out.println(" 4. Tabla de posiciones");
        System.out.println(" 5. Ver Resultados de una fecha");
        System.out.println(" 6. Tabla de goleadores");
        System.out.println(" 7. Estadisticas");
        System.out.println(" 8. Ver jugadores");
        System.out.println(" 0. Salir");
    }
    public static void subMenuEstadisticas(Scanner sc, Equipo[]arr, Jugador[] todosJug){
            int opcion;
            do {
                System.out.println("------ Menu Estadisticas ----");
                System.out.println("1. Edad promedio de los Jugadores");
                System.out.println("2. Cantidad de jugadores con edad Mayor a promedio + 5");
                System.out.println("3. Buscar jugador por edad y equipo");
                System.out.println("0. Volver al Menú Principal");
                System.out.print("Seleccione una opción: ");
                opcion = sc.nextInt();
                sc.nextLine(); // Consumir el salto de línea
                switch (opcion) {
                    case 1:
                        
                        int edadPromedio=edadPromedio(todosJug);
                        System.out.println("Edad promedio: "+edadPromedio);    
                        break;
                    case 2: 
                        
                        int edP= edadPromedio(todosJug);
                        int cantidad=cantJugadoresSuperanProm(opcion, edP, todosJug);
                        System.out.println("Cantidad de jugadores que superan en 5 la edad promedio: "+cantidad);
                        break;
                    case 3:
                        System.out.print("Ingrese el nombre del equipo: ");
                        String nomE=sc.nextLine();
                        System.out.print("Ingrese la edad: ");
                        int edad=sc.nextInt();
                        buscarPJMenosEdad(nomE, edad, arr);
                        break;
                    case 0:
                        System.out.println("Volviendo al menu principal");  
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
            } while (opcion != 0);
    }
    public static void subMenuJugadores(Scanner sc, Equipo[]arr, Jugador [] todosJug){
        int opcion;
        do {
            System.out.println("------ menu ordenar jugadores ------");
            System.out.println(" 1. Metodo de ordenamiento divide y venceras");
            System.out.println(" 2. Metodo de ordenamiento por fuerza bruta");
            System.out.println(" 0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    
                    mergeSortPorNombre(todosJug, 0, todosJug.length-1); 
                    mostrarJugadoresOrdenadosApellido(todosJug);     
                    break;
                case 2: 
                
                    ordenarJugadoresSegunNombre(todosJug);
                    mostrarJugadoresOrdenadosApellido(todosJug);
                    break;
                case 0:
                    System.out.println("Volviendo al menu principal");  
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 0);


}
   
//procesar archivos
    public static void procesarListaEquipos(String nombeArchivo,Equipo[]arr){
        int i=0;
    
        try{
            FileReader leerArchivo = new FileReader(nombeArchivo);
            BufferedReader blectura = new BufferedReader(leerArchivo);
            String linea = null;

            while ((linea = blectura.readLine()) != null && i < arr.length) {
            // Dividir la línea usando split con el delimitador ";"
                String[] datosEquipo = linea.split(";");

            // Asegurarse de que la línea tiene el formato correcto
                if (datosEquipo.length == 2) {
                    String nombre = datosEquipo[0].trim();
                    char categoria = datosEquipo[1].trim().charAt(0);

                    Equipo eq = new Equipo(nombre, categoria);
                    arr[i] = eq;
                     i++;
                }
            }
            blectura.close();
        }catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        catch (IOException ex) {
            System.err.println("Error leyendo algun archivo.");
        }
    }
    public static void procesarListaJugadores(String nombeArchivo,Equipo[]arr){
        

        try{
            FileReader leerArchivo = new FileReader(nombeArchivo);
            BufferedReader blectura = new BufferedReader(leerArchivo);
            String linea = null;

            while ((linea = blectura.readLine()) != null) {
                // Dividir la línea usando split con el delimitador ";"
                String[] datosJugador = linea.split(";");

                // Asegurarse de que la línea tiene el formato correcto
                if (datosJugador.length == 6) {
                    String apellido = datosJugador[0].trim();
                    String nombre = datosJugador[1].trim();
                    int edad = Integer.parseInt(datosJugador[2].trim());
                    int dni = Integer.parseInt(datosJugador[3].trim());
                    int numCamiseta = Integer.parseInt(datosJugador[4].trim());
                    String nomEquipo = datosJugador[5].trim();

                    int pos = buscarPosEquipo(arr, nomEquipo);
                    if (pos != -1) {
                        Jugador nuevoJug = new Jugador(apellido, nombre, dni, edad, numCamiseta, nomEquipo);
                        verificaryCargarNuevoJugador(arr, nuevoJug);
                    }
                }
            
            }
            blectura.close();
        }catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage() + "\nSignifica que el archivo del "       + "que queriamos leer no existe.");
        }
        catch (IOException ex) {
            System.err.println("Error leyendo algun archivo.");
        }
    }
    //fixture 
    public static Equipo[] crearArrTem(Equipo []arr){
        //este metodo crea un arreglo temporal con un numero de equipo par y no sobredimensionado
        Equipo[]arrTemp; 
        int j;
        //cuento la cantidad de equipos no nulos 
       int cantEquiposNoNulos=cantidadEquiposNoNulos(arr);
        boolean impar=cantEquiposNoNulos%2!=0;
        int longitud= impar? cantEquiposNoNulos+1:cantEquiposNoNulos;
        arrTemp=new Equipo[longitud];

        for(j=0; j<cantEquiposNoNulos; j++){
            arrTemp[j]=arr[j];
        }
        if(impar){//si la cantidad de equipos es impar a todos les tocara un partido de descanso
            arrTemp[cantEquiposNoNulos]=new Equipo("Descanso", 'N');
        }
        return arrTemp;
    }
    public static Partido[][] generarFixture(Equipo[]arr){
        Equipo [] arrTemp=crearArrTem(arr);
        int i,j;
        int cantFechas = arrTemp.length-1;
        int partidosFecha= arrTemp.length/2;
        Partido[][] fechas=new Partido[cantFechas][partidosFecha];

        for ( i = 0; i < cantFechas; i++) {
            for ( j = 0; j < partidosFecha; j++) {
                int local = (i + j) % (cantFechas);
                int visita = (cantFechas - j + i) % (cantFechas);
                // el ultimo equipo permane en el mismo lugar de los demás
                // rotar .
                if (j == 0) {
                    visita = cantFechas;
                }
                if(i%2==0){
                    fechas[i][j] = new Partido(arrTemp[local], arrTemp[visita]);
                }else{
                    fechas[i][j] = new Partido(arrTemp[visita], arrTemp[local]);
                }
                
            }
        }                     
    return fechas;

    }
    public static void mostrarFechas(Partido[][]fechas){
        //metodo para  mostrar el fixture del partido
        int i, j;
        
        System.out.println("-------------------------------------------------------------------");

        
        for ( i = 0; i < fechas.length; i++) {
            System.out.println("Fecha " + (i + 1) + ":");
            System.out.println("-------------------------------------------------------------------");
            for ( j = 0; j < fechas[i].length; j++) {
                Partido partido=fechas[i][j];
                System.out.println(partido.toStringFechas());
            }
            System.out.println("-------------------------------------------------------------------");
        }
    }
    //Carga Resultados Fechas 
    public static int fechaACargar(Partido[][] fixture){
        //este modulo busca la primera fecha que aun no se cargo o retorna -1 cuando todas han sido cargadas
        int pos= -1, i=0;
        boolean posSinCargar= false;
        while(i<fixture.length && !posSinCargar){
            if(fixture[i][0].getCargaResultados()){
                i++;
            }else{
                pos=i;
                posSinCargar=true;
            }
        }  
        if(pos!=-1){

        }
        return pos;

    }
    public static void cargarResultadosFecha(Partido[][]fixture){
        //carga los resultados de una unica fecha, cada fecha puede ser cargada una unica vez 
        int j=0;
        Scanner sc= new Scanner(System.in);
        int posACargar=fechaACargar(fixture); //busco la proxima fecha a cargar
        Equipo equipoLocal, equipoVisitante;

        if(posACargar!=-1){
            System.out.println("Se cargaran los datos de la fecha " +(posACargar+1));
            System.out.println("-------------------------------------------------");
            while(j<fixture[0].length){
                Partido partido=fixture[posACargar][j];
                equipoLocal=partido.getEquipoLocal();
                equipoVisitante=partido.getEquipoVisitante();
                
                if(!partido.esPartidoDescanso()){
                    System.out.println("Partido: "+equipoLocal.getNombre()+" vs "+equipoVisitante.getNombre());
                    //goles equipo local
                    int golesL=ingresarGoles(sc, equipoLocal, "local");
                    //goles equipo visitante
                    int golesV=ingresarGoles(sc, equipoVisitante, "visitante");
                    //carga el resultado del partido y actualiza las estadisticas de los equipos
                    fixture[posACargar][j].cargarResultados(golesL, golesV);
                    //mostrar resultados del partido
                    System.out.println("Resultado cargado: " + equipoLocal.getNombre() + " " + golesL + " - " + golesV + " " + equipoVisitante.getNombre());
                    System.out.println("-------------------------------------------------------------------");
                }else{
                    //marco el descanso como "jugado"
                    fixture[posACargar][j].setCargaResultado();
                }
                j++;  
            }
            
        }else{
            System.out.println("Todas las fechas han sido cargadas");
        }
    }
    public static int ingresarGoles(Scanner sc, Equipo equipo, String tipoEquipo){
        int golesC;

        System.out.print("Ingrese los goles del equipo "+tipoEquipo+" ("+equipo.getNombre()+"): ");
        int goles= sc.nextInt();
        if(goles>0){
            System.out.println("Alguno se efectuo por ser un gol en contra? Ingrese 1 para SI o cualquier numero para NO");
            int hayGC=sc.nextInt();
            if(hayGC!=1){
                registrarGoleadores(goles, equipo, tipoEquipo, sc);
            }else{
                golesC=ingresarGolesEnContra(sc, equipo, goles);
                //si todos los goles fueron en contra no necesito llamar a la funcion registrarGoleadores
                if(goles!=golesC){
                    System.out.println("Ahora se registraran los goles hechos por jugadores del equipo.");
                    registrarGoleadores(goles-golesC, equipo, "local", sc);  
                }
                
            }    
        }
        return goles;
    }
    public static int ingresarGolesEnContra(Scanner sc, Equipo equipo, int goles){
        int cant=-1;
        while(cant<0 || cant>goles){
            System.out.println("Cuantos goles en contra hubo?");
            cant=sc.nextInt();
            if(cant>goles || cant<0){
                System.out.println("Cantidad de goles no valida intente nuevamente.");
            }
        }
        return cant;
    }
    public static void registrarGoleadores(int cantGoles, Equipo equipo, String tipoEquipo, Scanner sc){
        int i=1, numCamiseta;
        Jugador jug;
        System.out.println("Se registraran los goles del equipo "+tipoEquipo+" "+ equipo.getNombre());
        while(i<=cantGoles){
            System.out.print("ingrese el numero de camiseta del jugador que realizo el gol n°"+i+": " );
            numCamiseta=sc.nextInt();
            jug=equipo.buscarJugadorPorCamiseta(numCamiseta);//verifica que haya un jugador con esa camiseta en el equipo
            if(jug!=null){
                //actualiza la cantidad de goles del jugador
                jug.añadirGol();
                System.out.println("Gol registrado para el jugador: " + jug.getNombre() + " (Camiseta n°" + numCamiseta + ")");
                i++;
            }else{
                System.out.println("Número de camiseta incorrecto, verifique la información e intente nuevamente.");
            }
        }
    }
    //agregar jugador
    public static void agregarNuevoJugadorUsuario(Equipo[]arrEquipos, Scanner sc){
        System.out.println("Se realizara la carga de un nuevo jugador, por favor ingrese los datos solicitados");
        
        System.out.print("Nombre del equipo: ");
        String nombreEquipo=sc.nextLine();

        System.out.print("Dni: ");
        int dni= sc.nextInt();
        System.out.print("Edad: ");
        int edad=sc.nextInt();
        System.out.print("Numero de camiseta: ");
        int camiseta=sc.nextInt();
        sc.nextLine();

        System.out.print("Nombre: ");
        String nombre= sc.nextLine();

        System.out.print("Apellido:");
        String apellido=sc.nextLine();
        
        Jugador nuevoJugador= new Jugador(apellido, nombre,dni, edad, camiseta, nombreEquipo);
        
        boolean cargaCompleta=verificaryCargarNuevoJugador(arrEquipos, nuevoJugador);
        if(cargaCompleta){
            System.out.println("La carga se completo con exito");

        }else{
            System.out.println("La carga no se pudo completar, verifique los datos del jugador y vuelva a intentar.");
        }
    }
    public static boolean verificaryCargarNuevoJugador(Equipo[]arrEquipos, Jugador nuevJugador){
        boolean cargaExitosa=false;
        
        String nombreEquipo=nuevJugador.getNomEquipo();
        //busco el equipo
        int pos=buscarPosEquipo(arrEquipos, nombreEquipo);
        if(pos!=-1){
            //el equipo existe puedo continuar 
            int dni= nuevJugador.getDni();
            //verifico que no este cargado en ningun equipo
            if(!jugadorExisteEnEquipos(arrEquipos, dni)){

                if(arrEquipos[pos].agregarJugador(nuevJugador))
                    cargaExitosa=true; 
            }
        }
        return cargaExitosa;
    }
    public static boolean jugadorExisteEnEquipos(Equipo[] arrEquipos, int dni){
        int i=0;
        boolean existeEnEquipo=false;
        while(i<arrEquipos.length && arrEquipos[i]!=null&&!existeEnEquipo){
                existeEnEquipo= arrEquipos[i].buscarJugadorPorDni(dni)!=null;
                i++;
            }
        return existeEnEquipo;
    }
    //tabla de posiciones
    public static void ordenarEquiposSegunPosiciones(Equipo []equipo){
        //insercion
        int p, j, dim;
        Equipo temp;

        dim = cantidadEquiposNoNulos(equipo);
        for(p = 1; p < dim; p++){
            temp = equipo[p];
            j = p;

            while(j>0 && temp.compareTo(equipo[j-1])>0){
                 equipo[j] =equipo[j-1];
                j--;
            }
            equipo[j] = temp;
        }
    }
    public static void verTablaPosiciones(Equipo[] equipos){
        int i=0;
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Tabla de posiciones");
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Equipo                 | Pts | PJ | PG | PE | PP | GF | GC | DF");
        System.out.println("-------------------------------------------------------------------");
        while(i<equipos.length&&equipos[i]!=null){
            System.out.println(equipos[i].toString());
            i++;
        }
        System.out.println("-------------------------------------------------------------------");
        System.out.println();
    }  
//Resultados fecha
    public static void mostrarOpcionesFechas(int cantOpciones){
        int i;
        System.out.println("ingrese la opcion que desea visualizar");
        for (i=1; i<=cantOpciones; i++){
            System.out.println(i+". Fecha "+i);
        }

    }
    public static void verResultadosFecha(Partido[][] part, Scanner sc){
        int cantOpciones=fechaACargar(part);//scantidad de fechas cargadas
        if(cantOpciones==-1)cantOpciones=part.length;
        if(cantOpciones>0){
            mostrarOpcionesFechas(cantOpciones);
        
            int op;
            do{
            System.out.println("Que resultados desea visualizar?");
            op= sc.nextInt(); 
            sc.nextLine();  
            }while(op<1||op>cantOpciones);
            int posReal=op-1;

            mostrarResultadosFecha(posReal, part);
         
        }else if(cantOpciones==0){
            System.out.println("Aun no hay fechas cargadas");
        }   
    } 
    public static void mostrarResultadosFecha(int numFecha, Partido[][] fechas){
        int i;
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Resultados Fecha "+(numFecha+1));
        System.out.println("-------------------------------------------------------------------");
        
        for (i=0; i<fechas[0].length;i++){
            Partido part=fechas[numFecha][i]; 
            if(!part.esPartidoDescanso()){
               System.out.println(part.toStringResultados());  
            }
        }
        System.out.println("-------------------------------------------------------------------");
        System.out.println();
        
    }
//tabla de goleadores
    public static void ordenarJugadoresSegunGoles(Jugador[]arr){
        //ordeno todos los jugadores por el metodo seleccion
        Jugador aux;
        int posMayor, n=arr.length, i=0;
        for(i=0; i<n-1; i++){
            posMayor=buscarMayor(i, arr);
            if(arr[posMayor].compareTo(arr[i])>0){
                aux=arr[i];
                arr[i]=arr[posMayor];
                arr[posMayor]=aux;
            }
        }
    }
    public static void verGoleadores(Jugador[]arr){
        int i=0;
        boolean parar=false;
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Goleadores del Torneo");
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Jugador                   Equipo            Goles");
        while(i<arr.length&&!parar){
            Jugador temp= arr[i];
            if(temp.getCantGoles()!=0){
                System.out.printf("%-20s      %-18s %-6d\n",
                temp.getNombre() + " " + temp.getApellido(),
                temp.getNomEquipo(),
                temp.getCantGoles()); 
                i++;
            }else{
                parar=true;
            }
        }
        System.out.println("-------------------------------------------------------------------");
        System.out.println();
    }   
    public static int buscarMayor(int desde, Jugador[]arr){
        int i, posMayor=desde;
        Jugador mayor;
        mayor=arr[desde];
        for(i=desde+1; i<arr.length;i++){
            if(arr[i].compareTo(mayor)>0){
                mayor=arr[i];
                posMayor=i;
            }
        }

        return posMayor;
    }
   //estadisticas
   //----edad promedio
    public static int edadPromedio(Jugador []arr){
    
        int suma= sumaDeEdades(arr, 0);
        
        return suma/arr.length;
    }  
    public static int sumaDeEdades(Jugador []jugadores, int i){
            int suma=0;
            if(i<jugadores.length){
                suma=jugadores[i].getEdad() + sumaDeEdades(jugadores, i+1);
            }
            return suma;
    }
    //---- menos edad 
    public static Jugador buscarMenorQueEdad(Jugador []arr, int edad, int indice){
        //este metodo retorna el primer jugador menor a una edad dada

        Jugador jugRetorno=null;

        if(indice<arr.length && arr[indice]!=null ){
            Jugador jugActual=arr[indice];
            if(jugActual.getEdad()< edad){
                jugRetorno=jugActual;
            }else{
                jugRetorno=buscarMenorQueEdad(arr, edad, indice+1);
            }
        }
        return jugRetorno;
    }
    public static void buscarPJMenosEdad(String Nequipo, int edad, Equipo[]arr){


        int pos= buscarPosEquipo(arr, Nequipo);
        if(pos!=-1){
            Equipo equipo= arr[pos];
            Jugador[] arrJugadores= equipo.getJugadores();
            Jugador jugador=buscarMenorQueEdad(arrJugadores, edad, 0);
            if(jugador!=null){
                System.out.println("Primer jugador con edad inferior a "+edad+" en el equipo "+equipo.getNombre()+": ");
                System.out.println(jugador.getApellido()+", "+jugador.getNombre()+" "+jugador.getEdad()+" años.");
            }else{
                System.out.println("No existe jugador con edad inferior a "+edad+" en el equipo "+equipo.getNombre());
            } 
        }else{
            System.out.println("El nombre del equipo no existe");
        }   
    } 
   //---supera en 5 al promedio
    public static int cantJugadoresSuperanProm(int i, int edadProm, Jugador[]arr){
        int cont=0;
        if(i<arr.length && arr[i]!=null){
            Jugador temp= arr[i];
            if(temp.getEdad()>(edadProm+5)){
                cont=1+cantJugadoresSuperanProm(i+1, edadProm, arr);
            }else{
                cont=cantJugadoresSuperanProm(i+1, edadProm, arr);
            }
        }
        return cont;
   }

    //jugadores ordenados por nombre
    public static void mostrarJugadoresOrdenadosApellido(Jugador[]arr){
        int i=0;
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Tabla de jugadores");
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Jugador                   Equipo                 Dni");
        while(i<arr.length){
            Jugador temp= arr[i];
            System.out.printf("%-20s      %-18s %12d \n",temp.getApellido()+ " " +temp.getNombre()  ,
            temp.getNomEquipo(), temp.getDni()); 
            i++;
           
            
        }

    }
   //--burbuja mejorado
    public static void ordenarJugadoresSegunNombre(Jugador[]arr){
        Jugador aux;
        int n=arr.length,i=0,j;
        boolean ordenado=false;

        while(i< n-1 && !ordenado){
            ordenado=true;
            for(j=0; j<n-i-1; j++){
                if(arr[j].compareToNombres(arr[j+1])>0){
                    ordenado= false;
                    aux=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=aux;
                }

            }
            i++;
        }

    }
    
    //-- mergesort
    public static void mergeSortPorNombre(Jugador[] arr, int ini, int fin) {
        if (ini < fin) {
            int mitad = (ini + fin) / 2;
            mergeSortPorNombre(arr, ini, mitad);
            mergeSortPorNombre(arr, mitad + 1, fin);
            merge(arr, ini, mitad, fin);
        }
    }
    public static void merge(Jugador[] arr, int ini, int mitad, int fin) {
        int posA = ini, posB = mitad + 1, posC = 0;
        Jugador[] aux = new Jugador[fin - ini + 1];

        while (posA <= mitad && posB <= fin) {
            if (arr[posA].compareToNombres(arr[posB]) < 0) {
                aux[posC++] = arr[posA++];
            } else {
                aux[posC++] = arr[posB++];
            }
        }

        while (posA <= mitad) {
            aux[posC++] = arr[posA++];
        }

        while (posB <= fin) {
            aux[posC++] = arr[posB++];
        }
        posC=0;
        while (posC<aux.length) {
            arr[ini + posC] = aux[posC];
            posC++;
        }
    }

    //aux
    public static Jugador[]obtenerTodosLosJugadores(Equipo[]arr){
        //recorre el arreglo equipo y retorna un arreglo con todos los jugadores
        
        int i=0, cantJugadoresTotal=0;
        boolean cantCompletada=false, cargaCompleta=false;
        //calculo la longitud del arreglo
        while(i<arr.length&& !cantCompletada){
            if(arr[i]!=null){
                cantJugadoresTotal+=arr[i].getCantJugadores(); 
                i++; 
            }else{
                cantCompletada=true;
            }
        }
        Jugador[] todosLosJugadores= new Jugador [cantJugadoresTotal];
        //cargo el arreglo de todos los jugadores
        int indice=0;
        i=0;
        while(i<arr.length&&!cargaCompleta){
            if(arr[i]!=null){
                Jugador[]temp=arr[i].getJugadores();
                int j=0;
                while(j<arr[i].getCantJugadores()){
                    if(temp[j]!=null){
                        todosLosJugadores[indice++]=temp[j];
                    }
                    j++;
                }

            }else{
                cargaCompleta=true;
            }
            i++;
        }
        return todosLosJugadores;
    }
    public static int cantidadEquiposNoNulos(Equipo []arr){
        int i=0;
        while(i<arr.length&&arr[i]!=null){
            
              i++;  
             
        }
        return i;
    }   
    public static int buscarPosEquipo(Equipo[] equipos, String nombreClub){
            //busco la posicion del equipo
            int posicionEquipo=-1, i=0;
            boolean encontrado=false;

            while(!encontrado&& i<10 && equipos[i]!=null){
                if(nombreClub.equalsIgnoreCase(equipos[i].getNombre())){
                    posicionEquipo=i;
                    encontrado=true;
                }
                i++;
            }
        return posicionEquipo; 
    }

} 
    



