package my.web.database;

import my.web.Shot;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class JdbcOrm implements Orm {

    private final String url = "jdbc:postgresql://localhost:5432/web3";
    private final String user = "postgres";
    private final String password = "root";

    private List<Shot> shots;
    private final Connection connection;

    public JdbcOrm() {
        connection = connect();
        createShotsTable();
    }

    private Connection connect() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    private void createShotsTable() {
        String SQL = """
                CREATE TABLE IF NOT EXISTS shots (
                    id INT GENERATED ALWAYS AS IDENTITY,
                    x REAL NOT NULL,
                    y REAL NOT NULL,
                    radius REAL NOT NULL,
                    hit BOOLEAN NOT NULL,
                    date TIMESTAMP NOT NULL,
                    executiontime BIGINT NOT NULL,
                    shooter TEXT NOT NULL
                )
                """;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void createShot(Shot shot) {
        String SQL = "INSERT INTO shots (x, y, radius, hit, date, executiontime, shooter) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(SQL);) {

            statement.setFloat(1, shot.getX());
            statement.setFloat(2, shot.getY());
            statement.setFloat(3, shot.getRadius());
            statement.setDate(5, new Date(shot.getDate().getTime()));
            statement.setBoolean(4, shot.getHit());
            statement.setLong(6, shot.getExecutionTime());
            statement.setString(7, shot.getShooter());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        shot.setId(rs.getInt(1));
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Shot> getShots(String shooter) {
        String SQL = "SELECT id, x, y, radius, hit, date, executiontime FROM shots WHERE shooter = ?";

        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, shooter);
            ResultSet rs = stmt.executeQuery();
            List<Shot> shots = new LinkedList<Shot>();
            while (rs.next()) {
                var id = rs.getInt("id");
                var x = rs.getInt("x");
                var y = rs.getInt("y");
                var radius = rs.getInt("radius");
                var hit = rs.getBoolean("hit");
                var date = rs.getDate("date");
                var executionTime = rs.getLong("executiontime");

                var shot = new Shot(id, x, y, radius, hit, date, executionTime, shooter);
                shots.add(shot);
            }
            return shots;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearShots(String shooter) {
        String SQL = "DELETE FROM shots WHERE shooter";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(SQL);) {
            return;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
