package personal_web.entity;
public class Picture {
    private int id;
    private String name;
    private String description;
    private String link;

    public Picture(int id,String name, String link,String ds){
        this.id=id;
        this.link=link;
        this.name=name;
        this.description=ds;
    }

    public void editDescription(String ds){
        this.description=ds;
    }
    public String getDescription(){
        return this.description;
    }
    public String getName(){
        return this.name;
    }
    public void addId(int id){this.id=id;}
    public String getLink(){
        return this.link;
    }
    public int getId(){
        return this.id;
    }
}
