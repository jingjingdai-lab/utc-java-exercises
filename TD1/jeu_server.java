/*
写代码流程
1.先给server和client建立连接
2.server可以接收到client的初始输入数据（写输入流）
3.client可以发送参数
  接下来进行游戏环节 server维护游戏状态 Client负责显示给用户看
4.初始化游戏状态 server收到参数后要创建一个变量 用于存放剩余火柴数量 发送给client并展示给玩家
*/


import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class jeu_server {

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
            
            //4.进入游戏主循环
           do{
                //每一轮开始前先告诉client 现在还剩多少根了 轮到谁行动了
                out.writeInt(nbAlluRest);
                out.writeInt(qui);            
            
            if (qui==0)//轮到玩家：接收玩家输入的操作（拿几根）
            {
                do{
                    int priseJoueur = in.readInt();
                    nbAlluRest -= priseJoueur;
                }
            }
            else//轮到电脑
            {
                prise = jeu_ordi (nb_allu_rest , nb_allu_max);
                System.out.println ("\nPrise de l'ordi :"+prise);
            }
            qui=(qui+1)%2;//切换回合！
            nb_allu_rest= nb_allu_rest - prise;//更新剩余火柴数量
            }while (nb_allu_rest >0);
           
           
           
           
           
           
           
           
           
           
            //4.接收玩家输入的操作（拿几根）
           int priseJoueur = in.readInt();
           System.out.println("Le joueur a pris : " + priseJoueur);
           nbAlluRest = nbAlluRest - priseJoueur;//做计算 还剩下几根
           System.out.println("Allumettes restantes : " + nbAlluRest);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
