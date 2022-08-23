package shopaddon.ocshopaddon.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionHandler {

    private static ConnectionHandler instance;
    private static final Lock lock = new ReentrantLock();

    private static Connection connection;
    private static String adress = "135.148.53.116";
    private static String port = "5432";
    private static String user = "postgres";
    private static String pass = "fhurybsfnejifh34729d";
    private static String dataBase = "postgres";

    public static ConnectionHandler getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ConnectionHandler();
                }
            }
        }
        return instance;
    }

    private ConnectionHandler() {
        try {
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection("jdbc:postgresql://" + adress + ":" + port + "/" + dataBase,user ,pass);

            initialiseDatabase();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }


    private static void initialiseDatabase() {

        try {
            PreparedStatement stmt = connection.prepareStatement("set search_path = shopAddon;");
            stmt.executeUpdate();

            if (connection.getSchema().equalsIgnoreCase("shopAddon")) {
                System.out.println("[Database] Schema detected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            try {

                System.out.println("[Database] No schema found. Creating new.");

                PreparedStatement preparedStatement = connection.prepareStatement("""
                create schema shopAddon;
                set search_path = shopAddon;
                
                create domain name_variable varchar(10);
                create domain UID varchar(50);
                
                create table player(
                    UUID UID primary key,
                    inactive_days integer not null
                );
                
                create table shop(
                    shop_UID UID primary key,
                    shop_nick name_variable,
                    owner UID not null,
                    foreign key (owner) references player(UUID)
                );
                CREATE PROCEDURAL LANGUAGE plpgsql;
                create or replace function set_days() returns trigger as $$
                    begin
                        if old.inactive_days is null then
                            new.inactive_days := 0;
                        end if;
                        return new;
                    end;
                    $$ language plpgsql;
                
                create trigger set_new_player_days
                    before insert on player
                    for each row
                    execute procedure set_days();
            """);

                preparedStatement.executeUpdate();
                preparedStatement.close();
                System.out.println("[Database] Schema and tables created successfully");

            } catch (SQLException f) {
                f.printStackTrace();
            }
        }
    }
}
