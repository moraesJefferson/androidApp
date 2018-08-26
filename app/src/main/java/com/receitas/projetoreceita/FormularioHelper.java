package com.receitas.projetoreceita;

import android.content.Intent;
import android.widget.EditText;

import com.receitas.projetoreceita.modelo.Pessoa;

public class FormularioHelper {

    private EditText nomeId;
    private EditText cpfId;
    private EditText servicoId;
    private EditText foneId;

    public FormularioHelper(adicionarReceita activity){

        nomeId = activity.findViewById(R.id.nomeId);
        cpfId = activity.findViewById(R.id.cpfId);
        servicoId = activity.findViewById(R.id.servicoId);
        foneId = activity.findViewById(R.id.foneId);
    }

    public Pessoa pegaDados(){

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nomeId.getText().toString());
        pessoa.setCpf(cpfId.getText().toString());
        pessoa.setServico(servicoId.getText().toString());
        pessoa.setTelefone(foneId.getText().toString());
        return pessoa;
    }
}
