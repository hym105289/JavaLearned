[TOC]

[TOC]



#一、Socket 通讯模型（聊天室）

## 1.1 单一客户端与服务端建立连接

### 1.1.1 客户端代码

```java
package danei.JAVASE03.chat;

import java.io.IOException;
import java.net.Socket;

/** 聊天室客户端
 *
 * Created by jinhua.chen on 2018/3/10.
 */
public class Client {
    /*
     java.net.Socket
     封装了 TCP 通信协议,使用它与远程计算机进行网络通讯.
     */
    private Socket socket;

    /*
    构造方法,用来初始化客户端.
     */
    public Client() throws IOException {
        /*
        实例化 socket 需要传入两个参数:
        服务端 IP 地址 和服务端端口号
        通过 ip 地址可以找到网络上的服务端所在的计算机,通过端口可以连接到该计算机上的服务端的应用程序.
        实例化 socket 的过程就是建立连接的过程,所以若这里连接失败,这里会抛出异常.
         */
        System.out.println("正在与服务端建立连接...");
        socket = new Socket("localhost",8088);
        System.out.println("与服务端连接成功!");
    }

    /*
    客户端的启动方法,从这里开始执行客户端逻辑
     */
    public void start(){

    }

    public static void main(String[] args) {

        Client client = null;
        try {
            client = new Client();
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端启动失败!");
        }

    }
}
```

###1.1.2 服务端代码

```java
package danei.JAVASE03.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/** 聊天室服务端
 * Created by jinhua.chen on 2018/3/10.
 */
public class Server {

    /*
    java.net.ServerSocket
    运行在服务端的 ServerSocket 主要有两个作用:
    1. 申请服务端口
    2. 监听服务端口,一旦一个客户端与该端口建立连接,则创建一个 Socket 与客户端进行通讯.
     */
    private ServerSocket serverSocket;

    public Server() throws IOException {
        /*
        初始化 ServerSocket 时指定一个端口号,
        该端口号不能与系统其他应用程序已申请的端口号重复,否则会抛出异常.
         */
        serverSocket = new ServerSocket(8088);
    }

    public void start(){
        try {
            /*
            ServerSocket 提供方法:
            Socket accept()
            该方法会监听 ServerSocket 申请的服务端端口.这是一个阻塞方法,直到一个客户端通过该端口连接才会返回一个 Socket.
            这个返回的 Socket 是用于与连接的客户端进行通讯的.

             */
            System.out.println("服务器正等待与客户端的连接...");
            Socket socket = serverSocket.accept();
            System.out.println("服务器和一个客户端连接了!");
        } catch (IOException e) {
			e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

​	先启动服务端，再启动客户端，即可建立连接。



## 1.2 客户端向服务端发送一行字符串，并在服务端输出

客户端代码

```java
public class Client {
    /*
    客户端的启动方法,从这里开始执行客户端逻辑
     */
    public void start() throws IOException {
        OutputStream os = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(os,"UTF-8"));
        pw.println("你好,服务器!");
        pw.flush();
    }
```

服务端代码

```java
public void start() throws IOException {
    System.out.println("服务器正在与客户端建立连接...");
    Socket socket = serverSocket.accept();
    System.out.println("服务端与一个客户端连接成功!");

    InputStream is = socket.getInputStream();
    BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
    String message = null;
    while ((message = br.readLine()) != null){
        System.out.println("客户端说:" + message);
    }
}
```



### 1.2.1 客户端从键盘向服务端不停发送消息，服务器接收消息

客户端代码：

```java
    public void start() throws IOException {
        OutputStream os = socket.getOutputStream();

        PrintWriter pw = new PrintWriter(new OutputStreamWriter(os,"UTF-8"),true);
        Scanner scanner = new Scanner(System.in);
        System.out.println("开始聊天吧!");
        while (true){
            String mess = scanner.next();
            pw.println(mess);
        }
    }
```

服务端代码不变。

##1.3 服务器接收多个客户端消息

```java
    System.out.println("服务器正在与客户端建立连接...");
    Socket socket = serverSocket.accept();
    System.out.println("服务端与一个客户端连接成功!");
```

​	因为ServerSocket 要一直监听端口号：是否有客户端建立连接，所以上面de 代码要一直执行，所以上面的代码要加一个 while 循环。

```java
while (true){
    System.out.println("服务器正在与客户端建立连接...");
    Socket socket = serverSocket.accept();
    System.out.println("服务端与一个客户端连接成功!");
}
```

​	但是加了一个循环后，由于是死循环，循环下面的代码将永远不会执行。所以这时候要起一个线程：**将服务端接收客户端消息的代码挪到线程 ClientHandler 中，从而实现服务端接收多个客户端消息。**



客户端代码不变，服务端代码：

```java
public class Server {

    public void start(){
        try {
            while (true){
                System.out.println("等待客户端连接...");
                final Socket socket = serverSocket.accept();
                System.out.println("一个客户端连接了!");
                ClientHandler handler = new ClientHandler(socket);
                Thread t = new Thread(handler);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler implements Runnable{
        private Socket socket;
        public ClientHandler(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            InputStream is = null;
            try {
                is = socket.getInputStream();
                BufferedReader br = null;
                br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                String message = null;
                while ((message = br.readLine()) != null){
                    System.out.println("客户端说:" + message);
                }
            }catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

## 1.4 区分不同的客户端, 客户端上下线的通知

客户端代码不变，服务端代码为

```java
public class Server {

    private ServerSocket serverSocket;
    private String host;

    private class ClientHandler implements Runnable{
        private Socket socket;
        public ClientHandler(Socket socket){
            this.socket = socket;
            /*
            通过 socket 获取远程计算机的信息
             */
            InetAddress address = socket.getInetAddress();
            host = address.getHostAddress();
        }

        @Override
        public void run() {
            InputStream is = null;
            try {
                is = socket.getInputStream();
                BufferedReader br = null;
                br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                String message = null;

                System.out.println(host + "上线了!");
                while ((message = br.readLine()) != null){
                    System.out.println(host + "说:" + message);
                }
            }catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            } finally {
                /*
                处理客户端断开连接以后的工作.
                 */
                System.out.println(host + "下线了!");
                if (socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
```

## 1.5 将客户端发送给服务端的消息回复给该客户端 

服务端代码

```java
public class Server {
    private class ClientHandler implements Runnable{
        @Override
        public void run() {
            InputStream is = null;
            OutputStream os = null;
            try {
                os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(os,"UTF-8"),true);

                System.out.println(host + "上线了!");
                while ((message = br.readLine()) != null){
//                    System.out.println(host + "说:" + message);
                    pw.println(host+ "说:" + message);
                }
            }catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            } finally {
                /*
                处理客户端断开连接以后的工作.
                 */
                System.out.println(host + "下线了!");
                if (socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
```

###1.5.2 客户端接收消息

**写代码过程中，发现客户端需要另起一个线程时刻接收服务端的消息。**

```java
public class Client {
    public void start() throws IOException {
        // 启动接收服务器发送消息的线程
        Thread t = new Thread(new ServerHandler());
        t.start();
    }

    /*
    该线程用来循环接收服务器发的消息,并输出到客户端自己的控制台上.
     */
    private class ServerHandler implements Runnable{
        @Override
        public void run() {
            InputStream is = null;
            try {
                is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                String mes = null;
                while ((mes = br.readLine()) != null){
                    System.out.println(mes);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

## 1.6 服务端广播消息给所有客户端

客户端代码不变，服务端实现思路：

- Server 是一个外部类，ClientHandler 是其内部类，在外部类定义一个集合，将所有ClientHandler 的输出流都放到这个集合里去，内部类可以访问这个集合；
- 只要有客户端发送消息，就遍历集合，集合第一条输出流将信息发给自己，集合第2条输出流将信息发给另一个客户端，这样这两个客户端就可以 互相查看和接收消息了。
- 若有客户端断开连接，需要从集合里删除该输出流。
- 两个线程要操作同一个集合，要考虑线程安全。

```java
public class Server {

    // 该集合用于存储每个 socket 的输出流,用于将消息广播给所有的客户端.
    private List<PrintWriter> pwList;

    public Server() throws IOException {
        serverSocket = new ServerSocket(8888);
        pwList = new ArrayList<PrintWriter>();
    }

    private class ClientHandler implements Runnable{
        @Override
        public void run() {
            try {
                /*
                  将客户端的输出流加入到共享集合里.
                  由于多个线程都会调用集合的 add 方法向集合添加输出流为了保证集合安全,需要加集合加锁.
                 */
                synchronized (pwList){
                    pwList.add(pw);
                }

                while ((message = br.readLine()) != null){
//                    System.out.println(host + "说:" + message);
//                    pw.println(host+ "说:" + message);
                  
                     /*
                    转发给所有客户端
                     */
                    synchronized (pwList){
                        Iterator<PrintWriter> it = pwList.iterator();
                        while (it.hasNext()){
                            it.next().println(host+ "说:" + message);
                        }
                    }
                }
            }catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            } finally {
                /*
                处理客户端断开连接以后的工作.
                 */
                synchronized (pwList){
                    // 将该客户端的输出流从共享集合里删除
                    pwList.remove(pw);
                }

                System.out.println(host + "下线了!");
                if (socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
```

## 1.7 哪个客户端上线了，在所有客户端上提示

**当有重复代码时，就考虑用一个方法实现，其他地方使用时调用该方法即可。**

客户端代码不变，服务端代码为：

```java
public class Server {
    /*
    将给定的消息广播给所有的客户端
     */
    public void sendMessage(String message){
        synchronized (pwList){
            Iterator<PrintWriter> it = pwList.iterator();
            while (it.hasNext()){
                it.next().println(message);
            }
        }
    }

    private class ClientHandler implements Runnable{
        @Override
        public void run() {
            try {
                sendMessage(host + "上线了!当前在线 " + pwList.size() + "人.");
              
                while ((message = br.readLine()) != null){
//                    System.out.println(host + "说:" + message);
//                    pw.println(host+ "说:" + message);

                    /*
                        转发给所有客户端
                     */
                    sendMessage(host+ "说:" + message);
                }
            }catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            } finally {
                /*
                处理客户端断开连接以后的工作.
                 */
                sendMessage(host + "下线了!当前在线 " + pwList.size() + "人.");
                if (socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

```

##阅读文章：http://blog.csdn.net/min996358312/article/details/62231033
