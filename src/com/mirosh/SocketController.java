package com.mirosh;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketController {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public SocketController(Socket socket) {
        try {
            setSocket(socket);
            setBufferedReader(this.socket);
            setBufferedWriter(this.socket);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public SocketController(ServerSocket serverSocket) {
        try {
            setSocket(serverSocket.accept());
            setBufferedReader(this.socket);
            setBufferedWriter(this.socket);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void send(String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public String receive() {
        try {
            return bufferedReader.readLine();
        }catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void closeAll() {
        try {
            bufferedWriter.close();
            bufferedReader.close();
            socket.close();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private void setSocket(Socket socket) {
        this.socket = socket;
    }

    private void setBufferedReader(Socket socket) throws IOException {
        if (socket == null) {
            return;
        }
        this.bufferedReader = createBufferedReader(socket.getInputStream());
    }

    private void setBufferedWriter(Socket socket) throws IOException {
        if (socket == null) {
            return;
        }
        this.bufferedWriter = createBufferedWriter(socket.getOutputStream());
    }

    private BufferedReader createBufferedReader(InputStream stream) {
        return new BufferedReader(new InputStreamReader(stream));
    }

    private BufferedWriter createBufferedWriter (OutputStream stream) {
        return new BufferedWriter(new OutputStreamWriter(stream));
    }

}
