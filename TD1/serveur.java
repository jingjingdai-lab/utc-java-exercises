/*
知识点一：
Socket = 两台电脑之间通信的“插口”允许 两个程序通过网络发送和接收数据。
Socket 实际上就是：(IP + Port)
*/

/*
服务器程序

定义一个 Server.java 程序，
用于 使用 TCP sockets 初始化（建立）一个连接。
该程序需要执行 一个无限循环（boucle infinie），
每当 有一个客户端与服务器建立连接时，
程序就 显示一条消息。
*/


/*
知识点二：
低级读写 直接对socket进行读写 在client和socket之间进行通信
OutputStream   → 发送数据 写数据
InputStream    → 接收数据 读数据
Client
  │
  │ OutputStream
  │ write("bonjour serveur")
  ▼
网络
  ▼
Server
  │
  │ InputStream
  │ read()
  ▼
收到消息
读操作是阻塞的 如果in.read()没有数据 程序会在这里等待 不会继续执行
解决办法 可以设置等待超时
*/

/*
修改 Serveur.java 程序，使它能够接收客户端发送的消息，并把这条消息显示在控制台上。
修改 Serveur.java 程序，使它能够向 client.java 程序发送一个回复。
*/

/*
知识点三：socket通信依赖连接两端同时存在！
如果client和server有一方先停止了 会发生什么？
1.当 client 提前停止时：在out.write后就client.close();
如果客户端在服务器关闭前关闭，
服务器可以在读写时检测到连接已关闭。read（） 方法可以返回 -1，
或者如果服务器仍在尝试写入，可以抛出异常。

2.当 server 提前停止时
如果服务器提前关闭，客户端将无法继续通信。读取可能失败，写入可能引发 SocketException。
*/

/*
知识点四：Socket "高层读写" 
不用自己处理byte[]，而是用更高级的Java类直接读写数据
DataOutputStream
DataInputStream
低层vs高层
低层：发送: out.write("bonjour".getBytes());
      接收: byte[] b = new byte[1024];
            in.read(b);
            new String(b);
高层：发送：outs.writeUTF("bonjour");
      接受：ins.readUTF();

Client
   │
writeUTF("bonjour serveur")
   │
网络
   │
readUTF()
   │
Server
*/

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;//serversocket用来创建服务器端socket 服务器的监听窗口等待客户端来连接
import java.net.Socket;//socket表示一次具体的连接 表示某个客户端已经连上来的连接

public class serveur {//整个服务器的外壳

    public static void main(String[] args) {//java程序的入口 当运行java server的时候 java虚拟机要自动找到main方法运行
    //public 公开的JVM能访问到 static不需要先创建对象就能运行 void方法不返回值
        try {//try代码块 因为这些网络操作可能出错例如端口被占用 网络异常等 把“可能出错的代码放进try代码块中 如果真的出错就跳到catch
            
            
            //1.连接服务器 连接成功后才可以收发消息
            ServerSocket serveur = new ServerSocket(20000); //创建服务器 类型是ServerSocket 监听端口20000
            //创建一个服务器 并让他在20000端口等待客户端连接
            //IP 找到哪台电脑 ，端口找到这台电脑上的程序
            System.out.println("Serveur démarré...");
            
            while(true){//无限循环 服务器不能只等待一个客户端就结束 要一直运行不断等待客户端

                Socket client = serveur.accept(); //等待客户端连接 一旦连接成功 就把这次连接存到变量client里面
                //注：这句代码会阻塞 如果客户端没有来连接 程序会停在这里一直等待直到继续执行
                //如果某个客户端执行了new socket("localhost",20000);accept()就会结束等待并返回一个Socket对象
                System.out.println("Client connecté !");


                //2.接受消息 要写在循环里面
                InputStream in = client.getInputStream();//获取输入流 接受服务器发来的数据
                byte[] buffer = new byte[1024];//创建缓冲区 准备一个数组用来存数据
                in.read(buffer);//读取数据
                 System.out.println("Le client a dit : " + new String(buffer));

                 //3.给客户端回复
                OutputStream out = client.getOutputStream();//获取输出流 得到一个“发送数据的通道”
                String message = "bonjour client";//准备要发送的内容
                out.write(message.getBytes());//发送数据 Java网络发送的是byte[] 所以要通过getBytes()把string->bytes
            
                client.close();
            }

    
        } catch (Exception e) {
            e.printStackTrace();//详细打印错误信息
        }

    }
}