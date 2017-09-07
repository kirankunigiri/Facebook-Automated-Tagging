package sample;

public class Group {

    private String content;

    public Group(){
        this.content = "Test";
    }

    public Group(String content){
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String name) {
        this.content = name;
    }

}