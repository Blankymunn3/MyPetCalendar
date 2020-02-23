package io.kim_kong.mypetcalendar.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.jetbrains.annotations.Contract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.kim_kong.mypetcalendar.R;
import io.kim_kong.mypetcalendar.databinding.CalendarHeaderBinding;
import io.kim_kong.mypetcalendar.databinding.DayItemBinding;
import io.kim_kong.mypetcalendar.databinding.EmptyDayBinding;
import io.kim_kong.mypetcalendar.databinding.ImageViewBinding;
import io.kim_kong.mypetcalendar.model.Winner;
import io.kim_kong.mypetcalendar.util.SetTextColor;
import io.kim_kong.mypetcalendar.viewmodel.CalendarHeaderViewModel;
import io.kim_kong.mypetcalendar.viewmodel.CalendarImageViewModel;
import io.kim_kong.mypetcalendar.viewmodel.CalendarViewModel;
import io.kim_kong.mypetcalendar.viewmodel.EmptyViewModel;

public class CalendarAdapter extends RecyclerView.Adapter {
    private final int HEADER_TYPE = 0;
    private final int EMPTY_TYPE = 1;
    private final int DAY_TYPE = 2;
    private final int IMAGE_TYPE = 3;

    private List<Object> mCalendarList;

    private Activity activity;

    private int year;
    private int month;
    private int day = 0;

    public CalendarAdapter(List<Object> calendarList, Activity activity) {
        mCalendarList = calendarList;
        this.activity = activity;
    }

    public void setCalendarList(List<Object> calendarList) {
        mCalendarList = calendarList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mCalendarList.get(position);
        if (item instanceof Long) {
            return HEADER_TYPE;
        } else if (item instanceof Boolean) {
            return EMPTY_TYPE;
        } else if (item instanceof Winner) {
            return IMAGE_TYPE;
        } else {
            return DAY_TYPE;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER_TYPE) {
            CalendarHeaderBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.calendar_header_item, parent, false);
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) binding.getRoot().getLayoutParams();
            params.setFullSpan(true);
            binding.getRoot().setLayoutParams(params);
            return new HeaderViewHolder(binding);
        } else if (viewType == EMPTY_TYPE) {
            EmptyDayBinding binding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.calendar_empty_item, parent, false);
            return new EmptyViewHolder(binding);
        } else if (viewType == IMAGE_TYPE) {
            ImageViewBinding binding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.calendar_day_image, parent, false);
            return new ImageViewHolder(binding);
        } else if (viewType == DAY_TYPE) {
            DayItemBinding binding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.calendar_day_item, parent, false);// 일자 타입
            return new DayViewHolder(binding);
        }
        return null;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        int leftSize = Math.round(5 * dm.density);
        int rightSize = Math.round(6 * dm.density);
        int bottomSize = Math.round(1 * dm.density);
        if (viewType == EMPTY_TYPE) {
            EmptyViewHolder holder = (EmptyViewHolder) viewHolder;
            EmptyViewModel model = new EmptyViewModel();
            holder.setViewModel(model);
        } else if (viewType == DAY_TYPE) {
            DayViewHolder holder = (DayViewHolder) viewHolder;
            Object item = mCalendarList.get(position);
            CalendarViewModel model = new CalendarViewModel();
            if (item instanceof Calendar) {
                model.setCalendar((Calendar) item);
            }
            holder.setViewModel(model);
            try {
                if (item instanceof Calendar) {
                    year = ((Calendar) item).get(Calendar.YEAR);
                    month = ((Calendar) item).get(Calendar.MONTH) + 1;
                    day = ((Calendar) item).get(Calendar.DAY_OF_MONTH);
                }
            } catch (ClassCastException e) {
                e.printStackTrace();
            }

            SetTextColor textColor = new SetTextColor(year, month, day);

            if (checkAnnounceComplete(year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day))) {
                holder.binding.txtDay.setBackground(activity.getResources().getDrawable(R.drawable.entry_alone_bg_on));
                if (day < 10) {
                    holder.binding.txtDay.setPadding(leftSize, 0, rightSize, bottomSize);
                }
            }
            if (textColor.textColor() == 0) {
                holder.binding.txtDay.setTextColor(activity.getResources().getColor(R.color.semiRed));
            } else if (textColor.textColor() == 1) {
                holder.binding.txtDay.setTextColor(activity.getResources().getColor(R.color.colorSkyBlue));
            } else {
                holder.binding.txtDay.setTextColor(activity.getResources().getColor(R.color.semiBlack));
            }
        } else if (viewType == IMAGE_TYPE) {

            ImageViewHolder holder = (ImageViewHolder) viewHolder;
            Object item = mCalendarList.get(position);
            CalendarImageViewModel model = new CalendarImageViewModel();
            if (item instanceof Winner) {
                model.setmImage(((Winner) item).getImageUrl());
            }
            holder.setViewModel(model);

            String getScreenTime = null;
            if (item instanceof Winner) {
                getScreenTime = ((Winner) item).getEventDate();
            }
            assert getScreenTime != null;
            year = Integer.parseInt(getScreenTime.substring(0, 4));
            month = Integer.parseInt(getScreenTime.substring(5, 7));
            day = Integer.parseInt(getScreenTime.substring(8, 10));

            holder.binding.txtImageDay.setText(Integer.toString(day));
            SetTextColor textColor = new SetTextColor(year, month, day);

            if (checkAnnounceComplete(year + "-" + month + "-" + day)) {
                holder.binding.txtImageDay.setBackground(activity.getResources().getDrawable(R.drawable.entry_alone_bg_on));
                if (day < 10) {
                    holder.binding.txtImageDay.setPadding(leftSize, 0, rightSize, bottomSize);
                }
            }
            if (textColor.textColor() == 0) {
                holder.binding.txtImageDay.setTextColor(activity.getResources().getColor(R.color.semiRed));
            } else if (textColor.textColor() == 1) {
                holder.binding.txtImageDay.setTextColor(activity.getResources().getColor(R.color.colorSkyBlue));
            } else {
                holder.binding.txtImageDay.setTextColor(activity.getResources().getColor(R.color.semiBlack));
            }
        }
    }


    @Contract("null -> false")
    private boolean checkAnnounceComplete(String date) {
        if(date != null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
            try {
                Date currentTime = new Date();
                String strNowDate = format.format(currentTime);
                Date nowDate = format.parse(strNowDate);
                Date getDate = format.parse(date);
                return nowDate.compareTo(getDate) == 0;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        if (mCalendarList != null) {
            return mCalendarList.size();
        }
        return 0;
    }


    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private CalendarHeaderBinding binding;

        private HeaderViewHolder(@NonNull CalendarHeaderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void setViewModel(CalendarHeaderViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }
    }


    private static class EmptyViewHolder extends RecyclerView.ViewHolder {
        private EmptyDayBinding binding;

        private EmptyViewHolder(@NonNull EmptyDayBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void setViewModel(EmptyViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }

    }

    private static class DayViewHolder extends RecyclerView.ViewHolder {
        private DayItemBinding binding;

        private DayViewHolder(@NonNull DayItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void setViewModel(CalendarViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }

    }


    private static class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageViewBinding binding;

        private ImageViewHolder(@NonNull ImageViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void setViewModel(CalendarImageViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }
    }

}