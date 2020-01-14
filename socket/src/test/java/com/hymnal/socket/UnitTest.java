package com.hymnal.socket;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hymnal.socket.defaultprotocol.Pack;
import com.hymnal.socket.defaultprotocol.ProtocolCodecFactoryImpl;

public class UnitTest {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger("test");
        SocketClient client = new SocketClient.Builder()
                .setType(SocketClient.Type.TCP, true)
                .setTag("Socket")
                .setIp("10.202.40.218", 10005)
                .setCodecFactory(

                        new ProtocolCodecFactoryImpl(new Pack("5aa5", 2, 4))
                )
                .setSocketCallBack(result -> {
                    if (result.isSuccess()) {
                        String data = result.getOrThrow();
                        logger.info("qxj: {}", data);

                    }

                    if (result.isFailure()) {
                        logger.error("异常: {}", result.exceptionOrNull());
                        if (result.exceptionOrNull() instanceof SocketException) {

                        }
                    }

                })
                .builder();

//        client.send("{\"token\":\"60f0429ecd41eae4321114bf64274a02\",\"type\":\"3\"}");
    }
}
