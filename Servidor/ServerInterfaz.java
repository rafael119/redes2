import java.io.*;
import java.net.*;
class ServerInterfaz
{
      public static void main(String args[])
      {
            //Genera Archivo DataInputStream
            File archivo;
            FileOutputStream salida = null;
            try {
              archivo = new File("datos.dat");
              salida = new FileOutputStream(archivo);
              String catalogo = "Coca_Cola:2,$10\nAudifonos:7,$100";
              byte[]escritura_catalogo = catalogo.getBytes();
              for (int i = 0; i < escritura_catalogo.length; i++) {
                  salida.write(escritura_catalogo[i]);
              }
              }catch(IOException e) {
			             e.printStackTrace();
            }

            //Lecctura de todos los archivos dentro de la carpeta servidor
            String Directorio = "/home/rene/Documentos/Java/Serializacion/Carrito_Compras/Servidor";
            File f = new File(Directorio);
            File [] archivos = f.listFiles();
            // Ciclo para obtener archivos solo con extension .JPG (-Falta el archivo catalogo)
            String nombre_archivo;
            int numero_archivos=0;
            int y = 0;
            for (int x=0;x<archivos.length;x++)
            {
              nombre_archivo= archivos[x].getName();
              if (nombre_archivo.endsWith(".jpg") || nombre_archivo.endsWith(".JPG")||nombre_archivo.endsWith(".dat"))
              {
                    numero_archivos +=1;
                    archivos[y] = archivos[x];
                    y +=1;
              }
            }
            ServerSocket ss = null;
            try
            {
                  ss      =      new      ServerSocket(4500);
            }
            catch      (IOException      ioe)
            {
                  System.err.println("Error al abrir el socket de servidor : " + ioe);
                  System.exit(-1);
            }
            String entrada;
            String respuesta;
            while(true)
            {
                  try
                  {
                        System.out.println("Servidor Esperando.....");
                        byte []b = new byte[1024];
                        Socket      sckt      =      ss.accept();
                        DataInputStream      dis      =  null;
                        DataOutputStream      dos      =      new      DataOutputStream(sckt.getOutputStream());
                        dos.writeInt(numero_archivos);
                        System.out.println("Numero de archivos a enviar: "+numero_archivos);
                        for (int i=0;i<numero_archivos;i++ )
                        {
                            nombre_archivo = archivos[i].getName();
                            String archivo_path = archivos[i].getAbsolutePath();
                            long tam_archivo = archivos[i].length();
                            dis = new DataInputStream(new FileInputStream(archivo_path));
                            dos.writeUTF(nombre_archivo);
                            dos.flush();
                            dos.writeLong(tam_archivo);
                            dos.flush();
                            long paq_enviados = 0;
                            int n;
                            while(paq_enviados<tam_archivo)
                            {
                              n = dis.read(b);
                              dos.write(b,0,n);
                              dos.flush();
                              paq_enviados += n;
                            }
                            dis.close();
                            System.out.println("Archivo enviado: "+nombre_archivo);
                        }
                        int      puerto      =      sckt.getPort();
                        InetAddress      direcc      =      sckt.getInetAddress();
                        dos.close();
                        System.out.println("Cliente = "+direcc+":"+puerto+"\t Cliente conectado");
                        sckt.close();

                      }
                      catch(Exception      e)
                      {
                        System.err.println("Se      ha      producido      la      excepción : " +e);
                      }
                  }
            }
}
