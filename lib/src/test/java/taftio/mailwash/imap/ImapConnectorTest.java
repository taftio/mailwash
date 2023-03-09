package taftio.mailwash.imap;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class ImapConnectorTest {

  @Test
  void test() throws Exception {

    String host = System.getProperty("mailwash.host");
    String user = System.getProperty("mailwash.user");
    String pass = System.getProperty("mailwash.pass");

    ImapConnector connector = new ImapConnector(host, user, pass);
    connector.connect();

    fail("Not yet implemented");
  }

}
