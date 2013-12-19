/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package socketstreamserveradvance;


import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class SocketStreamServerAdvance {
    static ServerSocket serverSocket;
            
    //addr solo para pruebas en local
    static InetSocketAddress localAddr = new InetSocketAddress("localhost",5555);
        
    //addr LAN (revisar cuando se reinicia la conexion IP)
    static InetSocketAddress localAddrPublicIP = new InetSocketAddress("192.168.1.189",5556);
    
    public static void main(String[] args) {
        String mensaje = "";
                
        try {            
            conectar();
            
            do{
                System.out.println("Esperando por un mensaje.");
                mensaje=aceptarSolicitud();
                System.out.println("Mensaje recibido: "+mensaje+'\n');
            }while(!(mensaje.trim()).equalsIgnoreCase("fin"));
            
            desconectar();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private static void conectar() throws IOException{
        //System.out.println("Creando el Socket servidor");
        serverSocket = new ServerSocket();
        
        System.out.println("Direccionando el Socket servidor a "
                +localAddrPublicIP);
        serverSocket.bind(localAddrPublicIP);
    }
    
    private static String aceptarSolicitud(){
       String strMensaje="";
        try {
            InputStream is;
            OutputStream os;            
            /*System.out.println("El Socket servidor acepta una solicitud de"
                    + " conexion y crea el Socket auxiliar");*/
            Socket auxSocket = serverSocket.accept();           
            /*System.out.println("Creando los streams de I/O para el socket"
                    + " auxiliar  para la conexion a partir del Socket"
                    + " servidor");*/
            is = auxSocket.getInputStream();
            os = auxSocket.getOutputStream();
            /*System.out.println("Creando el buffer del socket auxiliar de entrada"
                    + " en el servidor");*/
            byte[] mensaje=new byte[25];
            //System.out.println("Leyerndo el mensaje en el servidor");
            is.read(mensaje);            
            auxSocket.close();            
            strMensaje=new String(mensaje);            
        } catch (IOException ex) {
            Logger.getLogger(SocketStreamServerAdvance.class.getName()).
                    log(Level.SEVERE, null, ex);
        }        
        return strMensaje;
    }
    
    private static void desconectar() throws IOException{
        System.out.println("Cerrando el Socket servidor");
        serverSocket.close();
    }
}