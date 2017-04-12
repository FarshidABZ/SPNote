package com.farshidabz.supernote.interactor.dbhandler;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.farshidabz.supernote.model.FolderModel;

import java.util.HashMap;

/**
 * Created by FarshidAbz.
 * Since 4/12/2017.
 */

public class FolderTable extends Table<FolderModel> {

    public FolderTable(SQLiteOpenHelper helper) {
        super(helper, "folders");
        setKeyColumn(COLUMN_ID);
    }

    private final String COLUMN_ID = "id";
    private final String COLUMN_TITLE = "title";
    private final String COLUMN_PATH = "path";

    @Override
    protected void setColumns(HashMap<String, String> columns) {
        columns.put(COLUMN_ID, "int primary key");
        columns.put(COLUMN_TITLE, "varchar(60)");
        columns.put(COLUMN_PATH, "int");
    }

    @Override
    public boolean update(FolderModel folderModel) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, folderModel.getTitle());

        return db.update(getTableName(), values, COLUMN_ID + "=" + folderModel.getId(), null) > 0;
    }

    @Override
    public boolean create(FolderModel folderModel) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, folderModel.getId());
        values.put(COLUMN_TITLE, folderModel.getTitle());

        return (db.insert(getTableName(), null, values) > 0);
    }

    @Override
    protected FolderModel CursorToObject(Cursor c) {
        FolderModel folderModel = new FolderModel();
        folderModel.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
        folderModel.setTitle(c.getString(c.getColumnIndex(COLUMN_TITLE)));
        return folderModel;
    }
}
