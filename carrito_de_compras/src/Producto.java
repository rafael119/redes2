import java.io.Serializable;
public class Producto implements Serializable{
    String nombre;
    String categoria;
    Double precio;
    int existencias;
    
    public Producto(String nombre, String categoria, double precio, int existencias){
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.existencias = existencias;
    }
    public void verProductos(){
        System.out.println("nombre: "+nombre);
        System.out.println("categoria: "+categoria);
        System.out.println("precio: "+precio);
    }
}
