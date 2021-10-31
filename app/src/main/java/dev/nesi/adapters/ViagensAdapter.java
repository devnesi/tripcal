package dev.nesi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import dev.nesi.R;
import dev.nesi.models.Viagem;

public class ViagensAdapter extends ArrayAdapter<Viagem> {
    private ArrayList<Viagem> viagems;
    private Context context;

    public ViagensAdapter(Context context, ArrayList<Viagem> objects) {
        super(context,0, objects);
        this.context = context;
        this.viagems = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if(viagems != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_viagem, parent, false);

            final TextView textNomeViagem = (TextView) view.findViewById(R.id.txtNomeViagem);
            final TextView textPartidaEm = (TextView) view.findViewById(R.id.txtFaltam);
            final TextView textParticipantes = (TextView) view.findViewById(R.id.txtParticipantes);
            final TextView textDestino = (TextView) view.findViewById(R.id.txtDestino);
            final TextView textTotal = (TextView) view.findViewById(R.id.txtTotal);

            final Viagem viagem = viagems.get(position);

            long dt = viagem.getDtInit() - System.currentTimeMillis();
            if(dt < 0){
                textPartidaEm.setText("Viagem já realizada :)");

            } else {
                textPartidaEm.setText("Faltam "+ dt / 86400000L + " dias");
            }
            BigDecimal total = viagem.calculaTotal();
            String viajantes = viagem.getTravelers() == 1 ? "viajante cadastrado" : "viajantes cadastrados";

            textParticipantes.setText(viagem.getTravelers()+ " " + viajantes + " para essa viagem\ncada viajante um terá que pagar R$ " + total.divide(new BigDecimal(viagem.getTravelers()), BigDecimal.ROUND_UP));
            textDestino.setText(viagem.getDestiny());
            textTotal.setText("R$ "+ total);
            textNomeViagem.setText(viagem.getName());

        }
        return view;
    }
}
