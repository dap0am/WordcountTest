import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WordCount {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java filename <filepath>");
            return;
        }
        String filepath = args[0];
        try {
            Map<String, Integer> wordCountMap = countWords(filepath);
            printWordCounts(wordCountMap);
        } catch (IOException e) {
            System.err.println("Error occured while reading the file: " + e.getMessage());
        }
    }

    private static Map<String, Integer> countWords(String filepath) throws IOException{
        Map<String, Integer> wordCountMap = new HashMap<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(filepath))){
            String wordLine;
            while ((wordLine = reader.readLine()) != null){
                String[] words = wordLine.split("\\s+");
                for(String word : words){
                    word = normalizeword(word);
                    if (!word.isEmpty()){
                        wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
                    }
                }
            }
        }
        return wordCountMap;
    }
    private static String normalizeword(String word){
        return word.replaceAll("[^a-zA-Z]", "").toLowerCase();
    }

    private static void printWordCounts(Map<String, Integer> wordCountmap){
        wordCountmap.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }
}
