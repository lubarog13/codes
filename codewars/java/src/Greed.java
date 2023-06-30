import java.util.HashMap;
import java.util.Map;

public class Greed{
    public static int greedy(int[] dice){
        Map<Integer, Integer> count = new HashMap<>() {{put(1, 0);put(2, 0);put(3, 0);put(4, 0);put(5, 0);put(6, 0);}};
        int score = 0;
        for (Integer d : dice) {
            count.replace(d, count.get(d) + 1);
        }
        for (Integer key  : count.keySet()) {
            if(key!=1) {
                score += ((int)(count.get(key) / 3)) * 100 * key;
                if (key==5) {
                    score+= count.get(key)%3 * 50;
                }
            } else {
                score += ((int)(count.get(key) / 3)) * 1000;
                score+= count.get(key)%3 * 100;
            }
        }
        return score;
    }
}
