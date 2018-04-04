package rank;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rubrunghi.dev.minequotes.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import player.Player;

public class RankAdapter extends ArrayAdapter<RankPlayer> {

    private static class ViewHolder {
        public ImageView skin;
        public TextView playername;
        public TextView lb_rang;
        public TextView rang;

    }

    public RankAdapter(@NonNull Context context, ArrayList<RankPlayer> player) {
        super(context, 0, player);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        RankPlayer player = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ranglistecontent, viewGroup, false);
            viewHolder.skin = convertView.findViewById(R.id.head);
            viewHolder.playername = convertView.findViewById(R.id.profilename);
            viewHolder.rang = convertView.findViewById(R.id.rang);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.playername.setText(player.getPlayername());
        viewHolder.rang.setText("#" + String.valueOf(player.getRang()));
        Picasso.with(getContext()).load(Uri.parse(player.getSkinUrlHead())).error(R.mipmap.ic_launcher).into(viewHolder.skin);


        return convertView;
    }
}
