package ProyectoFinal.ProyectofinalP;

import java.util.Scanner;


public class menu {
    //Integrantes del grupo: Andrey Mckenzie y David Carrasquilla
     private conexion conexio = new conexion();
    
    public void menu(){
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Bienvenido a ITSE Booking");
        System.out.println("(1) Hotel Hilton");
        System.out.println("(2) Hotel Marbella");
        System.out.println("(3) Hotel Marriot");
        System.out.println("(4) Salir ");
        
        
        int eleccion = scan.nextInt();
        switch(eleccion){
            case 1 -> hilton();
            case 2 -> marbella();
            case 3 -> marriot();
            case 4 -> System.exit(0);
            default -> System.out.println("No ingreso una opcion valida");
        }
    };

    private void hilton() {
        Scanner scan = new Scanner(System.in);
        conexio.Hilton();
        
        System.out.println("------------------------------");
        System.out.println("(1) Desea retroceder al menu ");
        System.out.println("(2) Desea cerrar el programa ");
        System.out.println("------------------------------");
        
        int eleccion = scan.nextInt();
        switch(eleccion){
            case 1 -> menu();
            case 2 -> System.exit(0) ;
            default -> System.out.println("No ingreso una opcion valida");
        }
    }

    private void marbella() {
        Scanner scan = new Scanner(System.in);
        conexio.Marbella();
        
        System.out.println("------------------------------");
        System.out.println("(1) Desea retroceder al menu ");
        System.out.println("(2) Desea cerrar el programa ");
        System.out.println("------------------------------");
        
        int eleccion = scan.nextInt();
        switch(eleccion){
            case 1 -> menu();
            case 2 -> System.exit(0);
            default -> System.out.println("No ingreso una opcion valida");
        }
    }
    
    private void marriot() {
        Scanner scan = new Scanner(System.in);
        conexio.Marriot();
        
        System.out.println("------------------------------");
        System.out.println("(1) Desea retroceder al menu ");
        System.out.println("(2) Desea cerrar el programa ");
        System.out.println("------------------------------");
        
        int eleccion = scan.nextInt();
        switch(eleccion){
            case 1 -> menu();
            case 2 -> System.exit(0);
            default -> System.out.println("No ingreso una opcion valida");
        }
    }
}
