package br.usjt.ciclodevidagpsemapas30;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class LocalizacaoArrayAdapter extends ArrayAdapter <Localizacao> {
    public LocalizacaoArrayAdapter (Context context, List<Localizacao> localizacoes){
        super (context, -1, localizacoes);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LocalizacaoViewHolder vh;

        Localizacao localizacao = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);


        vh = new LocalizacaoViewHolder();

        vh.latitude = convertView.findViewById(R.id.latitude);
        vh.longitude = convertView.findViewById(R.id.longitude);

        convertView.setTag(vh);
        }

        vh = (LocalizacaoViewHolder) convertView.getTag();

        vh.latitude.setText("Latitude: " + localizacao.getLatitude());
        vh.longitude.setText("Longitude: " + localizacao.getLongitude());

        return convertView;
    }

    private class LocalizacaoViewHolder {
        public TextView latitude;
        public TextView longitude;
    }


}
