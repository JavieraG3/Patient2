import java.util.*;

public class Hospital2 {
    private Map<String, Patient2> totalPatient;
    private PriorityQueue<Patient2> attentionQueue; //gestiona a los pacientes en espera
    private Map<String, AreaAttention2> areasAttention;// asignar paciente a areas especificas
    private List<Patient2> patientAttended;// pacientes para la casa

    Hospital2() {
        this.totalPatient = new HashMap<String, Patient2>();
        this.areasAttention = new HashMap<>();
        this.patientAttended = new ArrayList<Patient2>();

        areasAttention.put("SAPU", new AreaAttention2("SAPU", 90));
        areasAttention.put("Urgencia adulto", new AreaAttention2("Urgencia adulto", 120));
        areasAttention.put("Infantil", new AreaAttention2("Infantil", 100));

        Comparator<Patient2> c = new Comparator<Patient2>() {

            public int compare(Patient2 p1, Patient2 p2) {
                int nuevaCategoria1 = getNuevaPrioridad(p1);
                int nuevaCategoria2 = getNuevaPrioridad(p2);

                if (nuevaCategoria1 < nuevaCategoria2) {
                    return -1;
                }
                if (nuevaCategoria1 > nuevaCategoria2) {
                    return 1;

                } else {//tienen la misma categoria, se resuelve en base a su orden de llegada
                    if (p1.getArrivalTime() < p2.getArrivalTime()) {
                        return -1;
                    }
                    if (p1.getArrivalTime() > p2.getArrivalTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
        };
        this.attentionQueue = new PriorityQueue<>(c);

    }
   public static  int getNuevaPrioridad(Patient2 p) {
       //se crean variables para esta "nueva" prioridad basada en tiempos de espera
       long tiempoEsperaMinutos= (System.currentTimeMillis() - p.getArrivalTime()) / 60000;
       //La categoria del paciente va subiendo dependiendo de su tiempo de espera
       //como c1 se atiende inmediato y c2 tiene max 30m nos enfocamos en c3 c4 c5

       if(p.getCategoria()==5 && tiempoEsperaMinutos> 90)
           return 4; //sube a categoria 4

       if(p.getCategoria()==4 && tiempoEsperaMinutos> 60)
           return 3; //sube a categoria 3

       if(p.getCategoria()==3 && tiempoEsperaMinutos> 40)
           return 2; //sube a categoria 2

      return p.getCategoria();
   }



    private String DetermineAreaAccordingCategory(int categoria) {// una forma para asignarle un area en especifica
        if (categoria == 1 || categoria == 2) return "Urgencia adulto";
        else if (categoria == 3 || categoria == 4) return "SAPU";
        else return "Infantil";
    }

    void registerPatient(Patient2 patient) {//agrega un paciente al mapa general y a la cola de espera
        String id = patient.getID();
        if (totalPatient.containsKey(id)) {//verifica si no esta registrado
            System.out.println("Paciente ya registrado");
        } else {
            totalPatient.put(id, patient); //agrrga al mapa
            String area = DetermineAreaAccordingCategory(patient.getCategoria());
            patient.setArea(area);
            attentionQueue.add(patient);//agreega a cola de atencion
        }
    }

    void realiseCategory(String ID, int newCategory) {
        Patient2 patient = totalPatient.get(ID);
        attentionQueue.remove(patient);//lo quita de la cola
        patient.setCategory(newCategory);
        attentionQueue.add(patient);//lo agrega con su nueva categoria
    }

    public Patient2 nextPatient() { //deriva al area
        if (attentionQueue.isEmpty()) {
            System.out.println("No hay pacientes disponibles");
            return null;
        }
        Patient2 patient = attentionQueue.poll();// deberia ya estar ordenado
        String areaName = DetermineAreaAccordingCategory(patient.getCategoria());
        AreaAttention2 area = areasAttention.get(areaName);

        if (area != null) {
            area.addPatient(patient);
            System.out.println("Paciente " + patient.getID() + " asignado a área " + areaName);
            return patient;
        } else {
            System.out.println("Área " + areaName + " no encontrada.");
        }

        return null;
    }

    public List<Patient2> getPatientsByCategory(int category) {//saca al de mayor categoria y lo asigna a su area???
        List<Patient2> pacientesFiltrados = new ArrayList<>();
        for (Patient2 patient : attentionQueue) {
            if (patient.getCategoria() == category && patient.getState().equals("En espera")) {
                pacientesFiltrados.add(patient);
            }
        }
        return pacientesFiltrados;
    }

    public AreaAttention2 getArea(String nameArea) {
        if (areasAttention.containsKey(nameArea)) {
            return areasAttention.get(nameArea);
        } else {
            System.out.println("Area no encontrada: " + nameArea);
            return null;
        }
    }
}
