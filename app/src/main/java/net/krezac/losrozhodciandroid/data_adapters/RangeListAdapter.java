package net.krezac.losrozhodciandroid.data_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.krezac.losrozhodciandroid.R;

import java.util.ArrayList;
import java.util.List;

import io.swagger.client.model.Range;


public class RangeListAdapter  extends ArrayAdapter<Range> {

    public RangeListAdapter(Context context, List<Range> rangeList) {
        super(context, 0, rangeList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.range_spinner_row, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.text_view_name);

        Range currentItem = getItem(position);

        if (currentItem != null) {
            textViewName.setText(currentItem.getName());
        }

        return convertView;
    }
}