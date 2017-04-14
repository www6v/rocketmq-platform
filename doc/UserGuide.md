## 计算平台-扫描结果离线处理调用接口 ##

### 场景 ###
扫描端上传完数据包后，服务端发送一条消息给计算平台，触发计算流程。

计算平台提供了http接口发送消息。

### java API ###

pom.xml增加下面dependency

    <dependency>
            <groupId>com.mashape.unirest</groupId>
            <artifactId>unirest-java</artifactId>
            <version>1.4.9</version>
     </dependency>

示例代码

	/**
     * video.url 扫描的压缩包url地址
     * record.id 可选 对应扫描结果库记录id，为计算完结果回传提供支持
     * alg.name 对应计算模块的库名
     * flow.name 对应计算模块的入口方法
     */
    public void testSendMsg() {
        try {
            String body = Unirest.post("http://10.240.212.164:8103/topic/downVideo.ar").header("accept", "application/json")
                .field("messageBody", "{'video.url':'http://10.4.65.35:8080/hdpSoftware/20170405094622-20170405094548.zip','record.id':'123','alg.name':'ImageFeature_ORB','flow.name':'Reconstruction'}").asString().getBody();
            System.out.println(body);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

### shell API ###
    //参数含义如上所述
	curl -d "messageBody={'video.name':'http://10.4.65.35:8080/hdpSoftware/20170405094622-20170405094548.zip','record.id':'123','alg.name':'ImageFeature_ORB','flow.name':'Reconstruction'}" 
     http://10.240.212.164:8103/topic/processFeature.ar