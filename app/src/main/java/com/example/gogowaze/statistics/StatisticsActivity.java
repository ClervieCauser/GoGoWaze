package com.example.gogowaze.statistics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.gogowaze.MainActivity;
import com.example.gogowaze.OnDataLoadedListener;
import com.example.gogowaze.R;
import com.example.gogowaze.model.VilleInterface;
import com.example.gogowaze.model.VilleSimpleFactory;
import com.example.gogowaze.statistics.creerStat.CreerStatistiqueActivity;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class StatisticsActivity extends AppCompatActivity implements OnDataLoadedListener {
    private JSONObject accidentData;
    private FragmentPagerAdapter adapter;
    private TypeAccidentFragment typeAccidentFragment;
    private GraviteFragment graviteFragment;
    private Button validerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistique);

        findViewById(R.id.buttonExit).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

        new DownloadJsonTask(this).execute("http://bluedays.com/data/web/donnees.json");

        validerButton = findViewById(R.id.valider);
        validerButton.setEnabled(false);
        validerButton.setOnClickListener(v -> {
            EditText searchEditText = findViewById(R.id.search_edit_text);
            String city = searchEditText.getText().toString();
            Log.d("ici", city);

            if (typeAccidentFragment != null && graviteFragment != null) {

                //création Factory en fonction de la ville

                VilleInterface villeSimpleFactory = null;
                try {
                    villeSimpleFactory = VilleSimpleFactory.createVille((JSONObject) this.accidentData.get(city), city);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                // Mettre à jour les vues des fragments
                typeAccidentFragment.updateView(villeSimpleFactory.getTypeAccident());
                graviteFragment.updateView(villeSimpleFactory.getGravite());
            }
            // Fermer le clavier
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
        });

        Button buttonGo = findViewById(R.id.buttonGo);
        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticsActivity.this, CreerStatistiqueActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDataLoaded(JSONObject accidentData) {
        this.accidentData = accidentData;
        setupFragments();


        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        Log.d("ici", "onDataLoaded");

        validerButton.setEnabled(true);
    }

    private void setupFragments() {

        Log.d("ici", "setupFragments");
        Log.d("ici", accidentData.toString());

        //on créé villeSimpleFactory ici
        //to do


        //on mettra villeSimpleFactory.getGravite en paramètre pour crée le fragment
        typeAccidentFragment = TypeAccidentFragment.newInstance();
        //on mettra villeSimpleFactory.getTypeAccident en paramètre pour créé le framgnet
        graviteFragment = GraviteFragment.newInstance();

        adapter = new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return typeAccidentFragment;
                    case 1:
                        return graviteFragment;
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
    }
}
