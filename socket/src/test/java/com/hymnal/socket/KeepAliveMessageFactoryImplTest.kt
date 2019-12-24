package com.hymnal.socket

import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory
import org.slf4j.LoggerFactory

internal class KeepAliveMessageFactoryImplTest : KeepAliveMessageFactory {
    private val logger by lazy { LoggerFactory.getLogger(KeepAliveMessageFactoryImplTest::class.java) }

    private val HEARTBEATREQUEST = "0x11"
    private val HEARTBEATRESPONSE = "0x22"

    override fun isRequest(ioSession: IoSession, o: Any): Boolean {
        logger.info("请求心跳包信息: {}", o)
        if (o == HEARTBEATREQUEST) {
            return true
        }
        return false
    }

    override fun isResponse(ioSession: IoSession, o: Any): Boolean {
//        logger.info("响应心跳包信息: {}", o)
//        if (o == HEARTBEATRESPONSE) {
//            return true
//        }
        return false
    }

    override fun getRequest(ioSession: IoSession): Any? {
//        logger.info("请求预设信息")
//        return HEARTBEATREQUEST
        return null
    }

    override fun getResponse(ioSession: IoSession, o: Any): Any? {
//        logger.info("响应预设信息")
        return HEARTBEATRESPONSE
//        return null
    }

}
