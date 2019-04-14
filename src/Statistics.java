public class Statistics implements Comparable{
    private String content;//存储分词内容
    private int num;//出现的次数

    public Statistics(String content){
        this.content = content;
        this.num  = 1;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNum() {
        return num;
    }

    public void setNum() {
        this.num  = this.num+1;
    }

    @Override
    public int compareTo(Object o) {
        Statistics p = (Statistics)o;
        return p.num-this.num;
    }

    @Override
    public boolean equals(Object o){
        Statistics p = (Statistics)o;
        return this.getContent().equals(p.content);
    }
}
