/* jeu d’allumettes
Rules:
在桌子上放置一定数量的火柴，参与游戏的双方为：
电脑（服务器）
玩家（客户端）
游戏目标是：
不要拿走最后一根火柴。
为了进行游戏，需要规定：
每次最多可以拿走多少根火柴。

游戏开始时:
用户需要通过 客户端程序（Client） 设置以下参数：
1.桌子上的火柴总数
范围：10 到 60 根
2.每次最多可以取走的火柴数量
3.谁先开始游戏
0 → 玩家先开始
1 → 电脑先开始

游戏流程:
这些参数必须先 发送给服务器。
然后游戏开始：
玩家（客户端）和电脑（服务器）轮流行动。
每一轮，玩家或服务器选择 拿走一定数量的火柴。
每次拿走的火柴数量 不能超过设定的最大值。

游戏结束条件
当桌子上 没有火柴时：
拿走最后一根火柴的人是输家
另一方获胜
*/

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class jeu_client {
    
  


    public static void main (String[] args){
        try{
            //1.连接服务器
            Socket socket = new Socket("localhost",20000);
            System.out.println("Connexion reussie...");
        
            //2.发送参数给服务器
            //2.1 先让用户输入三个参数
            Scanner sc = new Scanner(System.in);//当我需要从键盘输入时 我需要创建一个输入对象
            int nb_max_d;
            int nb_allu_max;
            int qui;
            
            do{//输入最大总数
              System.out.println ("Nombre d'allumettes disposées entre les deux joueurs (entre 10 et 60) :");
              nb_max_d=sc.nextInt();
            }while((nb_max_d<10) || (nb_max_d>60));
            
            do{//输入每轮最多可取火柴书
              System.out.println ("\nNombre maximal d'allumettes que l'on peut retirer : ");
              nb_allu_max=sc.nextInt();
              if (nb_allu_max >= nb_max_d)
              System.out.println ("Erreur !");
            }while ((nb_allu_max >= nb_max_d)||(nb_allu_max == 0));
            
            do{//输入谁先开始
              System.out.println ("\nQuel joueur commence? 0--> Joueur ; 1--> Ordinateur : ");
              qui=sc.nextInt();
              if ((qui != 0) && (qui != 1))
                 System.out.println ("\nErreur");
            }while ((qui != 0) && (qui != 1));
        
            //2.2发送三个参数给server
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeInt(nb_max_d);
            out.writeInt(nb_allu_max);
            out.writeInt(qui);
            
            //接受server收到参数后的回复
            DataInputStream in = new DataInputStream(socket.getInputStream());
            String reponse = in.readUTF();//socket里面的读写是按顺序的 TCP流通信 数据不会乱序 发一个读一个
            System.out.println("Le serveur a dit : " + reponse);
            
            int priseJoueur;
            int nbAlluRest;

             //3.进入游戏主循环
           do{
                //每一轮开始前先接收server的消息 现在还剩多少根了 轮到谁行动了
                //接收剩余火柴数量并展示给玩家
                nbAlluRest = in.readInt();//server发来的消息 显示现在还剩几根
                System.out.println("Allumettes restantes : " + nbAlluRest);
                qui = in.readInt();//server发来的消息 现在轮到谁了
                System.out.println("Maintenant: " + qui);
                
                 
                //如果已经小于0了就不继续玩了
                if (nbAlluRest <= 0) {
                    break;
                  }
                
                //如果轮到玩家就发给server自己要取多少根
                if (qui == 0) {
                  
                  do{//反复输入直到正确
                   System.out.println("Combien d'allumettes voulez-vous prendre ?");
                   priseJoueur = sc.nextInt();
                   if ((priseJoueur > nbAlluRest) || (priseJoueur > nb_allu_max))
                      {
                       System.out.println ("Erreur !\n");
                      }
                  }while ((priseJoueur > nbAlluRest) || (priseJoueur > nb_allu_max));
              
                  out.writeInt(priseJoueur);//正确后输出给server
                  }
                  else{//电脑环节 接收电脑发来的消息
                     int priseOrdi = in.readInt();   // 接收 server 发来的
                     System.out.println("\n\nL'ordinateur prend : " + priseOrdi);//打印电脑的获取的消息
                  }
                  
              }while (nbAlluRest >0);


              nbAlluRest = in.readInt();//server发来的消息 显示现在还剩几根
              qui = in.readInt();//server发来的消息 现在轮到谁了

              //胜利规则
              if (qui == 0) /* Cest à nous de jouer */
               System.out.println ("\nVous avez gagné!\n");
               else
               System.out.println ("\nVous avez perdu!\n");

            }
               
            
            catch (Exception e) {
            e.printStackTrace();//详细打印错误信息
        }
    }
}
