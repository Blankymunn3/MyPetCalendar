package io.kim_kong.mypetcalendar.view;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import io.kim_kong.mypetcalendar.R;
import io.kim_kong.mypetcalendar.adapter.CalendarAdapter;
import io.kim_kong.mypetcalendar.databinding.CalendarListBinding;
import io.kim_kong.mypetcalendar.model.Winner;
import io.kim_kong.mypetcalendar.util.Util;
import io.kim_kong.mypetcalendar.viewmodel.CalendarListViewModel;
public class CalendarMainFragment extends Fragment {

    private CalendarListBinding binding;
    private CalendarListViewModel model;

    private Context context;
    private Activity activity;

    private RecyclerView view;
    private CalendarAdapter adapter;
    private StaggeredGridLayoutManager manager;

    private ArrayList<Winner> winner;
    private GregorianCalendar cal;

    public CalendarMainFragment() {

    }

    CalendarMainFragment(Context context, Activity activity, ArrayList<Winner> winner, GregorianCalendar cal) {
        this.context = context;
        this.activity = activity;
        this.winner = winner;
        this.cal = cal;
    }

    @Override public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.recycler_item_calendar, container, false);
        Util.setIconTinkDark(getActivity(), false);
        model = ViewModelProviders.of(this).get(CalendarListViewModel.class);
        binding.setModel(model);

        initCalendarList(winner, cal);
        observe();

        return binding.getRoot();
    }

    public void initCalendarList(ArrayList<Winner> winner, GregorianCalendar cal) {

        try {
            if (model != null) {
                model.initCalendarList(winner ,cal);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override public void onResume() {
        super.onResume();
    }

    @Override public void onPause() {
        super.onPause();
    }

    @Override public void onDestroy() {
        super.onDestroy();
    }

    public void updateUI(ArrayList<Winner> winner,GregorianCalendar calendar) {
        View v = getView();
        if (v == null) return;
        initCalendarList(winner, calendar);
    }

    private void observe() {
        model.mCalendarList.observe(this, objects -> {
            view = binding.pagerCalendar;
            adapter = (CalendarAdapter) view.getAdapter();
            if (adapter != null) {
                adapter.setCalendarList(objects);
            } else {
                manager = new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL);
                adapter = new CalendarAdapter(objects, activity);
                view.setLayoutManager(manager);
                view.setAdapter(adapter);
                if (model.mCenterPosition >= 0) {
                    view.scrollToPosition(model.mCenterPosition);
                }
            }
        });

    }
}
