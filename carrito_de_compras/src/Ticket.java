import java.util.*;
import java.io.Serializable;
public class Ticket implements Serializable{
    ArrayList<Producto> ps;
    double Total;
    
    public Ticket(ArrayList<Producto> ps){
        double total=0;
        for(int i=0; i<ps.size(); i++){
            total = total + ps.get(i).precio;
        }
        this.ps = ps;
        this.Total = total;
    }
    public void mostrarTicket(){
        System.out.println("Tienda Feliz");
        for(int i=0; i<ps.size(); i++){
            System.out.println(ps.get(i).nombre+"    $"+ps.get(i).precio);
        }
        System.out.println("Total a pagar: $"+Total);
        System.out.println("Gracias por su compra");
    }
    public String toString(){
        String aux = "Tienda Feliz";
        for(int i=0; i<ps.size(); i++){
           aux+="\n"+ps.get(i).nombre+"   "+ps.get(i).precio;
        }
        aux+="\n\nTotal a pagar: "+Total+"\nGracias por su compra";
        return aux;
    }
}
