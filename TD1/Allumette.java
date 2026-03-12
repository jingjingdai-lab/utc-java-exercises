/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static java.lang.Math.floor;
import java.util.Scanner;

/**
 *
 * @author imineyou
 */
public class Allumette {
    
    
//电脑怎么决定取几根火柴
public static int jeu_ordi (int nb_allum, int prise_max)
{
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

//画出剩余火柴
public static void afficher_allu(int n)
{
int i;
 System.out.print("\n");
 for (i=0; i<n ;i++)
  System.out.print("  o");
 System.out.print("\n");
  for (i=0; i<n; i++)
  System.out.print("  |");
 System.out.print("\n"); 
  for (i=0; i<n; i++)
  System.out.print("  |");
 System.out.print("\n"); 

}
//server算完剩余火柴后 把数字发给client 由client来调用afficher
//让server只负责游戏逻辑 client负责显示


//游戏主流程
    public static void main(String[] args) 
{
//1.定义变量
int nb_max_d=0; /*nbre d'allumettes maxi au départ*/
int nb_allu_max=0; /*nbre d'allumettes maxi que l'on peut tirer au maxi*/
int qui=0; /*qui joue? 0=Nous --- 1=PC*/
int prise=0; /*nbre d'allumettes prises par le joueur*/
int nb_allu_rest=0; /*nbre d'allumettes restantes*/
Scanner sc=new Scanner(System.in);

//2.输入初始火柴总数 反复要求用户输入直到输入值在10-60之间
do{
System.out.println ("Nombre d'allumettes disposées entre les deux joueurs (entre 10 et 60) :");
nb_max_d=sc.nextInt();
}
while((nb_max_d<10) || (nb_max_d>60));

//3.输入每轮最多可取火柴书 不能是0 不能大于总火柴数 超出就反复输入
do
{
System.out.println ("\nNombre maximal d'allumettes que l'on peut retirer : ");
nb_allu_max=sc.nextInt();
if (nb_allu_max >= nb_max_d)
System.out.println ("Erreur !");
}
while ((nb_allu_max >= nb_max_d)||(nb_allu_max == 0));
/* On répète la demande de prise tant que le nombre donné n'est pas correct */

//4.输入谁先开始
do
{
System.out.println ("\nQuel joueur commence? 0--> Joueur ; 1--> Ordinateur : ");
qui=sc.nextInt();

if ((qui != 0) && (qui != 1))
System.out.println ("\nErreur");
}
while ((qui != 0) && (qui != 1));

//5.初始化火柴数量
nb_allu_rest = nb_max_d;

//6.游戏主循环
do
{
System.out.println ("\nNombre d'allumettes restantes :"+nb_allu_rest);//显示总火柴数
afficher_allu(nb_allu_rest);

if (qui==0)//轮到玩家
{
do
{
System.out.println ("\nCombien d'allumettes souhaitez-vous piocher ? ");
prise=sc.nextInt();
if ((prise > nb_allu_rest) || (prise > nb_allu_max))
{
System.out.println ("Erreur !\n");
}
}
while ((prise > nb_allu_rest) || (prise > nb_allu_max));
/* On répète la demande de prise tant que le nombre donné n'est pas correct */
}
else//轮到电脑
{
prise = jeu_ordi (nb_allu_rest , nb_allu_max);
System.out.println ("\nPrise de l'ordi :"+prise);
}
qui=(qui+1)%2;//切换回合！

nb_allu_rest= nb_allu_rest - prise;//更新剩余火柴数量
}
while (nb_allu_rest >0);


if (qui == 0) /* Cest à nous de jouer */
System.out.println ("\nVous avez gagné!\n");
else
System.out.println ("\nVous avez perdu!\n");
}   
    
}

/*
如何拆分Client和Server
1.放到 Client 的部分
Client 负责：
读取用户输入
显示剩余火柴
让用户输入本轮取几根
把参数和玩家操作发送给 server
接收 server 返回的信息并显示

2.放到 Server 的部分
Server 负责：
接收游戏参数
保存游戏状态
计算电脑取多少
更新剩余火柴
判断游戏是否结束
把结果发回 client


拆分方式
第一步：Client 发送初始化参数给 Server
        发送： 总火柴数 最大可取数 谁先开始
第二步：Server 保存这些参数并开始游戏逻辑
第三步：每轮循环
如果轮到玩家：server 把“当前剩余火柴数”发给 client
             client 显示并问用户取几根
             client 把输入发回 server

如果轮到电脑：server 调用 jeu_ordi(...)
             server 算出取多少
             server 把结果发给 client 显示
第四步：直到游戏结束
        server 发送：剩余火柴数 胜负结果
        client 显示最终结果
*/
