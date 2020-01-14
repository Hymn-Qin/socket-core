package com.hymnal.socket;

import org.json.JSONException;

public interface SocketCallback {
    void result(Result<String> result) throws JSONException;
}
