
public class WordProperty {

    public String word;
    public String POStag;
    public String NERtag;
    public String table;
    public WordProperty(String word, String POStag)
    {
        this.word = word;
        this.POStag = POStag;
        this.NERtag = "";
        this.table = "Table not in the database.";
    }
}
