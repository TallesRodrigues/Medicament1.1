package com.example.tallesrodrigues.medicament11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by TallesRodrigues on 7/26/2016.
 */
public class DatabaseController {
    private SQLiteDatabase db;
    private CreateDatabase database;

    public DatabaseController(Context context) {
        database = new CreateDatabase(context);
    }


    public String insertData(int id_Consulta, String medicamento, String concentracao, int dosagem, String dosagem_tipo, int turno_matutino,
                             int turno_vespertino, int turno_noturno, int periodo, String periodo_tipo, int duracao, String duracao_tipo, String status, String obs) {
        ContentValues values;
        long result;

        db = database.getWritableDatabase();
        values = new ContentValues();
        values.put(CreateDatabase.Id_Consulta, id_Consulta);
        values.put(CreateDatabase.Medicamento, medicamento);
        values.put(CreateDatabase.Concentracao, concentracao);
        values.put(CreateDatabase.Dosagem, dosagem);
        values.put(CreateDatabase.Dosagem_tipo, dosagem_tipo);
        values.put(CreateDatabase.Turno_matutino, turno_matutino);
        values.put(CreateDatabase.Turno_vespertino, turno_vespertino);
        values.put(CreateDatabase.Turno_noturno, turno_noturno);
        values.put(CreateDatabase.Periodo, periodo);
        values.put(CreateDatabase.Periodo_tipo, periodo_tipo);
        values.put(CreateDatabase.Duracao, duracao);
        values.put(CreateDatabase.Duracao_tipo, duracao_tipo);
        values.put(CreateDatabase.Status, status);
        values.put(CreateDatabase.Obs, obs);

        result = db.insert(CreateDatabase.TABELA, null, values);
        db.close();

        if (result == -1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";

    }

    public String insertData(String login, String password) {
        ContentValues values;
        long result;

        db = database.getWritableDatabase();
        values = new ContentValues();
        values.put(CreateDatabase.Email, login);
        values.put(CreateDatabase.Password, password);

        result = db.insert(CreateDatabase.LOGTABLE, null, values);
        db.close();

        if (result == -1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";

    }

    public Cursor loadData() {
        Cursor cursor;

        String[] columns = {"id as _id", "id_consulta", "medicamento", "concentracao", "dosagem", "dosagem_tipo", "turno_matutino", "turno_vespertino", "turno_noturno", "periodo", "periodo_tipo", "duracao", "duracao_tipo", "status", "obs"};
        db = database.getReadableDatabase();
        cursor = db.query("medicinetable", columns, null, null, null, null, null, null);

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            Log.e("Cursor not zero", "Aew carai");
        }
        db.close();
        return cursor;
    }

    public Cursor loadLog() {
        Cursor cursor;

        String[] columns = {"email", "password"};
        db = database.getReadableDatabase();
        cursor = db.query(CreateDatabase.LOGTABLE, columns, null, null, null, null, null, null);

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            Log.e("Cursor not zero", "Aew carai Log");
        }
        db.close();
        return cursor;
    }

    public void deleteAll(String tablename) {
        SQLiteDatabase db = database.getWritableDatabase();
        String delete = "DELETE FROM " + tablename;

        db = database.getWritableDatabase();

        try {
            db.rawQuery(delete, null);
        } catch (SQLiteAccessPermException a) {
            a.printStackTrace();
        }
    }
}
