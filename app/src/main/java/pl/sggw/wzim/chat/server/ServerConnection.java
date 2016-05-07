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

    public static ServerConnection getInstance() {
        return ourInstance;
    }

    private ServerConnection() {
    }

    /**
     * Asynchronously tries to register user on the server.
     *
     * @param context activity to call after method is finished. Has to implement RegisterTask.PostRegistrationCallback.
     * @param email email of new user.
     * @param name login of new user.
     * @param password password of new user.
     *
     * @author  Patryk Konieczny
     * @since   2016-05-07
     */
    public void register(RegisterTask.PostRegistrationCallback context,String email, String name, String password) {
        (new RegisterTask(context)).execute(email, name, password);
    }

    /**
     * Asynchronously tries to login user on the server.
     *
     * @param context activity to call after method is finished. Has to implement LoginTask.PostLoginCallback.
     * @param name login of new user.
     * @param password password of new user.
     *
     * @author  Patryk Konieczny
     * @since   2016-05-07
     */
    public void login(LoginTask.PostLoginCallback context, String name, String password) {
        (new LoginTask(context)).execute(name,password);
    }

}
