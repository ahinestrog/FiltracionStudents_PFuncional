import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.stream.*;


class Main {
    static List<Estudiante>student;
    public static void main(String[] args) throws IOException {
        cargarArchivo();
        System.out.println("Aspirantes por carrera");
        carreras();
        System.out.println();
        System.out.println("Total de mujeres por carrera: \n");
        mujeres();
        System.out.println();
        System.out.println("Total de hombres por carrera: \n");
        hombres();
        System.out.println();
        promedioCarrera();
        System.out.println();
        estuMaPuntajeCar();
        System.out.println();
        estuMaPuntaje();
        System.out.println();
        PromedioAll();


    }
    static void cargarArchivo() throws IOException{
        Pattern pattern =Pattern.compile(",");
        String filename= "student-scores.csv";

        try(Stream<String> lines = Files.lines(Path.of(filename))){
            student=lines.skip(1).map(line->{
                String[]arr=pattern.split(line);
                return new Estudiante(Integer.parseInt(arr[0]), arr[1], arr[2],arr[4], arr[9], Integer.parseInt(arr[10]));
            }).collect(Collectors.toList());
        }
    }

    //Función que agrupa los estudiantes según su carrera por medio de collectors y un método "groupingBy", este metodo crea
    //un mapa donde la clave es "carrera" y los valores son la lista de los estudiantes de dicha carrera.
    static void carreras() {
        Map<String, List<Estudiante>> carreritas = student.stream()
                .collect(Collectors.groupingBy(Estudiante::getCarrera));

        carreritas.forEach((carrera, students) -> {
            System.out.println("\nEstudiantes de la carrera " + carrera + ":");
            students.forEach(System.out::println);
            System.out.println("Total de estudiantes en la carrera " + carrera + ": " + students.size());
        });
    }

    //Métodos de filtrado para obtener el total de mujeres y hombres por carrera


    //Funcion que agrupa los estudiantes segun su genero, mujeres/hombres, por carrera. Esto se realiza por medio de un filtro
    // que implementa una lambda para cada estudiante el cual tenga genero femenino/masculino, utilizando el método "groupingBy" y cuenta cuántas
    // estudiantes hay en cada carrera utilizando "Collectors.counting()". El resultado es un mapa (totalMujeres/hombresPorCarrera) donde las claves
    // son los nombres de las carreras y los valores son el total de mujeres en cada carrera.
    static void mujeres() {
        Map<String, Long> mcar = student.stream()
                .filter(x -> x.getGenero().equals("female"))
                .collect(Collectors.groupingBy(Estudiante::getCarrera, Collectors.counting()));

        mcar.forEach((carrera, totalMujeres) -> {
            System.out.println("Total de mujeres en la carrera " + carrera + ": " + totalMujeres);
        });
    }

    static void hombres() {
        Map<String, Long> hcar = student.stream()
                .filter(x -> x.getGenero().equals("male"))
                .collect(Collectors.groupingBy(Estudiante::getCarrera, Collectors.counting()));

        hcar.forEach((carrera, totalHombres) -> {
            System.out.println("Total de hombres en la carrera " + carrera + ": " + totalHombres);
        });
    }


    //Se mapearán los promedios de las notas de los estudiantes, es decir, se guardarán en un mapa donde la clave es la carrera
    //y el valor es un número "double" (promedio de notas), luego se agrupan los estudiantes según el valor de su carrera, para luego
    //calcular el promedio de cada uno de los grupos de los estudiantes, y ese es el valor que se almacena en la clave de la carrera. Y luego, se imprime.
    static void promedioCarrera() {
        Map<String, Double> promCa = student.stream()
                .collect(Collectors.groupingBy(
                        Estudiante::getCarrera,
                        Collectors.averagingDouble(Estudiante::getPuntaje)
                ));

        System.out.println("\nPromedio de notas por carrera:\n");
        promCa.forEach((carrera, promedio) -> {
            System.out.println(carrera + ": " + promedio);
        });
    }


    //se crea un mapa qu será el que almacenará el estudiante con mayor puntaje por carrera, donde la clave es la carrera y el valor es el objeto de estudiante
    //Se obtiene la carrera del estudiante y se obtienen dos estudiantes, el primero es el estudiante que se está evaluando y el segundo es el estudiante que se encuentra,
    // y se verifica que estudiante tiene el puntaje más alta y se almacena en el mapa. Y se imprime.
    static void estuMaPuntajeCar() {
        Map<String, Estudiante> stuMayorP = student.stream()
                .collect(Collectors.toMap(
                        Estudiante::getCarrera,
                        Function.identity(),
                        (estudiante1, estudiante2) -> estudiante1.getPuntaje() > estudiante2.getPuntaje() ? estudiante1 : estudiante2
                ));

        System.out.println("Estudiante con el puntaje más alto por carrera:\n");
        stuMayorP.forEach((carrera, estudiante) -> {
            System.out.println("Carrera: " + carrera + "Estudiante: " + estudiante.getNombre() + " Puntaje: " + estudiante.getPuntaje());

        });
    }

    //Se declara una variable de tipo estudiante, que será la que almacene el estudiante con el mayor puntaje de todos
    //y por medio de un flujo de estudiantes, se obtiene el estudiante con el puntaje más alto por medio del ".max" y se almacena en la variable.
    //Se utiliza un "orElse" en caso de que no haya estudiante, a forma de excepción y se imprime el estudiante seguido de su puntaje.
    static void estuMaPuntaje() {
        Estudiante altoPuntaje = student.stream()
                .max((estudiante1, estudiante2) -> estudiante1.getPuntaje() > estudiante2.getPuntaje() ? 1 : -1)
                .orElse(new Estudiante(0, "Desconocido", "Quien sabe", "Ni se", "VAGO", 0));

        System.out.println("\nEstudiante con el puntaje más alto de todos:");
        System.out.println("Estudiante: " + altoPuntaje.getNombre()+ " Puntaje: "+ altoPuntaje.getPuntaje());
    }

    //Se crea un mapa en el cual la clave es la carrera del estudiante y el valor "double" es el promedio de notas de los estudiantes de esa carrera.
    // Se obtiene la carrera del estudiante y se agrupan por su respectiva carrera , por medio de otro "Collector" se obtiene el promedio de notas de los estudiantes de las carreras
    //y por medio de los metodos de entrySet y max, se obtiene la carrera con el mejor promedio de notas. Y se genera un "orElse" en caso de que no haya carrera con el mejor promedio.
    // Y se imprime.
    static void PromedioAll() {
        Map<String, Double> carreraProm = student.stream()
                .collect(Collectors.groupingBy(
                        Estudiante::getCarrera,
                        Collectors.averagingDouble(Estudiante::getPuntaje)
                ));

        String bestStu = carreraProm.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("NADA");

        System.out.println("\nCarrera con el mejor promedio de notas: " + bestStu);
    }
}