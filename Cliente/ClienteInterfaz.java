//SetBounds(derecha,arriba,ancho,alto);

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;


public class ClienteInterfaz extends JFrame {

  //Seccion del menu de inicio
  JFrame Menu_inicio_vent;
  JButton Btn_conectar_MI,Btn_Salir_MI;
  JLabel Nombre_tienda;
  //Seccion del catálogo
  JFrame Mostrar_catalogo_vent;
  JButton Btn_Realizar_Compra,Btn_Mostrar_Carro;
  JLabel Nombre_tienda_catalogo;
  JTabbedPane Productos;
  JPanel Electronicos_Pan,Comestibles_Pan,Ropa_Pan,Muebles_Pan;
  //Seccion del Estatus_Compra
  JFrame Estatus_Compra_vent;
  JButton Btn_Finalizar_Compra,Btn_Regresar_Catalogo;
  JTextArea Detalles_Compra;
  //Seleccion de Btn_Mostrar_Carro
  JFrame Mostrar_Carro_ventana;
  JButton Btn_Eliminar_Compra, Btn_Agregar_Compra;

  public ClienteInterfaz()
  {
    super();
    Iniciar_Menu();

  }

  public void Iniciar_Menu() //Ventana que establece la comunicación con el server
  {
      Menu_inicio_vent = new JFrame("Menú Inicio");
      Menu_inicio_vent.setSize(300,300);
      Menu_inicio_vent.setLocationRelativeTo(null);
      Menu_inicio_vent.setLayout(null);
      Menu_inicio_vent.setResizable(false);
      Menu_inicio_vent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Menu_inicio_vent.setVisible(true);
      Btn_conectar_MI = new JButton("Conectar");
      Btn_conectar_MI.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
            Iniciar_Catalogo();
            InetAddress direcc = null;
            Socket sckt = null;
            try  {  direcc = InetAddress.getByName("127.0.0.1");  }
            catch(UnknownHostException uhe)
            {
                  System.err.println("Host no encontrado : " + uhe);
                  System.exit(-1);
            }
            int puerto = 4500;
            try
            {
              sckt  =  new Socket(direcc,puerto);
              DataInputStream  dis  = new DataInputStream(sckt.getInputStream());
              int numero_archivos = dis.readInt();
              String nombre_archivo;
              int n;
              long leidos,tam_archivo;
              byte [] b = new byte[1024];
              System.out.println("El número de archivos recibidos son: "+numero_archivos);
              for (int i=0;i<numero_archivos;i++)
              {
                nombre_archivo = dis.readUTF();
                tam_archivo = dis.readLong();
                System.out.println("El nombre del archivo es: "+nombre_archivo);
                leidos = 0;
                File archivo_recibido = new File(nombre_archivo);
                DataOutputStream  dos  = new DataOutputStream(new FileOutputStream(archivo_recibido));
                while(leidos<tam_archivo)
                {
                  if ((leidos+b.length)>tam_archivo)
                    n = dis.read(b,0,((int)(tam_archivo-leidos)));
                  else
                    n = dis.read(b);
                  dos.write(b,0,n);
                  dos.flush();
                  leidos += n;
                }
                dos.close();
              }
              dis.close();
              System.out.println("Archivos recibidos");
            } catch(Exception      er)    {  System.err.println("Se ha producido la excepción : " +er); }
            try
            {
                        if (sckt!=null)
                          sckt.close();
            }
            catch(IOException      ioe) {  System.err.println("Error al cerrar el socket :" + ioe); }
        }
      });
      Btn_conectar_MI.setBounds(100,150,100,40);
      Nombre_tienda = new JLabel("Nombre de la tienda");
      Nombre_tienda.setBounds(70,70,250,40);
      Nombre_tienda.setFont(new Font("Arial", 0, 18));
      Menu_inicio_vent.add(Btn_conectar_MI);
      Menu_inicio_vent.add(Nombre_tienda);
  }

  public void Iniciar_Catalogo() //Ventana que muestra el catalogo de compras
  {
      Mostrar_catalogo_vent = new JFrame("Catálogo de productos");
      Mostrar_catalogo_vent.setSize(850,600);
      Mostrar_catalogo_vent.setLocationRelativeTo(null);
      Mostrar_catalogo_vent.setLayout(null);
      Mostrar_catalogo_vent.setResizable(false);
      Mostrar_catalogo_vent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Mostrar_catalogo_vent.setVisible(true);
      Menu_inicio_vent.setVisible(false);
      Nombre_tienda = new JLabel("Nombre de la tienda");
      Nombre_tienda.setBounds(20,20,250,40);
      Btn_Realizar_Compra = new JButton("Realizar Compra");
      Btn_Realizar_Compra.setBounds(660,500,160,40);
      Btn_Realizar_Compra.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
            Realizar_Compra();
        }
      });
      Mostrar_catalogo_vent.add(Btn_Realizar_Compra);
      Btn_Mostrar_Carro = new JButton("Mostrar carro");
      Btn_Mostrar_Carro.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
            Mostrar_Carro();
        }
      });
      Btn_Mostrar_Carro.setBounds(50,500,120,40);
      Mostrar_catalogo_vent.add(Btn_Mostrar_Carro);
      String cadena;
      try{
        File archivo_a_leer = new File("datos.dat");
        FileReader f = null;new FileReader(archivo_a_leer);
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) {
             System.out.println(cadena);
        }
        b.close();
      }catch (IOException e){
        e.printStackTrace();
      }
  }

  public void Mostrar_Carro()
  {
    Mostrar_catalogo_vent.setVisible(false);
    Mostrar_Carro_ventana = new JFrame("Carro de compras");
    Mostrar_Carro_ventana.setSize(700,600);
    Mostrar_Carro_ventana.setLocationRelativeTo(null);
    Mostrar_Carro_ventana.setLayout(null);
    Mostrar_Carro_ventana.setResizable(false);
    Mostrar_Carro_ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Mostrar_Carro_ventana.setVisible(true);
    Btn_Agregar_Compra = new JButton("Agregar producto");
    Btn_Agregar_Compra.setBounds(500,520,150,40);
    Btn_Agregar_Compra.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
          Mostrar_catalogo_vent.setVisible(true);
          Mostrar_Carro_ventana.setVisible(false);
      }
    });
    Btn_Eliminar_Compra = new JButton("Eliminar producto");
    Btn_Eliminar_Compra.setBounds(30,520,150,40);
    Mostrar_Carro_ventana.add(Btn_Agregar_Compra);
    Mostrar_Carro_ventana.add(Btn_Eliminar_Compra);
  }

  public void Realizar_Compra()
  {
    Mostrar_catalogo_vent.setVisible(false);
    Estatus_Compra_vent = new JFrame("Estatus de compra");
    Estatus_Compra_vent.setSize(500,650);
    Estatus_Compra_vent.setLocationRelativeTo(null);
    Estatus_Compra_vent.setLayout(null);
    Estatus_Compra_vent.setResizable(false);
    Estatus_Compra_vent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Estatus_Compra_vent.setVisible(true);
    Btn_Finalizar_Compra = new JButton("Finalizar Compra");
    Btn_Finalizar_Compra.setBounds(300,570,150,40);
    Btn_Finalizar_Compra.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
          JOptionPane.showMessageDialog(null,"Su compra se realizo correctamente");
      }
    });
    Btn_Regresar_Catalogo = new JButton("Regresar al catalogo");
    Btn_Regresar_Catalogo.setBounds(30,570,170,40);
    Btn_Regresar_Catalogo.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
          Mostrar_catalogo_vent.setVisible(true);
          Estatus_Compra_vent.setVisible(false);
      }
    });
    Detalles_Compra = new JTextArea();
    Detalles_Compra.setBounds(20,30,460,520);
    Detalles_Compra.insert("Detalles de la compra: ",0);
    Estatus_Compra_vent.add(Detalles_Compra);
    Estatus_Compra_vent.add(Btn_Regresar_Catalogo);
    Estatus_Compra_vent.add(Btn_Finalizar_Compra);
  }


  public static void main(String[] args) {
    ClienteInterfaz Cliente_Carrito = new ClienteInterfaz();
    Cliente_Carrito.setVisible(true);
  }
}
