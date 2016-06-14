package pl.sggw.wzim.chat.adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import pl.sggw.wzim.chat.R;
import pl.sggw.wzim.chat.model.LayoutMessage;
import pl.sggw.wzim.chat.model.Message;
import pl.sggw.wzim.chat.swagger.model.UserResponse;

/*
 * Created by Michal on 2016-05-18.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private List<Message> rawMessageList;
    private List<LayoutMessage> messageWLayoutInfoList;
    private List<UserResponse> conversationPariticipants;


    public void setConversationPariticipants(List<UserResponse> conversationPariticipants) {
        this.conversationPariticipants = conversationPariticipants;
    }

    public MessageAdapter(List<Message> messageList) {
        rawMessageList = messageList;
        if(messageList.size() == 0)
            messageWLayoutInfoList = new ArrayList<>();
        else
            messageWLayoutInfoList = LayoutMessage.parseRawMessageList(rawMessageList);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView content, timestamp;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            content = (TextView) view.findViewById(R.id.messageContentText);
            timestamp = (TextView) view.findViewById(R.id.timestampText);
            image = (ImageView) view.findViewById(R.id.avatarImageView);
        }
    }

    private int getBit(int position, int source)
    {
        return (source >> position) & 1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int xmlResource = R.layout.chat_message_row_left;

        //bit 0 = alignment type
        if(getBit(0,viewType) == 1){
                xmlResource = R.layout.chat_message_row_right;
        }

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(xmlResource, parent, false);

        //bit 1 = is avatar visible
        if(getBit(1,viewType) == 0){
            itemView.findViewById(R.id.avatarImageView).setVisibility(View.INVISIBLE);
        }

        //bit 2 = is stamp visible
        if(getBit(2,viewType) == 0){
            itemView.findViewById(R.id.timestampText).setVisibility(View.GONE);
        }

        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return messageWLayoutInfoList.get(position).getCompressedLayoutInfo();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Message msg = rawMessageList.get(position);
        holder.content.setText(msg.getMessageContent());
        holder.timestamp.setText(msg.getTimestamp());
        Bitmap avatar = getAvatarFromUID(msg.getSender());
        if(avatar != null)
            holder.image.setImageBitmap(avatar);
    }

    private Bitmap getAvatarFromUID(String uID){
        for(UserResponse user: conversationPariticipants){
            long id = user.getId();
            if(uID.equals(id+"")){
                return user.getAvatar();
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return rawMessageList.size();
    }


    public void addMessage(Message message){
        LayoutMessage lastMessage = null;

        if(rawMessageList.size() != 0) {
            lastMessage = messageWLayoutInfoList.get(messageWLayoutInfoList.size() - 1);
            messageWLayoutInfoList.remove(messageWLayoutInfoList.size()-1);
        }

        rawMessageList.add(message);

        messageWLayoutInfoList.addAll(LayoutMessage.parseLastTwoMessages(message, lastMessage));
    }

}
