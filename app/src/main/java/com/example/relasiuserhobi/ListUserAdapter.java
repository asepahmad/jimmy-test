package com.example.relasiuserhobi;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.ItemHolder> {

    private List<User> items = new ArrayList<>();

    ListUserAdapter(){}

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ItemHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        final User mData = items.get(position);
        holder.nama.setText("Nama : "+mData.getNama());
        holder.tglLahir.setText("Tgl Lahir : "+mData.getTanggal_lahir());
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("data02");

        //memanggil data detail berdasarkan id user
        String id_user = String.valueOf(mData.getId());
        mRef.child(id_user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //holder.tvInitial.setText(data.getNama());
                DetailUser detailUser = dataSnapshot.getValue(DetailUser.class);
                if(detailUser!=null) {
                    //menampilkan data cita-cita dan hobi
                    holder.citaCita.setText(detailUser.getCita_cita());
                    holder.hobi.setText(detailUser.getHobi());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    void add(User item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item to the Recycler adapter");
        }
        items.add(item);
       notifyDataSetChanged();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNama) TextView nama;
        @BindView(R.id.tvTglLahir) TextView tglLahir;
        @BindView(R.id.tvCitaCita) TextView citaCita;
        @BindView(R.id.tvHobi) TextView hobi;

        ItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
