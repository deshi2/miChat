package com.mirosh;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Rules the connection via TCP/IP sockets.
 */
public class SocketController {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public SocketController(Socket socket) {
        try {
            setSocket(socket);
            setBufferedReader();
            setBufferedWriter();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public SocketController(ServerSocket serverSocket) {
        try {
            setSocket(serverSocket.accept());
            setBufferedReader();
            setBufferedWriter();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Send string in JSON to the server.
     * @param message string to be sent.
     */
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

    /**
     * Close all conections for this socket.
     */
    public void closeAll() {
        try {
            bufferedWriter.close();
            bufferedReader.close();
            socket.close();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private BufferedReader createBufferedReader(InputStream stream) {
        return new BufferedReader(new InputStreamReader(stream));
    }

    private BufferedWriter createBufferedWriter (OutputStream stream) {
        return new BufferedWriter(new OutputStreamWriter(stream));
    }

    /* SETTERS AND GETTERS */

    private void setSocket(Socket socket) {
        this.socket = socket;
    }

    private Socket getSocket() {
        return this.socket;
    }

    private void setBufferedReader() throws IOException {
        if (getSocket() == null) {
            return;
        }
        this.bufferedReader = createBufferedReader(getSocket().getInputStream());
    }

    private void setBufferedWriter() throws IOException {
        if (getSocket() == null) {
            return;
        }
        this.bufferedWriter = createBufferedWriter(getSocket().getOutputStream());
    }

}