package org.eltech.ddm.inputdata.db;

import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Пул соединений
 */
public class ConnectionPool {
    //Строка подключения драйвера для СУБД MySQL
    private static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    //Строка подключения драйвера для СУБД Oracle
    private static String ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
    //Строка подключения драйвера для СУБД Microsoft
    private static String MICROSOFT_DRIVER = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
    //Строка подключения драйвера для СУБД PostgreSQL
    private static String POSTGRESQL_DRIVER = "org.postgresql.Driver";

    //Объект, содержщаий строки подключения к БД и их список доступных соединений
    private Map<String, List<Connection>> allConnections;
    //Список занятых соединений
    private List<Connection> usedConnections;

    /**
     * Конструктор, инициализация списков
     */
    private ConnectionPool() {
        allConnections = new HashMap<>();
        usedConnections = new ArrayList<>();
    }

    /**
     * Единственный объект пула соединений
     */
    private static class ConnectionPoolHolder {
        private static final ConnectionPool instance = new ConnectionPool();
    }

    /**
     * Получение объекта пула соединений
     * @return объект пула соединений
     */
    public static ConnectionPool getInstance() {
        return ConnectionPoolHolder.instance;
    }

    /**
     * Создание начального пула соединений
     * @param url адрес подключения к БД
     * @param user имя пользователя для подключения
     * @param password пароль для подключения
     */
    private void registerDriver(String url, String user, String password) throws ClassNotFoundException, SQLException {
        String driver = getDriver(url);
        Class.forName(driver);

        List<Connection> connections = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            connections.add(createConnection(url, user, password));
        }

        allConnections.put(url, connections);
    }

    /**
     * Создание соединение с БД
     * @param url адрес подключения к БД
     * @param user имя пользователя для подключения
     * @param password пароль для подключения
     * @return объект соединения с БД
     * @throws SQLException
     */
    private Connection createConnection(String url, String user, String password) throws SQLException {
        Connection con;

        con = DriverManager.getConnection(url, user, password);
        usedConnections.add(con);

        return con;
    }

    /**
     * Получение свободного соединения с БД
     * @param url адрес подключения к БД
     * @param user имя пользователя для подключения
     * @param password пароль для подключения
     * @return свободный объект соединения с БД
     */
    public synchronized Connection getConnection(String url, String user, String password) throws MiningException {
        Connection con;

        try {
            if (!allConnections.containsKey(url)) {
                registerDriver(url, user, password);
            }

            if (allConnections.get(url).size() == 0) {
                DriverManager.getDriver(url);
                con = createConnection(url, user, password);
                usedConnections.add(con);
            } else {
                con = allConnections.get(url).get(0);
                allConnections.get(url).remove(0);
                usedConnections.add(con);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            throw new MiningException(MiningErrorCode.INVALID_ARGUMENT, ex.getMessage());
        }

        return con;
    }

    /**
     * Освобождение соединения
     * @param con объект соединения, которое необходимо освободить
     * @param url адрес подключения к БД
     */
    public synchronized void freeConnection(Connection con, String url) {
        if (con != null) {
            if (usedConnections.remove(con)) {
                allConnections.get(url).add(con);
            }
        }
    }

    /**
     * Получение строки подключения драйвера для работы с БД
     * @param url адрес подключения к БД
     * @return строка подключения драйвера для работы с БД
     */
    private String getDriver(String url) throws ClassNotFoundException {
        String[] subStr;
        String delimeter = ":";
        subStr = url.split(delimeter);

        try {
            switch (subStr[1]) {
                case "mysql":
                    return MYSQL_DRIVER;
                case "oracle":
                    return ORACLE_DRIVER;
                case "microsoft":
                    return MICROSOFT_DRIVER;
                case "postgresql":
                    return POSTGRESQL_DRIVER;
                default:
                    throw new ClassNotFoundException("Can not find driver");
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new ClassNotFoundException("Can not parse url");
        }
    }
}