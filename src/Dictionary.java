import jieba.JiebaSegmenter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Dictionary {
    private List<Statistics> list = new ArrayList<>();

    public void Read(String path_host,String fw1) throws IOException {//path_host传入的文件夹路径  fwl输出的txt路径
        File file = new File(path_host);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {//如果该文件夹没有文件

                return;
            } else {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {//如果还有文件夹
                        System.out.println("读取文件夹:" + files[i].getAbsolutePath());
                        Read(files[i].getAbsolutePath(),fw1);
                    } else {
                        System.out.printf("正在读取文件"+files[i].getAbsolutePath() + "\r\n");
                        BufferedReader buf=new BufferedReader(new InputStreamReader(new FileInputStream(files[i].getAbsolutePath()),"UTF-8"));
//                        BufferedReader buf = new BufferedReader(new FileReader(new File(files[i].getAbsolutePath())));
                        String line = "";
                        while ((line = buf.readLine()) != null) {
//                            System.out.println("读下一行");
                            while (!line.isEmpty()) {
                                int tag;
                                String sentence;
                                line = line.trim();//去掉两边的空格
                                line = line.replaceAll("\\p{P}", " ");//将所有标点符号换成空格
//                                System.out.println(line);

                                tag = line.indexOf(" ");//根据空格来断句
                                if (tag != -1) sentence = line.substring(0, tag);
                                else sentence = line.trim();
//                                System.out.println("接取出来的词或句子" + sentence);

                                //结巴分词
                                List<String> temp;
                                JiebaSegmenter jiebaSegmenter = new JiebaSegmenter();
                                temp = jiebaSegmenter.sentenceProcess(sentence);

                                //计数
                                for (int i1 = 0;i1<temp.size();i1++) {
                                    int index = list.indexOf(new Statistics(temp.get(i1)));
                                    if (index != -1)
                                        list.get(index).setNum();
                                    else
                                        list.add(new Statistics(temp.get(i1)));
                                }
                                line = line.substring(tag+1);
//                                System.out.println("tag = "+tag);
                                if (tag == -1) break;
                            }
                        }
                        buf.close();
                    }
                }
                Collections.sort(list);
                for (int i2 = 0;i2<list.size();i2++){
                    System.out.println(list.get(i2).getContent()+"  "+list.get(i2).getNum());
                }

                File fw = new File(fw1);
                if (!fw.exists()){
                    fw.createNewFile();
                    BufferedWriter sbuf = new BufferedWriter(new FileWriter(fw1));
                    for (int i = 0;i<list.size();i++)
                        sbuf.write(list.get(i).getContent()+" "+list.get(i).getNum()+" \r");
                    sbuf.close();
                }else {
                    BufferedWriter sbuf = new BufferedWriter(new FileWriter(fw1));
                    for (int i = 0;i<list.size();i++)
                        sbuf.write(list.get(i).getContent()+" "+list.get(i).getNum()+" \r");
                    sbuf.close();
                }
            }
        }else {
            System.out.println("没有此文件夹！");
        }
    }
//    public void write(String fw,String line)throws IOException{
//        BufferedWriter sbuf = new BufferedWriter(new FileWriter(fw,true));
//        sbuf.write(line);
//        sbuf.close();
//    }
    public static void main(String args[]) throws IOException {
        Dictionary dictionary = new Dictionary();
        String origin = args[0];
        String destination  = args[1];
        dictionary.Read(origin,destination);
        System.out.println("操作完成");
//        String test = "dictionary is my best to sasda,so nidsaijddni ji";
//        System.out.println(test.replaceAll("\\p{P}", " "));
//        int tag = test.indexOf(" ");
//        System.out.println(test.substring(0,tag));
//        test = test.substring(tag+1);
//        System.out.println(test);
    }
}
