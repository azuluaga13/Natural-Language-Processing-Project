import java.util.ArrayList;

public class BusinessTier {

    private Queries qFactory;
    private Access_DB dbAccess;

    public BusinessTier(){
        qFactory = new Queries();
        dbAccess = new Access_DB();
    }

    public String containsApostrophe(String word){
        if(word.contains("'")){
            word = word.replaceAll("'","''");
            return word;
        }

        return word;
    }

    public boolean determineYesNoQuestion(ArrayList<WordProperty> queries, String oscarType){
        ArrayList<String> tables = new ArrayList<>();
        ArrayList<NounTableTuple> pNouns = new ArrayList<>();
        int numberOfTables = 0;

        for(WordProperty temp: queries) {
            if (temp.POStag.equals("NNP")) {

                String searchWord = containsApostrophe(temp.word);
                String t1 = dbAccess.searchMovieName(searchWord);
                boolean t3 = dbAccess.searchPersonLocation(searchWord);
                boolean t4 = dbAccess.searchPersonName(searchWord);

                if (t4|| !t1.equals("Table not in the database.")) {
                    if(t4) {
                        if (!tables.contains("Person")) {
                            tables.add("Person");

                        }
                        pNouns.add(new NounTableTuple(searchWord, "Person", "name"));
                    }
                    if (!t1.equals("Table not in the database.") && t1.contains(temp.word)) {
                        if (!tables.contains("Movie")) {
                            tables.add("Movie");
                        }
                        pNouns.add(new NounTableTuple(searchWord, "Movie", "name"));
                    }
                }
                else if (t3) {
                    if (!tables.contains("Person")) {
                        tables.add("Person");
                    }
                    pNouns.add(new NounTableTuple(searchWord, "Person", "pob"));
                }

            }
        }

        for(WordProperty temp:queries){
            if(!temp.table.equals("Table not in the database.")){
                if (!tables.contains(temp.table)) {
                    if(temp.table.equals("Actor") && !tables.contains("Person")) {

                        tables.add("Person");
                    }
                    if(temp.table.equals("Director") && !tables.contains("Person")) {
                        tables.add("Person");
                    }
                    tables.add(temp.table);
                }
            }
        }

        for(WordProperty temp:queries){
            if(temp.POStag.equals("CD")) {
                if (tables.contains("Oscar")) {
                    pNouns.add(new NounTableTuple(temp.word, "Oscar", "year"));
                }
                else{
                    pNouns.add(new NounTableTuple(temp.word, "Movie", "year"));
                }
            }
        }

        if(tables.contains("Oscar") && !oscarType.equals("Table not in the database.")) {
            pNouns.add(new NounTableTuple(oscarType, "Oscar", "type"));
        }
        else if(!oscarType.equals("Table not in the database.") && !tables.contains("Oscar")){
            tables.add("Oscar");
            pNouns.add(new NounTableTuple(oscarType, "Oscar", "type"));
        }

        for(int i = 0; i<tables.size();i++){
            if(tables.get(i).equals("Movie")){
                tables.remove(i);
                tables.add("Movie");
            }
        }

        numberOfTables = tables.size();

        if(numberOfTables == 1){
            qFactory.buildTable(tables.get(0));
        }
        else if(numberOfTables == 2){
            qFactory.buildTables(tables.get(0), tables.get(1));
        }
        else if(numberOfTables == 3){
            qFactory.buildTables(tables.get(0), tables.get(1), tables.get(2));
        }
        else if(numberOfTables == 4){
            qFactory.buildTables(tables.get(0), tables.get(1), tables.get(2), tables.get(3));
        }


        return evaluateAnswer(pNouns);
    }

    public String determineWHQuestion(ArrayList<WordProperty> querys, String whType, String oscarType){
        ArrayList<String> tables = new ArrayList<>();
        ArrayList<NounTableTuple> pNouns = new ArrayList<>();
        int numberOfTables = 0;

        for(WordProperty temp: querys) {

            if (temp.POStag.equals("NNP")) {

                String searchWord = containsApostrophe(temp.word);
                String t1 = dbAccess.searchMovieName(searchWord);
                boolean t3 = dbAccess.searchPersonLocation(searchWord);
                boolean t4 = dbAccess.searchPersonName(searchWord);

                if (t4|| !t1.equals("Table not in the database.")) {
                    if(t4) {
                        if (!tables.contains("Person")) {
                            tables.add("Person");
                        }
                        pNouns.add(new NounTableTuple(searchWord, "Person", "name"));
                    }
                    if (!t1.equals("Table not in the database.") && t1.contains(temp.word)) {
                        if (!tables.contains("Movie")) {
                            tables.add("Movie");
                        }
                        pNouns.add(new NounTableTuple(searchWord, "Movie", "name"));
                    }
                }
                else if (t3) {
                    if (!tables.contains("Person")) {
                        tables.add("Person");
                    }
                    pNouns.add(new NounTableTuple(searchWord, "Person", "pob"));
                }

            }
        }

        for(WordProperty temp:querys){
            if(!temp.table.equals("Table not in the database.")){
                if (!tables.contains(temp.table)) {
                    if(temp.table.equals("Actor") && !tables.contains("Person")) {
                        tables.add("Person");
                    }
                    if(temp.table.equals("Director") && !tables.contains("Person")) {
                        tables.add("Person");
                    }
                    tables.add(temp.table);
                }
            }
        }

        for(WordProperty temp:querys){
            if(temp.POStag.equals("CD")) {
                if (tables.contains("Oscar")) {
                    pNouns.add(new NounTableTuple(temp.word, "Oscar", "year"));
                }
                else{
                    pNouns.add(new NounTableTuple(temp.word, "Movie", "year"));
                }
            }
        }

        if(tables.contains("Oscar") && !oscarType.equals("Table not in the database.")) {
            pNouns.add(new NounTableTuple(oscarType, "Oscar", "type"));
        }
        else if(!oscarType.equals("Table not in the database.") && !tables.contains("Oscar")){
            tables.add("Oscar");
            pNouns.add(new NounTableTuple(oscarType, "Oscar", "type"));
        }

        for(int i = 0; i<tables.size();i++){
            if(tables.get(i).equals("Movie")){
                tables.remove(i);
                tables.add("Movie");
            }
        }

        numberOfTables = tables.size();

        if(whType.equals("Who") || whType.equals("Which")) {
            if(tables.contains("Person")) {
                qFactory.buildBase("Person.name");
            }
            else{
                qFactory.buildBase("Movie.name");
            }
        }
        else if(whType.equals("When")) {
            qFactory.buildBase("year");
        }

        if(numberOfTables == 1){
            qFactory.buildTable(tables.get(0));
        }
        else if(numberOfTables == 2){
            qFactory.buildTables(tables.get(0), tables.get(1));
        }
        else if(numberOfTables == 3){
            qFactory.buildTables(tables.get(0), tables.get(1), tables.get(2));
        }
        else if(numberOfTables == 4){
            qFactory.buildTables(tables.get(0), tables.get(1), tables.get(2), tables.get(3));
        }


        return evaluateWHAnswer(pNouns);
    }

    public boolean evaluateAnswer(ArrayList<NounTableTuple> pNouns){
        ArrayList<NounTableTuple> noDuplicates = new ArrayList<>();
        boolean contained = false;
        boolean answer = false;
        String query = "";

        for(NounTableTuple temp: pNouns){
            for(NounTableTuple temp2: noDuplicates){
                if(temp.getNoun().equals(temp2.getNoun())){
                    System.out.println("MATCH: "+temp.getNoun());
                    contained = true;
                }
            }

            if(!contained){
                noDuplicates.add(temp);
            }

            contained = false;
        }

        int numberOfWheres = noDuplicates.size();

        if(numberOfWheres == 1){
            query = qFactory.finalQuery(noDuplicates.get(0));
        }
        else if(numberOfWheres == 2){
            query = qFactory.finalQuery(noDuplicates.get(0), noDuplicates.get(1));
        }
        else if(numberOfWheres == 3){
            query = qFactory.finalQuery(noDuplicates.get(0), noDuplicates.get(1), noDuplicates.get(2));
        }

        qFactory.printQuery();
        answer = dbAccess.executeQuery(query);

        qFactory.resetBase();
        return answer;
    }

    public String evaluateWHAnswer(ArrayList<NounTableTuple> pNouns){
        ArrayList<NounTableTuple> noDuplicates = new ArrayList<>();
        boolean contained = false;
        String answer = null;
        String query = "";

        for(NounTableTuple temp: pNouns){
            for(NounTableTuple temp2: noDuplicates){
                if(temp.getNoun().equals(temp2.getNoun())){
                    contained = true;
                }
            }

            if(!contained){
                noDuplicates.add(temp);
            }

            contained = false;
        }

        int numberOfWheres = noDuplicates.size();

        if(numberOfWheres == 1){
            query = qFactory.finalQuery(noDuplicates.get(0));
        }
        else if(numberOfWheres == 2){
            query = qFactory.finalQuery(noDuplicates.get(0), noDuplicates.get(1));
        }
        else if(numberOfWheres == 3){
            query = qFactory.finalQuery(noDuplicates.get(0), noDuplicates.get(1), noDuplicates.get(2));
        }

        qFactory.printQuery();
        answer = dbAccess.executeWHQuery(query);

        qFactory.resetBase();

        return answer;
    }
}