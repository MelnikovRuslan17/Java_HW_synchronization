import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                int count = 0;
                String route = generateRoute("RLRFR", 100);
                for (int j = 0; j < route.length(); j++) {
                    if (route.charAt(j) == 'R') {
                        count++;
                    }
                }
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(count)) {
                        sizeToFreq.put(count, sizeToFreq.get(count) + 1);
                    } else {
                        sizeToFreq.put(count, 1);
                    }
                }
            }).start();

        }
        print(sizeToFreq);
    }
    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
    public static void print(Map<Integer, Integer> map){
        Map.Entry<Integer, Integer> maxE = map.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .orElse(null);
        System.out.println("Самое частое количество повторений " + maxE.getKey() + " (встретилось " + maxE.getValue() + " раз)");
        System.out.println("Другие размеры: ");
        map.remove(maxE.getKey());
        for(Map.Entry<Integer, Integer> entry : map.entrySet()){
            System.out.println("- "  + entry.getKey() + " (" + entry.getValue() + " раз)");
        }

}
}