package pl.sggw.wzim.chat;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

public class LoginRegistrationTest {

    ServerConnection serverConnection;

    @Before
    public void prepare() {
        serverConnection = ServerConnection.getInstance();
    }

    @Test
    public void loginExisting() {
        String login = "test0";
        String password = "test0";
        String response = serverConnection.login(login, password);

        printLoginPasswordResponse(login, password, response);
    }

    @Test
    public void loginRandom() {
        Random random = new Random();

        String login = "test" + random.nextLong();
        String password = "pass" + random.nextLong();
        String response = serverConnection.login(login, password);

        printLoginPasswordResponse(login, password, response);
    }

    @Test
    public void registerExisting() {
        String login = "test0";
        String password = "test0";
        String response = serverConnection.register(login, password);

        printLoginPasswordResponse(login, password, response);
    }

    @Test
    public void registerRandom() {
        Random random = new Random();

        String login = "test" + random.nextLong();
        String password = "pass";
        String response = serverConnection.register(login, password);

        printLoginPasswordResponse(login, password, response);
    }

    private void printLoginPasswordResponse(String login, String password, String response) {
        System.out.printf("Login: %s; Password: %s\nResponse:\n%s\n", login, password, response);
    }
}
