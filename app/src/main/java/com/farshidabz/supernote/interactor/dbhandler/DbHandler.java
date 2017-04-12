package com.farshidabz.supernote.interactor.dbhandler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by FarshidAbz.
 * Since 4/12/2017.
 */

public class DbHandler extends SQLiteOpenHelper {
    private NotesTable notesTable = new NotesTable(this);
    private FolderTable folderTable = new FolderTable(this);

    public DbHandler(Context context) {
        super(context, "super_note", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        notesTable.createTable(db);
        folderTable.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public NotesTable getNotesTable() {
        return notesTable;
    }

    public FolderTable getFolderTable() {
        return folderTable;
    }
}
