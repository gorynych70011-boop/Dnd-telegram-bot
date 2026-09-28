public class Check{
    public static int roll(){
        return (int) (Math.random() * 20) + 1;
    }
    public static boolean checkStat(int statValue, int difficulty){
        int dice = roll();
        int modifier = Math.floorDiv(statValue, 2);
        return dice + modifier >= difficulty;
    }
}