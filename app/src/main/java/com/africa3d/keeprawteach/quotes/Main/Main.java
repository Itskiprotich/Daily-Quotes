package com.africa3d.keeprawteach.quotes.Main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.africa3d.keeprawteach.quotes.Alert.ShowAlert;
import com.africa3d.keeprawteach.quotes.Fragment.One;
import com.africa3d.keeprawteach.quotes.Fragment.Three;
import com.africa3d.keeprawteach.quotes.Fragment.Two;
import com.africa3d.keeprawteach.quotes.R;

import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity {

    private TabLayout tabLayout;

    private ViewPager viewPager;

    private Toolbar toolbar;

    ShowAlert showAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showAlert=new ShowAlert(this);

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.setupWithViewPager(viewPager);

        toolbar=(Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Whatsapp Status");
        }
//        toolbar.setSubtitle("Test Subtitle");
        toolbar.inflateMenu(R.menu.main);
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new One(), "Love Status ");

        adapter.addFragment(new Two(), "Sad Status");

        adapter.addFragment(new Three(), "Attitude");

        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();

        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {

            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {

            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {

            mFragmentList.add(fragment);

            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            showAlert.openRate();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
