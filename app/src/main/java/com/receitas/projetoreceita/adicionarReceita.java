package com.receitas.projetoreceita;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

public class adicionarReceita extends AppCompatActivity {

    private EditText nomeId;
    private EditText cpfId;
    private EditText servicoId;
    private EditText foneId;
    private Button salvarId;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_receita);

        nomeId = findViewById(R.id.nomeId);
        cpfId = findViewById(R.id.cpfId);
        servicoId = findViewById(R.id.servicoId);
        foneId = findViewById(R.id.foneId);
        salvarId = findViewById(R.id.salvarId);

        salvarId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String nome = nomeId.getText().toString();
                final String servico = servicoId.getText().toString();
                final String cpf = cpfId.getText().toString();
                final String fone = foneId.getText().toString();

                final String servicos = " "+servico;

                if (nome.isEmpty() || servico.isEmpty() || cpf.isEmpty() || fone.isEmpty()){
                    Toast.makeText(getApplicationContext(),"PREENCHA TODOS OS CAMPOS",Toast.LENGTH_LONG).show();
                }else {

                    dialog = new AlertDialog.Builder(adicionarReceita.this);
                    dialog.setTitle("Adicionar Serviço");
                    dialog.setMessage("adicionar serviço?");
                    dialog.setCancelable(false);
                    dialog.setIcon(R.drawable.ic_check_box_black_24dp);
                    dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    dialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent intent = new Intent(adicionarReceita.this,MainActivity.class);
                            intent.putExtra("servicos",servicos);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),"Salvando dados",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
            }
        });
    }
}
