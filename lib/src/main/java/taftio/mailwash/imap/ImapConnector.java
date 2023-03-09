package taftio.mailwash.imap;

import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

public final class ImapConnector {

  private final String host;
  private final String user;
  private final String pass;

  public ImapConnector(String host, String user, String pass) {
    this.host = host;
    this.user = user;
    this.pass = pass;
  }

  public void connect() throws Exception {
    Properties props = new Properties();
    Session session = Session.getInstance(props);

    try (Store store = session.getStore("imaps")) {
      store.connect(host, user, pass);
      System.out.println(store);

      System.out.println("personal namespaces:");
      for (Folder f : store.getPersonalNamespaces()) {
        System.out.println(f.getFullName());
      }

      System.out.println("shared namespaces:");
      for (Folder f : store.getSharedNamespaces()) {
        System.out.println(f);
      }

      Folder root = store.getDefaultFolder();
      System.out.println("root: " + root);

      for (Folder f : root.list("*")) {
        System.out.println("list: " + f + " " + f.getType()
        + " holds_messages=" + (f.getType() & Folder.HOLDS_MESSAGES)
        + " holds_folders=" + (f.getType() & Folder.HOLDS_FOLDERS));
      }

      Folder inbox = store.getFolder("Inbox");
      inbox.open(Folder.READ_ONLY);
      Message messages[] = inbox.getMessages();
      for (Message message : messages) {
        System.out.println(message);
      }
      inbox.close();
    }

  }
}
