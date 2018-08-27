package com.receitas.projetoreceita;

import android.content.Intent;
import android.widget.EditText;

import com.receitas.projetoreceita.modelo.Pessoa;

public class FormularioHelper {

    private EditText nomeId;
    private EditText cpfId;
    private EditText servicoId;
    private EditText foneId;
    private EditText enderecoId;
    private Pessoa pessoa;

    public FormularioHelper(adicionarReceita activity) {

        nomeId = activity.findViewById(R.id.nomeId);
        cpfId = activity.findViewById(R.id.cpfId);
        servicoId = activity.findViewById(R.id.servicoId);
        foneId = activity.findViewById(R.id.foneId);
        enderecoId = activity.findViewById(R.id.enderecoId);
        pessoa = new Pessoa();
    }

    public Pessoa pegaDados() {
        pessoa.setNome(nomeId.getText().toString());
        pessoa.setCpf(cpfId.getText().toString());
        pessoa.setServico(servicoId.getText().toString());
        pessoa.setTelefone(foneId.getText().toString());
        pessoa.setEndereco(enderecoId.getText().toString());
        return pessoa;
    }

    public void preencheFormulario(Pessoa pessoa) {

        nomeId.setText(pessoa.getNome());
        cpfId.setText(pessoa.getCpf());
        servicoId.setText(pessoa.getServico());
        foneId.setText(pessoa.getTelefone());
        enderecoId.setText(pessoa.getEndereco());
        this.pessoa = pessoa;
    }
}
