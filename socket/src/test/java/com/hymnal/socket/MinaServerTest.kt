package com.hymnal.socket

import com.hymnal.socket.defaultprotocol.Pack
import com.hymnal.socket.defaultprotocol.ProtocolCodecFactoryImpl
import org.apache.mina.core.filterchain.IoFilter
import org.apache.mina.core.service.IoAcceptor
import org.apache.mina.core.session.IdleStatus
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.filter.codec.textline.LineDelimiter
import org.apache.mina.filter.codec.textline.TextLineCodecFactory
import org.apache.mina.filter.executor.ExecutorFilter
import org.apache.mina.filter.keepalive.KeepAliveFilter
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler
import org.apache.mina.filter.logging.LoggingFilter
import org.apache.mina.transport.socket.nio.NioSocketAcceptor
import org.slf4j.LoggerFactory
import java.io.IOException
import java.net.InetSocketAddress
import java.nio.charset.Charset
import java.util.concurrent.Executors


object MinaServerTest {
    private val logger = LoggerFactory.getLogger(MinaServerTest::class.java)

    // 端口
    private val MINA_PORT = 7085

    @JvmStatic
    fun main(args: Array<String>) {
        val acceptor: IoAcceptor
        try {
            // 创建一个非阻塞的服务端server
            acceptor = NioSocketAcceptor()
            // 此行代码能让你的程序整体性能提升10倍
            acceptor.getFilterChain().addLast(
                "threadPool",
                ExecutorFilter(Executors.newCachedThreadPool())
            )
//            // 设置编码过滤器（自定义）
//            val factory = TextLineCodecFactory(
//                Charset.forName("UTF-8"),
//                LineDelimiter.WINDOWS.value,
//                LineDelimiter.WINDOWS.value
//            )
//            factory.decoderMaxLineLength = 1024 * 1024
//            factory.encoderMaxLineLength = 1024 * 1024
//
//            val coder = ProtocolCodecFilter(factory)
            val coder = ProtocolCodecFilter(
                ProtocolCodecFactoryImpl(
                    pack = Pack(
                        header = "5aa5",
                        HEADER = 2,
                        LENGTH = 4
                    )
                )
            )
            acceptor.getFilterChain().addLast(
                "mycoder",
                coder
            )
            acceptor.getFilterChain().addLast("logger", LoggingFilter())
            acceptor.getFilterChain().addLast("heartbeat", createHearBeat())

            acceptor.sessionConfig.isKeepAlive = true
            // 设置缓冲区大小
            acceptor.sessionConfig.readBufferSize = 1024
            // 设置读写空闲时间
            acceptor.sessionConfig.setIdleTime(IdleStatus.BOTH_IDLE, 10)
            // 绑定handler
            acceptor.setHandler(ServerHandler())
            // 绑定端口 可同时绑定多个
            acceptor.bind(InetSocketAddress(MINA_PORT))
            logger.info("创建Mina服务端成功，端口：$MINA_PORT")
        } catch (e: IOException) {
            logger.error("创建Mina服务端出错：" + e.message)
        }

    }

    private fun createHearBeat(): IoFilter {
        //设置心跳工程
        val heartBeatFactory = KeepAliveMessageFactoryImplTest()
        //当读操作空闲时发送心跳
        val heartBeat = KeepAliveFilter(heartBeatFactory, IdleStatus.BOTH_IDLE)
        //设置心跳包请求后超时无反馈情况下的处理机制，默认为关闭连接,在此处设置为输出日志提醒
        heartBeat.requestTimeoutHandler = KeepAliveRequestTimeoutHandler.CLOSE
        //是否回发
        heartBeat.isForwardEvent = true
        //发送频率
        heartBeat.requestInterval = 15
        //设置心跳包请求后 等待反馈超时时间。 超过该时间后则调用KeepAliveRequestTimeoutHandler.CLOSE */
        heartBeat.requestTimeout = 15
        return heartBeat
    }
}



