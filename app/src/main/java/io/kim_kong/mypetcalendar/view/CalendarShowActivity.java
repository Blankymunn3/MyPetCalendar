package io.kim_kong.mypetcalendar.view;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import io.kim_kong.mypetcalendar.R;
import io.kim_kong.mypetcalendar.adapter.CalendarFragmentAdapter;
import io.kim_kong.mypetcalendar.databinding.CalendarMainBinding;
import io.kim_kong.mypetcalendar.model.Winner;
import io.kim_kong.mypetcalendar.util.Util;
import io.kim_kong.mypetcalendar.util.YearMonthPicker;
import io.kim_kong.mypetcalendar.viewmodel.CalendarMainViewModel;

public class CalendarShowActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    public final static String TAG = CalendarShowActivity.class.getSimpleName();
    private final static int PAGE_CENTER = 1;

    private CalendarMainBinding binding;
    private CalendarMainViewModel model;

    public Context context;
    public Activity activity;

    GregorianCalendar cal = new GregorianCalendar();
    GregorianCalendar nextCal = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, 1, 0, 0, 0);
    GregorianCalendar previousCal = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) - 1, 1, 0, 0, 0);
    ArrayList<Winner> winners;

    int year = 0;
    int month;
    int focus = 0;

    ViewPager viewPager;

    CalendarFragmentAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_calendar);
        model = ViewModelProviders.of(this).get(CalendarMainViewModel.class);
        Util.setIconTinkDark(this, true);
        binding.setModel(model);
        binding.setLifecycleOwner(this);

        initCalendar();

        binding.calendarBackBtn.setOnClickListener(v -> onBackPressed());

        binding.layoutCalendarTitle.setOnClickListener(v -> onSelectYearMonth());

        binding.txtCalendarToday.setOnClickListener(v -> {
            year = 0;
            cal = new GregorianCalendar();
            onCallCalendar(winners, cal);
        });
    }

    private void initCalendar() {

        context = this;
        activity = this;

        try {
            winners = (ArrayList<Winner>) getIntent().getSerializableExtra("winners");
            if (model != null) {
                model.initMainCalendarTitle(cal);
            }

            viewPager = binding.vpFragment;

            CalendarMainFragment[] fragList = new CalendarMainFragment[3];
            fragList[0] = new CalendarMainFragment(this, this, winners, previousCal);
            fragList[1] = new CalendarMainFragment(this, this, winners, cal);
            fragList[2] = new CalendarMainFragment(this, this, winners, nextCal);

            pagerAdapter = new CalendarFragmentAdapter(getSupportFragmentManager(), fragList);

            viewPager.setAdapter(pagerAdapter);
            viewPager.setOnPageChangeListener(this);
            binding.vpFragment.setCurrentItem(1, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void onCallCalendar(ArrayList<Winner> winners, GregorianCalendar cal) {
        model.initMainCalendarTitle(new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0));
        cal = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
        pagerAdapter.setCalendar(winners, cal);
    }

    public void onSelectYearMonth() {
        YearMonthPicker yearMonthPicker;
        if (year == 0) {
            yearMonthPicker = new YearMonthPicker();
        } else {
            yearMonthPicker = new YearMonthPicker(cal.get(Calendar.YEAR), month + 1);
        }
        yearMonthPicker.setListener(onDateSetListener);
        yearMonthPicker.show(getSupportFragmentManager(), "yearMonthPicker");
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int pickYear, int pickMonth, int dayOfMonth) {
            year = pickYear;
            month = pickMonth - 1;
            cal = new GregorianCalendar(year, month, 1, 0, 0, 0);
            model.initMainCalendarTitle(cal);
            pagerAdapter.setCalendar(winners, cal);
        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position == 0)
            model.initMainCalendarTitle(new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) - 1, 1, 0, 0, 0));
        else if (position == 2)
            model.initMainCalendarTitle(new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, 1, 0, 0, 0));
        else
            model.initMainCalendarTitle(new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0));
    }

    @Override
    public void onPageSelected(int position) {
        focus = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            if (focus < PAGE_CENTER) {
                cal = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) - 1, 1, 0, 0, 0);
            } else if (focus > PAGE_CENTER) {
                cal = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, 1, 0, 0, 0);
            }
            pagerAdapter.setCalendar(winners, cal);
            viewPager.setCurrentItem(1, false);
        }
    }
}
