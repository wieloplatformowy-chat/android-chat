package pl.sggw.wzim.chat.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.os.Handler;

import pl.sggw.wzim.chat.server.tasks.GetLastMessagesTask;
import pl.sggw.wzim.chat.server.tasks.GetUnreadMessagesTask;
import pl.sggw.wzim.chat.swagger.model.MessageResponse;

/**
 * @author Patryk Konieczny
 * @since 11.06.2016
 */
public class ChatService extends Service implements GetUnreadMessagesTask.PostGetUnreadMessagesCallback, GetLastMessagesTask.PostGetMessageCallback {

    private Handler unreadConversationHandler;
    private Runnable unreadConversationRun;
    private Handler newMessagesHandler;
    private Runnable newMessagesRun;

    private List<UnreadConversationServiceCallback> registeredForUnreadConversation;
    private Map<Long, NewMessagesServiceCallback> registeredForNewMessages;

    private Map<Long, List<MessageResponse>> conversationLast20Messages;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int onStart = super.onStartCommand(intent, flags, startId);
        registeredForUnreadConversation = new LinkedList<>();
        registeredForNewMessages = new HashMap<>();
        conversationLast20Messages = new HashMap<>();
        final ChatService service = this;

        unreadConversationHandler = new Handler();
        unreadConversationRun = new Runnable() {
            @Override
            public void run() {
                if (!registeredForUnreadConversation.isEmpty()) ServerConnection.getInstance().getUnreadMessages(service);
            }
        };
        unreadConversationHandler.postDelayed(unreadConversationRun,1000);

        newMessagesHandler = new Handler();
        newMessagesRun = new Runnable() {
            @Override
            public void run() {
                for (Long conversation:
                        registeredForNewMessages.keySet()) {
                    ServerConnection.getInstance().getLastMessages(service, conversation);
                }
            }
        };

        return onStart;
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
            registered.onNewUnreadConversation(conversationsIDs);
        }
        unreadConversationHandler.postDelayed(unreadConversationRun,5000);
    }

    @Override
    public void onGetUnreadMessagesFail(GetUnreadMessagesTask.GetUnreadMessagesError error) {
        unreadConversationHandler.postDelayed(unreadConversationRun,5000);
    }

    @Override
    public void onGetMessageSuccess(Long conversationID, List<MessageResponse> messages) {
        List<MessageResponse> last20Messages = conversationLast20Messages.get(conversationID);
        if (last20Messages != null && last20Messages.size() > 0){
            messages.removeAll(last20Messages);
            int newMessagesCount = messages.size();
            if (last20Messages.size() + newMessagesCount > 20) {
                for (int i = 0; i < newMessagesCount; i++) {
                    last20Messages.remove(i);
                }
            }
            last20Messages.addAll(messages);
        } else {
            last20Messages = messages;
        }
        NewMessagesServiceCallback callback = registeredForNewMessages.get(conversationID);
        if (callback != null) callback.onNewConversationMessages(messages);
        else registeredForNewMessages.remove(conversationID);
        newMessagesHandler.postDelayed(newMessagesRun,5000);
    }

    @Override
    public void onGetMessageFail(GetLastMessagesTask.GetMessagesError error) {
        newMessagesHandler.postDelayed(newMessagesRun,5000);
    }

    public interface UnreadConversationServiceCallback {
        void onNewUnreadConversation(List<Long> conversationsIDs);
    }

    public interface NewMessagesServiceCallback {
        void onNewConversationMessages(List<MessageResponse> newMessages);
    }
}

