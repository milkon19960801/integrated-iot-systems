package websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;



@ServerEndpoint("/ws")
public class Server {

    public  static String  getMsg() throws MalformedURLException {
        String res,msg,temp,time;
        URL u = new URL("https://mdna.azurewebsites.net/api/GetDataFromTableStorage?limitRows=1&orderBy=desc");

        try (InputStream in = u.openStream()) {
            Object obj = new String(in.readAllBytes());
            msg = obj.toString();

            temp = msg.substring(16,21);
            StringBuilder sb = new StringBuilder(temp);
            temp= sb.toString();

            time = msg.substring(103,122);
            StringBuilder sb1 = new StringBuilder(time);

            time= sb1.toString();



            res =temp+time;
            return res;

        } catch (IOException e) { e.printStackTrace(); }
        return  "";
    }
    @OnOpen
    public void open(Session session) throws IOException {
//            session.getBasicRemote().sendText(getMsg());
    }

    @OnClose
    public void close(Session session){
    }

    @OnMessage
    public void handleMessage(String message, Session session) throws IOException, EncodeException, InterruptedException {
        while (true)
        {
            for (Session peer : session.getOpenSessions()) {
                    peer.getBasicRemote().sendText(getMsg());
                    Thread.sleep(5500);
            }
        }

    }
}