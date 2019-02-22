import java.util.*;
import java.net.*;
import java.io.*;
public class Servidor {
    static int count=0;
    public static void main(String []args){
        try{
            ArrayList<Producto> ps = new ArrayList<Producto>();
            int pto = 9000;
            ServerSocket s = new ServerSocket(pto);
            System.out.println("Servicio Iniciado...");
            s.setReuseAddress(true);
            for(;;){
                switch(count){
                    case 0:
                    {
                        Socket cl = s.accept();
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Servidor/lista"));
                        ps = (ArrayList)ois.readObject();
                        ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
                        oos.writeObject(ps);
                        oos.flush();
                        oos.close();
                        cl.close();
                        count ++;
                    }
                    break;
                    case 1:
                        for(int i =0; i<ps.size(); i++){
                            try{
                                int pto2 = 8001;
                                ServerSocket s2 =  new ServerSocket(pto2);
                                Socket cl2 = s2.accept();
                                DataOutputStream dos = new DataOutputStream(cl2.getOutputStream());
                                File f = new File("Servidor/images/"+ps.get(i).nombre+".jpg");
                                System.out.println("nombre archivo: "+ps.get(i).nombre+".jpg");
                                int Enviados =0;
                                dos.writeLong(f.length());
                                dos.flush();
                                dos.flush();
                                System.out.println("tamaño: "+f.length());
                                dos.writeUTF(f.getName());
                                dos.flush();
                                dos.flush();
                                DataInputStream dis = new DataInputStream(new FileInputStream("Servidor/images/"+ps.get(i).nombre+".jpg"));
                                while(Enviados<f.length()){
                                    byte[]b = new byte[20000];
                                    int n = dis.read(b);
                                    dos.write(b, 0, n);
                                    dos.flush();
                                    Enviados = Enviados + n;
                                }
                                dis.close();
                                dos.close();
                                cl2.close();
                                s2.close();
                                System.out.println("imagen enviada");
                            }catch(Exception e){
                                e.printStackTrace();
                            }    
                        }
                        System.out.println("Archivos enviados");
                        
                        
                        count++;
                    break; 
                    case 2:
                        Socket cl = s.accept();
                        ObjectInputStream ois = new ObjectInputStream(cl.getInputStream());
                        ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
                        ArrayList<Producto> listaP = (ArrayList)ois.readObject();
                        ps = (ArrayList)ois.readObject();
                        ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(new File("Servidor/lista")));
                        salida.writeObject(ps);
                        salida.close();
                        System.out.println("Ticket recibido");
                        Ticket t = new Ticket(listaP);
                        oos.writeObject(t);
                        System.out.println("ticket enviado");
                        oos.flush();
                        oos.close();
                        ois.close();
                        cl.close();
                        count = 0;
                    break;    
                }
                /*System.out.println("Cliente conectado :"+cl.getInetAddress()+" puerto: "+cl.getPort());
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Servidor/lista"));
                ps = (ArrayList)ois.readObject();
                ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
                oos.writeObject(ps);
                oos.flush();
                DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
                //dos.writeInt(ps.size());
                for(int i =0; i<ps.size(); i++){
                    //DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
                    File f = new File("Servidor/images/"+ps.get(i).nombre+".jpg");
                    System.out.println("nombre archivo: "+ps.get(i).nombre+".jpg");
                    int Enviados =0;
                    dos.writeLong(f.length());
                    dos.flush();
                    dos.flush();
                    System.out.println("tamaño: "+f.length());
                    dos.writeUTF(f.getName());
                    dos.flush();
                    dos.flush();
                    DataInputStream dis = new DataInputStream(new FileInputStream("Servidor/images/"+ps.get(i).nombre+".jpg"));
                    while(Enviados<f.length()){
                        byte[]b = new byte[20000];
                        int n = dis.read(b);
                        dos.write(b, 0, n);
                        dos.flush();
                        Enviados = Enviados + n;
                    }
                    dis.close();
                    //dos.close();
                    System.out.println("imagen enviada");
                }
                //ArrayList<Producto> listaP = (ArrayList)ois.readObject();
                //System.out.println("Ticket enviado");
                //Ticket t = new Ticket(listaP);
                //oos.writeObject(t);
                //oos.flush();
                oos.close();
                ois.close();
                cl.close();
                dos.close();
                System.exit(0);*/
           }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
