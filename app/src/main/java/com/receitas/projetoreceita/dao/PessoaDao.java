package com.receitas.projetoreceita.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.receitas.projetoreceita.modelo.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class PessoaDao extends SQLiteOpenHelper {

    public PessoaDao(Context context) {
        super(context, "Servicos", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS servicos (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, cpf INT, servicos TEXT, fone TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insere(Pessoa pessoa) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put("nome", pessoa.getNome());
        dados.put("cpf", pessoa.getCpf());
        dados.put("servicos", pessoa.getServico());
        dados.put("fone", pessoa.getTelefone());

        db.insert("servicos", null, dados);
    }

    public List<Pessoa> buscar() {

        String sql = "SELECT * FROM servicos ORDER BY id DESC;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Pessoa> pessoas = new ArrayList<Pessoa>();

        while (cursor.moveToNext()) {
            Pessoa pessoa = new Pessoa();
            pessoa.setServico(cursor.getString(cursor.getColumnIndex("servicos")));
            pessoa.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            pessoa.setCpf(cursor.getString(cursor.getColumnIndex("cpf")));
            pessoa.setTelefone(cursor.getString(cursor.getColumnIndex("fone")));
            pessoas.add(pessoa);
        }
        cursor.close();
        return pessoas;
        //comenta
    }
}
