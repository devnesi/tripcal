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

import java.math.BigDecimal;

import dev.nesi.calculators.Airfare.AirfareCalculationService;

public class TabDiversos extends Fragment {
    private EditText etDiversos;
    public static double total_diversos = 0D;

    public TabDiversos() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etDiversos = view.findViewById(R.id.etDiversos);
        etDiversos.setText(String.valueOf(total_diversos));

        etDiversos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty() || Double.valueOf(s.toString()).intValue() == 0){
                    total_diversos = 0D;
                } else {
                    total_diversos = Math.abs(Double.parseDouble(s.toString())) ;
                }
                calculaDiversos();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_diversos, container, false);
    }

    public static BigDecimal calculaDiversos() {
        return new BigDecimal(total_diversos);
    }
}