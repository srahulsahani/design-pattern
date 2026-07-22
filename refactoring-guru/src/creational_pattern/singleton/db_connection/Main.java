package creational_pattern.singleton.db_connection;

public class Main {
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            DBConnection.getDBConnection("Connection1");
        });

        Thread t2 = new Thread(()->{
            DBConnection.getDBConnection("Connection2");
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
