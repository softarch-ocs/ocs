package services.security;

import java.util.Base64;
import data.entities.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.ldap.Rdn;
import services.exceptions.OcsServiceException;

public class LDAPService {

    private final static String PROVIDER_URL = "ldap://arch-ldap:389/";
    private final static String USER_QUERY = "cn=%s,ou=ocs,dc=ocs,dc=com";
    private final static String INITIAL_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
    private final static String LDAP_ADMIN = "cn=admin,dc=ocs,dc=com";
    private final static String LDAP_PASSWORD = "ArqSoft2016i";

    public boolean login(String user, String password) {

        Hashtable<String, Object> env = new Hashtable<String, Object>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, PROVIDER_URL);

        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, String.format(USER_QUERY, Rdn.escapeValue(user)));
        env.put(Context.SECURITY_CREDENTIALS, password);
        try {
            DirContext ctx = new InitialDirContext(env);
            ctx.close();
        } catch (NamingException ex) {
            return false;
        }
        return true;

    }

    public void register(User user) {

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, PROVIDER_URL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, LDAP_ADMIN);
        env.put(Context.SECURITY_CREDENTIALS, LDAP_PASSWORD);

        String entryDN = String.format(USER_QUERY, Rdn.escapeValue(user.getEmail()));

        // entry's attributes  
        DirContext ctx = null;

        try {
            ctx = new InitialDirContext(env);

            BasicAttributes entry = new BasicAttributes();

            Attribute oc;
            oc = new BasicAttribute("objectClass");
            oc.add("top");
            oc.add("inetOrgPerson");

            entry.put(new BasicAttribute("cn", user.getEmail()));
            entry.put(new BasicAttribute("sn", user.getLastName()));
            entry.put(new BasicAttribute("givenName", user.getFirstName()));
            entry.put(new BasicAttribute("userPassword", "{MD5}" + digest(user.getPassword())));
            entry.put(oc);

            ctx.createSubcontext(entryDN, entry);

        } catch (NamingException e) {
            System.err.println("AddUser: error adding entry." + e);
            throw new OcsServiceException(e);
        }
    }

    public String digest(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] passwordInBytes = password.getBytes();
            return new String(Base64.getEncoder().encode(md5.digest(passwordInBytes)));

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LDAPService.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }

    }

}
