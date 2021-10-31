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

import com.google.android.material.tabs.TabLayout;

import java.math.BigDecimal;

import dev.nesi.calculators.Airfare.AirfareCalculationService;
import dev.nesi.calculators.Lodging.LodgingCalculationService;
import dev.nesi.models.Lodging;

public class TabHospedagem extends Fragment {
    private SeekBar seekBarCustoMedioHospedagem;
    private EditText etQuantidadeNoite, etQuantidadeQuarto;
    private TextView tvTotalGastoHospedagem, tvCustoMedioHospedagem;
    public static Lodging lodging;
    public TabHospedagem() {
    }

    public static TabHospedagem newInstance() {
        TabHospedagem fragment = new TabHospedagem();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lodging = new Lodging();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_hospedagem, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        seekBarCustoMedioHospedagem = view.findViewById(R.id.seekBarCustoMedioHospedagem);

        etQuantidadeNoite = view.findViewById(R.id.etQuantidadeNoite);
        etQuantidadeQuarto = view.findViewById(R.id.etQuantidadeQuarto);
        tvCustoMedioHospedagem = view.findViewById(R.id.tvCustoMedioHospedagem);
        tvTotalGastoHospedagem = view.findViewById(R.id.tvTotalGastoHospedagem);


        etQuantidadeNoite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty() || Integer.parseInt(s.toString()) == 0){
                    lodging.setNumberOfDays(1);
                } else {
                    lodging.setNumberOfDays(Integer.valueOf(s.toString()));
                }
                updateValorFinal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etQuantidadeQuarto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty() || Integer.parseInt(s.toString()) == 0){

                    lodging.setNumberOfRooms(1);
                } else {
                    lodging.setNumberOfRooms(Integer.valueOf(s.toString()));
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
        seekBarCustoMedioHospedagem.setMax(150000);
        seekBarCustoMedioHospedagem.setMin(0);

        seekBarCustoMedioHospedagem.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvCustoMedioHospedagem.setText("R$ " + ((float) progress) /100);
                lodging.setAverageCost((double) progress/100);
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
        tvTotalGastoHospedagem.setText("R$ "+ calculaHospedagem());
        TabGeral.calculaTotal();
    }

    public static BigDecimal calculaHospedagem() {
        if(lodging == null || lodging.getNumberOfDays() == 0 || lodging.getNumberOfRooms() == 0){
            return BigDecimal.ZERO;
        }
        return new LodgingCalculationService(lodging).calculate();
    }

}