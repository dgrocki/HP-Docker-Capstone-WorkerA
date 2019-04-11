package com.hp.pwp.capstone

import java.net.URI;
import java.util.concurrent.Future;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;

public class EventClient
{
    public static void connect()
    {
        URI uri = URI.create("ws://workmanager/workManager/socket/");

        WebSocketClient client = new WebSocketClient();
        try
        {
            try
            {
                client.start();
                // The socket that receives events
                WebSocket socket = new WebSocket();
                // Attempt Connect
                Future<Session> fut = client.connect(socket,uri);
                // Wait for Connect
                Session session = fut.get();
                // Send a message
                session.getRemote().sendString("Ready For Work");
                // Close session
                session.close();
            }
            finally
            {
            }
        }
        catch (Throwable t)
        {
            t.printStackTrace(System.err);
        }
    }
}
