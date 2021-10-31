package dev.nesi;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.math.BigDecimal;

import dev.nesi.calculators.Gasoline.GasolineCalculationService;
import dev.nesi.models.Gasoline;

public class TabGasolina extends Fragment {
    public static Gasoline gasoline;
    private SeekBar seekBarQuilometrosTotais, seekBarMediaLitro, seekBarCustoMedioLitro;
    private TextView tvQuilometrosTotais, tvMediaLitro, tvCustoMedioLitro, tvTotalGastoTransporte;
    private EditText etQuantidadeVeiculos;
    public TabGasolina() {

    }
    public static TabGasolina newInstance() {
        TabGasolina fragment = new TabGasolina();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gasoline = new Gasoline();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tab_gasolina, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvQuilometrosTotais = view.findViewById(R.id.tvQuilometrosTotais);
        tvMediaLitro = view.findViewById(R.id.tvMediaLitro);
        tvCustoMedioLitro = view.findViewById(R.id.tvCustoMedioLitro);
        tvTotalGastoTransporte = view.findViewById(R.id.tvTotalGastoTransporte);

        seekBarQuilometrosTotais = view.findViewById(R.id.seekBarQuilometrosTotais);
        seekBarMediaLitro = view.findViewById(R.id.seekBarMediaLitro);
        seekBarCustoMedioLitro = view.findViewById(R.id.seekBarCustoMedioLitro);

        etQuantidadeVeiculos = view.findViewById(R.id.etQuantidadeVeiculos);

        etQuantidadeVeiculos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()){
                    gasoline.setNumberOfVehicles(1D);
                } else {
                    gasoline.setNumberOfVehicles(Double.valueOf(s.toString()));
                }
                updateValorFinal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        seekBars();
    }

    @SuppressLint("NewApi")
    public void seekBars(){
        seekBarQuilometrosTotais.setMax(10000);
        seekBarQuilometrosTotais.setMin(0);

        seekBarMediaLitro.setMin(0);
        seekBarMediaLitro.setMax(40);

        seekBarCustoMedioLitro.setMin(10);
        seekBarCustoMedioLitro.setMax(1500);

        seekBarQuilometrosTotais.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvQuilometrosTotais.setText(progress +  " Km");
                gasoline.setNumberOfKm((double) progress);
                updateValorFinal();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        seekBarMediaLitro.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvMediaLitro.setText(progress + " Km");
                gasoline.setKmPerLiter((double) progress);
                updateValorFinal();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seekBarCustoMedioLitro.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvCustoMedioLitro.setText("R$ " + ((float) progress) /100);
                gasoline.setLiterCost((double) progress/100);
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
        tvTotalGastoTransporte.setText("R$ "+ calculaGasolina());
        TabGeral.calculaTotal();
    }

    public static BigDecimal calculaGasolina() {
        if(gasoline == null || gasoline.getNumberOfKm() == 0 || gasoline.getKmPerLiter() == 0 || gasoline.getLiterCost() == 0 || gasoline.getNumberOfVehicles() == 0){
            return BigDecimal.ZERO;
        }

        return new GasolineCalculationService(gasoline).calculate();
    }
}