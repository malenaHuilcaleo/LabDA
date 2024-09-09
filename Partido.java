public class Partido {
    private int golesLocales;
    private int golesVisitantes;
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private boolean cargaResultados;
    
    //constructores
 
    public Partido(Equipo local, Equipo visitante){
        golesLocales=0;
        golesVisitantes=0;
        equipoLocal=local;
        equipoVisitante=visitante;
        cargaResultados=false;
    }
    //observadores
    public int getGolesLocales(){
        return golesLocales;
    }
    public int getGolesVisitantes(){
        return golesVisitantes;
    }
    public Equipo getEquipoLocal(){
        return equipoLocal;
    }
    public Equipo getEquipoVisitante(){
        return equipoVisitante;
    }
    public boolean getCargaResultados(){
        return cargaResultados;
    }
    public String toStringFechas(){
        String cadena;
        if (equipoLocal.getNombre().equals("Descanso")) {
            cadena="Fecha libre: " + equipoVisitante.getNombre();
        } else if (equipoVisitante.getNombre().equals("Descanso")) {
            cadena="Fecha libre: " + equipoLocal.getNombre();
        } else {
            cadena= equipoLocal.getNombre() + " vs " + equipoVisitante.getNombre();
        } 
        return cadena;
    }
    public String toStringResultados(){ 
        
        return String.format("%20s  %2d %6d   %-20s\n",
            equipoLocal.getNombre(), golesLocales,golesVisitantes ,equipoVisitante.getNombre());

    }

    //modifadores
    public void cargarResultados(int golesL, int golesV){
        golesLocales=golesL;
        golesVisitantes=golesV;
        cargaResultados=true;
        //actualizo enseguida las estadisticas del equipo
        equipoLocal.actualizarEstadisticas(golesL, golesV);
        equipoVisitante.actualizarEstadisticas(golesV, golesL);
    }
    public void setCargaResultado(){
        //este modulo solo me permite modificar la carga de resultado cuando el partido representa un descanso
        if(esPartidoDescanso()){
            cargaResultados=true;
        }
    }
    //propias del tipo
    public boolean esPartidoDescanso(){
        boolean esPartidoDescanso=false;
        if(equipoLocal.getNombre().equals("Descanso")||equipoVisitante.getNombre().equals("Descanso")){
            esPartidoDescanso=true;
        }
        return esPartidoDescanso;
    }
   


}
