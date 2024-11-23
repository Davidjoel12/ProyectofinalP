package ProyectoFinal.ProyectofinalP;

import java.sql.Statement;
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
        
        
       public void Hilton() {
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
                        System.out.println("Ingrese su nombre");
                        String nombreCliente = scaner.nextLine();
                        System.out.println("--------------------");
                              
                        System.out.println("Ingrese su correo");
                        String correoCliente = scaner.nextLine();
                        
                        System.out.println("Ingrese su telefono");
                        String telefonoCliente = scaner.nextLine();

                        // Insertar datos en la tabla Cliente
                        String consultaCliente = "INSERT INTO Cliente (Nombre, Correo, Telefono) VALUES ('" +
                                                  nombreCliente + "', '" + correoCliente + "', '" + telefonoCliente + "')";
                        Statement stmtCliente =  conn.createStatement();
                        int filasCliente = stmtCliente.executeUpdate(consultaCliente);

                        if (filasCliente > 0) {
                            System.out.println("Cliente registrado correctamente.");
                        } else {
                            System.out.println("Error al registrar al cliente.");
                            return;
                        }

                        // Obtener el IDCliente recién registrado
                        String consultaIDCliente = "SELECT MAX(IDCliente) AS IDCliente FROM Cliente";
                        ResultSet rsCliente = stmtCliente.executeQuery(consultaIDCliente);
                        int idCliente = 0;
                        if (rsCliente.next()) {
                            idCliente = rsCliente.getInt("IDCliente");
                        }

                        // Paso 2: Solicitar tipo de habitación
                        System.out.println("Seleccione el tipo de habitación:");
                        System.out.println("1. Cama Matrimonial");
                        System.out.println("2. Dos Camas");
                        System.out.println("3. Una Cama Matrimonial y una Pequeña Cama");
                        int opcionHabitacion = scaner.nextInt();
                        scaner.nextLine(); // Limpiar el buffer

                        int idHabitacion;
                        switch (opcionHabitacion) {
                            case 1 -> idHabitacion = 1; // ID de la habitación con Cama Matrimonial
                            case 2 -> idHabitacion = 2; // ID de la habitación con Dos Camas
                            case 3 -> idHabitacion = 3; // ID de la habitación con Matrimonial + Pequeña
                            default -> {
                                System.out.println("Opción inválida. Intente de nuevo.");
                                return;
                            }
                        }

                        // Paso 3: Solicitar fechas de la reserva
                        System.out.println("Ingrese la fecha inicial de su reserva (formato: yyyy-MM-dd HH:mm:ss):");
                        String fechaInicio = scaner.nextLine();
                        System.out.println("Ingrese la fecha final de su reserva (formato: yyyy-MM-dd HH:mm:ss):");
                        String fechaFin = scaner.nextLine();

                        // Insertar datos en la tabla Reserva
                        String consultaReserva = "INSERT INTO Reserva (IDUsuario, IDHabitacion, FechaInicio, FechaFin, Estado) " +
                                                 "VALUES (" + idCliente + ", " + idHabitacion + ", '" + fechaInicio + "', '" + fechaFin + "', 'Activa')";
                        Statement stmtReserva = conn.createStatement();
                        int filasReserva = stmtReserva.executeUpdate(consultaReserva);

                        if (filasReserva > 0) {
                            // Obtener el IDReserva recién creado
                            String consultaIDReserva = "SELECT MAX(IDReserva) AS IDReserva FROM Reserva";
                            ResultSet rsReserva = stmtReserva.executeQuery(consultaIDReserva);

                            if (rsReserva.next()) {
                                int numeroDeReserva = rsReserva.getInt("IDReserva");
                                System.out.println("Reserva creada exitosamente.");
                                System.out.println("Su número de reserva es: " + numeroDeReserva);
                            } else {
                                System.out.println("No se pudo obtener el número de la reserva.");
                            }
                        } else {
                            System.out.println("Error al crear la reserva.");
                        }
                    }
                    case 2 -> {
                        System.out.println("Ingrese el número de reserva:");
                        int numeroReserva = scaner.nextInt();
                        scaner.nextLine();
                        
                        consulta = "SELECT Cliente.Nombre, Habitacion.TipoHabitacion, FechaInicio, FechaFin, Estado" +
                                    "FROM ((Reserva" +
                                    "INNER JOIN Cliente ON Reserva.IDReserva = Cliente.IDCliente)"+
                                    "INNER JOIN Habitacion ON Reserva.IDReserva = Habitacion.IDHabitacion)"+
                                    "Where IDReserva ="+ numeroReserva +";";

                        try {
                            java.sql.Statement stmt = conn.createStatement();
                            System.out.println("La preparación de la consulta fue exitosa.");

                            ejecucion = stmt.executeQuery(consulta);


                            System.out.printf("%-10s %-30s %-20s %-10s %-10s%n", "Nombre", "TipoHabitacion", "FechaInicio", "FechaFin", "Estado");
                            System.out.println("---------------------------------------------------------------------------");
                            while (ejecucion.next()) {
                                String usuario = ejecucion.getString("Nombre");
                                String habitacion = ejecucion.getString("TipoHabitacion");
                                String fechaI = ejecucion.getString("FechaInicio");
                                String fechaF = ejecucion.getString("FechaFin");
                                String estado = ejecucion.getString("Estado");
                                System.out.printf("%-10s %-30s %-20s %-10s %-10s%n",usuario, habitacion, fechaI, fechaF, estado);
                            }

                            System.out.println("Ingrese el ajuste de fechas:");
                            System.out.print("Fecha Inicio (YYYY-MM-DD): ");
                            String nuevaFechaInicio = scaner.nextLine();
                            System.out.print("Fecha Fin (YYYY-MM-DD): ");
                            String nuevaFechaFin = scaner.nextLine();

                            String actualizarConsulta = "UPDATE Reserva SET FechaInicio = '" + nuevaFechaInicio + "', FechaFin = '" + nuevaFechaFin + "' WHERE IDReserva = " + numeroReserva;

                            int filasActualizadas = stmt.executeUpdate(actualizarConsulta);

                            if (filasActualizadas > 0) {
                                System.out.println("Las fechas se han actualizado exitosamente.");
                            } else {
                                System.out.println("Error: no se encontró una reserva con ese número.");
                            }
                        } catch (SQLException e) {
                            System.out.println("Ocurrió un error al realizar la reserva.");
                            e.printStackTrace();
                        }
                    }
                    case 3 -> {
                        System.out.println("Ingrese el numero de reserva que desea eliminar:");
                        int numeroReserva = scaner.nextInt();

                        System.out.println("Estas seguro de eliminar esta cuenta? (si/no)");
                        String confirmacion = scaner.next();

                        if (confirmacion.equalsIgnoreCase("si")) {
                            consulta = "DELETE FROM Reserva WHERE IDReserva = " + numeroReserva;
                            prepararConsulta = conn.createStatement();
                            int filasEliminadas = prepararConsulta.executeUpdate(consulta);

                            if (filasEliminadas > 0) {
                                System.out.println("Reserva eliminada exitosamente.");
                            } else {
                                System.out.println("No se encontro la reserva o no se pudo eliminar.");
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



       
        public void Marbella()  {
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
                        System.out.println("Ingrese su nombre");
                        String nombreCliente = scaner.nextLine();
                        System.out.println("--------------------");
                              
                        System.out.println("Ingrese su correo");
                        String correoCliente = scaner.nextLine();
                        
                        System.out.println("Ingrese su telefono");
                        String telefonoCliente = scaner.nextLine();

                        // Insertar datos en la tabla Cliente
                        String consultaCliente = "INSERT INTO Cliente (Nombre, Correo, Telefono) VALUES ('" +
                                                  nombreCliente + "', '" + correoCliente + "', '" + telefonoCliente + "')";
                        Statement stmtCliente =  conn.createStatement();
                        int filasCliente = stmtCliente.executeUpdate(consultaCliente);

                        if (filasCliente > 0) {
                            System.out.println("Cliente registrado correctamente.");
                        } else {
                            System.out.println("Error al registrar al cliente.");
                            return;
                        }

                        // Obtener el IDCliente recién registrado
                        String consultaIDCliente = "SELECT MAX(IDCliente) AS IDCliente FROM Cliente";
                        ResultSet rsCliente = stmtCliente.executeQuery(consultaIDCliente);
                        int idCliente = 0;
                        if (rsCliente.next()) {
                            idCliente = rsCliente.getInt("IDCliente");
                        }

                        // Paso 2: Solicitar tipo de habitación
                        System.out.println("Seleccione el tipo de habitación:");
                        System.out.println("1. Cama Matrimonial");
                        System.out.println("2. Dos Camas");
                        System.out.println("3. Una Cama Matrimonial y una Pequeña Cama");
                        int opcionHabitacion = scaner.nextInt();
                        scaner.nextLine(); // Limpiar el buffer

                        int idHabitacion;
                        switch (opcionHabitacion) {
                            case 1 -> idHabitacion = 1; // ID de la habitación con Cama Matrimonial
                            case 2 -> idHabitacion = 2; // ID de la habitación con Dos Camas
                            case 3 -> idHabitacion = 3; // ID de la habitación con Matrimonial + Pequeña
                            default -> {
                                System.out.println("Opción inválida. Intente de nuevo.");
                                return;
                            }
                        }

                        // Paso 3: Solicitar fechas de la reserva
                        System.out.println("Ingrese la fecha inicial de su reserva (formato: yyyy-MM-dd HH:mm:ss):");
                        String fechaInicio = scaner.nextLine();
                        System.out.println("Ingrese la fecha final de su reserva (formato: yyyy-MM-dd HH:mm:ss):");
                        String fechaFin = scaner.nextLine();

                        // Insertar datos en la tabla Reserva
                        String consultaReserva = "INSERT INTO Reserva (IDUsuario, IDHabitacion, FechaInicio, FechaFin, Estado) " +
                                                 "VALUES (" + idCliente + ", " + idHabitacion + ", '" + fechaInicio + "', '" + fechaFin + "', 'Activa')";
                        Statement stmtReserva = conn.createStatement();
                        int filasReserva = stmtReserva.executeUpdate(consultaReserva);

                        if (filasReserva > 0) {
                            // Obtener el IDReserva recién creado
                            String consultaIDReserva = "SELECT MAX(IDReserva) AS IDReserva FROM Reserva";
                            ResultSet rsReserva = stmtReserva.executeQuery(consultaIDReserva);

                            if (rsReserva.next()) {
                                int numeroDeReserva = rsReserva.getInt("IDReserva");
                                System.out.println("Reserva creada exitosamente.");
                                System.out.println("Su número de reserva es: " + numeroDeReserva);
                            } else {
                                System.out.println("No se pudo obtener el número de la reserva.");
                            }
                        } else {
                            System.out.println("Error al crear la reserva.");
                        }
                    }
                    case 2 -> {
                        System.out.println("Ingrese el número de reserva:");
                        int numeroReserva = scaner.nextInt();
                        scaner.nextLine();
                        
                        consulta = "SELECT Cliente.Nombre, Habitacion.TipoHabitacion, FechaInicio, FechaFin, Estado" +
                                    "FROM ((Reserva" +
                                    "INNER JOIN Cliente ON Reserva.IDReserva = Cliente.IDCliente)"+
                                    "INNER JOIN Habitacion ON Reserva.IDReserva = Habitacion.IDHabitacion)"+
                                    "Where IDReserva ="+ numeroReserva +";";

                        try {
                            java.sql.Statement stmt = conn.createStatement();
                            System.out.println("La preparación de la consulta fue exitosa.");

                            ejecucion = stmt.executeQuery(consulta);


                            System.out.printf("%-10s %-30s %-20s %-10s %-10s%n", "Nombre", "TipoHabitacion", "FechaInicio", "FechaFin", "Estado");
                            System.out.println("---------------------------------------------------------------------------");
                            while (ejecucion.next()) {
                                String usuario = ejecucion.getString("Nombre");
                                String habitacion = ejecucion.getString("TipoHabitacion");
                                String fechaI = ejecucion.getString("FechaInicio");
                                String fechaF = ejecucion.getString("FechaFin");
                                String estado = ejecucion.getString("Estado");
                                System.out.printf("%-10s %-30s %-20s %-10s %-10s%n",usuario, habitacion, fechaI, fechaF, estado);
                            }

                            System.out.println("Ingrese el ajuste de fechas:");
                            System.out.print("Fecha Inicio (YYYY-MM-DD): ");
                            String nuevaFechaInicio = scaner.nextLine();
                            System.out.print("Fecha Fin (YYYY-MM-DD): ");
                            String nuevaFechaFin = scaner.nextLine();

                            String actualizarConsulta = "UPDATE Reserva SET FechaInicio = '" + nuevaFechaInicio + "', FechaFin = '" + nuevaFechaFin + "' WHERE IDReserva = " + numeroReserva;

                            int filasActualizadas = stmt.executeUpdate(actualizarConsulta);

                            if (filasActualizadas > 0) {
                                System.out.println("Las fechas se han actualizado exitosamente.");
                            } else {
                                System.out.println("Error: no se encontró una reserva con ese número.");
                            }
                        } catch (SQLException e) {
                            System.out.println("Ocurrió un error al realizar la reserva.");
                            e.printStackTrace();
                        }
                    }
                    case 3 -> {
                        System.out.println("Ingrese el numero de reserva que desea eliminar:");
                        int numeroReserva = scaner.nextInt();

                        System.out.println("Estas seguro de eliminar esta cuenta? (si/no)");
                        String confirmacion = scaner.next();

                        if (confirmacion.equalsIgnoreCase("si")) {
                            consulta = "DELETE FROM Reserva WHERE IDReserva = " + numeroReserva;
                            prepararConsulta = conn.createStatement();
                            int filasEliminadas = prepararConsulta.executeUpdate(consulta);

                            if (filasEliminadas > 0) {
                                System.out.println("Reserva eliminada exitosamente.");
                            } else {
                                System.out.println("No se encontro la reserva o no se pudo eliminar.");
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
                        System.out.println("Ingrese su nombre");
                        String nombreCliente = scaner.nextLine();
                        System.out.println("--------------------");
                              
                        System.out.println("Ingrese su correo");
                        String correoCliente = scaner.nextLine();
                        
                        System.out.println("Ingrese su telefono");
                        String telefonoCliente = scaner.nextLine();

                        // Insertar datos en la tabla Cliente
                        String consultaCliente = "INSERT INTO Cliente (Nombre, Correo, Telefono) VALUES ('" +
                                                  nombreCliente + "', '" + correoCliente + "', '" + telefonoCliente + "')";
                        Statement stmtCliente =  conn.createStatement();
                        int filasCliente = stmtCliente.executeUpdate(consultaCliente);

                        if (filasCliente > 0) {
                            System.out.println("Cliente registrado correctamente.");
                        } else {
                            System.out.println("Error al registrar al cliente.");
                            return;
                        }

                        // Obtener el IDCliente recién registrado
                        String consultaIDCliente = "SELECT MAX(IDCliente) AS IDCliente FROM Cliente";
                        ResultSet rsCliente = stmtCliente.executeQuery(consultaIDCliente);
                        int idCliente = 0;
                        if (rsCliente.next()) {
                            idCliente = rsCliente.getInt("IDCliente");
                        }

                        // Paso 2: Solicitar tipo de habitación
                        System.out.println("Seleccione el tipo de habitación:");
                        System.out.println("1. Cama Matrimonial");
                        System.out.println("2. Dos Camas");
                        System.out.println("3. Una Cama Matrimonial y una Pequeña Cama");
                        int opcionHabitacion = scaner.nextInt();
                        scaner.nextLine(); // Limpiar el buffer

                        int idHabitacion;
                        switch (opcionHabitacion) {
                            case 1 -> idHabitacion = 1; // ID de la habitación con Cama Matrimonial
                            case 2 -> idHabitacion = 2; // ID de la habitación con Dos Camas
                            case 3 -> idHabitacion = 3; // ID de la habitación con Matrimonial + Pequeña
                            default -> {
                                System.out.println("Opción inválida. Intente de nuevo.");
                                return;
                            }
                        }

                        // Paso 3: Solicitar fechas de la reserva
                        System.out.println("Ingrese la fecha inicial de su reserva (formato: yyyy-MM-dd HH:mm:ss):");
                        String fechaInicio = scaner.nextLine();
                        System.out.println("Ingrese la fecha final de su reserva (formato: yyyy-MM-dd HH:mm:ss):");
                        String fechaFin = scaner.nextLine();

                        // Insertar datos en la tabla Reserva
                        String consultaReserva = "INSERT INTO Reserva (IDUsuario, IDHabitacion, FechaInicio, FechaFin, Estado) " +
                                                 "VALUES (" + idCliente + ", " + idHabitacion + ", '" + fechaInicio + "', '" + fechaFin + "', 'Activa')";
                        Statement stmtReserva = conn.createStatement();
                        int filasReserva = stmtReserva.executeUpdate(consultaReserva);

                        if (filasReserva > 0) {
                            // Obtener el IDReserva recién creado
                            String consultaIDReserva = "SELECT MAX(IDReserva) AS IDReserva FROM Reserva";
                            ResultSet rsReserva = stmtReserva.executeQuery(consultaIDReserva);

                            if (rsReserva.next()) {
                                int numeroDeReserva = rsReserva.getInt("IDReserva");
                                System.out.println("Reserva creada exitosamente.");
                                System.out.println("Su número de reserva es: " + numeroDeReserva);
                            } else {
                                System.out.println("No se pudo obtener el número de la reserva.");
                            }
                        } else {
                            System.out.println("Error al crear la reserva.");
                        }
                    }
                    case 2 -> {
                        System.out.println("Ingrese el número de reserva:");
                        int numeroReserva = scaner.nextInt();
                        scaner.nextLine();
                        
                        consulta = "SELECT Cliente.Nombre, Habitacion.TipoHabitacion, FechaInicio, FechaFin, Estado" +
                                    "FROM ((Reserva" +
                                    "INNER JOIN Cliente ON Reserva.IDReserva = Cliente.IDCliente)"+
                                    "INNER JOIN Habitacion ON Reserva.IDReserva = Habitacion.IDHabitacion)"+
                                    "Where IDReserva ="+ numeroReserva +";";

                        try {
                            java.sql.Statement stmt = conn.createStatement();
                            System.out.println("La preparación de la consulta fue exitosa.");

                            ejecucion = stmt.executeQuery(consulta);


                            System.out.printf("%-10s %-30s %-20s %-10s %-10s%n", "Nombre", "TipoHabitacion", "FechaInicio", "FechaFin", "Estado");
                            System.out.println("---------------------------------------------------------------------------");
                            while (ejecucion.next()) {
                                String usuario = ejecucion.getString("Nombre");
                                String habitacion = ejecucion.getString("TipoHabitacion");
                                String fechaI = ejecucion.getString("FechaInicio");
                                String fechaF = ejecucion.getString("FechaFin");
                                String estado = ejecucion.getString("Estado");
                                System.out.printf("%-10s %-30s %-20s %-10s %-10s%n",usuario, habitacion, fechaI, fechaF, estado);
                            }

                            System.out.println("Ingrese el ajuste de fechas:");
                            System.out.print("Fecha Inicio (YYYY-MM-DD): ");
                            String nuevaFechaInicio = scaner.nextLine();
                            System.out.print("Fecha Fin (YYYY-MM-DD): ");
                            String nuevaFechaFin = scaner.nextLine();

                            String actualizarConsulta = "UPDATE Reserva SET FechaInicio = '" + nuevaFechaInicio + "', FechaFin = '" + nuevaFechaFin + "' WHERE IDReserva = " + numeroReserva;

                            int filasActualizadas = stmt.executeUpdate(actualizarConsulta);

                            if (filasActualizadas > 0) {
                                System.out.println("Las fechas se han actualizado exitosamente.");
                            } else {
                                System.out.println("Error: no se encontró una reserva con ese número.");
                            }
                        } catch (SQLException e) {
                            System.out.println("Ocurrió un error al realizar la reserva.");
                            e.printStackTrace();
                        }
                    }
                    case 3 -> {
                        System.out.println("Ingrese el numero de reserva que desea eliminar:");
                        int numeroReserva = scaner.nextInt();

                        System.out.println("Estas seguro de eliminar esta cuenta? (si/no)");
                        String confirmacion = scaner.next();

                        if (confirmacion.equalsIgnoreCase("si")) {
                            consulta = "DELETE FROM Reserva WHERE IDReserva = " + numeroReserva;
                            prepararConsulta = conn.createStatement();
                            int filasEliminadas = prepararConsulta.executeUpdate(consulta);

                            if (filasEliminadas > 0) {
                                System.out.println("Reserva eliminada exitosamente.");
                            } else {
                                System.out.println("No se encontro la reserva o no se pudo eliminar.");
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

