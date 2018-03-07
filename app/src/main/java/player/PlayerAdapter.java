package player;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rubrunghi.dev.minequotes.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class PlayerAdapter extends ArrayAdapter<Player> {

    private static class ViewHolder {
        public ImageView skin;
        public TextView playername;

    }

    public PlayerAdapter(@NonNull Context context, ArrayList<Player> player) {
        super(context, 0, player);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Player player = getItem(position);
        Log.wtf("Playername: ", player.getPlayername());
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.playerlistcontent, viewGroup, false);
            viewHolder.skin = convertView.findViewById(R.id.playerListSkin);
            viewHolder.playername = convertView.findViewById(R.id.playerListName);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.playername.setText(player.getPlayername());

        Picasso.with(getContext()).load(Uri.parse(player.getSkinUrl())).error(R.mipmap.ic_launcher).into(viewHolder.skin);

        if(!LoadAllPlayersManager.players.contains(player)) {
            LoadAllPlayersManager.players.add(player);
        }

        return convertView;
    }
}
