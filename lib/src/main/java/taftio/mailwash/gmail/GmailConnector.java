package taftio.mailwash.gmail;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class GmailConnector {

  public void connect() throws Exception {

    NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();

    JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
    GoogleClientSecrets gcs;
    try (Reader reader = new InputStreamReader(getClass().getResourceAsStream("/client_secrets.json"))) {
      gcs = GoogleClientSecrets.load(jsonFactory, reader);
    }

    Collection<String> scopes = Collections.singleton(GmailScopes.GMAIL_MODIFY);

    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(transport, jsonFactory, gcs,
        scopes).build();

    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

    Gmail service = new Gmail.Builder(transport, jsonFactory, credential).setApplicationName("Mail Wash").build();

    // Print the labels in the user's account.
    String user = "me";
    ListLabelsResponse listResponse = service.users().labels().list(user).execute();
    List<Label> labels = listResponse.getLabels();
    if (labels.isEmpty()) {
      System.out.println("No labels found.");
    } else {
      System.out.println("Labels:");
      for (Label label : labels) {
        System.out.printf("- %s\n", label.getName());
      }
    }

  }
}
