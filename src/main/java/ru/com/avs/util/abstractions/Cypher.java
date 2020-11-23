package ru.com.avs.util.abstractions;

public interface Cypher {
    public byte [] decrypt(byte []input);
    public byte [] encrypt(byte []input);      
    
    
}
