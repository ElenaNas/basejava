package webapp;

public class MainUtil {
    public static void main(String[] args) {
        System.out.println(Integer.valueOf(-1) == Integer.valueOf(-1));
        System.out.println(Integer.valueOf(-1) == new Integer(-1));
        int result=getIndex();
        //System.out.println(Integer.valueOf(-1) == result);
        System.out.println(result);
    }

    private static Integer getIndex(){
        return null;
    }
}
