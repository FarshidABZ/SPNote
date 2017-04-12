package com.farshidabz.supernote.interactor.dbhandler;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.farshidabz.supernote.model.NoteModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by FarshidAbz.
 * Since 4/12/2017.
 */

public class NotesTable extends Table<NoteModel> {
    public NotesTable(SQLiteOpenHelper helper) {
        super(helper, "notes");
        setKeyColumn(COLUMN_ID);
    }

    private final String COLUMN_ID = "id";
    private final String COLUMN_TITLE = "title";
    private final String COLUMN_CONTENT = "content";
    private final String COLUMN_PATH = "path";
    private final String COLUMN_FOLDER_ID = "folder_id";

    @Override
    protected void setColumns(HashMap<String, String> columns) {
        columns.put(COLUMN_ID, "int primary key");
        columns.put(COLUMN_TITLE, "varchar(60)");
        columns.put(COLUMN_CONTENT, "text");
        columns.put(COLUMN_PATH, "text");
    }

    @Override
    public boolean update(NoteModel noteModel) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, noteModel.getTitle());
        values.put(COLUMN_CONTENT, noteModel.getContent());

        return db.update(getTableName(), values, COLUMN_ID + "=" + noteModel.getId(), null) > 0;
    }

    @Override
    public boolean create(NoteModel noteModel) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, noteModel.getId());
        values.put(COLUMN_TITLE, noteModel.getTitle());
        values.put(COLUMN_CONTENT, noteModel.getContent());

        return (db.insert(getTableName(), null, values) > 0);
    }

    @Override
    protected NoteModel CursorToObject(Cursor c) {
        NoteModel noteModel = new NoteModel();
        noteModel.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
        noteModel.setTitle(c.getString(c.getColumnIndex(COLUMN_TITLE)));
        noteModel.setContent(c.getString(c.getColumnIndex(COLUMN_CONTENT)));
        return noteModel;
    }

    public ArrayList<NoteModel> getNotesOfFolder(int folderId) {
        return getNotes(String.valueOf(folderId));
    }

    public ArrayList<NoteModel> getNotes() {
        return getNotes("");
    }

    private ArrayList<NoteModel> getNotes(String folderId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = null;
        if (!TextUtils.isEmpty(folderId))
            selection = COLUMN_FOLDER_ID + "=" + folderId;

        Cursor c = db.query(getTableName(), getColumns(), selection, null, null, null, null);

        ArrayList<NoteModel> noteModels = new ArrayList<>(c.getCount());

        c.moveToFirst();
        while (!c.isAfterLast()) {
            noteModels.add(CursorToObject(c));
            c.moveToNext();
        }

        c.close();

        return noteModels;
    }

    public boolean deleteNotesOfFolder(int folderId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(getTableName(), COLUMN_FOLDER_ID + "=" + folderId, null) > 0;
    }
}
