package net.rickiekarp.homeassistant.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.rickiekarp.homeassistant.R;
import net.rickiekarp.homeassistant.net.communication.vo.VONote;

/**
 *
 * @author Paresh N. Mayani
 */
public class NoteHistoryListViewAdapter extends BaseAdapter
{
    public ArrayList<VONote> items;
    private Activity activity;

    public NoteHistoryListViewAdapter(Activity activity, ArrayList<VONote> noteList) {
        super();
        this.activity = activity;
        this.items = noteList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView txtFirst;
        TextView txtSecond;
        TextView txtThird;
        TextView txtFourth;
    }

    public VONote getNoteAtIndex(int index) {
        return items.get(index);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater =  activity.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.item_notehistory_list, null);
            holder = new ViewHolder();
            holder.txtFirst = (TextView) convertView.findViewById(R.id.FirstText);
            holder.txtSecond = (TextView) convertView.findViewById(R.id.SecondText);
            holder.txtThird = (TextView) convertView.findViewById(R.id.ThirdText);
            holder.txtFourth = (TextView) convertView.findViewById(R.id.FourthText);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        VONote map = items.get(position);
        holder.txtFirst.setText(map.getTitle());
        holder.txtSecond.setText(String.valueOf(map.getPrice()));
        holder.txtThird.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(map.getDateBought()));
        holder.txtFourth.setText(String.valueOf(map.getStore_id()));

        return convertView;
    }

}