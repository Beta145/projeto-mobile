package br.com.senaijandira.fintechs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

public class ReceitaDAO {

    //Arraylist para guardar os contatos, tipo um banco Fake
    //ArrayList<Contato> lstContato = new ArrayList<>();

    private Integer idDespesa = 0;
    private int id;

    //Esquema de classe Singleton
    private static ReceitaDAO instance;

    public static ReceitaDAO getInstance(){

        if(instance == null) {
            instance = new ReceitaDAO();

        }

        return instance;
    }

    public Boolean inserir(Context context, Despesa receita){

        //acessar o banco em modo escrita
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("descricao",receita.getDescricao());
        valores.put("valor",receita.getValor());
        valores.put("data",receita.getData().toString());
        valores.put("conta",receita.getConta());
        valores.put("categoria",receita.getCategoria());


        //TODO:Arrumar data de nascimento e foto
        //valores.put("dt_nascimento",contato.getDt_nascimento());

        //inserindo os valores na tabela (nome da tabela, coluna que recebera
        //valor nulo(caso tenha valor nulo, valores)
        Long id = db.insert("tbl_fintechs", null, valores);

        if (id != -1){
            return true;
        }else{
            return false;
        }


    }

    public ArrayList<Despesa> selecionarTodos(Context context){

        ArrayList<Despesa> retorno = new ArrayList<>();

        //Acessando o Banco no modo leitura
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "select * from tbl_fintechs";

        //Execultando no banco (comando e os null(whare caso tenha) e retornando o resultado
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()){

            Despesa receita = new Despesa();
            receita.setId(cursor.getInt(0));
            receita.setDescricao(cursor.getString(1));
            receita.setValor(cursor.getFloat(2));
            //despesa.setData(cursor.getString(3));

            receita.setConta(cursor.getString(4));
            receita.setCategoria(cursor.getString(5));

            //TODO: Arrumar dt nascimento
            receita.setData(new Date());

            retorno.add(receita);


        }

        return retorno;

    }


    public Despesa selectionarUm(Context context,int id){

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
        String sql = "select * from tbl_fintechs where idDespesa = " +id;

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            Despesa receita = new Despesa();
            receita.setId(cursor.getInt(0));
            receita.setDescricao(cursor.getString(1));
            receita.setValor(cursor.getFloat(2));
            //despesa.setData(cursor.getString(3));
            receita.setConta(cursor.getString(3));
            receita.setCategoria(cursor.getString(4));

            receita.setData (new Date());//Arrumar data

            cursor.close();
            return receita;
        }

        return null;
    }

    public Boolean remover(Context context, Integer id){

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        db.delete("tbl_fintechs", "idDespesa = ?", new String[]{id.toString()});

        return true;
    }

    public Boolean atualizar(Context context, Despesa receita){

        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("descricao",receita.getDescricao());
        valores.put("valor",receita.getValor());
        valores.put("data",receita.getData().toString());
        valores.put("conta",receita.getConta());
        valores.put("categoria",receita.getCategoria());

        //TODO:Arrumar data de nascimento e foto
        //valores.put("data",despesa.getData().toString());




        db.update("tbl_fintechs", valores, "idDespesa = ?", new String[]{receita.getId().toString()});

        return true;

    }

}
