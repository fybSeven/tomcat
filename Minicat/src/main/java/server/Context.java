package server;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR: yb.feng
 * @DATE: 2020/5/7 15:50
 * @DESC: /demo1
 */
public class Context {

    Map<String, Wrapper> wrapperMap = new HashMap<>();

    public Map<String, Wrapper> getWrapperMap() {
        return wrapperMap;
    }

    public void setWrapperMap(Map<String, Wrapper> wrapperMap) {
        this.wrapperMap = wrapperMap;
    }

    private static String fix = "file:///";

    public Context(File context) {
        //serverPath D:\学习\作业\阶段二\模块1\webapps\demo1\server
        String serverPath = context.getPath() + "\\server";
        File serverFile = new File(serverPath);
        if (serverFile.isDirectory()){
            File[] files = serverFile.listFiles();
            if (files != null && files.length > 0){
                for (File classFile : files) {
                    String url = serverPath.replaceAll("\\\\", "/");
                    String name = fix + url + "/" + classFile.getName();
                    String beanName = classFile.getName().replaceAll(".class", "");
                    CustomizedClassLoad customizedClassLoad = new CustomizedClassLoad();
                    try {
                        Class<?> aClass = customizedClassLoad.findClass(name);
                        Object servlet = aClass.newInstance();
                        wrapperMap.put(beanName, new Wrapper(beanName, servlet));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
