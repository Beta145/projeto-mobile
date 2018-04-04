package br.com.senaijandira.fintechs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CadastroReceitaActivity extends AppCompatActivity {

    EditText txt_valor;
    EditText txt_data;
    EditText txt_descricao;

    Spinner spinner_conta;
    Spinner spinner_categoria;

    Despesa receita;
    Boolean modoEdicao = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_receita);

        txt_descricao = (EditText) findViewById(R.id.txt_descricao);
        txt_valor = (EditText) findViewById(R.id.txt_valor);
        txt_data = (EditText) findViewById(R.id.txt_data);

        Add_itens_categoria();
        Add_itens_conta();
        //addListenerOnSpinnerItemSelection();

        Integer idReceita = getIntent().getIntExtra("idReceita", 0);

        if (idReceita != 0) {
            //edição
            modoEdicao = true;
            receita = ReceitaDAO.getInstance().selectionarUm(this, idReceita);

            txt_descricao.setText(receita.getDescricao());
            Locale ptBr = new Locale("pt", "BR");
            NumberFormat nf = NumberFormat.getCurrencyInstance(ptBr);


            txt_valor.setText(nf.format(receita.getValor()));
            txt_data.setText(new SimpleDateFormat("dd/MM/yyyy")
                    .format(receita.getData()));

        }
    }

    public void salvar(View v){
        //verificar se o nome não esta vazio
        if(txt_descricao.getText().toString().isEmpty()){
            txt_descricao.setError("Preencha a descrição");
            return;
        }

        Despesa d;

        if( modoEdicao) {
            d = receita;
        }else {
            d = new Despesa();
        }

        d.setDescricao(txt_descricao.getText().toString());
        float number = Float.valueOf(txt_valor.getText().toString().replace(",",".").replace("R$", ""));
        d.setValor(number);

        d.setCategoria(spinner_categoria.getSelectedItem().toString());
        d.setConta(spinner_conta.getSelectedItem().toString());


        String dt_despesa = txt_data.getText().toString();

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        try {
            //formatar a data
            Date dt = df.parse(dt_despesa);

            //definindo a dt de nascimento
            d.setData(dt);

        }catch (Exception ex){

            txt_data.setError("Preencha uma data correta.");
            return;
        }


        if(modoEdicao){

            ReceitaDAO.getInstance().atualizar(this, d);
            //msg de atualizado sucesso
            Toast.makeText(this, "Atualizado com sucesso",
                    Toast.LENGTH_SHORT).show();
        }else {
            //inserir no banco de dados
            ReceitaDAO.getInstance().inserir(this, d);

            //msg de inserido sucesso
            Toast.makeText(this, "Inserido com sucesso",
                    Toast.LENGTH_SHORT).show();
        }

        //Analogo ao System.out
        //Log.d("Idcontato", c.getId().toString());

        //finalizar a tela
        finish();

    }

    public void Add_itens_conta() {
        spinner_conta = (Spinner) findViewById(R.id.spinner_conta_receita);

        List<String> list = new ArrayList<String>();
        list.add("Bradesco");
        list.add("Itau");
        list.add("Caixa");
        list.add("Banco do Brasil");
        list.add("Conta Bancaria");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_conta.setAdapter(dataAdapter);
    }


    public void Add_itens_categoria() {
        spinner_categoria = (Spinner) findViewById(R.id.spinner_categoria_receita);

        List<String> list = new ArrayList<String>();
        list.add("Aluguel");
        list.add("Venda");
        list.add("Salario");
        list.add("Bico");
        list.add("Outros");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_categoria.setAdapter(dataAdapter);
    }
}
