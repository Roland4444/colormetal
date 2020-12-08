package ru.com.avs.util;

import java.io.IOException;

public interface OnDeclined {
    public void declined() throws IOException, InterruptedException;
}
