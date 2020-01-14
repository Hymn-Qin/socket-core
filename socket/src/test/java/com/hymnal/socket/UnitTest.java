package com.hymnal.socket;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kotlin.Result;

import com.hymnal.socket.defaultprotocol.Pack;
import com.hymnal.socket.defaultprotocol.ProtocolCodecFactoryImpl;

public class UnitTest {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger("test");
        SocketClient client = new SocketClient.Builder()
                .setType(SocketClient.Type.TCP, true)
                .setTag("Socket")
                .setIp("10.202.91.98", 7085)
                .setCodecFactory(

                        new ProtocolCodecFactoryImpl(new Pack("5aa5", 2, 4))
                )
                .setResponse(result -> {

                    if (JavaAdapter.isSuccess(result)) {
                        Object data = JavaAdapter.getData(result);
                        logger.info(data.toString());
                    }

                    if (JavaAdapter.isFailure(result)) {
                        if (JavaAdapter.getException(result) instanceof SocketException) {

                        }
                    }

                })
                .builder();

        client.send("{\"token\":\"60f0429ecd41eae4321114bf64274a02\",\"type\":\"3\"}");
    }
}
