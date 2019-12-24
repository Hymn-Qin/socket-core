package com.hymnal.socket

import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory
import org.slf4j.LoggerFactory

internal class KeepAliveMessageFactoryImpl : KeepAliveMessageFactory {
    private val logger by lazy { LoggerFactory.getLogger("KeepAlive") }

    private val HEARTBEATREQUEST = "0x11"
    private val HEARTBEATRESPONSE = "0x22"

    //判断是否心跳请求包  是的话返回true
    //服务器不会给客户端发送请求包，因此不关注请求包，直接返回false
    override fun isRequest(ioSession: IoSession, o: Any): Boolean {

//        if (o == HEARTBEATREQUEST) {
//            logger.info("请求心跳包信息: {}", o)
//            return true
//        }
        return false
    }

    //由于被动型心跳机制，没有请求当然也就不关注反馈 因此直接返回false
    //客户端关注请求反馈，因此判断mesaage是否是反馈包
    override fun isResponse(ioSession: IoSession, o: Any): Boolean {
        if (o == HEARTBEATRESPONSE) {
            logger.info("响应心跳包信息: {}", o)
            return true
        }
        return false
    }

    // 被动型心跳机制无请求  因此直接返回null
    // 获取心跳请求包 non-null
    override fun getRequest(ioSession: IoSession): Any? {
        logger.info("请求预设信息")
        return HEARTBEATREQUEST
    }

    // 根据心跳请求request 反回一个心跳反馈消息 non-nul
    // 服务器不会给客户端发送心跳请求，客户端当然也不用反馈  该方法返回null
    override fun getResponse(ioSession: IoSession, o: Any): Any? {
//        logger.info("响应预设信息: {}", o)
//        return HEARTBEATRESPONSE
        return null
    }

}
