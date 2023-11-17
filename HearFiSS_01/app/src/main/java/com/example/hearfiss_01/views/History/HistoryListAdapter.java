package com.example.hearfiss_01.views.History;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hearfiss_01.R;
import com.example.hearfiss_01.db.dao.HrTestDAO;
import com.example.hearfiss_01.db.DTO.ResultDTO;
import com.example.hearfiss_01.global.GlobalVar;
import com.example.hearfiss_01.views.PTT.PttResult02Activity;
import com.example.hearfiss_01.views.WRS.WrsResult02Activity;

import java.util.ArrayList;

public class HistoryListAdapter extends RecyclerView.Adapter <HistoryListAdapter.HistoryListViewHolder> {
    private ArrayList<ResultDTO> dataList;
    String m_TAG = "HistoryListAdapter";

    public HistoryListAdapter(ArrayList<ResultDTO> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public HistoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_list, parent,false);
        return new HistoryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryListViewHolder holder, int position) {
        ResultDTO data = dataList.get(position);
        holder.date.setText(data.getTg_Date());
        holder.type.setText(data.getTg_type());
        holder.leftResult.setText(data.getLeft_Result());
        holder.rightResult.setText(data.getRight_Result());
        holder.Result.setText(data.getResult());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class HistoryListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView date;
        private TextView type;
        private TextView leftResult;
        private TextView rightResult;
        private TextView Result;
        private AppCompatButton detail_button;

        public HistoryListViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.item_board_date);
            type = itemView.findViewById(R.id.item_board_type);
            Result = itemView.findViewById(R.id.item_result);
            leftResult = itemView.findViewById(R.id.item_left_result);
            rightResult = itemView.findViewById(R.id.item_right_result);
            detail_button = itemView.findViewById(R.id.detailBtn);
            detail_button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.detailBtn){
                Log.v(m_TAG, "detailBtn Click");
                int mPosition = getAdapterPosition();
                Context context = v.getContext();

                int iPositionTgid = dataList.get(mPosition).getTg_id();
                HrTestDAO dao = new HrTestDAO(context);
                GlobalVar.g_TestGroup = dao.selectTestGroupFromTgId(iPositionTgid);

                if(dataList.get(mPosition).getTg_type().contains("PTA")){
                    Intent intent = new Intent(context, PttResult02Activity.class);
                    (context).startActivity(intent);
                }

                if(dataList.get(mPosition).getTg_type().contains("WRS")){
                    Intent intent = new Intent(context, WrsResult02Activity.class);
                    (context).startActivity(intent);
                }

                dao.releaseAndClose();
            }

        }
    }
}
