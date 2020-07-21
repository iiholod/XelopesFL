package org.eltech.ddm.inputdata.db;

import org.eltech.ddm.miningcore.MiningDataException;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ECategoricalAttributeProperties;
import org.eltech.ddm.miningcore.miningdata.ENumericalAttributeProperties;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class FieldDBConvert {
    protected String tableName;
    protected String columnType;

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setTypeColumn(String type){
        columnType = type;
    }

    public abstract ECategoricalAttributeProperties getCategoricalProperties(Connection con, String nameField) throws MiningException;

    public abstract ENumericalAttributeProperties getNumericalProperties(Connection con, String nameField) throws MiningException;

    public double getAllRowCount(Connection con, String nameField) throws MiningDataException {
        String allRowCountQuery = "SELECT COUNT(" + nameField + ") AS result from " + tableName;
        return executeNumberQuery(con, allRowCountQuery);
    }

    public double getUniqueRowCountQuery(Connection con, String nameField) throws MiningDataException {
        String uniqueRowCountQuery = "SELECT COUNT(distinct " + nameField + ") AS result from " + tableName;
        return executeNumberQuery(con, uniqueRowCountQuery);
    }

    public double getMaxValue(Connection con, String nameField) throws MiningDataException {
        String maxQuery = "SELECT MAX(" + nameField + ") AS result from " + tableName;
        return executeNumberQuery(con, maxQuery);
    }

    public double getMinValue(Connection con, String nameField) throws MiningDataException {
        String minQuery = "SELECT MIN(" + nameField + ") AS result from " + tableName;
        return executeNumberQuery(con, minQuery);
    }

    public long getEarliestDate(Connection con, String nameField) throws MiningDataException {
        String earliestQuery = "SELECT MIN(" + nameField + ") AS result from " + tableName;
        return executeDateQuery(con, earliestQuery);
    }


    public long getOldestDate(Connection con, String nameField) throws MiningDataException {
        String earliestQuery = "SELECT MAX(" + nameField + ") AS result from " + tableName;
        return executeDateQuery(con, earliestQuery);
    }

    public double executeNumberQuery(Connection con, String query) throws MiningDataException {

        double result;
        Statement stmt;
        ResultSet rs;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            rs.next();
            result = rs.getDouble("result");
        } catch (SQLException ex) {
            throw new MiningDataException(ex.getMessage());
        }

        return result;
    }

    public ResultSet getValues(Connection con, String nameField) throws SQLException {
        String getValueQuery = "select distinct " + nameField + " from " + tableName + " order by " + nameField;
        Statement stmt;
        ResultSet rs;

        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        rs = stmt.executeQuery(getValueQuery);

        return rs;
    }

    public long executeDateQuery(Connection con, String query) throws MiningDataException {
        long result;
        Statement stmt;
        ResultSet rs;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            rs.next();
            result = rs.getTimestamp("result").getTime() / 1000;
        } catch (SQLException ex) {
            throw new MiningDataException(ex.getMessage());
        }

        return result;
    }
}
