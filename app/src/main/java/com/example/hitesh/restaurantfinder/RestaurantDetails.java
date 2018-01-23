package com.example.hitesh.restaurantfinder;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class RestaurantDetails extends AppCompatActivity implements ResFragment1.OnFragmentInteractionListener,ResFragment2.OnFragmentInteractionListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static String resId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        Intent intent=getIntent();
         resId=intent.getStringExtra("resId");
        Toast.makeText(getApplicationContext(),intent.getStringExtra("resId"),Toast.LENGTH_SHORT).show();
        toolbar=(Toolbar)findViewById(R.id.toolbar2);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout=(TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        //showFragment(resId);
    }
//    public void showFragment(String s){
//        FragmentTransaction trans=getSupportFragmentManager().beginTransaction();
//        ResFragment1 fragment1=ResFragment1.newInstance(s);
//        trans.add(R.id.fragment_container,fragment1,"fragment1");
//        trans.commit();
//    }
    private void  setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        ResFragment1 resFragment1=ResFragment1.newInstance(resId);
        ResFragment2 resFragment2=new ResFragment2();
        adapter.addFragment(resFragment1,"Details");
        adapter.addFragment(resFragment2,"menu");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter{

        private final List<Fragment> fragmentList=new ArrayList<>();
        private final List<String> fragmentTitleList=new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
        public void addFragment(Fragment fragment,String title){
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
