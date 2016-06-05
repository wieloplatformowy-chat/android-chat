package pl.sggw.wzim.chat.server;


import pl.sggw.wzim.chat.server.tasks.*;
import pl.sggw.wzim.chat.swagger.model.*;

/**
 * @author Patryk Konieczny
 * @since 02.05.2016
 */
public class ServerConnection {
    private static ServerConnection ourInstance = new ServerConnection();
    private static TokenResponse userToken = null;

    public static ServerConnection getInstance() {
        return ourInstance;
    }

    private ServerConnection() {
    }

    /**
     * Asynchronously tries to register user on the server.
     *
     * @param context callback notified after execution of an api call.
     * @param email email of a new user.
     * @param name login of a new user.
     * @param password password of a new user.
     */
    public void register(RegisterTask.PostRegistrationCallback context,String email, String name, String password) {
        (new RegisterTask(context, email, name, password)).execute();
    }

    /**
     * Asynchronously tries to login user on the server.
     *
     * @param context callback notified after execution of an api call.
     * @param name login of user.
     * @param password password of user.
     */
    public void login(LoginTask.PostLoginCallback context, String name, String password) {
        (new LoginTask(context, name, password)).execute();
    }

    /**
     * Sets token of a logged user.
     * Na razie tylko przekazanie do klasy, prawdopodobnie później będą zmiany związane
     * z jego przetrzymywaniem.
     *
     * @param token new user token.
     */
    public void setUserToken(TokenResponse token){
        userToken = token;
    }

    /**
     * Asynchronously tries to get data of a logged user.
     * User must be logged in in order to use this method (otherwise it calls onWhoAmIFail callback).
     *
     * @param context callback notified after execution of an api call.
     */
    public void whoAmI(WhoAmITask.PostWhoAmICallback context){
        if (userToken != null) (new WhoAmITask(context,userToken.getToken())).execute();
        else context.onWhoAmIFail(WhoAmITask.WhoAmIError.LOGIN_REQUIRED);
    }

    /**
     * Asynchronously tries to logout logged user.
     * User must be logged in in order to use this method (otherwise it calls onLogoutFail callback).
     *
     * @param context callback notified after execution of an api call.
     */
    public void logout(LogoutTask.PostLogoutCallback context){
        if (userToken != null) (new LogoutTask(context,userToken.getToken())).execute();
        else context.onLogoutFail(LogoutTask.LogoutError.LOGIN_REQUIRED);
    }

    /**
     * Asynchronously tries to find user with given parameters.
     * User must be logged in in order to use this method (otherwise it calls onSearchUserFail callback).
     *
     * @param context callback notified after execution of an api call.
     * @param email email of a searched user.
     * @param name name of a searched user.
     */
    public void searchUser(SearchUserTask.PostSearchUserCallback context, String email, String name) {
        SearchUserParams userParams = new SearchUserParams();
        userParams.setEmail(email);
        userParams.setName(name);
        if (userToken != null) (new SearchUserTask(context, userParams,userToken.getToken())).execute();
        else context.onSearchUserFail(SearchUserTask.SearchUserError.LOGIN_REQUIRED);
    }

    /**
     * Asynchronously tries to get last 20 messages in given conversation.
     * User must be logged in in order to use this method (otherwise it calls onGetMessageFail callback).
     *
     * @param context callback notified after execution of an api call.
     * @param conversationID ID of conversation, where messages are.
     */
    public void getLastMessages(GetLastMessagesTask.PostGetMessageCallback context, long conversationID) {
        if (userToken != null) (new GetLastMessagesTask(context, conversationID, userToken.getToken())).execute();
        else context.onGetMessageFail(GetLastMessagesTask.GetMessagesError.LOGIN_REQUIRED);
    }

    /**
     * Asynchronously tries to get 20 messages before given message in given conversation.
     * User must be logged in in order to use this method (otherwise it calls onGetEarlierMessagesFail callback).
     *
     * @param context callback notified after execution of an api call.
     * @param lastMessageID ID of message, it will search for messages before this.
     * @param conversationID ID of conversation, where messages are.
     */
    public void getEarlierMessages(GetEarlierMessagesTask.PostGetEarlierMessagesCallback context, long lastMessageID, long conversationID){
        if (userToken != null) (new GetEarlierMessagesTask(context,lastMessageID, conversationID, userToken.getToken())).execute();
        else context.onGetEarlierMessagesFail(GetEarlierMessagesTask.GetEarlierMessagesError.LOGIN_REQUIRED);
    }

    /**
     * Asynchronously tries to get send message in given conversation.
     * User must be logged in in order to use this method (otherwise it calls onSendMessageFail callback).
     *
     * @param context callback notified after execution of an api call.
     * @param conversationID ID of conversation, where messages are.
     * @param message content of message
     */
    public void SendMessage(SendMessageTask.SendMessageCallback context, long conversationID, String message){
        SendMessageParams messageParams = new SendMessageParams();
        messageParams.setConversationId(conversationID);
        messageParams.setMessage(message);
        if (userToken != null) (new SendMessageTask(context, messageParams, userToken.getToken())).execute();
        else context.onSendMessageFail(SendMessageTask.SendMessageError.LOGIN_REQUIRED);
    }

    /**
     * Asynchronously tries to get send message in given conversation.
     * User must be logged in in order to use this method (otherwise it calls onGetUnreadMessagesFail callback).
     *
     * @param context callback notified after execution of an api call.
     */
    public void GetUnreadMessages(GetUnreadMessagesTask.PostGetUnreadMessagesCallback context){
        if (userToken != null) (new GetUnreadMessagesTask(context, userToken.getToken())).execute();
        else context.onGetUnreadMessagesFail(GetUnreadMessagesTask.GetUnreadMessagesError.LOGIN_REQUIRED);
    }

    /**
     * Asynchronously tries to get conversation with given user
     * User must be logged in in order to use this method (otherwise it calls onGetConversationsFail callback).
     *
     * @param context callback notified after execution of an api call.
     * @param userID ID of user you want a conversation with.
     */
    public void GetConversation(GetConversationTask.PostGetConversationCallback context, Long userID)
    {
        if (userToken != null) (new GetConversationTask(context, userID, userToken.getToken())).execute();
        else context.onGetConversationsFail(GetConversationTask.GetConversationsError.LOGIN_REQUIRED);
    }

    /**
     * Asynchronously tries to add user as a friend.
     * User must be logged in in order to use this method (otherwise it calls onAddFriendFail callback).
     *
     * @param context callback notified after execution of an api call.
     * @param userID ID of user you want to add as a friend.
     */
    public void AddFriend(AddFriendTask.PostAddFriendCallback context, Long userID){
        if (userToken != null) (new AddFriendTask(context, userID, userToken.getToken())).execute();
        else context.onAddFriendFail(AddFriendTask.AddFriendError.LOGIN_REQUIRED);
    }

    /**
     * Asynchronously tries to add delete user from friendlist.
     * User must be logged in in order to use this method (otherwise it calls onDeleteFriendFail callback).
     *
     * @param context callback notified after execution of an api call.
     * @param userID ID of user you want to delete from friends.
     */
    public void DeleteFriend(DeleteFriendTask.PostDeleteFriendCallback context, Long userID){
        if (userToken != null) (new DeleteFriendTask(context, userID, userToken.getToken())).execute();
        else context.onDeleteFriendFail(DeleteFriendTask.DeleteFriendError.LOGIN_REQUIRED);
    }

    /**
     * Asynchronously tries to get friendlist of logged user from server.
     * User must be logged in in order to use this method (otherwise it calls onMyFriendsFail callback).
     *
     * @param context callback notified after execution of an api call.
     */
    public void MyFriends(MyFriendsTask.PostMyFriendsCallback context){
        if (userToken != null) (new MyFriendsTask(context, userToken.getToken())).execute();
        else context.onMyFriendsFail(MyFriendsTask.MyFriendsError.LOGIN_REQUIRED);
    }


}
