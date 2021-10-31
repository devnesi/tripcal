package dev.nesi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.math.BigDecimal;

import dev.nesi.calculators.Airfare.AirfareCalculationService;
import dev.nesi.calculators.Gasoline.GasolineCalculationService;
import dev.nesi.models.Airfare;
import dev.nesi.models.Gasoline;

public class TabAero extends Fragment {
    public static Airfare airfare;
    private SeekBar seekBarCustoTarifaPessoa, seekBarAluguelVeiculo;
    private TextView tvCustoTarifaPessoa, tvAluguelVeiculo, tvTotalTarifaArea;

    public TabAero() {
    }

    public static TabAero newInstance() {
        TabAero fragment = new TabAero();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        seekBarCustoTarifaPessoa = view.findViewById(R.id.seekBarCustoTarifaPessoa);
        seekBarAluguelVeiculo = view.findViewById(R.id.seekBarAluguelVeiculo);

        tvCustoTarifaPessoa = view.findViewById(R.id.tvCustoTarifaPessoa);
        tvAluguelVeiculo = view.findViewById(R.id.tvAluguelVeiculo);
        tvTotalTarifaArea = view.findViewById(R.id.tvTotalTarifaArea);
        airfare = new Airfare();

        seekBars();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_aero, container, false);
    }

    @SuppressLint("NewApi")
    public void seekBars() {
        seekBarAluguelVeiculo.setMax(150000);
        seekBarAluguelVeiculo.setMin(10);

        seekBarCustoTarifaPessoa.setMax(150000);
        seekBarCustoTarifaPessoa.setMin(0);

        seekBarCustoTarifaPessoa.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvCustoTarifaPessoa.setText("R$ " + ((float) progress) /100);
                airfare.setCostPerPerson((double) progress/100);
                updateValorFinal();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarAluguelVeiculo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvAluguelVeiculo.setText("R$ " + ((float) progress) /100);
                airfare.setVehicleRent((double) progress/100);
                updateValorFinal();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateValorFinal(){
        tvTotalTarifaArea.setText("R$ "+ calculaAero());
        TabGeral.calculaTotal();
    }

    public static BigDecimal calculaAero() {
        if(airfare == null || airfare.getCostPerPerson() == 0 || airfare.getVehicleRent() == 0){
            return BigDecimal.ZERO;
        }
        airfare.setNumberOfPeople(TabGeral.viagem.getTravelers());

        return new AirfareCalculationService(airfare).calculate();
    }

}