package server;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR: yb.feng
 * @DATE: 2020/5/7 15:50
 * @DESC: /localhost
 */
public class Host {

    Map<String, Context> contextMap = new HashMap<>();

    public Map<String, Context> getContextMap() {
        return contextMap;
    }

    public void setContextMap(Map<String, Context> contextMap) {
        this.contextMap = contextMap;
    }

    public Host(String appBase) {
        //appBase D:/学习/作业/阶段二/模块1/tomcat/webapps
        File file = new File(appBase);
        if (file.isDirectory()){
            File[] contexts = file.listFiles();
            for (File context : contexts) {
                String fileName = context.getName();
                //demo1
                contextMap.put(fileName, new Context(context));
            }
        }

    }
}
