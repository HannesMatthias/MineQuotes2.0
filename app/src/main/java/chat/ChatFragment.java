package chat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.rubrunghi.dev.minequotes.R;

import static android.app.Activity.RESULT_OK;


public class ChatFragment extends Fragment {



    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter adapter;
    ImageButton send;
    RelativeLayout layout;
    EditText input;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_chat, container, false);
        layout = (RelativeLayout) view.findViewById(R.id.chatActivity);
        send = (ImageButton) view.findViewById(R.id.send);
        input = view.findViewById(R.id.input);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().push().setValue( new Message(input.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getEmail()) );
                input.setText("");
            }
        });

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE);
        }else {
            Toast.makeText(getContext(), "Wilkommen", Toast.LENGTH_SHORT).show();
            chat();
        }


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(getContext(), "Wilkommen!", Toast.LENGTH_SHORT).show();
                chat();
            }else {
                Snackbar.make(layout, "Error", Snackbar.LENGTH_LONG).show();
                getActivity().finish();
            }
        }
    }
    public void chat() {
        ListView listOfMessage = (ListView) view.findViewById(R.id.messageList);
        adapter = new FirebaseListAdapter<Message>(getActivity(),Message.class,R.layout.list_item,FirebaseDatabase.getInstance().getReference())
        {
            @Override
            protected void populateView(View v, Message model, int position) {

                //Get references to the views of list_item.xml
                TextView messageText, messageUser, messageTime;
                messageText = (TextView) v.findViewById(R.id.message_text);
                messageUser = (TextView) v.findViewById(R.id.message_user);
                messageTime = (TextView) v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));

            }
        };
        listOfMessage.setAdapter(adapter);
    }

}
