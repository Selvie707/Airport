package com.example.airport;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class AdapterAirport extends RecyclerView.Adapter<AdapterAirport.ViewHolderAirport> {
    private final Context context;
    private final ArrayList<String> alId;
    private final ArrayList<String> alName;
    private final ArrayList<String> alCity;
    private final ArrayList<String> alAddress;

    public AdapterAirport(Context context, ArrayList<String> alId, ArrayList<String> alName,
                          ArrayList<String> alCity, ArrayList<String> alAddress) {
        this.context = context;
        this.alId = alId;
        this.alName = alName;
        this.alCity = alCity;
        this.alAddress = alAddress;
    }

    @NonNull
    @Override
    public ViewHolderAirport onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_bandara, parent, false);
        return new ViewHolderAirport(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAirport holder, int position) {
        StringBuilder cityAddress = new StringBuilder();
        cityAddress.append(alAddress.get(position)).append(", ").append(alCity.get(position));

        holder.tvId.setText(alId.get(position));
        holder.tvName.setText(alName.get(position));
        holder.tvCityAddress.setText(cityAddress.toString());
    }

    @Override
    public int getItemCount() {
        return alName.size();
    }

    public class ViewHolderAirport extends RecyclerView.ViewHolder {
        private final TextView tvId;
        private final TextView tvName;
        private final TextView tvCityAddress;
        public static final String EXTRA_ID = "vId";
        public static final String EXTRA_NAME = "vName";
        public static final String EXTRA_CITY = "vCity";
        public static final String EXTRA_ADDRESS = "vAddress";

        public ViewHolderAirport(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_list_item_id);
            tvName = itemView.findViewById(R.id.tv_list_item_name);
            tvCityAddress = itemView.findViewById(R.id.tv_list_item_city_address);

            itemView.setOnClickListener(v -> {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                String message = String.format(context.getString(R.string.alertMessage), tvName.getText().toString());
                alertBuilder.setTitle(context.getString(R.string.alertTitle));
                alertBuilder.setMessage(message);
                alertBuilder.setCancelable(true);
                alertBuilder.setNegativeButton(context.getString(R.string.alertOptI), (dialog, which) -> handleDeleteData(dialog));

                alertBuilder.setPositiveButton(context.getString(R.string.alertOptII), (dialog, which) -> handleEditData());
                alertBuilder.show();
            });
        }

        private void handleDeleteData(DialogInterface dialog) {
            MyDBHelper mdb = new MyDBHelper(context);
            long execution = mdb.DeleteAirport(tvId.getText().toString());
            if (execution == -1) {
                Toast.makeText(context, context.getString(R.string.alertOptIFailed), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, context.getString(R.string.alertOptISuccess), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }

        private void handleEditData() {
            String id = tvId.getText().toString();
            String name = tvName.getText().toString();
            String city = tvCityAddress.getText().toString();

            String[] parts = city.split(",");
            String partAddress = parts[0].trim();
            String partCity = parts[1].trim();

            Intent intent = new Intent(context, EditActivity.class);
            intent.putExtra(EXTRA_ID, id);
            intent.putExtra(EXTRA_NAME, name);
            intent.putExtra(EXTRA_CITY, partCity);
            intent.putExtra(EXTRA_ADDRESS, partAddress);
            context.startActivity(intent);
        }
    }
}
