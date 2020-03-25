package backend.Authentication;


import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MyLoginModule implements LoginModule {
    private boolean isAuthenticated = false;
    private CallbackHandler callbackHandler;
    private Subject subject;
    private Set<MyPrincipal> principals;
    private Map<String, ?> options;

    public void initialize(Subject subject, CallbackHandler callbackHandler,
                           Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.options = options;
    }

    public boolean login() throws LoginException {
        if (callbackHandler == null)
            throw new LoginException("no handler");

        NameCallback nameCall = new NameCallback("username: ");
        PasswordCallback passCall = new PasswordCallback("password: ", false);
        final Callback[] calls = new Callback[]{nameCall, passCall};
        try {
            callbackHandler.handle(calls);
        } catch (UnsupportedCallbackException e) {
            LoginException e2 = new LoginException("Unsupported callback");
            e2.initCause(e);
            throw e2;
        } catch (IOException e) {
            LoginException e2 = new LoginException("I/O exception in callback");
            e2.initCause(e);
            throw e2;
        }

        return checkLogin(nameCall.getName(), passCall.getPassword());
    }

    private boolean checkLogin(String username, char[] password) throws LoginException {
        try {
            Scanner in = new Scanner(new FileReader("" + options.get("pwfile")));
            while (in.hasNextLine()) {
                String[] inputs = in.nextLine().split("\\|");
                if (inputs[0].equals(username) && Arrays.equals(inputs[1].toCharArray(), password)) {
                    String role = inputs[2];
                    role.trim();
                    principals = new HashSet<>();
                    principals.add(new MyPrincipal("username", username));
                    principals.add(new MyPrincipal("role", role));
                    isAuthenticated = true;
                    return true;
                }
            }
            in.close();
            return false;
        } catch (Exception e) {
            LoginException e2 = new LoginException("Can't open password file");
            e2.initCause(e);
            throw e2;
        }
    }

    public boolean commit() throws LoginException {
        if(isAuthenticated) {
            for(MyPrincipal p : principals) {
                subject.getPrincipals().add(p);
            }
        } else {
            throw new LoginException("Authentication failure");
        }
        return isAuthenticated;
    }

    public boolean abort() throws LoginException {
        principals = null;
        isAuthenticated = false;
        return false;
    }

    public boolean logout() throws LoginException {
        for(MyPrincipal p : principals) {
            subject.getPrincipals().remove(p);
        }
        principals = null;
        return true;
    }

}
