package br.com.senaijandira.fintechs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by sn1041520 on 21/02/2018.
 */

public class ReceitaAdapter extends ArrayAdapter<Despesa> {


    public ReceitaAdapter(Context ctx, ArrayList<Despesa> lst) {
        super(ctx, 0, lst);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        View v = convertView;

        if (v == null) {

            v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item, null);
        }

        //pegar o contato que esta sendo montado
        Despesa item = getItem(position);

        TextView txt_item_descricao = v.findViewById(R.id.txt_item_descricao);
        TextView txt_item_categoria = v.findViewById(R.id.txt_item_categoria);


        txt_item_descricao.setText(item.getDescricao());
        txt_item_categoria.setText(item.getCategoria());


        return v;
    }
}