package ro.webdata.normalization.timespan;

import py4j.GatewayServer;

public class Gateway {
    int port;

    public Gateway(int port) {
        this.port = port;
    }

    private GatewayServer server;

    public void startServer() {
        System.out.println("Gateway Server is starting on port " + this.port);

        this.server = new GatewayServer(this, this.port);
        this.server.start();

        System.out.println("Gateway Server successfully started on port " + server.getListeningPort());
    }

    public void stopServer(int ms) {
        new Thread(() -> {
            try {
                // Give Python time to disconnect to avoid the Py4J network error
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Shutting down Gateway Server...");
            this.server.shutdown();
        }).start();
    }
}
