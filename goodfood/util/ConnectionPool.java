public class ConnectionPool {
	private static String DRIVER_NAME = "org.h2.Driver";
    private static String URL  = "jdbc:h2:tcp://localhost/~/goodFood";
	private static final int MAX_CONNECTION_NUMBER = 3;
    private static String USER_NAME = "admin";
    private static String PASSWORD = "secret";

    private static ConnectionPool instance = null;
    private List<Connection> usedConnection = new ArrayList<Connection>();
    private static List<Connection> availableConnections = new ArrayList<Connection>();


    private ConnectionPool(){}

    public static ConnectionPool getInstance() throws SQLException {
        if(instance == null){
            loadDriver(DRIVER_NAME);

            synchronized (ConnectionPool.class){
                if (instance==null){
                    instance = new ConnectionPool();
                    for(int count = 0 ; count < MAX_CONNECTION_NUMBER; count++){
                        availableConnections.add(DriverManager.getConnection(URL, USER_NAME, PASSWORD));
                    }
                }
            }
        }

        return  instance;
    }
	
	private static void loadDriver(String driverName) {
        try {
            Driver driver = (Driver) Class.forName(driverName).newInstance();
            DriverManager.registerDriver(driver);
        } catch (Exception e) {
            System.out.println("Can't register JDBC driver ");
        }
    }

    public Connection getConnection(){
        if(availableConnections.size() == 0){
            System.out.println("No free connection");
            return  null;
        }
        else {
            Connection connection = availableConnections.remove(availableConnections.size() - 1);
            return connection;
        }
    }

    public  boolean returnConnection(Connection connection){
        if (connection != null){
            usedConnection.remove(connection);
            availableConnections.add(connection);
            return  true;
        }
        return  false;
    }

    public int getFreeConnectionCount(){
        return availableConnections.size();
    }
}
