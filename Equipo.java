/**
 * equipo
 */
public class Equipo {
    private String nombre;
    private char categoria;
    private int jugados;
    private int ganados;
    private int perdidos;
    private int empatados;
    private int golesaFavor;
    private int golesEnContra;
    private Jugador[] jugadores;
    private int cantJugadores;

    //Constructor 
    public Equipo(String nom, char cat){
        nombre=nom;
        categoria=cat;
        jugadores=new Jugador[15];
        jugados=0;
        ganados=0;
        perdidos=0;
        empatados=0;
        golesaFavor=0;
        golesEnContra=0;
        cantJugadores=0;
        
    } 
    //Observadores
    public String getNombre(){
        return nombre;
    }
    public Jugador[]getJugadores(){
        return jugadores;
    }
    public char getCategoria(){
        return categoria;
    }
    public int getJugados(){
        return jugados;
    }
    public int  getGanados(){
        return ganados;
    }
    public int getPerdidos(){
        return perdidos;
    }
    public int  getEmpatados(){
        return empatados;
    }
    public int getGolesaFavor(){
        return golesaFavor;
    }
    public int getGolesEnContra(){
        return golesEnContra;
    }
    public int getCantJugadores(){
        return cantJugadores;
    }
    public String toString() {
        return String.format("%-23s| %-4d| %-3d| %-3d| %-3d| %-3d| %-3d| %-3d| %-3d",
                nombre, puntosAcumulados(), jugados, ganados, empatados,
                perdidos, golesaFavor, golesEnContra, diferenciaGoles());
    }
    
    public void mostrarJugadoresEquipo(){
        for(int i=0; i<jugadores.length;i++){
             if(jugadores[i]!=null){
                Jugador jugador = jugadores[i];
                System.out.println("jugador " +i +": "+jugador.getNombre()+ ", Camiseta: " + jugador.getCamiseta());    
            }    
        }
    }
    //Modificadores
    public boolean agregarJugador(Jugador nuevoJugador){
           boolean cargado=false;
           if(cantJugadores<jugadores.length){
            if(buscarJugadorPorCamiseta(nuevoJugador.getCamiseta())==null){
                jugadores[cantJugadores]=nuevoJugador;
                cargado=true;
                cantJugadores++;
            }
           
           }                      
           return cargado;
        }
    public void actualizarEstadisticas(int aFavor, int enContra){

        if(aFavor> enContra){
            ganados++;
        }else if(enContra> aFavor){
            perdidos++;
        }else{
            empatados++;
        }
        golesaFavor+=aFavor;
        golesEnContra+=enContra;
        jugados++;

    }
  

    
    //Propios del tipo
    public int puntosAcumulados(){
        return (ganados*3+empatados);
    }
    public int diferenciaGoles(){
        return golesaFavor-golesEnContra;
    }

    public int compareTo(Equipo otroEquipo){
        int dif;
        dif=this.puntosAcumulados() - otroEquipo.puntosAcumulados();
        //si los puntos acumulados son iguales compara por diferencia de goles
        if(dif==0){
            dif=this.diferenciaGoles()- otroEquipo.diferenciaGoles();
        }
        return dif;
    }

   

   public Jugador buscarJugadorPorCamiseta(int numCamiseta){
        int i=0;
        Jugador jug=null;
        boolean encontrado=false;
        while(i<jugadores.length && jugadores[i]!=null && !encontrado){
            if(jugadores[i].getCamiseta()==numCamiseta){
                jug=jugadores[i];
                encontrado= true;
            }else{
                i++;
            }
        }
        return jug;//retorna el jugador encontrado o null si no existe jugador con esa camiseta
    }  
     public Jugador buscarJugadorPorDni(int dni){
        int i=0;
        Jugador jug=null;
        boolean encontrado=false;
        while(i<jugadores.length && jugadores[i]!=null&&!encontrado){
            if(jugadores[i].getDni()==dni){
                jug=jugadores[i];
                encontrado= true;
            }else{
                i++;
            }
        }
        return jug;//retornajugador con ese dni o null si no existe
    }
  
     public boolean equals(Equipo otroEquipo){
        return nombre.equalsIgnoreCase(otroEquipo.nombre);
    }
}