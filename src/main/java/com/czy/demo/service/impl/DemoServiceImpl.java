package com.czy.demo.service.impl;

import com.czy.demo.dao.DemoDao;
import com.czy.demo.service.DemoService;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    DemoDao demoDao;

    private List<String> vocab = getVocab();
    private List<Map<Integer, Float>> sortTopicWord = getSortTopicWord();

    public DemoServiceImpl() throws IOException {
    }

    private List<String> getVocab(){
        System.out.println("初始化vocab");

        String vocabPath = "D:\\IntelliJ IDEA\\Projects\\Demo\\src\\main\\resources\\data\\vocab.txt";
        int vocabSize = 7000;

        List<String> vocab = new ArrayList<>();
        InputStreamReader inStrR = null;
        try {
            inStrR = new InputStreamReader(new FileInputStream(vocabPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(inStrR);
        int i=0;
        String line = null;
        try {
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (line != null&&i<vocabSize) {
            line=line.toLowerCase();
            vocab.add(line);
            i++;
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vocab;
    }

    private List<Map<Integer, Float>> getSortTopicWord() throws IOException {
        System.out.println("初始化sortTopicWord");
        String filePath = "D:\\IntelliJ IDEA\\Projects\\Demo\\src\\main\\resources\\data\\sortTopicWordAll.txt";

        List<Map<Integer, Float>> sortTopicWord = new ArrayList<>();

        InputStreamReader inStrR = new InputStreamReader(new FileInputStream(filePath));
        BufferedReader br = new BufferedReader(inStrR);
        String line = br.readLine();
        while (line != null){
            Map<Integer, Float> map = new HashMap<>();
            String[] topicWordsStrings=line.split(":");
            Integer topic=new Integer(topicWordsStrings[0]);
            String[] wordsProStrings=topicWordsStrings[1].split(" ");
            for(int i = 0; i < wordsProStrings.length; i++,i++){
                Integer word = new Integer(wordsProStrings[i]);
                float pro = Float.parseFloat(wordsProStrings[i+1]);
                map.put(word, pro);
            }
            sortTopicWord.add(map);
            line = br.readLine();
        }

        return sortTopicWord;
    }



    @Override
    public void getData(String startDate, String endDate, String keywords) {
        int topic = wordToTopic(keywords);

    }

    public int wordToTopic(String keywords){
        int K = sortTopicWord.size();
        HashMap<Integer, Float> result = new HashMap<>();
        Segment segment = HanLP.newSegment();
        List<Term> termList = CoreStopWordDictionary.apply(segment.seg(keywords));
        List<Integer> wordList = new ArrayList<>();
        for(Term term : termList){
            String word = term.word;
            if(word.length() > 1){
                if(vocab.contains(word)){
                    wordList.add(vocab.indexOf(word));
                }
            }
        }
        float[] weights = new float[K];
        float max = (float) -1.0;
        int index = -1;
        for(int i = 0; i < K; i++){
            Map<Integer, Float> map = sortTopicWord.get(i);
            for(int word : wordList){
                if(map.containsKey(word)){
                    weights[i] += map.get(word);
                }
            }
            if(weights[i] > max){
                max = weights[i];
                index = i;
            }
        }

        return index;
    }





}
