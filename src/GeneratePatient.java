import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneratePatient {

    private static String[] names = {
            "Guliano", "Javiera", "Francisco", "Benjamin", "Ronal",
            "Samuel", "Agustin", "Lucía", "Diego", "Camila",
            "Mateo", "Valentina", "Andrés", "Cristian", "Tomás",
            "Elena", "Gabriel", "Isabella", "Samuel", "Martina"
    };
    private static String[] lastNames = {
            "Bardi", "Olguin", "Gatica", "López", "Martínez",
            "Sánchez", "Ramírez", "Cruz", "Olmos", "Díaz",
            "Torres", "Vargas", "Castillo", "Morales", "Ortiz",
            "Flores", "Ruiz", "Medina", "Herrera", "Silva"
    };
    private static String[] areas = {
            "Sapu", "Urgencia Adulto", "infantil"
    };
    private static String prefijo = "A";
    private static int counter = 1;
    private static long timestamp_inicio = 0;
    private static Random random = new Random();

    public static List<Patient2> generatePatient(int cantidad) {

        List<Patient2> patientsRandom = new ArrayList<>();

        for (int i = 1; i <= cantidad; i++) {

            String name= names[random.nextInt(names.length)];
            String lastname = lastNames[random.nextInt(lastNames.length)];
            String id = prefijo + String.format("%04d",counter);
            long timestamp = timestamp_inicio + (i * 10 * 60* 1000);

            Patient2 p = new Patient2(name, lastname, id, timestamp);
            patientsRandom.add(p);

            counter++;
        }

        return patientsRandom;
    }

    public static void guardarDatos(List<Patient2> patients, String nombreArchivo) {

        try(FileWriter writer = new FileWriter(nombreArchivo)) {

            writer.write("Nombre, Apellido, Id, Tiempo de llegada \n");// encabezados de columans

            for (Patient2 p : patients) {
                writer.write(p.getName() + "," + p.getLastName() + "," +
                        p.getID() + "," + p.getArrivalTime() + "\n");
            }

            System.out.println("El Archivo " + nombreArchivo + " ha sido guardado correctamente.");

        }catch(IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    public static ArrayList<Patient2> leerDatos(String nombreArchivo) {

        ArrayList<Patient2> patientRandom = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {// leer linea por linea
            String linea = br.readLine();

            while ((linea = br.readLine()) != null) {
                String[] parts = linea.split(",");

                if (parts.length == 4) {
                    String name = parts[0].trim();
                    String lastname = parts[1].trim();
                    String id = parts[2].trim();
                    int arrivalTime = Integer.parseInt(parts[3].trim());

                    patientRandom.add(new Patient2(name,lastname,id,arrivalTime));
                }
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return patientRandom;
    }
    public static void main(String[] args) {

        List<Patient2> listPatient = GeneratePatient.generatePatient(1000);
        guardarDatos(listPatient, "Pacientes_24H.txt");

        /*
        //para leer los juegos
        List<Patient> patientList = GeneratePatient.leerDatos("Pacientes_24H.csv");

        for (int i = 0; i < juegos.size(); i++) {
            System.out.println((i + 1) + ") : " + juegos.get(i).imprimir());
        }
        */

    }
}
