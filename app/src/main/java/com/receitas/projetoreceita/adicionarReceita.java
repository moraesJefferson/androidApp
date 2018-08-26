package com.receitas.projetoreceita;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.receitas.projetoreceita.dao.PessoaDao;
import com.receitas.projetoreceita.modelo.Pessoa;

public class adicionarReceita extends AppCompatActivity {

    private FormularioHelper helper;
    private AlertDialog.Builder dialog;
    private Pessoa pessoa;
    private PessoaDao pessoaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_receita);

        helper = new FormularioHelper(this);
        pessoaDao = new PessoaDao(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario_proseguir, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nextId:
                retornarDados();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void retornarDados() {

        pessoa = helper.pegaDados();

        if (pessoa.getNome().isEmpty() || pessoa.getServico().isEmpty() || pessoa.getCpf().isEmpty() || pessoa.getTelefone().isEmpty()) {
            Toast.makeText(getApplicationContext(), "PREENCHA TODOS OS CAMPOS", Toast.LENGTH_LONG).show();
        } else {

            dialog = new AlertDialog.Builder(adicionarReceita.this);
            dialog.setTitle("Adicionar Serviço");
            dialog.setMessage("Confirmar?");
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.ic_check_box_black_24dp);

            dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            dialog.setPositiveButton("Prosseguir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    pessoaDao.insere(pessoa);
                    pessoaDao.close();
                    Toast.makeText(getApplicationContext(), "Salvando dados", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            dialog.create();
            dialog.show();
        }
    }
}