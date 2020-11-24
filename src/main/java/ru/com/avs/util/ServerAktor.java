package ru.com.avs.util;


import abstractions.Cypher;
import impl.JAktor;


import javax.swing.*;
import java.io.IOException;

public class ServerAktor extends JAktor {
    private Cypher cypher;
    public OnApproved onapproved;

    public void setCypher(Cypher cypher) {
        this.cypher = cypher;
    }

    @Override
    public int send(byte[] message, String address) throws IOException {
        return this.client.send(this.cypher.encrypt(message), address);
    }

    @Override
    public void receive(byte[] message_) throws IOException {

        byte[] message = cypher.decrypt(message_);
        System.out.println("\n\n\n\nCATCHED SOMETHING!!!!!!");
        try {
            onapproved.passed();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}