package elagin.dmitry;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(50);

        try (ServerSocket server = new ServerSocket(3333)) {


            while (true) {
                System.out.println("Сервер ожидает соединения...");
                Socket socket = server.accept();
                System.out.println("Соединение принято");
                service.execute(new Connection(socket));
            }

        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода!\n" + e.getMessage());
        }
    }
}
