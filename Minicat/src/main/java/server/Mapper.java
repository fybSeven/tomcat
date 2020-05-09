package server;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR: yb.feng
 * @DATE: 2020/5/7 15:49
 * @DESC:
 */
public class Mapper {

    Map<String, Host> hostMap = new HashMap<>();

    private int port = 8080;

    public Map<String, Host> getHostMap() {
        return hostMap;
    }

    public void setHostMap(Map<String, Host> hostMap) {
        this.hostMap = hostMap;
    }

    public Mapper buildMapper(){
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("server.xml");
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            Element connector = (Element) rootElement.selectSingleNode("/server/service/connector");
            String configPort = connector.attributeValue("port");
            this.port = Integer.parseInt(configPort);
            Element hostElement = (Element) rootElement.selectSingleNode("/server/service/engine/host");
            //localhost
            String hostName = hostElement.attributeValue("name");
            //D:/学习/作业/阶段二/模块1/webapps
            String appBase = hostElement.attributeValue("appBase");
            Host host = new Host(appBase);
            hostMap.put(hostName, host);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return this;
    }
}
