package br.com.senaijandira.fintechs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class VisualizarActivity extends AppCompatActivity {

    Integer idDespesa;

    TextView spinner_conta;
    TextView spinner_categoria;
    TextView txt_descricao;
    TextView txt_valor;
    TextView txt_data;
    TextView txt_parcela;


    Despesa despesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar);

        txt_descricao = (TextView) findViewById(R.id.txt_descricao_visualizar);
        txt_valor = (TextView) findViewById(R.id.txt_valor_visualizar);
        txt_data = (TextView) findViewById(R.id.txt_data_visualizar);
        txt_parcela = (TextView) findViewById(R.id.txt_parcela_visualizar);
        spinner_categoria = (TextView) findViewById(R.id.txt_categoria_visualizar);
        spinner_conta = (TextView) findViewById(R.id.txt_conta_visualizar);


        //acessar o ID passado por Intent
        Intent intent = getIntent();
        idDespesa = intent.getIntExtra("idDespesa", 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //selecionar despesa do banco
        despesa = DespesaDAO.getInstance().selectionarUm(this, idDespesa);

        txt_descricao.setText(despesa.getDescricao());

        Locale ptBr = new Locale("pt","BR");
        NumberFormat nf = NumberFormat.getCurrencyInstance(ptBr);
        txt_valor.setText(nf.format(despesa.getValor()));

        String data = new SimpleDateFormat("dd/MM/yyyy").format(despesa.getData());
        txt_data.setText(data);
        txt_parcela.setText(despesa.getParcela());
        spinner_categoria.setText(despesa.getCategoria());
        spinner_conta.setText(despesa.getConta());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_visualizar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menu_excluir){
            //excluir

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Excluir");
            builder.setMessage("Tem certeza que deseja excluir essa despesa?");

            final Context context = this;
            builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DespesaDAO.getInstance().remover(context,idDespesa);
                    finish();
                }
            });

            builder.setNegativeButton("NÃO", null);
            builder.create().show();


        }

        if(item.getItemId() == R.id.menu_editar){

            Intent intent = new Intent(this, CadastroActivity.class);
            intent.putExtra("idDespesa", idDespesa);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

}
