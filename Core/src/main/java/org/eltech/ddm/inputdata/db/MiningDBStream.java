package org.eltech.ddm.inputdata.db;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningDataException;
import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningdata.EPhysicalData;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для работы с БД
 */
public class MiningDBStream extends MiningInputStream {

    //Соединение с БД
    private Connection con;
    //Адрес подключения к БД
    private String url;
    //Параметры подключения к серверу
    private String user;
    private String password;
    //Название таблицы
    private String tableName;

    //Данные из БД, полученные в результате запроса
    private ResultSet values;
    //Метаданные о БД
    private DatabaseMetaData metaData;
    //Настройки по преобразованию данных
    private Map<String, FieldDBConvert> allSettings = new HashMap<>();

    public MiningDBStream() {
    }

    /**
     * Конструктор без параметра настроек преобразования данных
     * @param url адрес подключения к БД
     * @param user имя пользователя для подключения
     * @param password пароль для подключения
     * @param tableName имя таблицы, с которой будет происходить работа
     */
    public MiningDBStream(String url, String user, String password, String tableName) throws MiningException {
        init(url, user, password, tableName);
        allSettings = new HashMap<>();
        open();
        physicalData = recognize();
    }

    /**
     * Конструктор с параметром настроек преобразования данных
     * @param url адрес подключения к БД
     * @param user имя пользователя для подключения
     * @param password пароль для подключения
     * @param tableName имя таблицы, с которой будет происходить работа
     * @param allSettings объект, содержащий настройки по преобразованию данных
     */
    public MiningDBStream(String url, String user, String password, String tableName, Map<String, FieldDBConvert> allSettings) throws MiningException {
        init(url, user, password, tableName);
        if (allSettings != null)
            this.allSettings = allSettings;
        open();
        physicalData = recognize();
    }

    /**
     * Конструктор без инициализации
     * @param url адрес подключения к БД
     * @param user имя пользователя для подключения
     * @param password пароль для подключения
     * @param tableName имя таблицы, с которой будет происходить работа
     */
    public static MiningDBStream createWithoutInit(String url, String user, String password, String tableName) throws MiningException {
        return new MiningDBStream(url, user,
                password, tableName, false);
    }

    /**
     * @param url адрес подключения к БД
     * @param user имя пользователя для подключения
     * @param password пароль для подключения
     * @param tableName имя таблицы, с которой будет происходить работа
     * @param needInit требуется ли инициализация
     */
    private MiningDBStream(String url, String user, String password, String tableName, boolean needInit) throws MiningException {
        allSettings = new HashMap<>();
        init(url, user, password, tableName);
        if (needInit) {
            open();
            physicalData = recognize();
        }
    }

    /**
     * @param url адрес подключения к БД
     * @param user имя пользователя для подключения
     * @param password пароль для подключения
     * @param tableName имя таблицы, с которой будет происходить работа
     */
    private void init(String url, String user, String password, String tableName) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.tableName = tableName;
    }

    /**
     * Отрытие соединения с БД
     */
    @Override
    public void open() throws MiningException {
        try {
            con = ConnectionPool.getInstance().getConnection(url, user, password);
            metaData = con.getMetaData();
            this.open = true;
        } catch (SQLException ex) {
            this.open = false;
            throw new MiningDataException("Can not get connection to database: " + ex.getMessage());
        }
    }

    /**
     * Закрытие соединения с БД
     */
    @Override
    public void close() throws MiningException {
        if (!open) {
            throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA, "Stream is already closed");
        }

        ConnectionPool.getInstance().freeConnection(con, url);
        this.open = false;
    }

    /**
     * Создание логической модели данных
     */
    @Override
    public EPhysicalData recognize() throws MiningException {
        logicalData = new ELogicalData();
        logicalData.setName(tableName);

        try {
            prepareAllSettings(prepareColumns());
            for (Map.Entry<String, FieldDBConvert> entry : allSettings.entrySet()) {
                logicalData.getAttributes().add(getLogicalAttribute(entry.getKey(), entry.getValue()));
            }
        } catch (SQLException e) {
            throw new MiningDataException(e.getMessage());
        }

        return null;
    }

    /**
     * Удаление колонок с первичным и внешним ключом
     * @return объект, содержащий все названия колонок и их тип данных
     */
    private Map<String, String> prepareColumns() throws MiningDataException {
//        List<String> fkColumnNames = getForeignKeys();
//        String pkColumnNames = getPrimaryKey();
        Map<String, String> columnsInfo = getColumnsInfo();

//        if (columnsInfo.containsKey(pkColumnNames)) {
//            columnsInfo.remove(pkColumnNames);
//        }
//
//        for (String fkColumnName : fkColumnNames) {
//            if (columnsInfo.containsKey(fkColumnName)) {
//                columnsInfo.remove(fkColumnName);
//            }
//        }

        if (allSettings != null) {
            for (Map.Entry<String, FieldDBConvert> entry : allSettings.entrySet()) {
                if (columnsInfo.containsKey(entry.getKey())) {
                    columnsInfo.remove(entry.getKey());
                }
            }
        }

        return columnsInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getVectorsNumber() {
        if (values == null) {
            try {
                getData();
            } catch (MiningDataException e) {
                e.printStackTrace();
            }
        }

        int counter = 0;
        try {
            while (values.next()) {
                ++counter;
            }
            return values.getFetchSize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }

    /**
     * Добавление настроек по умолчанию
     * @param columnsInfo объект, содержащий названия столбцов и их типов данных
     */
    private void prepareAllSettings(Map<String, String> columnsInfo) {
        for (Map.Entry<String, String> entry : columnsInfo.entrySet()) {
            if (entry.getValue().toLowerCase().contains("char")
                    || entry.getValue().toLowerCase().contains("text")) {
                allSettings.put(entry.getKey(), new DefStringConvert());
            } else if (entry.getValue().toLowerCase().contains("int")
                    || entry.getValue().toLowerCase().contains("float")
                    || entry.getValue().toLowerCase().contains("double")
                    || entry.getValue().toLowerCase().contains("real")
                    || entry.getValue().toLowerCase().contains("dec")) {
                allSettings.put(entry.getKey(), new DefNumsConvert());
            } else if (entry.getValue().toLowerCase().contains("date")
                    || entry.getValue().toLowerCase().contains("time")) {
                allSettings.put(entry.getKey(), new DefDateTimeConvert());
            } else if (entry.getValue().toLowerCase().contains("bool")
                    || entry.getValue().toLowerCase().contains("binary")) {
                allSettings.put(entry.getKey(), new DefBooleanConvert());
            }
        }
    }

    /**
     *
     * @param columnName название колонки
     * @param settings настройки преобразования для данного столбца
     * @return логический атрибут
     */
    private ELogicalAttribute getLogicalAttribute(String columnName, FieldDBConvert settings) throws SQLException, MiningException {
        ELogicalAttribute la = new ELogicalAttribute();

        la.setName(columnName);
        settings.setTableName(tableName);
        settings.setTypeColumn(getColumnInfo(columnName));
        la.setCategoricalProperties(settings.getCategoricalProperties(con, columnName));
        la.set_numerical_properties(settings.getNumericalProperties(con, columnName));

        return la;
    }

    /**
     * Сброс текущей позиции на строку перед первой
     */
    @Override
    public void reset() throws MiningException {
        if (!open)
            throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA, "Can't reset closed stream. Call open()");

        try {
            values.beforeFirst();
        } catch (SQLException e) {
            throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA, "Can not reset "+e.getMessage());
        }
        cursorPosition = -1;
    }

    /**
     * Чтение текущий строки данных в БД
     * @return преобразованные данные из БД в объект MiningVector
     */
    @Override
    public MiningVector readPhysicalRecord() throws MiningException {
        if (values == null) {
            getData();
        }

        double[] valuesMV = new double[logicalData.getAttributesNumber()];
        List<ELogicalAttribute> laList = logicalData.getAttributes();
        ELogicalAttribute la = null;
        int i = 0;

        try {
            values.next();
        } catch (SQLException e) {
            throw new MiningDataException(e.getMessage());
        }

        for (Map.Entry<String, FieldDBConvert> entry : allSettings.entrySet()) {
            for (int j = 0; j < laList.size(); j++) {
                if (laList.get(j).getName().equals(entry.getKey())) {
                    la = laList.get(j);
                }
            }
            valuesMV[i] = readValue(la, entry);
            ++i;
        }
        ++cursorPosition;
        MiningVector miningVector = new MiningVector(valuesMV);
        miningVector.setLogicalData(logicalData);
        miningVector.setIndex(cursorPosition);

        return miningVector;
    }

    /**
     * Чтение и преобразование одной ячейки таблицы БД
     * @param la логический атрибут
     * @param entry объект, содержащий название столбца таблицы и настройки преобразования
     * @return преобразованное значение
     */
    private double readValue(ELogicalAttribute la, Map.Entry<String, FieldDBConvert> entry) throws MiningException {
        if (!open)
            throw new MiningException(MiningErrorCode.INVALID_DATA_TYPE, "Can't perform operation on closed stream. Call open()");

        double readValue = 0;
        try {
            if (entry.getValue().getClass().getName().equals(DefStringConvert.class.getName())) {
                String strObj = values.getString(entry.getKey());
                if (la.getCategoricalProperties().getSize() != 0)
                    if (strObj != null)
                        readValue = ((DefStringConvert) entry.getValue()).getCategoriesIndices().get(strObj);
                    else
                        readValue = ((DefStringConvert) entry.getValue()).getCategoriesIndices().get("null");
            } else if (entry.getValue().getClass().getName().equals(DefNumsConvert.class.getName())) {
                readValue = values.getDouble(entry.getKey());
            } else if (entry.getValue().getClass().getName().equals(DefBooleanConvert.class.getName())) {
                readValue = values.getBoolean(entry.getKey()) ? 1 : 0;
            } else if (entry.getValue().getClass().getName().equals(DefDateTimeConvert.class.getName())) {
                if (values.getTimestamp(entry.getKey()) != null && !values.getString(entry.getKey()).equals("")) {
                    readValue = values.getTimestamp(entry.getKey()).getTime() / 1000;
                }
            }
        } catch (SQLException ex) {
            throw new MiningDataException(ex.getMessage());
        }

        return readValue;
    }

    /**
     * Чтение указанной строки
     * @param position индекс строки
     * @return преобразованная строка из БД в объект MiningVector
     */
    @Override
    protected MiningVector movePhysicalRecord(int position) throws MiningException {
        if (position < 0) {
            throw new IndexOutOfBoundsException("Position cannot be less than 0");
        }

        if (values == null) {
            getData();
        }

        int offset = position - cursorPosition;

        if (offset < 0) {
            cursorPosition = -1;
            offset = position - cursorPosition;
            try {
                values.beforeFirst();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < offset - 1; i++) {
            try {
                values.next();
                ++cursorPosition;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return readPhysicalRecord();
    }

    /**
     * Получение данных тз БД
     */
    private void getData() throws MiningDataException {
        String getDataQuery = "select * from " + tableName + " order by " + allSettings.entrySet().iterator().next().getKey();
        Statement stmt;

        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            values = stmt.executeQuery(getDataQuery);
        } catch (SQLException ex) {
            throw new MiningDataException(ex.getMessage());
        }
    }

    /**
     * Получение названий и типов данных всех столбцов
     * @return объект всех названий столбцов и их типов данных
     */
    private Map<String, String> getColumnsInfo() throws MiningDataException {
        ResultSet rs;
        Map<String, String> columnsInfo;

        try {
            rs = metaData.getColumns(null, null, tableName, null);
            columnsInfo = new HashMap<>();
            while (rs.next()) {
                columnsInfo.put(rs.getString("COLUMN_NAME"), rs.getString("TYPE_NAME"));
            }
        } catch (SQLException ex) {
            throw new MiningDataException("Can not get connection to database: " + ex.getMessage());
        }

        return columnsInfo;
    }

    /**
     * Полчучение типа столбца по названию
     * @param columnName название столбца
     * @return тип столбца
     */
    private String getColumnInfo(String columnName) throws SQLException {
        ResultSet rs = metaData.getColumns(null, null, tableName, columnName);
        rs.next();
        return rs.getString("TYPE_NAME");
    }

    /**
     * Получение списка внешних ключей
     * @return список внешних ключей
     */
    private List<String> getForeignKeys() throws MiningDataException {
        ResultSet rs;
        List<String> fkColumnName = new ArrayList<>();

        try {
            rs = metaData.getImportedKeys(null, null, tableName);
            while (rs.next()) {
                fkColumnName.add(rs.getString("FKCOLUMN_NAME"));
            }
        } catch (SQLException ex) {
            throw new MiningDataException("Can not get connection to database: " + ex.getMessage());
        }

        return fkColumnName;
    }

    /**
     * Получение названия столбца с первичными ключами
     * @return название столбца
     */
    private String getPrimaryKey() throws MiningDataException {
        ResultSet rs;
        String pkColumnName;

        try {
            rs = metaData.getPrimaryKeys(null, "public", tableName);
            rs.next();
            pkColumnName = rs.getString("COLUMN_NAME");
        } catch (SQLException ex) {
            throw new MiningDataException("Can not get connection to database: " + ex.getMessage());
        }

        return pkColumnName;
    }

}


