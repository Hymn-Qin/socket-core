# socket-core

[![](https://www.jitpack.io/v/Hymnal-Qin/socket-core.svg)](https://www.jitpack.io/#Hymnal-Qin/socket-core)




Step 1. Add the JitPack repository to your build file

    gradle
    maven
    sbt
    leiningen

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}

Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.Hymnal-Qin:socket-core:v1.1.0'
		//可以依赖
		implementation 'org.slf4j:slf4j-api:1.7.26'
	}


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



