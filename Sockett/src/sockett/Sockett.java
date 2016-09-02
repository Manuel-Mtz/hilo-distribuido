/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockett;
import java.io.*;
import java.net.*;
import java.util.Scanner;
/**
 *
 * @author Manuel
 */
public class Sockett {

    public static String respuesta;
    //public static String respuestaFuncion="";
    public static String[] respuestaFuncion= new String[50];
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {//throws SocketException, IOException {
        // TODO code application logic here
        
      //String servidor = "127.0.0.1";
      //int puerto = 80;
      String servidor="";
      String respuesta="";
      int contador=0;
      String puert="";
      String ayuda= "";
      int rangoA=0;
      int rangoB=0;
      int rango=0;
      int rangoFinal=0;
      boolean ayudaServidor=false;
      boolean bandera=false;
      InetAddress[] probandoIP=new InetAddress[50];
      while(bandera == false){
      try{
        System.out.println("Introduce la dirección IP");
        Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
        servidor = entradaEscaner.nextLine ();
        
        System.out.println("Introduce el primer numero de puerto a escanear");
        entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
        String entradaTeclado = entradaEscaner.nextLine ();
        // System.out.println(entradaTeclado); 
        rangoA= Integer.parseInt(entradaTeclado); 
        
        System.out.println("Introduce el ultimo numero de puerto a escanear");
        entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
        String entrada = entradaEscaner.nextLine ();
        //System.out.println(entrada);
        rangoB= Integer.parseInt(entrada); 
        if(rangoA> rangoB){
            int aux= rangoA;
            rangoA=rangoB;
            rangoB=aux;
        }
        rango = rangoB - (rangoA+1);
        rangoFinal=rangoB;
        System.out.println("Rango: "+rango);
        bandera=true;
      }catch (NumberFormatException a){
            System.out.println("No es un valor numerico");
      }
      }//Fin de la obtención de datos por parte del usuario
      
      //if(rango>15){
      try{ //pobablemente solo sirve con un servidor
      DatagramSocket clientSocket = new DatagramSocket();
      InetAddress IPAddress = InetAddress.getByName("255.255.255.255");
      byte[] data = new byte[1024];
      byte[] receiveData = new byte[1024];
      //data = "Descubriendo servicios...".getBytes();
      data= "necesito ayuda para procesar".getBytes();
      DatagramPacket packet = new DatagramPacket(data, data.length, IPAddress, 1234);/////////////////////////////////
      clientSocket.send(packet);
      System.out.println("Datos enviados...");
      clientSocket.setSoTimeout(10000);
      //Se necesita capturar varios packetes de ayuda
      ayudaServidor=true;
      while(ayudaServidor){
      
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      clientSocket.receive(receivePacket);
      contador=contador+1;
      probandoIP[contador] = receivePacket.getAddress();
      System.out.println(probandoIP[contador]);
      String modifiedSentence = new String(receivePacket.getData()); 
      //System.out.println(modifiedSentence);
     
      System.out.println("Contador:"+contador);
      //guardar la IP de varios equipos
      }
      clientSocket.close();
      
        }catch(Exception e){
        //    System.out.println("no encontro mas ayuda");
           
            //ayudaServidor=false;
            //se puede agregar bandera para detener el ciclo de busqueda de ayuda
        }
      
     // }
      if(contador > 0){
          
          int auxiliar = 0;
          System.out.println("se divide el trabajo");
          int rangoAyuda=(rango/(contador+1));
         // int rangoFinal= rangoB;
          System.out.println("Rango a dividir el trabajo: "+ rangoAyuda);
           rangoB=rangoA + rangoAyuda;
           int nuevoRangoA= rangoB+1 ;
           int nuevoRangoB= nuevoRangoA+rangoAyuda;
          while(auxiliar < contador){
           auxiliar=auxiliar+1;
           if(auxiliar== contador) nuevoRangoB=rangoFinal;
            ayuda= ayudaS(probandoIP[auxiliar],nuevoRangoA,nuevoRangoB,servidor,auxiliar);
            nuevoRangoA=nuevoRangoB+1;
            nuevoRangoB= nuevoRangoB+rangoAyuda;
           
            //contador=contador-1;
          }
         
       /*    int rangoFinal= rangoB;
          rangoB=rangoB - (rango/2);
          ayuda= ayudaS(probandoIP,rangoB+1,rangoFinal,servidor,contador);
          System.out.println("RangoA:" +rangoA+ "rangoB:" +rangoB+"rangoFinal:"+rangoFinal);*/
        //  int nuevoRangoA= rangoB+1 ;
          //llamar a funcion de socket para que realice la busqueda de puertos desde rangoB+1 hasta nuevoRango
         
          
          
        
      }
      
      System.out.println("Empiezo a buscar:");
      // Busqueda de los puertos
      while(rangoA<= rangoB){
        try{
        //poner el ciclo dependiendo de los rangos
        
        Socket socket= new Socket (servidor,rangoA);
        BufferedReader in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
	//if (0==0)
	//	return;
	String c = "Saludos desde el cliente";
        out.println(c);
        respuesta=respuesta+"Esta disponible el puerto: "+ rangoA+"\n";
	//String line = "Esta diponible el puerto:"+rangoA;
	
       // System.out.println(line);
	socket.close();
        rangoA=rangoA+1;
        }catch (IOException e)
      {
       		//System.out.println("No esta disponible el puerto:"+rangoA);
                respuesta=respuesta+"No esta disponible el puerto: "+ rangoA+"\n";
                rangoA=rangoA+1;
      }
      }//fin del ciclo 
        System.out.println(respuesta);
        //System.out.println(ayuda);
       try{ Thread.sleep(1400);}
       catch(Exception e){}
        //ciclo para imprimir los resultados
       for (int i=1; i<=contador; i++){
        System.out.println(respuestaFuncion[i]);
       }
        /*
        try{
      DatagramSocket clientSocket = new DatagramSocket();
      InetAddress IPAddress = InetAddress.getByName("255.255.255.255");
      byte[] data = new byte[1024];
      data = "Descubriendo servicios...".getBytes();
      DatagramPacket packet = new DatagramPacket(data, data.length, IPAddress, 1234);
      clientSocket.send(packet);
      System.out.println("Datos enviados...");
      clientSocket.close();
        }catch(Exception e){
        }
        */
    }

    public static String ayudaS(InetAddress dir, int rangoA, int rangoB, String direccion, int contador){
       // String  respuesta="";
        Runnable miRunnable = new Runnable()
        {
           public void run()
           {
              try
              {
                   Socket socket= new Socket (dir,5000);
        //BufferedReader in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
	//if (0==0)
	//	return;
	//String c = dir.toString();
        out.println(direccion);
       
        out.println(Integer.toString(rangoA));
        out.println(Integer.toString(rangoB));
            //  Thread.sleep(5000); //Tarea que consume diez segundos.
        //respuesta=in.readLine();
        DataInputStream in =
            new DataInputStream(socket.getInputStream());
        String hola= in.readUTF();
        // System.out.println("respuesta que se regresa:"+hola);
         respuestaFuncion[contador] = hola;// se modifico aqui
       // System.out.println("Esperada:"+respuestaFuncion);
       // System.out.println(respuesta);
        //respuesta=respuesta+"Esta disponible el puerto: "+ rangoA+"\n";
	//String line = "Esta diponible el puerto:"+rangoA;
	
        //System.out.println("llego");
        
	socket.close();
                  
            // return hola;     
           
                
              }
              catch (Exception e)
              {
                 e.printStackTrace();
              }
           }
        };
        Thread hilo = new Thread (miRunnable);
        hilo.start();
        String salida="";
        /*if (respuestaFuncion!=null)
        return respuestaFuncion;
        else return respuesta;*/
        return salida;
     }
       /*
        try{
        //poner el ciclo dependiendo de los rangos
        
        Socket socket= new Socket (dir,5000);
        //BufferedReader in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
	//if (0==0)
	//	return;
	String c = dir.toString();
        out.println(c);
       
        out.println(Integer.toString(rangoA));
        out.println(Integer.toString(rangoB));
        //respuesta=in.readLine();
        DataInputStream in =
            new DataInputStream(socket.getInputStream());
        respuesta = in.readUTF();
       // System.out.println(respuesta);
        //respuesta=respuesta+"Esta disponible el puerto: "+ rangoA+"\n";
	//String line = "Esta diponible el puerto:"+rangoA;
	
       // System.out.println(line);
	socket.close();
        
        }catch (IOException e)
      {
          System.out.println("problema");
      }
         
         
        return respuesta;
    }*/
    
}
