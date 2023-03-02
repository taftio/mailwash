package taftio.mailwash.gmail;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class GmailConnectorTest {

  @Test
  void test() throws Exception {

    GmailConnector gmail = new GmailConnector();
    gmail.connect();

    fail("Not yet implemented");
  }

}
