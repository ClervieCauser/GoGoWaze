package com.example.gogowaze;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistique);

        // Obtenez une référence au TabLayout et au ViewPager dans votre activité ou votre fragment
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        // Créer un adaptateur de fragments pour le ViewPager
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                // Retourner le fragment approprié pour chaque onglet
                switch (position) {
                    case 0:
                        return new TypeAccidentFragment();
                    case 1:
                        return new TypeAccidentFragment();
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                // Retourner le nombre total d'onglets
                return 2;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                // Retourner le titre de chaque onglet
                switch (position) {
                    case 0:
                        return "Type Accident";
                    case 1:
                        return "Gravité";
                    default:
                        return null;
                }
            }
        };

        // Attacher l'adaptateur de fragments au ViewPager
        viewPager.setAdapter(adapter);

        // Attacher le ViewPager au TabLayout
        tabLayout.setupWithViewPager(viewPager);
    }
}
