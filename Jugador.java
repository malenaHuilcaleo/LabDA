public class Jugador {
    private String apellido;
    private String nombre;
    private int edad;
    private int dni;
    private int numCamiseta;
    private int cantGoles;
    private String nomEquipo;

    //Constructores
    public Jugador(String ape, String nom, int docu, int ed, int num, String nomEq){
        edad=ed;
        nombre=nom;
        apellido=ape;
        dni=docu;
        numCamiseta=num;
        cantGoles=0;
        nomEquipo=nomEq;
        

    }
    public Jugador(int docu){
        edad=0;
        nombre="";
        apellido="";
        dni=docu;
        numCamiseta=0;
        cantGoles=0;
        nomEquipo="";
        
    }
    //Observadores
    public int getEdad(){
        return edad;
    }
    public int getDni(){
        return dni;
    }
    public int getCamiseta(){
        return numCamiseta;
    }
    public String getNombre(){
        return nombre;
    }
    public String getApellido(){
        return apellido;
    }

    public int getCantGoles(){
        return cantGoles;
    }
    public String getNomEquipo(){
        return nomEquipo;
    }
    //modificadores
    public void setEdad(int ed){
        edad=ed;
    }
    public void setNombre(String nom){
        nombre=nom;
    }

    public void setApellido(String ape){
        apellido=ape;
    }
    
    public void añadirGol(){
        cantGoles++;
    }
    //propios del tipo
    public boolean equals(Jugador otroJugador){
        
        return dni==otroJugador.getDni();
    }
    public int compareTo(Jugador otroJugador){
        return cantGoles-otroJugador.getCantGoles();
    }
    public int compareToNombres(Jugador otroJugador){
        int aux=apellido.compareToIgnoreCase(otroJugador.getApellido());
        if(aux==0){
            aux=nombre.compareTo(otroJugador.getNombre());
        }
        return aux;
    }

    
}
