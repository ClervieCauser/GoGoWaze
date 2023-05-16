package com.example.gogowaze;

import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistique);

        new DownloadJsonTask(this).execute("http://bluedays.com/data/web/donnees.json");
    }

    @Override
    public void onDataLoaded(AccidentData accidentData) {
        this.accidentData = accidentData;

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return TypeAccidentFragment.newInstance(accidentData);
                    case 1:
                        return TypeAccidentFragment.newInstance(accidentData); // Remplacez par le fragment pour "gravite"
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
    }
}
