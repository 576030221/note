package cyh.note.设计模式.单例设计模式.懒汉模式;

public class  懒汉 {
    static String qq = "1";
    public static void main(String[] args) {

    }

}
class 懒{
    private static 懒 lan;
    private 懒(){

    }
    public static 懒 getInstance(){
        if (lan == null) {
            lan = new 懒();
        }
        return lan;
    }
}