package pl.sggw.wzim.chat.server;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import pl.sggw.wzim.chat.server.tasks.*;
import pl.sggw.wzim.chat.swagger.*;
import pl.sggw.wzim.chat.swagger.model.*;
import pl.sggw.wzim.chat.swagger.api.*;

/**
 * @author Patryk Konieczny
 * @since 02.05.2016
 */
public class ServerConnection {
    private static ServerConnection ourInstance = new ServerConnection();
    private static TokenDto userToken = null;

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
    public void setUserToken(TokenDto token){
        userToken = token;
    }

}
