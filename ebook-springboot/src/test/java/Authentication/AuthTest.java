package Authentication;

import backend.Authentication.MyAction;
import backend.Authentication.MyCallbackHandler;
import com.sun.security.auth.UnixPrincipal;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.util.Set;

public class AuthTest {
    public static void main(final String[] args) {
        try {
            System.setSecurityManager(new SecurityManager());
            String username = "harry";
            char[] password = new char[]{'s', 'e', 'c', 'r', 'e', 't'};
//            String username = "carl";
//            char[] password = new char[]{'g', 'u', 'e', 's', 's', 'm', 'e'};
            LoginContext context = new LoginContext("Login1", new MyCallbackHandler(username, password));
            context.login();
            System.out.println("Authentication successful.");
            Subject subject = context.getSubject();
            System.out.println("subject=" + subject);


            Set<Principal> ps = subject.getPrincipals();
            ps.add(new UnixPrincipal("admin"));
            Principal[] principals = subject.getPrincipals().toArray(new Principal[0]);
            for (Principal p : principals) {
                System.out.println(p.getName());
            }

            PrivilegedAction action = new MyAction("os.name");
            Object result = Subject.doAsPrivileged(subject, action, AccessController.getContext());
            System.out.println(result);
            context.logout();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
