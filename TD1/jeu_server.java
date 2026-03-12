/*
写代码流程
1.先给server和client建立连接
2.server可以接收到client的初始输入数据（写输入流）
3.client可以发送参数
  接下来进行游戏环节 server维护游戏状态 Client负责显示给用户看
4.初始化游戏状态 server收到参数后要创建一个变量 用于存放剩余火柴数量 发送给client并展示给玩家
5.轮到玩家时 server读取玩家发来的拿取数量并计算剩余   client输入拿取数量并传递给server server判断数据是否正确 如果错误返回错误消息给client client接收错误信息
*/


import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import static java.lang.Math.floor;

public class jeu_server {


    //电脑怎么决定取几根火柴
    public static int jeu_ordi (int nb_allum, int prise_max){
	    int prise = 0;//电脑最终决定取多少个火柴
	    int s = 0;
	    float t = 0;
	    s = prise_max + 1;
	    t = ((float) (nb_allum - s)) / (prise_max + 1);
	    while (t != floor(t))
	    {
		    s--;
		    t = ((float) (nb_allum-s)) / (prise_max + 1);
	    }
	    prise = s - 1;
	    if (prise == 0)
	    prise = 1;
	    return (prise);
    }//这个函数应该放在server端 因为sercer是电脑

    public static void main(String[] args) {
        try {
            //1.先连接服务器
            ServerSocket server = new ServerSocket(20000);
            System.out.println("Serveur demarre, en attente du client...");
            Socket client = server.accept();
            System.out.println("Client connecte !");
            
            //2.接收数据
            DataInputStream in = new DataInputStream(client.getInputStream());

            int nb_max_d = in.readInt();//火柴总数
            int nb_allu_max = in.readInt();//最大输入数
            int qui = in.readInt();//谁先开始

            System.out.println("Nombre total d'allumettes : " + nb_max_d);
            System.out.println("Prise maximale : " + nb_allu_max);
            System.out.println("Qui commence : " + qui);
            //收到参数后给client回消息
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF("Parametres recus");
            
            //3.初始化游戏状态
            int nbAlluRest = nb_max_d;//server创建变量用于存放剩余火柴数量 然后把这个状态发给client
            out.writeInt(nbAlluRest);//不需要每次输入输出都创建输出流 建立连接的时候创一个 之后都用同一个
            

            int priseJoueur;

            //4.进入游戏主循环
           do{
                //每一轮开始前先告诉client 现在还剩多少根了 轮到谁行动了
                out.writeInt(nbAlluRest);
                out.writeInt(qui);            
            
            if (qui==0)//轮到玩家：接收玩家输入的操作（拿几根）不需要管玩家的拿取 只需要收到参数即可 然后做计算
            {
                    priseJoueur = in.readInt();
                    System.out.println ("\n玩家拿了几个:"+ priseJoueur);
                    nbAlluRest -= priseJoueur;   
            }
            else//轮到电脑
            {
                priseJoueur = jeu_ordi (nbAlluRest , nb_allu_max);
                System.out.println ("\nPrise de l'ordi :"+ priseJoueur);
            }
            //回合结束server更新剩余数量和qui现在轮到谁并传递给client
            qui=(qui+1)%2;//切换回合！
            nbAlluRest= nbAlluRest - priseJoueur;//更新剩余火柴数量
            out.writeInt(qui);
            out.writeInt(nbAlluRest);
            }while (nbAlluRest >0);
    



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
