package server;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

public class RequestProcessor extends Thread {

    private Socket socket;
    private Mapper mapper;

    public RequestProcessor(Socket socket, Mapper mapper) {
        this.socket = socket;
        this.mapper = mapper;
    }

    @Override
    public void run() {
        try{
            InputStream inputStream = socket.getInputStream();

            // 封装Request对象和Response对象
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());

            // 静态资源处理
            if(mapper == null) {
                response.outputHtml(request.getUrl());
            }else{
                // 动态资源servlet请求
                Host host = mapper.getHostMap().get(request.getHost());
                Context context = host.getContextMap().get(request.getContext());
                Map<String, Wrapper> wrapperMap = context.getWrapperMap();
                for (Map.Entry<String, Wrapper> entry : wrapperMap.entrySet()){
                    if (entry.getKey().toLowerCase().contains(request.getServlet())){
                        Wrapper value = entry.getValue();
                        Object servlet = value.getServlet();
                        Method[] declaredMethods = servlet.getClass().getDeclaredMethods();
                        for (Method declaredMethod : declaredMethods) {
                            System.out.println(declaredMethod.getName());
                        }
                        Method service = servlet.getClass().getMethod("service", OutputStream.class);
                        service.setAccessible(true);
                        service.invoke(servlet, new Object[]{socket.getOutputStream()});
                    }
                }
            }

            socket.close();

        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
