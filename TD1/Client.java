/*
客户端程序
定义一个 Client.java 程序，
用于 通过 TCP sockets 连接到 Server.java。
当 客户端程序和服务器程序成功建立连接 时，
client.java 程序需要显示消息：
connexion réussie ...
*/


/*
在 Client.java 程序中，使用 OutputStream 类提供的方法，实现一条消息的发送。
在 client.java 程序中显示服务器返回的回复。
*/

//new的使用 看到代码时问自己 我是不是在创建一个新的对象 如果是->用new 
//                                                   如果不是 而调用方法得到对象就不用new
import java.net.Socket;
import java.io.OutputStream;
import java.io.InputStream;

public class Client {
    public static void main (String[] args){
        try{
           //1.连接服务器
            Socket socket = new Socket("localhost",20000);//连接服务器 IP是localhost Port是20000
            System.out.println("Connexion reussie...");
        
            //2.发送消息给服务器
            OutputStream out = socket.getOutputStream();//获取输出流 得到一个“发送数据的通道”
            String message = "bonjour serveur";//准备要发送的内容
            out.write(message.getBytes());//发送数据 Java网络发送的是byte[] 所以要通过getBytes()把string->bytes
        
            // 3 接收服务器回复
            InputStream in = socket.getInputStream();//获取输入流 接受服务器发来的数据
            byte[] buffer = new byte[1024];//创建缓冲区 准备一个数组用来存数据
            in.read(buffer);//读取数据
            System.out.println("Le serveur a dit : " + new String(buffer));//new String 转成字符串并打印
            
            socket.close();
        }
        catch (Exception e) {
            e.printStackTrace();//详细打印错误信息
        }
    }
}
