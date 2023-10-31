public class Estudiante {
    private int id;
    private String nombre;
    private String apellido;
    private String genero;
    private String carrera;
    private int puntaje;


    public Estudiante(int id, String nombre, String apellido, String genero, String carrera, int puntaje) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.carrera = carrera;
        this.puntaje = puntaje;
    }

    //setters y getters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

     public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

     public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

     public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }

     public String getCarrera() {
        return carrera;
    }
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

     public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje){
        this.puntaje = puntaje;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "Nombre='" + nombre + '\'' +
                ", apellido ='" + apellido + '\'' +
                ", genero =" + genero +
                ", carrera ='" + carrera + '\'' +
                ", puntaje =" + puntaje + '\''  +
                '}';
    }
}
