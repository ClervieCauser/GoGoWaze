package com.example.gogowaze;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.gogowaze.AccidentData;
import com.example.gogowaze.DownloadJsonTask;
import com.example.gogowaze.OnDataLoadedListener;
import com.example.gogowaze.TypeAccidentFragment;
import com.google.android.material.tabs.TabLayout;
public class StatisticsActivity extends AppCompatActivity implements OnDataLoadedListener {
    private AccidentData accidentData;
    private FragmentPagerAdapter adapter;
    private TypeAccidentFragment typeAccidentFragment;

    private GraviteFragment graviteFragment;
    private Button validerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistique);

        new DownloadJsonTask(this).execute("http://bluedays.com/data/web/donnees.json");

        validerButton = findViewById(R.id.valider);
        validerButton.setEnabled(false);
        validerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchEditText = findViewById(R.id.search_edit_text);
                String city = searchEditText.getText().toString();
                Log.d("ici", city);

                if (typeAccidentFragment != null && graviteFragment != null) {
                    // Obtenir le TableLayout du fragment TypeAccident
                    TableLayout tableLayoutType = typeAccidentFragment.getView().findViewById(R.id.table_layout);

                    // Appeler fillTableWithData pour TypeAccident
                    typeAccidentFragment.fillTableWithData(tableLayoutType, city);

                    // Obtenir le TableLayout du fragment Gravite
                    TableLayout tableLayoutGravite = graviteFragment.getView().findViewById(R.id.table_layout);

                    // Appeler fillTableWithData pour Gravite
                    graviteFragment.fillTableWithData(tableLayoutGravite, city);
                }
                // Fermer le clavier
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
            }
        });

    }

    @Override
    public void onDataLoaded(AccidentData accidentData) {
        this.accidentData = accidentData;

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        adapter = new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        typeAccidentFragment = TypeAccidentFragment.newInstance(accidentData);
                        return typeAccidentFragment;
                    case 1:
                        graviteFragment = GraviteFragment.newInstance(accidentData);
                        return graviteFragment; // Remplacez par le fragment pour "gravite"
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
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

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        validerButton.setEnabled(true);
    }
}
