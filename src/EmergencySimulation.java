import java.text.SimpleDateFormat;
import java.util.List;

public class EmergencySimulation{

    private List<Patient2> randomPatient;

    public void PassgeOfTime(){// integra y atiende paciente en los tiempos indicados

        SimpleDateFormat formato= new SimpleDateFormat("HH:mm:ss");//formato.format(currentTime) -> Para mostrar en hh:mm:ss

        long minute = 60 * 1000;
        long interval_24H = 24 * 60 * minute;
        long addInterval = 10 * minute;
        long attendInterval = 15 * minute;


        long startTime = System.currentTimeMillis();//Tiempo de partida
        long lastPatientAdded = startTime;
        long lastPatientAttended = startTime;

        List<Patient2> PatientList = GeneratePatient.leerDatos("Pacientes_24H.csv");
        int c = 0;

        while (System.currentTimeMillis() - startTime < interval_24H) {
            long currentTime = System.currentTimeMillis(); // Tiempo transcurrido

            if (currentTime - lastPatientAdded >= addInterval) { // cada 10 min
                if(c<PatientList.size()){
                    randomPatient.add(PatientList.get(c));
                    c++;
                }else{
                    System.out.println("No se encuentran mas pacientes");
                }

                lastPatientAdded = currentTime;
            }

            if (currentTime - lastPatientAttended >= attendInterval) { // cada 15 min
                if(!randomPatient.isEmpty()){
                    Patient2 atendido = randomPatient.remove(0);
                    //System.out.println("Paciente atendido a las: " + formato.format(currentTime));
                }else{
                    System.out.println("No hay patientes por atender");
                }
                lastPatientAttended = currentTime;
            }

            // Pequeña pausa para no saturar la CPU
            try {
                Thread.sleep(minute); // 100 ms de espera entre verificaciones
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
