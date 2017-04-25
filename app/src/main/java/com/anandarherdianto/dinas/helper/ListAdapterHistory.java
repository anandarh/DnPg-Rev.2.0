package com.anandarherdianto.dinas.helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anandarherdianto.dinas.R;
import com.anandarherdianto.dinas.model.HistoryModel;
import com.anandarherdianto.dinas.model.LHistoryModel;

import java.util.List;

/**
 * Created by Ananda R. Herdianto on 24/04/2017.
 */

public class ListAdapterHistory extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<LHistoryModel> historyItems;

    public ListAdapterHistory(Activity activity, List<LHistoryModel> historyItems) {
        this.activity = activity;
        this.historyItems = historyItems;
    }

    @Override
    public int getCount() {
        return historyItems.size();
    }

    @Override
    public Object getItem(int position) {
        return historyItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_row_history, null);
        }

        TextView txtLevel = (TextView) convertView.findViewById(R.id.lLevel);
        TextView txtNote = (TextView) convertView.findViewById(R.id.lNote);
        TextView txtDate = (TextView) convertView.findViewById(R.id.lDate);

        LHistoryModel hm = historyItems.get(position);

        txtLevel.setText("Nilai Level : "+hm.getAvgLevel());
        txtNote.setText("Catatan : "+hm.getNote());
        txtDate.setText(hm.getDate());

        return convertView;
    }
}
