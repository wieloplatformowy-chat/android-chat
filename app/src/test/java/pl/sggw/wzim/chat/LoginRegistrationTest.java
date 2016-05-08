package pl.sggw.wzim.chat;

import org.junit.Before;
import org.junit.Test;
import org.junit.*;
import java.util.Random;

import pl.sggw.wzim.chat.server.ServerConnection;

public class LoginRegistrationTest {

    ServerConnection serverConnection;

    @Before
    public void prepare() {
        serverConnection = ServerConnection.getInstance();
    }

    @Test
    public void loginExisting() {
        System.out.printf("loginExisting:");
        String login = "test0";
        String password = "test0";
        String response = serverConnection.login(login, password);

        printLoginPasswordResponse(login, password, response);
        Assert.assertEquals(response,"success");
    }

    @Test
    public void loginRandom() {
        System.out.printf("loginRandom:");
        Random random = new Random();

        String login = "test" + random.nextLong();
        String password = "pass" + random.nextLong();
        String response = serverConnection.login(login, password);

        printLoginPasswordResponse(login, password, response);
        Assert.assertNotEquals(response, "success");
    }

    @Test
    public void registerExisting() {
        System.out.printf("registerExisting:");
        String login = "test0";
        String password = "test0";
        String response = serverConnection.register(login, password);

        printLoginPasswordResponse(login, password, response);
        Assert.assertNotEquals(response, "success");
    }

    @Test
    public void registerRandom() {
        System.out.printf("registerRandom:");
        Random random = new Random();

        String login = "test" + random.nextLong();
        String password = "pass";
        String response = serverConnection.register(login, password);

        printLoginPasswordResponse(login, password, response);
        Assert.assertEquals(response, "success");
    }



    private void printLoginPasswordResponse(String login, String password, String response) {
        System.out.printf("Login: %s; Password: %s\nResponse:\n%s\n", login, password, response);
    }
}
