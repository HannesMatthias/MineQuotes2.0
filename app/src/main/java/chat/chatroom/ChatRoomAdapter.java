package chat.chatroom;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.library.bubbleview.BubbleTextView;
import com.rubrunghi.dev.minequotes.Client;
import com.rubrunghi.dev.minequotes.MainActivity;
import com.rubrunghi.dev.minequotes.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import chat.Message;
import chat.friends.Friends;
import rank.RankHandler;


public class ChatRoomAdapter extends ArrayAdapter<Friends> {

    private static class ViewHolder {
        public ImageView skin;
        public TextView name;

    }

    public ChatRoomAdapter(@NonNull Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Friends friends = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_room_item, viewGroup, false);
            viewHolder.skin = convertView.findViewById(R.id.chat_skin);
            viewHolder.name = convertView.findViewById(R.id.chat_friend);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Picasso.with(getContext()).load(Uri.parse(friends.getHeadUrl())).error(R.mipmap.ic_launcher).into(viewHolder.skin);
        viewHolder.name.setText(friends.getFriendName());



        return convertView;
    }
}
