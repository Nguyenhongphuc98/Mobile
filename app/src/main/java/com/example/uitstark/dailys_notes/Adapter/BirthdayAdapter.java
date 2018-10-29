package com.example.uitstark.dailys_notes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.uitstark.dailys_notes.DTO.BirthDay;
import com.example.uitstark.dailys_notes.R;

import java.util.List;

public class BirthdayAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<BirthDay> listBirthday;

    public static class ViewHolder{
        TextView soThuTu, tenBanBe, ngaySinh, quanHe;
    }

    public BirthdayAdapter(Context context, int layout, List<BirthDay> listBirthday){
        this.context=context;
        this.layout=layout;
        this.listBirthday=listBirthday;
    }

    @Override
    public int getCount() {
        return listBirthday.size();
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
            holder.tenBanBe=viewRow.findViewById(R.id.tvName);
            holder.ngaySinh=viewRow.findViewById(R.id.tvBirthday);
            holder.quanHe=viewRow.findViewById(R.id.tvRelationship);

            viewRow.setTag(holder);
        }

        ViewHolder viewHolder= (ViewHolder) viewRow.getTag();
        viewHolder.soThuTu.setText(String.valueOf(listBirthday.get(position).getId()));
        viewHolder.tenBanBe.setText(listBirthday.get(position).getName());
        viewHolder.ngaySinh.setText(listBirthday.get(position).getBornDay());
        viewHolder.quanHe.setText(listBirthday.get(position).getNote());

        return viewRow;
    }
}
