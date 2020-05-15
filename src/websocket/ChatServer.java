package websocket;

import com.google.gson.Gson;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/ws")
public class ChatServer {
    private Map<String, String> usernames = new HashMap<String, String>();

    @OnOpen
    public void open(Session session) throws IOException, EncodeException {


        URL u = new URL("https://mdna.azurewebsites.net/api/GetDataFromTableStorage?limitRows=1&orderBy=desc");
        try (InputStream in = u.openStream()) {
            Object obj = new String(in.readAllBytes());
            session.getBasicRemote().sendText("Senaste meddelandet."+ obj.toString() );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void close(Session session) throws IOException, EncodeException {
    }

    @OnMessage
    public void handleMessage(String message, Session session) throws IOException, EncodeException, InterruptedException {
        String userId = session.getId();
        while (!usernames.containsValue(message))
        {
            String username = usernames.get(userId);
            for (Session peer : session.getOpenSessions())
            {
                URL u = new URL("https://mdna.azurewebsites.net/api/GetDataFromTableStorage?limitRows=1&orderBy=desc");

                try (InputStream in = u.openStream())
                {
                     Object obj = new String(in.readAllBytes());
                    peer.getBasicRemote().sendText("Senaste meddelandet."+ obj.toString());
                    Thread.sleep(7000);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}