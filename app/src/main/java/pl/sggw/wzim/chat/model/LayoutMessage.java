package pl.sggw.wzim.chat.model;

import java.util.ArrayList;
import java.util.List;

import pl.sggw.wzim.chat.server.ServerConnection;

/**
 * Created by Michal on 2016-05-18.
 */
public class LayoutMessage extends Message{


    private boolean isTimeStampVisible;
    private boolean isAvatarVisible;
    private int alignmentType;

    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_RIGHT = 1;

    public LayoutMessage(String messageContent, String timestamp, String sender) {
        super(messageContent, timestamp, sender);

        alignmentType = ALIGN_LEFT;
        isAvatarVisible = true;
        isTimeStampVisible = true;
    }

    public static List<LayoutMessage> parseLastTwoMessages(Message newMessage, LayoutMessage prevMessage){
        List<LayoutMessage> convertedList = new ArrayList<>();

        LayoutMessage newMsgWInfo = new LayoutMessage(newMessage.getMessageContent(),newMessage.getTimestamp(), newMessage.getSender());

        if(prevMessage!=null && newMessage.getSender().equals(prevMessage.getSender())){
            newMsgWInfo.setAvatarVisible(false);
            prevMessage.setTimeStampVisible(false);
        }

        if(newMessage.getSender().equals(ServerConnection.getInstance().getLoggedUser().getId().toString())){
            newMsgWInfo.setAlignmentType(ALIGN_RIGHT);
        }

        if(prevMessage != null) convertedList.add(prevMessage);
        convertedList.add(newMsgWInfo);

        return convertedList;
    }

    public static List<LayoutMessage> parseRawMessageList(List<Message> rawMessageList){
        Message m;
        LayoutMessage msgWInfo;

        List<LayoutMessage> convertedList = new ArrayList<>();

        for(int i = 0; i < rawMessageList.size(); i++){
            m = rawMessageList.get(i);
            msgWInfo = new LayoutMessage(m.getMessageContent(),m.getTimestamp(),m.getSender());

            if(msgWInfo.getSender().equals(ServerConnection.getInstance().getLoggedUser().getId().toString())){
                msgWInfo.setAlignmentType(ALIGN_RIGHT);
            }

            if(i != 0 && rawMessageList.get(i-1).getSender().equals(m.getSender())){
                msgWInfo.setAvatarVisible(false);
            }

            if(i != rawMessageList.size() - 1 && rawMessageList.get(i+1).getSender().equals(m.getSender())){
                msgWInfo.setTimeStampVisible(false);
            }

            convertedList.add(msgWInfo);
        }
        return convertedList;
    }


    /**
     *  first(smallest) bit = alignmentType
     *  second bit = isAvatarVisible
     *  third bit = isTimeStampVisible
     */
    public int getCompressedLayoutInfo() {
        int result = ((isTimeStampVisible) ? 1 : 0);
        result <<= 1;
        result |= ((isAvatarVisible) ? 1 : 0);
        result <<= 1;
        result |= alignmentType;

        return result;
    }

    public void setTimeStampVisible(boolean timeStampVisible) {
        isTimeStampVisible = timeStampVisible;
    }

    public void setAvatarVisible(boolean avatarVisible) {
        isAvatarVisible = avatarVisible;
    }

    public void setAlignmentType(int alignmentType) {
        this.alignmentType = alignmentType;
    }
}
