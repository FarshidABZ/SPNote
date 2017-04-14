package com.farshidabz.supernote.interactor.dbhandler;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by FarshidAbz.
 * Since 4/12/2017.
 */
public abstract class Table<T> {
    private String CREATE_QUERY = "CREATE TABLE %s(%s);";
    private String COLUMNS_TEMPLATE = "%s %s";

    private String SELECT_QUERY = "SELECT $s from %s";
    private String WHERE_QUERY = "%s where %d";

    protected SQLiteOpenHelper dbHelper;

    private HashMap<String, String> columns = new HashMap<>();

    private String table_name;
    private String key_column;

    public void createTable() {
        dbHelper.getWritableDatabase().execSQL(getCreationQuery());
    }

    public void createTable(SQLiteDatabase db) {
        db.execSQL(getCreationQuery());
    }

    protected String getKeyColumn() {
        return key_column;
    }

    protected void setKeyColumn(String key_column) {
        this.key_column = key_column;
    }

    protected String getTableName() {
        return table_name;
    }

    public Table(SQLiteOpenHelper dbHelper, String table_name) {
        this.dbHelper = dbHelper;
        this.table_name = table_name;

        setColumns(columns);
    }

    public boolean delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(getTableName(), getKeyColumn() + "=" + id, null) > 0;
    }

    public void dropTable() {
        dbHelper.getWritableDatabase().execSQL("DROP TABLE " + getTableName());
    }

    public T get(int id) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.query(getTableName(), getColumns(), getKeyColumn() + "=" + Integer.toString(id), null, null, null, null);

        c.moveToFirst();

        if (c.getCount() != 1) return null;
        T p = CursorToObject(c);
        c.close();
        return p;
    }

    public ArrayList<T> getAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.query(getTableName(), getColumns(), null, null, null, null, null);

        ArrayList<T> t = new ArrayList<>(c.getCount());
        c.moveToFirst();

        int len = c.getCount();
        for (int i = 0; i < len; i++) {
            t.add(CursorToObject(c));
            c.moveToNext();
        }

        c.close();
        return t;
    }

    public String getCreationQuery() {
        ArrayList<String> params = new ArrayList<>();
        String params_string = "";
        for (String key : columns.keySet()) {
            params.add(String.format(COLUMNS_TEMPLATE, key, columns.get(key)));
        }

        int len = params.size() - 1;
        for (int i = 0; i < len; i++) {
            params_string += params.get(i) + ",";
        }
        params_string += params.get(params.size() - 1);

        return String.format(CREATE_QUERY, table_name, params_string);
    }

    protected final String[] getColumns() {
        String[] list = new String[columns.size()];
        columns.keySet().toArray(list);
        return list;
    }

    protected abstract void setColumns(HashMap<String, String> columns);

    public abstract boolean create(T object);

    public abstract boolean update(T object);

    protected abstract T CursorToObject(Cursor cursor);
}