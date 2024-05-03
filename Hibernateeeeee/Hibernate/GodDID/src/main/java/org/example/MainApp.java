package org.example;
import org.example.Entities.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import javax.persistence.Query;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    private static SessionFactory sessionFactory;


    public static void main(String[] args) {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        Scanner scanner = new Scanner(System.in);
        int opcion;


        while (true){
            System.out.println("1. Listar entidades");
            System.out.println("2. Insertar entidad");
            System.out.println("3. Eliminar entidad");
            System.out.println("4. Actualizar entidad");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion=scanner.nextInt();
            if (opcion==1){
                listarEntidades();
            } else if (opcion==2) {
                insertarEntidad();
            }else if (opcion==3) {
                eliminarEntidad();
            }else if (opcion==4) {
                actualizarEntidad();
            }else if (opcion==5) {
                break;
            }else{
                continue;
            }
        }
        sessionFactory.close();

    }
    private static void listarEntidades() {
        Scanner scanner = new Scanner(System.in);
        int opcion2;

        do {
            System.out.println("Seleccione una entidad:");
            System.out.println("1. Match");
            System.out.println("2. Player");
            System.out.println("3. Plays");
            System.out.println("4. Team");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion2 = scanner.nextInt();

            switch (opcion2) {
                case 1:
                    listarMatch();
                    break;
                case 2:
                    listarPlayer();
                    break;
                case 3:
                    listarPlays();
                    break;
                case 4:
                    listarTeam();
                    break;
                case 5:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione otra opción.");
            }

        } while (opcion2 != 7);

    }

    private static void listarMatch() {
        Session session = sessionFactory.openSession();
        try {
            List<Match> matches = session.createQuery("FROM Match").list();
            for (Match match : matches) {
                System.out.println(match);
            }
        } finally {
            session.close();
        }
    }

    private static void listarPlayer() {
        Session session = sessionFactory.openSession();
        try {
            List<Player> players = session.createQuery("FROM Player").list();
            for (Player player : players) {
                System.out.println(player);
            }
        } finally {
            session.close();
        }
    }

    private static void listarPlays() {
        Session session = sessionFactory.openSession();
        try {
            List<Plays> playsList = session.createQuery("FROM Plays").list();
            for (Plays plays : playsList) {
                System.out.println(plays);
            }
        } finally {
            session.close();
        }
    }

    private static void listarTeam() {
        Session session = sessionFactory.openSession();
        try {
            List<Team> teams = session.createQuery("FROM Team").list();
            for (Team team : teams) {
                System.out.println(team);
            }
        } finally {
            session.close();
        }
    }
    private static void insertarEntidad() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Seleccione una entidad para insertar:");
            System.out.println("1. Match");
            System.out.println("2. Player");
            System.out.println("3. Plays");
            System.out.println("4. Team");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    insertarMatch();
                    break;
                case 2:
                    insertarPlayer();
                    break;
                case 3:
                    insertarPlays();
                    break;
                case 4:
                    insertarTeam();
                    break;
                case 5:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione otra opción.");
            }

        } while (opcion != 4);
    }

    private static void insertarMatch() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Indica el archivo y su ruta para los partidos: ");
        String fichero = scanner.nextLine();

        try {
            FileAccessor fileAccessor = new FileAccessor();
            fileAccessor.readMatchesFile(fichero);
            System.out.println("Matches insertados correctamente.");
        } catch (IOException | SQLException e) {
            System.out.println("Error al insertar partidos: " + e.getMessage());
        }
    }

    private static void insertarPlayer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Indica el archivo y su ruta para los jugadores: ");
        String fichero = scanner.nextLine();

        try {
            FileAccessor fileAccessor = new FileAccessor();
            fileAccessor.readPlayersFile(fichero);
            System.out.println("Jugadores insertados correctamente.");
        } catch (IOException | SQLException e) {
            System.out.println("Error al insertar jugadores: " + e.getMessage());
        }
    }

    private static void insertarPlays() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Indica el archivo y su ruta para los registros de juego: ");
        String fichero = scanner.nextLine();

        try {
            FileAccessor fileAccessor = new FileAccessor();
            fileAccessor.readPlaysFile(fichero);
            System.out.println("Registros de juego insertados correctamente.");
        } catch (IOException | SQLException e) {
            System.out.println("Error al insertar registros de juego: " + e.getMessage());
        }
    }
    private static void insertarTeam() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Indica el archivo y su ruta para los equipos: ");
        String fichero = scanner.nextLine();

        try {
            FileAccessor fileAccessor = new FileAccessor();
            fileAccessor.readTeamsFile(fichero);
            System.out.println("Equipos insertados correctamente.");
        } catch (IOException | SQLException e) {
            System.out.println("Error al insertar equipos: " + e.getMessage());
        }
    }




    private static void eliminarEntidad() {
        Scanner scanner = new Scanner(System.in);
        int opcion2;

        do {
            System.out.println("Seleccione una entidad a eliminar:");
            System.out.println("1. Match");
            System.out.println("2. Player");
            System.out.println("3. Plays");
            System.out.println("4. Team");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion2 = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion2) {
                case 1:
                    eliminarMatch();
                    break;
                case 2:
                    eliminarPlayer();
                    break;
                case 3:
                    eliminarPlays();
                    break;
                case 4:
                    eliminarTeam();
                    break;
                case 5:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione otra opción.");
            }

        } while (opcion2 != 5);
    }




    private static void eliminarMatch() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el código del Match a eliminar: ");
        int codigoMatch = scanner.nextInt();

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Match match = session.get(Match.class, codigoMatch);
            if (match != null) {
                session.delete(match);
                System.out.println("Match eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún Match con el código proporcionado.");
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void eliminarPlayer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el ID del Player a eliminar: ");
        int idPlayer = scanner.nextInt();

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Player player = session.get(Player.class, idPlayer);
            if (player != null) {
                session.delete(player);
                System.out.println("Player eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún Player con el ID proporcionado.");
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void eliminarPlays() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el código del Match para eliminar los registros de juego: ");
        int codigoMatch = scanner.nextInt();

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Plays WHERE match_code = :codigoMatch");
            query.setParameter("codigoMatch", codigoMatch);
            int rowsAffected = query.executeUpdate();
            System.out.println(rowsAffected + " registros de juego eliminados correctamente para el Match con código " + codigoMatch);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void eliminarTeam() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el código del Team a eliminar: ");
        int codigoTeam = scanner.nextInt();

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Team team = session.get(Team.class, codigoTeam);
            if (team != null) {
                session.delete(team);
                System.out.println("Team eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún Team con el código proporcionado.");
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    private static void actualizarEntidad() {
        Scanner scanner = new Scanner(System.in);
        int opcion2;

        do {
            System.out.println("Seleccione una entidad a actualizar:");
            System.out.println("1. Match");
            System.out.println("2. Player");
            System.out.println("3. Plays");
            System.out.println("4. Team");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion2 = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion2) {
                case 1:
                    actualizarMatch();
                    break;
                case 2:
                    actualizarPlayer();
                    break;
                case 3:
                    actualizarPlays();
                    break;
                case 4:
                    actualizarTeam();
                    break;
                case 5:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione otra opción.");
            }

        } while (opcion2 != 5);
    }

    private static void actualizarMatch() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el código del Match a actualizar: ");
        int codigoMatch = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Match match = (Match) session.get(Match.class, codigoMatch);
            if (match != null) {
                // Solicitar al usuario que ingrese los nuevos datos del partido
                System.out.println("Ingrese la nueva fecha del partido (YYYY-MM-DD): ");
                String nuevaFechaStr = scanner.nextLine();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date nuevaFecha = dateFormat.parse(nuevaFechaStr);
                match.setPlayed(nuevaFecha);

                session.update(match);
                System.out.println("Match actualizado correctamente.");
            } else {
                System.out.println("No se encontró ningún Match con el código proporcionado.");
            }
            tx.commit();
        } catch (HibernateException | ParseException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void actualizarPlayer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el ID del Player a actualizar: ");
        int idPlayer = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Player player = (Player) session.get(Player.class, idPlayer);
            if (player != null) {
                // Solicitar al usuario que ingrese los nuevos datos del jugador
                System.out.println("Ingrese el nuevo nombre del jugador: ");
                String nuevoNombre = scanner.nextLine();
                player.setForename(nuevoNombre);

                System.out.println("Ingrese el nuevo apellido del jugador: ");
                String nuevoApellido = scanner.nextLine();
                player.setSurname(nuevoApellido);

                session.update(player);
                System.out.println("Player actualizado correctamente.");
            } else {
                System.out.println("No se encontró ningún Player con el ID proporcionado.");
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void actualizarPlays() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el código del partido para actualizar los Plays: ");
        int codigoPartido = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            // Verificar si hay Plays asociados al partido especificado
            Query query = session.createQuery("FROM Plays WHERE matchCode = :codigoPartido");
            query.setParameter("codigoPartido", codigoPartido);
            List<Plays> playsList = (List<Plays>) ((org.hibernate.query.Query<?>) query).list();

            if (!playsList.isEmpty()) {
                // Si hay Plays asociados al partido, solicitar al usuario los nuevos datos y actualizarlos
                for (Plays play : playsList) {
                    System.out.println("Actualizando Plays para el partido con código: " + codigoPartido);
                    System.out.println("Ingrese el nuevo número de starts para el jugador con ID " + play.getPlayerId() + ": ");
                    int nuevosStarts = scanner.nextInt();
                    play.setStarts(nuevosStarts);

                    System.out.println("Ingrese el nuevo número de substituted para el jugador con ID " + play.getPlayerId() + ": ");
                    int nuevosSubstituted = scanner.nextInt();
                    play.setSubstituted(nuevosSubstituted);

                    System.out.println("Ingrese el nuevo número de goles para el jugador con ID " + play.getPlayerId() + ": ");
                    int nuevosGoles = scanner.nextInt();
                    play.setGoals(nuevosGoles);

                    System.out.println("Ingrese el nuevo número de tarjetas amarillas para el jugador con ID " + play.getPlayerId() + ": ");
                    int nuevasAmarillas = scanner.nextInt();
                    play.setYellow(nuevasAmarillas);

                    System.out.println("¿El jugador con ID " + play.getPlayerId() + " recibió una tarjeta roja? (true/false): ");
                    boolean nuevaRoja = scanner.nextBoolean();
                    play.setRed(nuevaRoja);

                    // Actualizar el objeto Plays en la base de datos
                    session.update(play);
                    System.out.println("Plays actualizado correctamente para el jugador con ID " + play.getPlayerId());
                }
            } else {
                System.out.println("No se encontraron Plays asociados al partido con código: " + codigoPartido);
            }

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    private static void actualizarTeam() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el código del Team a actualizar: ");
        int codigoTeam = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Team team = (Team) session.get(Team.class, codigoTeam);
            if (team != null) {
                // Solicitar al usuario que ingrese los nuevos datos del equipo
                System.out.println("Ingrese el nuevo nombre del equipo: ");
                String nuevoNombre = scanner.nextLine();
                team.setName(nuevoNombre);

                System.out.println("Ingrese el nuevo estadio del equipo: ");
                String nuevoEstadio = scanner.nextLine();
                team.setStadium(nuevoEstadio);

                System.out.println("Ingrese la nueva ciudad del equipo: ");
                String nuevaCiudad = scanner.nextLine();
                team.setCity(nuevaCiudad);

                session.update(team);
                System.out.println("Team actualizado correctamente.");
            } else {
                System.out.println("No se encontró ningún Team con el código proporcionado.");
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }



}

