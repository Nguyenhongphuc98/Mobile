package com.example.uitstark.dailys_notes.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uitstark.dailys_notes.DTO.BirthDay;
import com.example.uitstark.dailys_notes.DTO.Note;
import com.example.uitstark.dailys_notes.R;

import java.util.List;

public class NoteAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Note> listNote;

    public static class ViewHolder{
        TextView soThuTu, title;
        LinearLayout linearLayout;
    }

    public NoteAdapter(Context context, int layout, List<Note> listNote){
        this.context=context;
        this.layout=layout;
        this.listNote=listNote;
    }

    @Override
    public int getCount() {
        return listNote.size();
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
            holder.soThuTu=viewRow.findViewById(R.id.tvAvatarNote);
            holder.title=viewRow.findViewById(R.id.tvTitleNote);
            holder.linearLayout=viewRow.findViewById(R.id.layoutListNote);


            viewRow.setTag(holder);
        }

        ViewHolder viewHolder= (ViewHolder) viewRow.getTag();
        //viewHolder.soThuTu.setText(String.valueOf(listNote.get(position).getId()));
        viewHolder.title.setText(listNote.get(position).getTitle());
        viewHolder.title.setTextColor(Color.WHITE);
        viewHolder.linearLayout.setBackgroundColor(listNote.get(position).getColor());

        return viewRow;
    }
}
