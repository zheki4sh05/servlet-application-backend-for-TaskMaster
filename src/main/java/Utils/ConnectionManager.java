package Utils;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionManager {
    private static BlockingQueue<Connection> pool;
    static{
        loadDriver();
        initConnectionPool();
    }

    private static void loadDriver() {
        try {
            Class.forName(DataBase.CNM.value);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void initConnectionPool() {
        int size = Integer.parseInt(DataBase.POOL_SIZE.value);
        pool = new ArrayBlockingQueue<Connection>(size);
        for (int i=0;i<size;i++){
            Connection connection = open();
            Connection proxyConnection = (Connection) Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(), new Class[]{Connection.class},
                    (proxy, method, args) -> method.getName().equals("close") ? pool.add((Connection) proxy) : method.invoke(connection,args));
            pool.add(proxyConnection);
        }
    }
    public static Connection get(){
        try {
            return  pool.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Connection open(){
        try{
            return DriverManager.getConnection(DataBase.URL.value, DataBase.NAME.value, DataBase.PWD.value);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
