package com.example.uitstark.dailys_notes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.uitstark.dailys_notes.DTO.BirthDay;
import com.example.uitstark.dailys_notes.DTO.ToDo;
import com.example.uitstark.dailys_notes.R;

import java.util.List;

public class ToDoAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<ToDo> listToDo;

    public static class ViewHolder{
        TextView soThuTu, title, time;
    }

    public ToDoAdapter(Context context, int layout, List<ToDo> listToDo){
        this.context=context;
        this.layout=layout;
        this.listToDo=listToDo;
    }

    @Override
    public int getCount() {
        return listToDo.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRow= convertView;

        if(viewRow==null){
            viewRow=inflater.inflate(layout,parent,false);
            ViewHolder holder=new ViewHolder();
            holder.soThuTu=viewRow.findViewById(R.id.tvAvatar);
            holder.title=viewRow.findViewById(R.id.tvTitleToDo);
            holder.time=viewRow.findViewById(R.id.tvTimeToDo);
            viewRow.setTag(holder);
        }

        ViewHolder viewHolder= (ViewHolder) viewRow.getTag();
        viewHolder.soThuTu.setText(String.valueOf(listToDo.get(position).getId()));
        viewHolder.title.setText(listToDo.get(position).getTitle());
        viewHolder.time.setText(listToDo.get(position).getTime());
        return viewRow;
    }
}
