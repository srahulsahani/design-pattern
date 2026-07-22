package creational_pattern.singleton.db_connection;

public class DBConnection {
    static private volatile DBConnection connection;

    private DBConnection(String name){
        System.out.println("Connection to DB established by: " + name);
    }

    static DBConnection getDBConnection(String conName){
        if(connection == null) {
            synchronized (DBConnection.class) {
                if (connection == null) {
                    connection = new DBConnection(conName);
                }
            }
        }
        return connection;
    }
}
