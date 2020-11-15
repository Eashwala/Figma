package com.ipl.user.screens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.tabs.TabLayout;
import com.ipl.user.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StatisticsFragment extends Fragment {

    TabLayout tab_layout_week;

    ArrayList<BarEntry>  visitors = new ArrayList<>();
    String todaydate ,dateOutput;
    BarChart barchart;
    private static final String FORMAT_DD_MM_YYYY = "dd/MM/yyyy";
    SimpleDateFormat format = new SimpleDateFormat(FORMAT_DD_MM_YYYY);

    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_statistics, container, false);


        tab_layout_week = (TabLayout)view.findViewById(R.id.tab_layout_week1);


        barchart  = view.findViewById(R.id.barchart);

        tab_layout_week.addTab(tab_layout_week.newTab().setText("WEEK"));
        tab_layout_week.addTab(tab_layout_week.newTab().setText("MONTH"));
        tab_layout_week.addTab(tab_layout_week.newTab().setText("YEAR"));



        visitors.add(new BarEntry(2014, 420));
        visitors.add(new BarEntry(2014, 420));
        visitors.add(new BarEntry(2015, 400));
        visitors.add(new BarEntry(2016, 350));
        visitors.add(new BarEntry(2017, 480));
        visitors.add(new BarEntry(2018, 520));

        BarDataSet barDataSet = new BarDataSet(visitors,"visitors");
        //  barDataSet.setValueTextColor(getResources().getColor(R.color.blueprimary));
        barDataSet.setColors(getResources().getColor(R.color.blueprimary));
        barDataSet.setValueTextSize(12f);

        BarData barData = new BarData(barDataSet);


        barchart.setFitBars(true);
        barchart.setData(barData);
        barchart.animateY(2000);
        tab_layout_week.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                   getweek();
                }
                else if (tab.getPosition() == 1){

                    getMonth();

                }else if (tab.getPosition() == 2){
                   getYear();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //    selectedates.setText(todaydate +"-" + dateOutput);

//        BarData data = new BarData("Sep", 3.4f);
//        dataList.add(data);
//
//        data = new BarData("Oct", 8f);
//        dataList.add(data);
//
//        data = new BarData("Nov", 1.8f);
//        dataList.add(data);
//
//        data = new BarData("Dec", 7.3f);
//        dataList.add(data);
//
//        data = new BarData("Jan", 6.2f);
//        dataList.add(data);
//
//        data = new BarData("Feb", 3.3f, "3.3€");
//        dataList.add(data);
//
//
//
//        mChart.setDataList(dataList);
//        mChart.build();

        return view;
    }



    private void getweek() {
        Calendar calendar = Calendar.getInstance();
        todaydate = format.format(calendar.getTime());

        calendar.add(Calendar.DAY_OF_WEEK, -7);
        Date date = calendar.getTime();
        //   SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        dateOutput = format.format(date);

        Toast.makeText(getContext()," 1 week   "+dateOutput,Toast.LENGTH_LONG).show();
    }

    private void getMonth() {
        Calendar calendar = Calendar.getInstance();
        todaydate = format.format(calendar.getTime());

        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();
        dateOutput = format.format(date);
        Toast.makeText(getContext(), " 1 month  "+dateOutput,Toast.LENGTH_LONG).show();
    }

    private void getYear() {
        Calendar calendar = Calendar.getInstance();
        todaydate = format.format(calendar.getTime());

        calendar.add(Calendar.YEAR, -1);
        Date date = calendar.getTime();
        dateOutput = format.format(date);
        Toast.makeText(getContext(), "1 year  "+dateOutput,Toast.LENGTH_LONG).show();
    }


}