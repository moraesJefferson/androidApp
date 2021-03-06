package com.receitas.projetoreceita.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.receitas.projetoreceita.modelo.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class PessoaDao extends SQLiteOpenHelper {

    public PessoaDao(Context context) {
        super(context, "Servicos", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS servicos (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, cpf INT, servicos TEXT, fone TEXT,endereco TEXT,foto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion){
            case 1:
                sql = "ALTER TABLE servicos ADD COLUMN caminhoFoto TEXT";
                db.execSQL(sql);
                break;
        }

    }

    public void insere(Pessoa pessoa) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosdaPessoa(pessoa);

        db.insert("servicos", null, dados);
    }

    @NonNull
    private ContentValues pegaDadosdaPessoa(Pessoa pessoa) {
        ContentValues dados = new ContentValues();
        dados.put("nome", pessoa.getNome());
        dados.put("cpf", pessoa.getCpf());
        dados.put("servicos", pessoa.getServico());
        dados.put("fone", pessoa.getTelefone());
        dados.put("endereco",pessoa.getEndereco());
        dados.put("foto",pessoa.getFoto());
        return dados;
    }

    public List<Pessoa> buscar() {

        String sql = "SELECT * FROM servicos ORDER BY id;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Pessoa> pessoas = new ArrayList<Pessoa>();

        while (cursor.moveToNext()) {
            Pessoa pessoa = new Pessoa();
            pessoa.setServico(cursor.getString(cursor.getColumnIndex("servicos")));
            pessoa.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            pessoa.setId(cursor.getLong(cursor.getColumnIndex("id")));
            pessoa.setCpf(cursor.getString(cursor.getColumnIndex("cpf")));
            pessoa.setTelefone(cursor.getString(cursor.getColumnIndex("fone")));
            pessoa.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            pessoa.setFoto(cursor.getString(cursor.getColumnIndex("foto")));
            pessoas.add(pessoa);
        }
        cursor.close();
        return pessoas;
    }

    public void deletar(Pessoa pessoa) {

        SQLiteDatabase db = getWritableDatabase();
        String[] parametro = {String.valueOf(pessoa.getId().toString())};
        db.delete("servicos", "id = ? ", parametro);

    }

    public void update(Pessoa pessoa) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosdaPessoa(pessoa);
        String[] parametro = {String.valueOf(pessoa.getId().toString())};
        db.update("servicos", dados, "id = ? ", parametro);
    }
}
