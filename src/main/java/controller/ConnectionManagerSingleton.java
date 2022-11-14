package controller;

public class ConnectionManagerSingleton {
    public static ThreadLocal<ConnectionManager> instance;
    static {
        ThreadLocal<ConnectionManager> dm;
        try {
            dm = new ThreadLocal<ConnectionManager>() {
                @Override protected ConnectionManager initialValue() {
                    try {
                        return new ConnectionManager();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
            dm = null;
        }
        instance = dm;
    }
}