package io.kim_kong.mypetcalendar.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import io.kim_kong.mypetcalendar.model.Winner;
import io.kim_kong.mypetcalendar.view.CalendarMainFragment;

public class CalendarFragmentAdapter extends FragmentPagerAdapter {
    private CalendarMainFragment[] fragList;
    private List<String> NamePage = new ArrayList<>();

    public CalendarFragmentAdapter(@NonNull FragmentManager fm, CalendarMainFragment[] fragList) {
        super(fm);
        this.fragList = fragList;
    }

    @NonNull @Override public Fragment getItem(int position) {
        return fragList[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return NamePage.get(position);
    }

    @Override public int getCount() {
        return 3;
    }

    public void setCalendar(ArrayList<Winner> winner, GregorianCalendar currentMonth) {

        GregorianCalendar prevMonth = new GregorianCalendar(currentMonth.get(Calendar.YEAR), currentMonth.get(Calendar.MONTH) - 1, 1, 0, 0, 0);

        GregorianCalendar nextMonth = new GregorianCalendar(currentMonth.get(Calendar.YEAR), currentMonth.get(Calendar.MONTH) + 1, 1, 0, 0, 0);

        fragList[0].updateUI(winner, prevMonth);
        fragList[1].updateUI(winner, currentMonth);
        fragList[2].updateUI(winner, nextMonth);
    }
}
