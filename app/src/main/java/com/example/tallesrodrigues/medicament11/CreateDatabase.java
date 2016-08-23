
package com.example.tallesrodrigues.medicament11;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by allanromanato on 5/27/15.
 */
public class CreateDatabase extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "medicament.db";
    private static final String ID = "_id";


    private static final int VERSION = 1;
    public static final String TABELA = "medicinetable";
    public static final String LOGTABLE = "logtable";
    public static final String Id_Consulta = "id_Consulta";
    public static final String Medicamento = "medicamento";
    public static final String Concentracao = "concentracao";
    public static final String Dosagem = "dosagem";
    public static final String Dosagem_tipo = "dosagem_tipo";
    public static final String Turno_matutino = "turno_matutino";
    public static final String Turno_vespertino = "turno_vespertino";
    public static final String Turno_noturno = "turno_noturno";
    public static final String Periodo = "periodo";
    public static final String Periodo_tipo = "periodo_tipo";
    public static final String Duracao = "duracao";
    public static final String Duracao_tipo = "duracao_tipo";
    public static final String Status = "status";
    public static final String Obs = "obs";
    public static final String Email = "email";
    public static final String Password = "password";


    public CreateDatabase(Context context) {
        super(context, NOME_BANCO, null, VERSION);

    }

    public CreateDatabase(Context context, int versao) {
        super(context, NOME_BANCO, null, versao);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ////String medicamento,String concentracao,String duracao_t,int dosagem, int period,int id_image
        String sql = "CREATE TABLE IF NOT EXISTS \"medicinetable\"(\n" +
                "  \"id\" INTEGER PRIMARY KEY NOT NULL,\n" +
                "  \"id_Consulta\" INTEGER,\n" +
                "  \"medicamento\" TEXT NOT NULL,\n" +
                "  \"concentracao\" TEXT NOT NULL,\n" +
                "  \"dosagem\" REAL NOT NULL,\n" +
                "  \"dosagem_tipo\" TEXT,\n" +
                "  \"turno_matutino\" INTEGER,\n" +
                "  \"turno_vespertino\" INTEGER ,\n" +
                "  \"turno_noturno\" INTEGER ,\n" +
                "  \"periodo\" INTEGER ,\n" +
                "  \"periodo_tipo\" TEXT,\n" +
                "  \"duracao\" INTEGER,\n" +
                "  \"duracao_tipo\" TEXT,\n" +
                "  \"status\" TEXT,\n" +
                "  \"obs\" TEXT\n" +
                ");";
        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS \"logtable\"(\n" +
                "  \"email\" TEXT NOT NULL,\n" +
                "  \"password\" TEXT NOT NULL\n" +
                ");";
        db.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABELA);
        onCreate(db);
    }
}