package pl.sggw.wzim.chat.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RunnableFuture;

import android.os.Handler;
import android.util.Log;

import pl.sggw.wzim.chat.model.Message;
import pl.sggw.wzim.chat.server.tasks.GetLastMessagesTask;
import pl.sggw.wzim.chat.server.tasks.GetUnreadMessagesTask;
import pl.sggw.wzim.chat.swagger.model.MessageResponse;

/**
 * @author Patryk Konieczny
 * @since 11.06.2016
 */
public class ChatService extends Service implements GetUnreadMessagesTask.PostGetUnreadMessagesCallback, GetLastMessagesTask.PostGetMessageCallback {
    private static ChatService ourInstance = new ChatService();

    private boolean isServiceActive = true;
    private Handler unreadConversationHandler;
    private Runnable unreadConversationRun;
    private Handler newMessagesHandler;
    private Runnable newMessagesRun;

    private List<UnreadConversationServiceCallback> registeredForUnreadConversation;
    private Map<Long, NewMessagesServiceCallback> registeredForNewMessages;

    private Map<Long, List<MessageResponse>> conversationLast20Messages;

    public static ChatService getInstance() {
        return ourInstance;
    }

    private ChatService()
    {
        registeredForUnreadConversation = new LinkedList<>();
        registeredForNewMessages = new HashMap<>();
        conversationLast20Messages = new HashMap<>();
        final ChatService service = this;

//        unreadConversationHandler = new Handler();
//        unreadConversationRun = new Runnable() {
//            @Override
//            public void run() {
//                if (!registeredForUnreadConversation.isEmpty()) ServerConnection.getInstance().getUnreadMessages(service);
//
//            }
//        };
//        unreadConversationHandler.postDelayed(unreadConversationRun,1000);
//
//        newMessagesHandler = new Handler();
//        newMessagesRun = new Runnable() {
//            @Override
//            public void run() {
//
//                for (Long conversation:
//                        registeredForNewMessages.keySet()) {
//                    ServerConnection.getInstance().getLastMessages(service, conversation);
//                }
//            }
//        };
//        newMessagesHandler.postDelayed(newMessagesRun,1000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isServiceActive) {
                        Log.d("TAG","Chat service is running");
                        Thread.sleep(1500);
                        if (!registeredForUnreadConversation.isEmpty())
                            ServerConnection.getInstance().getUnreadMessages(service);
                        for (Long conversation :
                                registeredForNewMessages.keySet()) {
                            ServerConnection.getInstance().getLastMessages(service, conversation);
                        }
                    }
                }catch(InterruptedException e){}
            }
        }).start();
    }



    /**
     * Register callback, so it will be notified when there will be new unread conversations.
     *
     * @param contextToRegister callback notified after execution of an api call.
     */
    public void registerToService(UnreadConversationServiceCallback contextToRegister) {
        if (!registeredForUnreadConversation.contains(contextToRegister))
            registeredForUnreadConversation.add(contextToRegister);
    }

    /**
     * Unregister callback, so it will be no longer notified by unread conversations.
     *
     * @param contextToUnregister callback which won't be notified anymore.
     */
    public void unregisterFromService(UnreadConversationServiceCallback contextToUnregister) {
        registeredForUnreadConversation.remove(contextToUnregister);
    }

    /**
     * Register callback, so it will be notified when there will be new messages in specified conversation.
     *
     * @param contextToRegister callback notified after execution of an api call.
     * @param conversationID ID of conversation, where messages are.
     */
    public void registerToService(NewMessagesServiceCallback contextToRegister, long conversationID){
        registeredForNewMessages.put(conversationID,contextToRegister);
    }

    /**
     * Unregister callback, so it will be no longer notified by new messages.
     *
     * @param contextToUnregister callback which won't be notified anymore.
     */
    public void unregisterFromService(NewMessagesServiceCallback contextToUnregister){
        registeredForNewMessages.remove(contextToUnregister);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onGetUnreadMessagesSuccess(List<Long> conversationsIDs) {
        for (UnreadConversationServiceCallback registered:
                registeredForUnreadConversation) {
           if(registered != null) registered.onNewUnreadConversation(conversationsIDs);
        }
       //unreadConversationHandler.postDelayed(unreadConversationRun,5000);
    }

    @Override
    public void onGetUnreadMessagesFail(GetUnreadMessagesTask.GetUnreadMessagesError error) {
        //unreadConversationHandler.postDelayed(unreadConversationRun,5000);
    }

//    @Override
//    public void onGetMessageSuccess(Long conversationID, List<MessageResponse> messages) {
//        MessageResponse lastMessage = conversationLastMessages.get(conversationID);
//
//        if (lastMessage != null){
//            int newMessagesPosition = -1;
//            for(int i =0; i < messages.size(); i++){
//                if(messages.get(i).getId() == lastMessage.getId()){
//                    newMessagesPosition = i+1;
//                    break;
//                }
//            }
//            if(newMessagesPosition != -1){
//                messages = messages.subList(newMessagesPosition,messages.size());
//            }else{
//                messages.clear();
//            }
//        }
//        if(messages.size()!= 0) {
//            conversationLastMessages.remove(lastMessage);
//            lastMessage = messages.get(messages.size() - 1);
//            conversationLastMessages.put(conversationID,lastMessage);
//        }
//
//
//        NewMessagesServiceCallback callback = registeredForNewMessages.get(conversationID);
//        if (callback != null && messages.size() != 0) callback.onNewConversationMessages(messages);
//        else registeredForNewMessages.remove(conversationID);
//        //newMessagesHandler.postDelayed(newMessagesRun,5000);
//    }

    @Override
    public void onGetMessageSuccess(Long conversationID, List<MessageResponse> messages) {
//        List<MessageResponse> last20Messages = conversationLast20Messages.get(conversationID);
//        conversationLast20Messages.remove(last20Messages);
//        if (last20Messages != null && last20Messages.size() > 0){
//
//            for(MessageResponse msg: last20Messages){
//                if(messages.contains(msg)){
//                    messages.remove(msg);
//                }
//            }
//
//
//            int newMessagesCount = messages.size();
//            if (last20Messages.size() + newMessagesCount > 20) {
//                for (int i = 0; i < newMessagesCount; i++) {
//                    last20Messages.remove(i);
//                }
//            }
//            last20Messages.addAll(messages);
//        } else {
//            last20Messages = messages;
//        }
//        conversationLast20Messages.put(conversationID,last20Messages);

        NewMessagesServiceCallback callback = registeredForNewMessages.get(conversationID);
        if (callback != null) callback.onNewConversationMessages(messages);
        else registeredForNewMessages.remove(conversationID);
    }

    @Override
    public void onGetMessageFail(GetLastMessagesTask.GetMessagesError error) {
        //newMessagesHandler.postDelayed(newMessagesRun,5000);
    }

    public interface UnreadConversationServiceCallback {
        void onNewUnreadConversation(List<Long> conversationsIDs);
    }

    public interface NewMessagesServiceCallback {
        void onNewConversationMessages(List<MessageResponse> newMessages);
    }

    public void stopChatService(){
        isServiceActive = false;
    }
}

