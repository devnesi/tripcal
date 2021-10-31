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

import dev.nesi.calculators.Airfare.AirfareCalculationService;
import dev.nesi.calculators.Meals.MealsCalculationService;
import dev.nesi.models.Meals;

public class TabRefeicoes extends Fragment {
    private TextView tvCustoRefeicao, tvTotalGastoRefeicao;
    private EditText etRefeicaoPorDia;
    private SeekBar seekBarCustoRefeicao;
    public static Meals meals;

    public TabRefeicoes() {
    }

    public static TabRefeicoes newInstance() {
        TabRefeicoes fragment = new TabRefeicoes();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        meals = new Meals();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_refeicoes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        seekBarCustoRefeicao = view.findViewById(R.id.seekBarCustoRefeicao);

        etRefeicaoPorDia = view.findViewById(R.id.etRefeicaoPorDia);
        tvCustoRefeicao = view.findViewById(R.id.tvCustoRefeicao);
        tvTotalGastoRefeicao = view.findViewById(R.id.tvTotalGastoRefeicao);

        etRefeicaoPorDia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty() || Integer.parseInt(s.toString()) == 0){
                    meals.setMealsPerDay(1);
                } else {
                    meals.setMealsPerDay(Integer.valueOf(s.toString()));
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
    private void seekBars(){
        seekBarCustoRefeicao.setMax(150000);
        seekBarCustoRefeicao.setMin(0);

        seekBarCustoRefeicao.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvCustoRefeicao.setText("R$ " + ((float) progress) /100);
                meals.setMealEstimatedValue((double) progress/100);
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
        tvTotalGastoRefeicao.setText("R$ "+ calculaRefeicoes());
        TabGeral.calculaTotal();
    }

    public static BigDecimal calculaRefeicoes() {
        if(meals == null || meals.getMealEstimatedValue() == 0 || meals.getMealsPerDay() == 0){
            return BigDecimal.ZERO;
        }

        meals.setNumberOfPeople(TabGeral.viagem.getTravelers());
        meals.setNumberOfDays(TabGeral.viagem.getDiferenceDates());

        return new MealsCalculationService(meals).calculate();
    }

}