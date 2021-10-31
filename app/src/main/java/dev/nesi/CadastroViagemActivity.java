package dev.nesi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class CadastroViagemActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private TabItem tabGeral, tabGasolina, tabAero, tabRefeicoes, tabHospedagem, tabDiversos;
    private ViewPager viewPager;
    public PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_viagem);

        tabLayout = findViewById(R.id.tabLayout);
        tabGeral = findViewById(R.id.tabGeral);
        tabGasolina = findViewById(R.id.tabGasolina);
        tabAero = findViewById(R.id.tabAero);
        tabRefeicoes = findViewById(R.id.tabRefeicoes);
        tabHospedagem = findViewById(R.id.tabHospedagem);
        tabDiversos = findViewById(R.id.tabDiversos);
        viewPager = findViewById(R.id.viewPager);

        pagerAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                pagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}