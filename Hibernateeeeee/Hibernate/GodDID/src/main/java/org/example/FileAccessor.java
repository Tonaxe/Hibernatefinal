package org.example;

import org.example.Entities.Match;
import org.example.Entities.Player;
import org.example.Entities.Plays;
import org.example.Entities.Team;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class FileAccessor {
    private ArrayList<Team> llistaTeams = new ArrayList<>();
    private ArrayList<Match> llistaMatches = new ArrayList<>();
    private ArrayList<Plays> llistaPlays = new ArrayList<>();
    private ArrayList<Player> llistaPlayers = new ArrayList<>();

    private Connection connection;

    public FileAccessor() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/fut10";
        String user = "postgres";
        String password = "postgres";
        connection = DriverManager.getConnection(url, user, password);
    }


    public void readTeamsFile(String filename) throws IOException, SQLException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                try (Scanner scanner = new Scanner(linea).useDelimiter(",")) {
                    int code = scanner.nextInt();
                    String name = scanner.next();
                    String stadium = scanner.next();
                    String city = scanner.next();
                    insertTeam(code, name, stadium, city);
                }
            }
        }
    }

    public void readMatchesFile(String filename) throws IOException, SQLException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                try (Scanner scanner = new Scanner(linea).useDelimiter(",")) {
                    int code = scanner.nextInt();
                    String playedStr = scanner.next();
                    Date played = null;
                    try {
                        played = new SimpleDateFormat("yyyy-MM-dd").parse(playedStr);
                    } catch (ParseException e) {
                        System.out.println("Error al convertir la fecha: " + e.getMessage());
                        continue;
                    }
                    insertMatch(code, played);
                }
            }
        }
    }

    public void readPlayersFile(String filename) throws IOException, SQLException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                try (Scanner scanner = new Scanner(linea).useDelimiter(",")) {
                    int id = scanner.nextInt();
                    String surname = scanner.next();
                    String forename = scanner.next();
                    insertPlayer(id, surname, forename);
                }
            }
        }
    }

    public void readPlaysFile(String filename) throws IOException, SQLException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                try (Scanner scanner = new Scanner(linea).useDelimiter(",")) {
                    int matchCode = scanner.nextInt();
                    int playerId = scanner.nextInt();
                    int starts = scanner.nextInt();
                    int substituted = scanner.nextInt();
                    int goals = scanner.nextInt();
                    int yellow = scanner.nextInt();
                    boolean red = scanner.nextBoolean();
                    insertPlay(matchCode, playerId, starts, substituted, goals, yellow, red);
                }
            }
        }
    }


    private void insertTeam(int code, String name, String stadium, String city) throws SQLException {
        String query = "INSERT INTO team (code, name, stadium, city) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, code);
            statement.setString(2, name);
            statement.setString(3, stadium);
            statement.setString(4, city);
            statement.executeUpdate();
        }
    }

    private void insertMatch(int code, Date played) throws SQLException {
        String query = "INSERT INTO match (code, played) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, code);
            statement.setDate(2, new java.sql.Date(played.getTime()));
            statement.executeUpdate();
        }
    }

    private void insertPlayer(int id, String surname, String forename) throws SQLException {
        String query = "INSERT INTO player (id, surname, forename) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setString(2, surname);
            statement.setString(3, forename);
            statement.executeUpdate();
        }
    }

    private void insertPlay(int matchCode, int playerId, int starts, int substituted, int goals, int yellow, boolean red) throws SQLException {
        String query = "INSERT INTO plays (match_code, player_id, starts, substituted, goals, yellow, red) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, matchCode);
            statement.setInt(2, playerId);
            statement.setInt(3, starts);
            statement.setInt(4, substituted);
            statement.setInt(5, goals);
            statement.setInt(6, yellow);
            statement.setBoolean(7, red);
            statement.executeUpdate();
        }
    }





    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
