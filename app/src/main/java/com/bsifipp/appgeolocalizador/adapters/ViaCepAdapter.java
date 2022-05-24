package com.bsifipp.appgeolocalizador.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bsifipp.appgeolocalizador.R;
import com.bsifipp.appgeolocalizador.models.ViaCep;

import java.util.List;

public class ViaCepAdapter extends ArrayAdapter<ViaCep> {
    private int layout;
    public ViaCepAdapter(@NonNull Context context, int resource, @NonNull List<ViaCep> objects) {
        super(context, resource, objects);
        layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(this.layout,parent,false);
        }

        TextView tvLogradouro = convertView.findViewById(R.id.tvLogradouro);

        ViaCep vc = this.getItem(position);

        tvLogradouro.setText(vc.logradouro + " - " + vc.complemento + " - " + vc.bairro);

        return convertView;
    }
}
