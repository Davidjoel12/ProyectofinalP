package ProyectoFinal.ProyectofinalP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Date;




public class conexion {
    //Integrantes del grupo: Anel Pineda y David Carrasquilla
        private Connection conn = null;
        private java.sql.Statement prepararConsulta = null;
        private ResultSet ejecucion = null;
        private String consulta;
        
        
       public void Hilton() throws SQLException {
            Scanner scaner = new Scanner(System.in);

            System.out.println("Bienvenido, desea:");
            System.out.println("(1) Crear una reserva");
            System.out.println("(2) Cambiar la fecha de reservacion");
            System.out.println("(3) Eliminar la reserva");
            System.out.println("(4) Salir ");

            int eleccion = scaner.nextInt();
            try {
                conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=GestionHospedaje;encrypt=false", "josue", "1234");
                System.out.println("La conexión fue exitosa con el driver.");

                switch(eleccion) {
                    case 1 -> {
                        System.out.println("Ingrese la fecha inicial de su reserva:");
                        String fechaInicio = scaner.nextLine();
                        System.out.println("Ingrese la fecha final de su reserva:");
                        String fechaFin = scaner.nextLine();

                        try {
                            consulta = "INSERT INTO Reserva (FechaInicio, FechaFin) VALUES ('" + fechaInicio + "', '" + fechaFin + "')";
                            prepararConsulta = conn.createStatement();

                            int filasInsertadas = prepararConsulta.executeUpdate(consulta);

                            if (filasInsertadas > 0) {
                                consulta = "SELECT IDReserva FROM Reserva WHERE IDReserva = (SELECT MAX(IDReserva) FROM Reserva)";
                                ResultSet rs = prepararConsulta.executeQuery(consulta);

                                if (rs.next()) {
                                    int numeroDeReserva = rs.getInt("IDReserva");
                                    System.out.println("Reserva creada exitosamente.");
                                    System.out.println("Su numero de reserva es: " + numeroDeReserva);
                                } else {
                                    System.out.println("No se pudo obtener el número de la cuenta.");
                                }
                            } else {
                                System.out.println("Error al crear la cuenta.");
                            }
                        } catch (SQLException e) {
                            System.out.println("Ocurrió un error al realizar la reserva.");
                            e.printStackTrace();
                        }
                    }
                    case 2 -> {
                        System.out.println("Ingrese el número de reserva:");
                        int numeroReserva = scaner.nextInt();
                        
                        consulta = "SELECT * FROM Rserva Where IDReserva = "+ numeroReserva +")";

                        try {
                            java.sql.Statement stmt = conn.createStatement();
                            System.out.println("La preparación de la consulta fue exitosa.");

                            ejecucion = stmt.executeQuery(consulta);


                            System.out.printf("%-10s %-30s %-20s %-10s %-10s %-10s%n", "Reserva", "Usuario", "Habitacion", "FechaInicio", "FechaFin", "Estado");
                            System.out.println("---------------------------------------------------------------------------");
                            while (ejecucion.next()) {
                                String id = ejecucion.getString("IDReserva");
                                String usuario = ejecucion.getString("IDUsuario");
                                String habitacion = ejecucion.getString("IDHabitacion");
                                String fechaI = ejecucion.getString("FechaInicio");
                                String fechaF = ejecucion.getString("FechaFin");
                                String estado = ejecucion.getString("Estado");
                                System.out.printf("%-10s %-30s %-20s %-10s %-10s%n",id,usuario, habitacion, fechaI, fechaF, estado);
                            }

                        } catch (SQLException e) {
                            System.out.println("Ocurrió un error al realizar la reserva.");
                            e.printStackTrace();
                        }
                        
                        
                        System.out.println("Ingrese el ajuste de fechas:");
                        System.out.println("Fecha Inicio:");
                        String fechaI = scaner.nextLine();
                        System.out.println("Fecha Fin:");
                        String fechaF = scaner.nextLine();

                        if (fechaI > 0) {
                            consulta = "UPDATE Reserva SET FechaInicio and FechaFin =  + " + fechaI + " WHERE IDReserva = " + numeroReserva;
                            prepararConsulta = conn.createStatement();
                            int filasActualizadas = prepararConsulta.executeUpdate(consulta);

                            if (filasActualizadas > 0) {
                                System.out.println("Deposito realizado exitosamente.");
                            } else {
                                System.out.println("Error al realizar el deposito.");
                            }
                        } else {
                            System.out.println("El deposito debe ser mayor a cero.");
                        }
                    }
                    case 3 -> {
                        System.out.println("Ingrese el numero de reserva que desea eliminar:");
                        int numeroReserva = scaner.nextInt();

                        System.out.println("Estas seguro de eliminar esta cuenta? (si/no)");
                        String confirmacion = scaner.next();

                        if (confirmacion.equalsIgnoreCase("si")) {
                            consulta = "DELETE FROM Rserva WHERE IDReserva = " + numeroReserva;
                            prepararConsulta = conn.createStatement();
                            int filasEliminadas = prepararConsulta.executeUpdate(consulta);

                            if (filasEliminadas > 0) {
                                System.out.println("Cuenta eliminada exitosamente.");
                            } else {
                                System.out.println("No se encontro la cuenta o no se pudo eliminar.");
                            }
                        } else {
                            System.out.println("Transacción no realizada.");
                        }
                    }
                    case 4 -> System.exit(0);
                    default -> System.out.println("No ingreso una opción válida.");
                }
            } catch (SQLException e) {
                System.out.println("Error en la conexion o consulta.");
                e.printStackTrace();
            } finally {
              try {
                    if (ejecucion != null) ejecucion.close();
                    if (prepararConsulta != null) prepararConsulta.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }



        


        
        public void Marbella() throws SQLException {
            Scanner scaner = new Scanner(System.in);

            System.out.println("Bienvenido, desea:");
            System.out.println("(1) Crear una reserva");
            System.out.println("(2) Cambiar la fecha de reservacion");
            System.out.println("(3) Eliminar la reserva");
            System.out.println("(4) Salir ");

            int eleccion = scaner.nextInt();
            try {
                conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=GestionHospedaje;encrypt=false", "josue", "1234");
                System.out.println("La conexión fue exitosa con el driver.");

                switch(eleccion) {
                    case 1 -> {
                        System.out.println("Ingrese la fecha inicial de su reserva:");
                        String fechaInicio = scaner.nextLine();
                        System.out.println("Ingrese la fecha final de su reserva:");
                        String fechaFin = scaner.nextLine();

                        if (fechaInicio >= 0) {
                            consulta = "INSERT INTO Reserva (FechaInicio, FechaFin) VALUES (" + fechaInicio + fechaFin +")";
                            prepararConsulta = conn.createStatement();

                            int filasInsertadas = prepararConsulta.executeUpdate(consulta);

                            if (filasInsertadas > 0) {
                                consulta = "SELECT IDReserva FROM Reserva WHERE IDReserva = (SELECT MAX(IDReserva) FROM Reserva)";
                                ResultSet rs = prepararConsulta.executeQuery(consulta);

                                if (rs.next()) {
                                    int numeroDeReserva = rs.getInt("IDReserva");
                                    System.out.println("Reserva creada exitosamente.");
                                    System.out.println("Su numero de reserva es: " + numeroDeReserva);
                                } else {
                                    System.out.println("No se pudo obtener el número de la cuenta.");
                                }
                            } else {
                                System.out.println("Error al crear la cuenta.");
                            }
                        } else {
                            System.out.println("El saldo inicial no puede ser negativo.");
                        }
                    }
                    case 2 -> {
                        System.out.println("Ingrese el número de reserva:");
                        int numeroReserva = scaner.nextInt();
                        
                        consulta = "SELECT * FROM Rserva Where IDReserva = "+ numeroReserva +")";

                        try {
                            java.sql.Statement stmt = conn.createStatement();
                            System.out.println("La preparación de la consulta fue exitosa.");

                            ejecucion = stmt.executeQuery(consulta);


                            System.out.printf("%-10s %-30s %-20s %-10s %-10s %-10s%n", "Reserva", "Usuario", "Habitacion", "FechaInicio", "FechaFin", "Estado");
                            System.out.println("---------------------------------------------------------------------------");
                            while (ejecucion.next()) {
                                String id = ejecucion.getString("IDReserva");
                                String usuario = ejecucion.getString("IDUsuario");
                                String habitacion = ejecucion.getString("IDHabitacion");
                                String fechaI = ejecucion.getString("FechaInicio");
                                String fechaF = ejecucion.getString("FechaFin");
                                String estado = ejecucion.getString("Estado");
                                System.out.printf("%-10s %-30s %-20s %-10s %-10s%n",id,usuario, habitacion, fechaI, fechaF, estado);

                            }
                        
                        System.out.println("Ingrese el ajuste de fechas:");
                        System.out.println("Fecha Inicio:");
                        String fechaI = scaner.nextLine();
                        System.out.println("Fecha Fin:");
                        String fechaF = scaner.nextLine();

                        if (fechaI > 0) {
                            consulta = "UPDATE Reserva SET FechaInicio and FechaFin =  + " + fechaI + " WHERE IDReserva = " + numeroReserva;
                            prepararConsulta = conn.createStatement();
                            int filasActualizadas = prepararConsulta.executeUpdate(consulta);

                            if (filasActualizadas > 0) {
                                System.out.println("Deposito realizado exitosamente.");
                            } else {
                                System.out.println("Error al realizar el deposito.");
                            }
                        } else {
                            System.out.println("El deposito debe ser mayor a cero.");
                        }
                    }
                    case 3 -> {
                        System.out.println("Ingrese el numero de reserva que desea eliminar:");
                        int numeroReserva = scaner.nextInt();

                        System.out.println("Estas seguro de eliminar esta cuenta? (si/no)");
                        String confirmacion = scaner.next();

                        if (confirmacion.equalsIgnoreCase("si")) {
                            consulta = "DELETE FROM IDRserva WHERE IDReserva = " + numeroReserva;
                            prepararConsulta = conn.createStatement();
                            int filasEliminadas = prepararConsulta.executeUpdate(consulta);

                            if (filasEliminadas > 0) {
                                System.out.println("Cuenta eliminada exitosamente.");
                            } else {
                                System.out.println("No se encontro la cuenta o no se pudo eliminar.");
                            }
                        } else {
                            System.out.println("Transacción no realizada.");
                        }
                    }
                    case 4 -> System.exit(0);
                    default -> System.out.println("No ingreso una opción válida.");
                }
            } catch (SQLException e) {
                System.out.println("Error en la conexion o consulta.");
                e.printStackTrace();
            } finally {
              try {
                    if (ejecucion != null) ejecucion.close();
                    if (prepararConsulta != null) prepararConsulta.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        
        
        
        
        
        
        public void Marriot() {
            Scanner scaner = new Scanner(System.in);

            System.out.println("Bienvenido, desea:");
            System.out.println("(1) Crear una reserva");
            System.out.println("(2) Cambiar la fecha de reservacion");
            System.out.println("(3) Eliminar la reserva");
            System.out.println("(4) Salir ");

            int eleccion = scaner.nextInt();
            try {
                conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=GestionHospedaje;encrypt=false", "josue", "1234");
                System.out.println("La conexión fue exitosa con el driver.");

                switch(eleccion) {
                    case 1 -> {
                        System.out.println("Ingrese la fecha inicial de su reserva:");
                        String fechaInicio = scaner.nextLine();
                        System.out.println("Ingrese la fecha final de su reserva:");
                        String fechaFin = scaner.nextLine();

                        if (fechaInicio >= 0) {
                            consulta = "INSERT INTO Reserva (FechaInicio, FechaFin) VALUES (" + fechaInicio + fechaFin +")";
                            prepararConsulta = conn.createStatement();

                            int filasInsertadas = prepararConsulta.executeUpdate(consulta);

                            if (filasInsertadas > 0) {
                                consulta = "SELECT IDReserva FROM Reserva WHERE IDReserva = (SELECT MAX(IDReserva) FROM Reserva)";
                                ResultSet rs = prepararConsulta.executeQuery(consulta);

                                if (rs.next()) {
                                    int numeroDeReserva = rs.getInt("IDReserva");
                                    System.out.println("Reserva creada exitosamente.");
                                    System.out.println("Su numero de reserva es: " + numeroDeReserva);
                                } else {
                                    System.out.println("No se pudo obtener el número de la cuenta.");
                                }
                            } else {
                                System.out.println("Error al crear la cuenta.");
                            }
                        } else {
                            System.out.println("El saldo inicial no puede ser negativo.");
                        }
                    }
                    case 2 -> {
                        System.out.println("Ingrese el número de reserva:");
                        int numeroReserva = scaner.nextInt();
                        
                        consulta = "SELECT * FROM Rserva Where IDReserva = "+ numeroReserva +")";

                        try {
                            java.sql.Statement stmt = conn.createStatement();
                            System.out.println("La preparación de la consulta fue exitosa.");

                            ejecucion = stmt.executeQuery(consulta);


                            System.out.printf("%-10s %-30s %-20s %-10s %-10s %-10s%n", "Reserva", "Usuario", "Habitacion", "FechaInicio", "FechaFin", "Estado");
                            System.out.println("---------------------------------------------------------------------------");
                            while (ejecucion.next()) {
                                String id = ejecucion.getString("IDReserva");
                                String usuario = ejecucion.getString("IDUsuario");
                                String habitacion = ejecucion.getString("IDHabitacion");
                                String fechaI = ejecucion.getString("FechaInicio");
                                String fechaF = ejecucion.getString("FechaFin");
                                String estado = ejecucion.getString("Estado");
                                System.out.printf("%-10s %-30s %-20s %-10s %-10s%n",id,usuario, habitacion, fechaI, fechaF, estado);

                            }
                        
                        System.out.println("Ingrese el ajuste de fechas:");
                        System.out.println("Fecha Inicio:");
                        String fechaI = scaner.nextLine();
                        System.out.println("Fecha Fin:");
                        String fechaF = scaner.nextLine();

                        if (fechaI > 0) {
                            consulta = "UPDATE Reserva SET FechaInicio and FechaFin =  + " + fechaI + " WHERE IDReserva = " + numeroReserva;
                            prepararConsulta = conn.createStatement();
                            int filasActualizadas = prepararConsulta.executeUpdate(consulta);

                            if (filasActualizadas > 0) {
                                System.out.println("Deposito realizado exitosamente.");
                            } else {
                                System.out.println("Error al realizar el deposito.");
                            }
                        } else {
                            System.out.println("El deposito debe ser mayor a cero.");
                        }
                    }
                    case 3 -> {
                        System.out.println("Ingrese el numero de reserva que desea eliminar:");
                        int numeroReserva = scaner.nextInt();

                        System.out.println("Estas seguro de eliminar esta cuenta? (si/no)");
                        String confirmacion = scaner.next();

                        if (confirmacion.equalsIgnoreCase("si")) {
                            consulta = "DELETE FROM IDRserva WHERE IDReserva = " + numeroReserva;
                            prepararConsulta = conn.createStatement();
                            int filasEliminadas = prepararConsulta.executeUpdate(consulta);

                            if (filasEliminadas > 0) {
                                System.out.println("Cuenta eliminada exitosamente.");
                            } else {
                                System.out.println("No se encontro la cuenta o no se pudo eliminar.");
                            }
                        } else {
                            System.out.println("Transacción no realizada.");
                        }
                    }
                    case 4 -> System.exit(0);
                    default -> System.out.println("No ingreso una opción válida.");
                }
            } catch (SQLException e) {
                System.out.println("Error en la conexion o consulta.");
                e.printStackTrace();
            } finally {
              try {
                    if (ejecucion != null) ejecucion.close();
                    if (prepararConsulta != null) prepararConsulta.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        
    }

}

