package chat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.library.bubbleview.BubbleTextView;
import com.rubrunghi.dev.minequotes.MainActivity;
import com.rubrunghi.dev.minequotes.R;

import chat.Message;


public class ChatAdapter extends ArrayAdapter<Message> {

    private static class ViewHolder {
        public TextView sender;
        public BubbleTextView message;
        public TextView date;

        public TextView sender2;
        public BubbleTextView message2;
        public TextView date2;

    }

    public ChatAdapter(@NonNull Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Message message = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_item, viewGroup, false);
            viewHolder.sender2 = convertView.findViewById(R.id.message_sender2);
            viewHolder.message2 = convertView.findViewById(R.id.message_chat2);
            viewHolder.date2 = convertView.findViewById(R.id.message_date2);

//            viewHolder.sender = convertView.findViewById(R.id.message_sender);
//            viewHolder.message = convertView.findViewById(R.id.message_chat);
//            viewHolder.date = convertView.findViewById(R.id.message_date);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(MainActivity.profile.getUsername().equals(message.getSenderID())) {
            viewHolder.sender2.setText(message.getSenderID());
            viewHolder.message2.setText(message.getMessageText());
            viewHolder.date2.setText(message.getMessageTime());
        }else {
            viewHolder.sender2.setText(message.getSenderID());
            viewHolder.message2.setText(message.getMessageText());
            viewHolder.date2.setText(message.getMessageTime());

        }



        return convertView;
    }
}
