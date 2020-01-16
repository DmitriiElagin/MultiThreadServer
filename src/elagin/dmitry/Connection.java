package elagin.dmitry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection implements Runnable {
    private Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            String ip = socket.getInetAddress().getHostAddress();
            System.out.println(ip + " подключился.");
            while (!socket.isClosed()) {
                if (in.available() > 0) {
                    String msg = in.readUTF();
                    System.out.println(ip + ": " + msg);
                    if (!msg.equalsIgnoreCase("quit")) {
                        out.writeUTF("Вы: " + msg);
                        out.flush();
                    } else {
                        out.writeUTF("ОК");
                        in.close();
                        out.close();
                        socket.close();
                    }
                }

            }
            System.out.println(ip + " отключен.");

        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода\n" + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Ошибка закрытия сокета\n" + e.getMessage());
            }
        }

    }
}
