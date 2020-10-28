package com.karles.project;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class Reader {

    private List<String> wordList = new ArrayList<>();
    public boolean moveToNextFilter = true;
    private int counter = 0;
    Map<String, Integer> mapWithoutDuplcates = new HashMap<String, Integer>();
    Map<String, Integer> aGMap = new HashMap<String, Integer>();
    Map<String, Integer> hNMap = new HashMap<String, Integer>();
    Map<String, Integer> oUMap = new HashMap<String, Integer>();
    Map<String, Integer> vZMap = new HashMap<String, Integer>();
    Map<String, Integer> filteredMap = new HashMap<String, Integer>();
    Map<String, Integer> filteredMap2 = new HashMap<String, Integer>();
    Map<String, Integer> filteredMap3 = new HashMap<String, Integer>();


    void start(String filePath) throws IOException {
        Reader reader = new Reader();
        mapWithoutDuplcates = reader.getDuplicates(reader.getWordList(filePath));
        mapWithoutDuplcates = new TreeMap<String, Integer>(mapWithoutDuplcates);
        mapFilter(mapWithoutDuplcates);
    }

    public List<String> getWordList(String pathName)
            throws FileNotFoundException, NoSuchElementException {

        File file = new File(pathName);
        File[] filesArray = file.listFiles();


        for (File f : filesArray) {

            Scanner fileReader = new Scanner(new File(String.valueOf(f)));

            while (fileReader.hasNextLine()) {

                String line = fileReader.nextLine().replaceAll("\"", "");
                String[] word = line.split("\\s+");

                for (int i = 0; i < word.length; i++) {
                    String cleanWord = word[i].replaceAll("[^a-zA-Z0-9]", "");
                    this.wordList.add(cleanWord.toLowerCase());
                }

            }
        }
        return wordList;
    }

    public Map<String, Integer> getDuplicates(List<String> x) {

        Map<String, Integer> map = new HashMap<String, Integer>();

        for (String temp : x) {
            Integer count = map.get(temp);
            map.put(temp, (count == null) ? 1 : count + 1);
        }
        return map;
    }

    void mapFilter(Map<String, Integer> mapWithoutDuplcates) throws IOException {
        filteredMap.putAll(mapWithoutDuplcates);

        while (moveToNextFilter) {
            mapWithoutDuplcates.entrySet().forEach(entry -> {

                if (Stream.of("a", "b", "c", "d", "e", "f", "g").anyMatch(s -> entry.getKey().startsWith(s))) {
                    aGMap.put(entry.getKey(), entry.getValue());
                    filteredMap.remove(entry.getKey(), entry.getValue());

                }
            });
            moveToNextFilter = false;
            aGMap = new TreeMap<String, Integer>(aGMap);
            filteredMap = new TreeMap<String, Integer>(filteredMap);
            filteredMap2.putAll(filteredMap);

        }
        try {
            writeFile(aGMap, "a-g.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!moveToNextFilter) {

            filteredMap.entrySet().forEach(entry -> {
                if (Stream.of("h", "i", "j", "k", "l", "m", "n").anyMatch(s -> entry.getKey().startsWith(s))) {
                    hNMap.put(entry.getKey(), entry.getValue());
                    filteredMap2.remove(entry.getKey(), entry.getValue());
                }
            });
            moveToNextFilter = true;
            hNMap = new TreeMap<String, Integer>(hNMap);
            filteredMap2 = new TreeMap<String, Integer>(filteredMap2);
            filteredMap3.putAll(filteredMap2);
        }

        try {
            writeFile(hNMap, "h-n.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (moveToNextFilter) {

            filteredMap2.entrySet().forEach(entry -> {
                if (Stream.of("o", "p", "q", "r", "s", "t", "u").anyMatch(s -> entry.getKey().startsWith(s))) {
                    oUMap.put(entry.getKey(), entry.getValue());
                    filteredMap3.remove(entry.getKey(), entry.getValue());
                }
            });
            moveToNextFilter = false;
            oUMap = new TreeMap<String, Integer>(oUMap);
        }
        try {
            writeFile(oUMap, "o-u.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!moveToNextFilter) {

            filteredMap3.entrySet().forEach(entry -> {
                if (Stream.of("v", "w", "x", "y", "z").anyMatch(s -> entry.getKey().startsWith(s))) {
                    vZMap.put(entry.getKey(), entry.getValue());
                }
            });
            moveToNextFilter = true;
            vZMap = new TreeMap<String, Integer>(vZMap);
        }
        try {
            writeFile(vZMap, "v-z.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void createFile(String fileName) {

        counter++;

        try {
            File file = new File(fileName);

            if (file.createNewFile()) {

            } else {
                System.out.println("File with the same name already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    void writeFile(Map<String, Integer> mapAccordingFirstLetter, String fileName) throws IOException {

        BufferedWriter bf = null;

        try {
            createFile(fileName);
            bf = new BufferedWriter(new FileWriter(fileName));
            for (Map.Entry<String, Integer> entry : mapAccordingFirstLetter.entrySet()) {

                bf.write(entry.getKey() + " " + entry.getValue());
                bf.newLine();
            }
            bf.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                bf.close();
                if (counter == 4) {
                    System.out.println("Files created successfully!");
                    System.exit(0);
                }
            } catch (Exception e) {
            }
        }
    }
}
