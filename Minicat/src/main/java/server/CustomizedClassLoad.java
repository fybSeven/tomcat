package server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @AUTHOR: yb.feng
 * @DATE: 2020/5/9 14:30
 * @DESC:
 */
public class CustomizedClassLoad extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println(name);
        byte[] cLassBytes = null;
        Path path = null;
        try {
            path = Paths.get(new URI(name));
            cLassBytes = Files.readAllBytes(path);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        Class clazz = defineClass(cLassBytes, 0, cLassBytes.length);
        return clazz;
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        CustomizedClassLoad customizedClassLoad = new CustomizedClassLoad();
        Class<?> lagouServlet = customizedClassLoad.findClass("file:///D:/学习/作业/阶段二/模块1/tomcat/webapps/demo1/server/LagouServlet.class");
//        Method service = lagouServlet.getMethod("service");
//        String str = (String) service.invoke(lagouServlet.newInstance());
//        System.out.println(str);



    }
}
