package br.com.senaijandira.fintechs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;


public class DespesaDAO {

    //Arraylist para guardar os contatos, tipo um banco Fake
    //ArrayList<Contato> lstContato = new ArrayList<>();

    private Integer idDespesa = 0;
    private int id;

    //Esquema de classe Singleton
    private static DespesaDAO instance;

    public static DespesaDAO getInstance(){

        if(instance == null) {
            instance = new DespesaDAO();

        }

        return instance;
    }

    public Boolean inserir(Context context, Despesa despesa){

        //acessar o banco em modo escrita
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("descricao",despesa.getDescricao());
        valores.put("valor",despesa.getValor());
        valores.put("data",despesa.getData().toString());
        valores.put("parcela",despesa.getParcela());
        valores.put("conta",despesa.getConta());
        valores.put("categoria",despesa.getCategoria());


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

            Despesa despesa = new Despesa();
            despesa.setId(cursor.getInt(0));
            despesa.setDescricao(cursor.getString(1));
            despesa.setValor(cursor.getFloat(2));
            //despesa.setData(cursor.getString(3));
            despesa.setParcela(cursor.getString(3));
            despesa.setConta(cursor.getString(4));
            despesa.setCategoria(cursor.getString(5));

            //TODO: Arrumar dt nascimento
            despesa.setData(new Date());

            retorno.add(despesa);


        }

        return retorno;

    }


    public Despesa selectionarUm(Context context,int id){

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
        String sql = "select * from tbl_fintechs where idDespesa = " +id;

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            Despesa despesa = new Despesa();
            despesa.setId(cursor.getInt(0));
            despesa.setDescricao(cursor.getString(1));
            despesa.setValor(cursor.getFloat(2));
            //despesa.setData(cursor.getString(3));
            despesa.setParcela(cursor.getString(3));
            despesa.setConta(cursor.getString(4));
            despesa.setCategoria(cursor.getString(5));

            despesa.setData (new Date());//Arrumar data

            cursor.close();
            return despesa;
        }

        return null;
    }

    public Boolean remover(Context context,Integer id){

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        db.delete("tbl_fintechs", "idDespesa = ?", new String[]{id.toString()});

        return true;
    }

    public Boolean atualizar(Context context, Despesa despesa){

        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("descricao",despesa.getDescricao());
        valores.put("valor",despesa.getValor());
        valores.put("data",despesa.getData().toString());
        valores.put("conta",despesa.getConta());
        valores.put("categoria",despesa.getCategoria());

        //TODO:Arrumar data de nascimento e foto
        //valores.put("data",despesa.getData().toString());




        db.update("tbl_fintechs", valores, "idDespesa = ?", new String[]{despesa.getId().toString()});

        return true;

    }

}
