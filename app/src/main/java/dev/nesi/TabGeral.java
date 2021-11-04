package dev.nesi;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import dev.nesi.models.Viagem;


public class TabGeral extends Fragment {
    private static final Calendar myCalendar = Calendar.getInstance();
    private static final Calendar myCalendar2 = Calendar.getInstance();
    private EditText etDataInicial, etDataFinal, etQuantidadeViajantes, etNomeViagem, etDestino;
    private Context context;
    private Button btnSave;
    private TextView tvTotal;
    public static Viagem viagem;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public TabGeral() {

    }

    public static TabGeral newInstance() {
        TabGeral fragment = new TabGeral();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("usuarios").child(mAuth.getCurrentUser().getUid()).child("viagens");

        viagem = new Viagem();

        viagem.setTotal(new BigDecimal(0));
        viagem.setDtInit(System.currentTimeMillis() + 86400000);
        viagem.setDtFinish(System.currentTimeMillis() + 86400000*2);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etNomeViagem = view.findViewById(R.id.etNomeViagem);
        tvTotal =  view.findViewById(R.id.tvTotal);
        etDataInicial = view.findViewById(R.id.etDataInicial);
        etDataFinal = view.findViewById(R.id.etDataFim);
        etQuantidadeViajantes = view.findViewById(R.id.etQuantidadeViajantes);
        etDestino = view.findViewById(R.id.etDestino);

        tvTotal.setText("Total: R$ " + viagem.getTotal().toString());
        btnSave = view.findViewById(R.id.btnTeste);

        final DatePickerDialog.OnDateSetListener dateInicio = (v, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelInicio();
        };

        final DatePickerDialog.OnDateSetListener dateFim = (v, year, month, dayOfMonth) -> {
            myCalendar2.set(Calendar.YEAR, year);
            myCalendar2.set(Calendar.MONTH, month);
            myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelFim();
        };

        etDataInicial.setOnClickListener(v -> {
            new DatePickerDialog(context, dateInicio, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        etDataFinal.setOnClickListener(v -> {
            new DatePickerDialog(context, dateFim, myCalendar2
                    .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                    myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
        });

        myCalendar.setTimeInMillis(viagem.getDtInit());
        updateLabelInicio();

        myCalendar2.setTimeInMillis(viagem.getDtFinish());
        updateLabelFim();

        etQuantidadeViajantes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty() || Integer.parseInt(s.toString()) == 0){
                    viagem.setTravelers(1);
                } else {
                    viagem.setTravelers(Integer.valueOf(s.toString()));
                }
                calculaTotal();
                tvTotal.setText("Total: R$ " + viagem.getTotal().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSave.setOnClickListener(v -> {

            viagem.setName(etNomeViagem.getText().toString());
            viagem.setDestiny(etDestino.getText().toString());

            viagem.setGasoline(null);
            viagem.setAirfare(null);
            viagem.setLodging(null);
            viagem.setMeals(null);

            if(!TabGasolina.calculaGasolina().equals(BigDecimal.ZERO)){
                viagem.setGasoline(TabGasolina.gasoline);
            }

            if(!TabAero.calculaAero().equals(BigDecimal.ZERO)){
                viagem.setAirfare(TabAero.airfare);
            }

            if(!TabHospedagem.calculaHospedagem().equals(BigDecimal.ZERO)){
                viagem.setLodging(TabHospedagem.lodging);
            }

            if(!TabRefeicoes.calculaRefeicoes().equals(BigDecimal.ZERO)){
                viagem.setMeals(TabRefeicoes.meals);
            }

            if(!TabDiversos.calculaDiversos().equals(BigDecimal.ZERO)){
                viagem.setDiversos(TabDiversos.total_diversos);
            }

            mDatabase.child(String.valueOf(System.currentTimeMillis())).setValue(viagem);
            getActivity().finish();
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tab_geral, container, false);
    }

    private void updateLabelInicio() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.US);
        etDataInicial.setText(sdf.format(myCalendar.getTime()));
        viagem.setDtInit(myCalendar.getTimeInMillis());
        calculaTotal();
        tvTotal.setText("Total: R$ " + viagem.getTotal().toString());
    }

    private void updateLabelFim() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.US);
        etDataFinal.setText(sdf.format(myCalendar2.getTime()));
        viagem.setDtFinish(myCalendar2.getTimeInMillis());
        calculaTotal();
        tvTotal.setText("Total: R$ " + viagem.getTotal().toString());
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);

    }

    @SuppressLint("NewApi")
    public static void calculaTotal(){
        final BigDecimal totalGasolina = Optional.of(TabGasolina.calculaGasolina()).orElse(new BigDecimal(0));
        final BigDecimal totalAero = Optional.of(TabAero.calculaAero()).orElse(new BigDecimal(0));
        final BigDecimal totalHospedagem = Optional.of(TabHospedagem.calculaHospedagem()).orElse(new BigDecimal(0));
        final BigDecimal totalRefeicoes = Optional.of(TabRefeicoes.calculaRefeicoes()).orElse(new BigDecimal(0));
        final BigDecimal totalDiversos = Optional.of(TabDiversos.calculaDiversos()).orElse(new BigDecimal(0));

        viagem.setTotal(totalGasolina.add(totalAero).add(totalHospedagem).add(totalRefeicoes).add(totalDiversos));
    }

}