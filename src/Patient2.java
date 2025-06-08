import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.*;

class Patient2{
    private String name;
    private String lastName;
    private String ID; //rut
    private int categoria; // del 1 al 5 C1-C5
    private long arrivalTime;
    private String state;
    private String area;
    private Stack<String> changeHistory;//categoria que ira cambaidno el paciente

    Patient2(String name, String lastName, String ID, long arrivalTime) {
        this.name = name;
        this.lastName = lastName;
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.state = "En espera";
        this.area = "Sin asignar";
        this.changeHistory = new Stack<>();
        Random rand = new Random();

        int rango = rand.nextInt(1, 101);

        if (rango <= 10)// 10%
            this.categoria = 1;
        else if (rango <= 25)// 15%
            this.categoria = 2;
        else if (rango <= 43)// 18%
            this.categoria = 3;
        else if (rango <= 70)// 27%
            this.categoria = 4;
        else // 30%
            this.categoria = 5;

    }
    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getID() {
        return ID;
    }
    int getCategoria() {
        return categoria;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }
    public String getArea() {
        return area;
    }
    public String getState() {
        return state;
    }

    public Stack<String> getChangeHistory() {
        return changeHistory;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setCategory(int newCategory) {
        this.categoria = newCategory;
        recordChange("Categoria cambiada a C" + newCategory);
    }
    public void setState(String newState) {
        List<String> EV = Arrays.asList("En atencion", "Atendido");//estados validos
        if(EV.contains(newState)){
            this.state = newState;
            recordChange("Estado cambiada a " + newState);
        }else{
            throw new IllegalArgumentException("Estado invalido");
        }
    }

    public String fullName() {
        return name + " " + lastName;
    }
    public long currentTimeout() {
        return (long) (System.currentTimeMillis() - arrivalTime) / 60000;

    }

    public void recordChange(String descripcion){//cambia el historial del paciente
        changeHistory.push(descripcion);//pila que registra cambios
    }

    public String getLastChange() {//obtiene y remueve el ultimo cambio
        if (changeHistory.size() > 0) {
            return changeHistory.pop();
        } else {
            return "No se encuentran cambios disponibles";
        }
    }
}


