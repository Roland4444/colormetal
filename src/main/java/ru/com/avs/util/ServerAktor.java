package ru.com.avs.util;


import impl.JAktor;
import ru.com.avs.util.abstractions.Cypher;

import java.io.IOException;

public class ServerAktor extends JAktor {
    private Cypher cypher;

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

    }
}