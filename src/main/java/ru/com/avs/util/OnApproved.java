package ru.com.avs.util;

import java.io.IOException;

public interface OnApproved {
    public void passed() throws IOException, InterruptedException;
}
