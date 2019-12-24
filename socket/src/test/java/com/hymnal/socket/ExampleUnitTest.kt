package com.hymnal.socket

import com.hymnal.socket.default.Pack
import com.hymnal.socket.default.ProtocolCodecFactoryImpl
import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.CumulativeProtocolDecoder
import org.apache.mina.filter.codec.ProtocolDecoderOutput
import org.apache.mina.filter.codec.ProtocolEncoder
import org.apache.mina.filter.codec.ProtocolEncoderOutput
import org.junit.Test
import org.junit.Assert.*
import org.slf4j.LoggerFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, (2 + 2).toLong())
    }

    @Test
    fun test() {

    }

    @Test
    fun socketTest() {


    }


}


fun main(args: Array<String>) {
//    val a = 123
//    println()

    test()
}

fun test () {

    val logger = LoggerFactory.getLogger("test")
    val request = "{\"type\":\"3\"}"
    logger.info("start")
    val client = SocketClient.Builder()
        .setType(SocketClient.Type.TCP, true)
        .setTag("Socket")
        .setIp(ip = "127.0.0.1", port = 7085)
//        .setCodecFactory(
////            ProtocolCodecFactoryImpl(
////                SocketProtocolEncoderImpl(),
////                SocketProtocolDecoderImpl()
////            )
//            ProtocolCodecFactoryImpl(Pack(header = "5aa5", HEADER = 2, LENGTH = 4))
//        )
        .setResponse(Response {

            if (it.isSuccess) {
                logger.info("返回数据 {}",it.getOrNull())

            }

            if (it.isFailure) {
                if (it.exceptionOrNull() is SocketException) {
                    logger.info("服务器异常")
                }
            }
        })
        .builder()
    client.send(request)
}

/**
 * 自定义编码器
 */
class SocketProtocolEncoderImpl() : ProtocolEncoder {

    private val logger by lazy { LoggerFactory.getLogger("Encoder") }

    override fun encode(p0: IoSession?, p1: Any?, p2: ProtocolEncoderOutput?) {
        // 初始化缓冲区
        val buffer = IoBuffer.allocate(p1.toString().length)
            .setAutoExpand(true)//自动扩容
            .setAutoShrink(true)//自动收缩

        //内容
        if (p1 != null) buffer.put(int2ByteArray(p1.toString().toInt()))
        logger.info("发送：{}", int2ByteArray(p1.toString().toInt()))
        buffer.flip()
        p2?.write(buffer)
    }

    override fun dispose(p0: IoSession?) {

    }

    /**
     * int转byte数组
     *
     * @param i int
     * @return byte数组
     */
    private fun int2ByteArray(i: Int): ByteArray {
        val result = ByteArray(4)
        result[0] = (i and 0xFF).toByte()
        result[1] = (i shr 8 and 0xFF).toByte()
        result[2] = (i shr 16 and 0xFF).toByte()
        result[3] = (i shr 24 and 0xFF).toByte()
        return result
    }
}

/**
 * 自定义解码器
 */
class SocketProtocolDecoderImpl() : CumulativeProtocolDecoder() {

    override fun doDecode(p0: IoSession, p1: IoBuffer, p2: ProtocolDecoderOutput): Boolean {

        return false
    }
}
