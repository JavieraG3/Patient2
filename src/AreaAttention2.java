import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

class AreaAttention2{
    private String name;
    private PriorityQueue<Patient2> patientHeap;//mantiene los paciente ordenados por orden de llegada y urgencia
    private int maxCapacity;

    public AreaAttention2(String nombre, int maxCapacity){// la cola para las pruebas se lo podemos dar de antemano ahi veremos
        this.name = nombre;
        this.maxCapacity = maxCapacity;

//-1 p1 va antes que p2
// 1 p2 va antes que p1
//0 iguales y se considera el orden de llegada

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
        patientHeap = new PriorityQueue<Patient2>(c);

    }
    public static int getNuevaPrioridad(Patient2 p) {
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

    void addPatient(Patient2 paciente){
        if(isSaturade()){
            System.out.println("Se alcanzó la capacidad maxima en el área" + name);
        }else{
            patientHeap.offer(paciente);
        }
    }
    public Patient2 attendPatient() {
        if (!patientHeap.isEmpty()) { //verifico que no este vacia la cola de prioridad
            Patient2 p = patientHeap.peek(); // paciente prioritario
            p.setState("Atendido");//cambio el estado a atendido
            return patientHeap.poll();//retorna la cabeza/paciente
        }else{
            return null;//cola vacia
        }
    }
    public boolean isSaturade(){
        return patientHeap.size() >= maxCapacity;
    }
    public List<Patient2> obtenerPacientesPorHeapSort(){
        PriorityQueue<Patient2> aux= new PriorityQueue<Patient2>(patientHeap); //creamos un auxiliar para no modificar el original

        List<Patient2> ordenados= new ArrayList<Patient2>();

        while(!aux.isEmpty()){
            ordenados.add(aux.poll()); //ahora al ser una lista se usa add, offer solo usamos en colas
        }

        return ordenados;
    }

}
