import java.util.Scanner;
class Tutorial{
public static void main(String args[]){
System.out.println("Command Options: ");
System.out.println("a: Geek's Name");
System.out.println("b: Num Questions Asked");
System.out.println("c: All Numbers Are the Same");
System.out.println("d: Sum Between Two Integers");
System.out.println("e: Repeat the String");
System.out.println("f: It is Palindrome");
System.out.println("?: Display");
System.out.println("q: Quit");
Scanner scan = new Scanner(System.in);
String choice = scan.nextLine();
do {
switch (choice){
    case "a":
        break;
    case "b":
        break;
    case "c":

        System.out.println("Enter the first number");
        int input1 = scan.nextInt();
        System.out.println("Enter the second number");
        int input2 = scan.nextInt();
        System.out.println("Enter the third number");
        int input3 = scan.nextInt();
        break;
    case "d":
        System.out.println("Enter the first number");
        int num1 = scan.nextInt();
        System.out.println("Enter the second number");
        int num2 = scan.nextInt();
        break;
    case "e":
        System.out.println("Enter a string: ");
        String word1 = scan.nextLine();
        System.out.println("Enter an integer: ");
        int numberOfTimes = scan.nextInt();
        System.out.println("Enter the third number");
        break;
    case "f":
        System.out.println("Enter a string: ");
        String word2 = scan.nextLine();
        break;
    case "?":
            System.out.println("Command Options: ");
            System.out.println("a: Geek's Name");
            System.out.println("b: Num Questions Asked");
            System.out.println("c: All Numbers Are the Same");
            System.out.println("d: Sum Between Two Integers");
            System.out.println("e: Repeat the String");
            System.out.println("f: It is Palindrome");
            System.out.println("?: Display");
            System.out.println("q: Quit");
            break;
        }  }while (choice != "q");

}
}
