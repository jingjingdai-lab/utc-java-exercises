import java.util.Scanner;
//package utc-java-exercises.0903-ex;
//package 的意思是这个java文件属于某个“包”
//类似于文件夹结构管理代码
//package中不能有- 只能有数字和_

/*
猜数字小游戏

规则：
1. 电脑设置一个数字（例如 7）
2. 玩家输入一个数字
3. 程序判断：
   - 如果猜对 → Bravo
   - 如果太大 → Trop grand
   - 如果太小 → Trop petit
*/


public class GuessNumber {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);//
        int secret = 7;

        while(true){
          System.out.println("Guess a number");
          int guess = scanner.nextInt();
          if(guess == secret){
               System.out.println("Bravo!");
               break;
          } else if (guess > secret){
              System.out.println("Trop grand!");
          } else{
               System.out.println("Trop petit!");
          }
        }
        scanner.close();
    }
}
