import java.util.Hashtable;

public class Queries {

        private Hashtable<String, String> ids;
        private String base;

        public Queries(){
            base = "SELECT *";

            ids = new Hashtable<>();

            ids.put("Actor", "Actor.actor_id");
            ids.put("Actor2", "Actor.movie_id");
            ids.put("Director", "Director.director_id");
            ids.put("Director2", "Director.movie_id");
            ids.put("Oscar", "Oscar.person_id");
            ids.put("Oscar2", "Oscar.movie_id");
            ids.put("Movie", "Movie.id");
            ids.put("Person", "Person.id");

        }

        public void resetBase(){
            base = "SELECT *";
        }

        public void buildBase(String whType){
            base = "SELECT "+whType;
        }

        public void buildTable(String table1){
            base = base+" FROM "+table1;
        }

        public void buildTables(String table1, String table2){
            String secondActorOscar = table1+"2";
            String secondTable = table2+"2";
            base = base+" FROM "+table1 +" INNER JOIN "+table2+" ON ";


            if(table1 == "Movie" && table2 == "Oscar"){
                base = base + ids.get(table1) + " = "+ids.get(secondTable);
            }
            else if(table2 == "Movie" && table1 == "Oscar"){
                base = base+ids.get(secondActorOscar)+" = "+ids.get(table2);
            }
            else if(table1 == "Movie" && table2 == "Actor"){
                base = base + ids.get(table1) + " = "+ids.get(secondTable);
            }
            else if(table2 == "Movie" && table1 == "Actor"){
                base = base+ids.get(secondActorOscar)+" = "+ids.get(table2);
            }
            else{
                base = base+ids.get(table1)+" = "+ids.get(table2);
            }
        }

        public void buildTables(String table1, String table2, String table3){

            base = base+" FROM "+table1 +" INNER JOIN "+table2+" ON ";

            if(table1 == "Movie" && table2 == "Oscar"){
                base = base + ids.get(table1) + " = "+ids.get(table2+"2");
            }
            else if(table2 == "Movie" && table1 == "Oscar"){
                base = base+ids.get(table1+"2")+" = "+ids.get(table2);
            }
            if(table1 == "Movie" && table2 == "Actor"){
                base = base + ids.get(table1) + " = "+ids.get(table2+"2");
            }
            else if(table2 == "Movie" && table1 == "Actor"){
                base = base+ids.get(table1+"2")+" = "+ids.get(table2);
            }
            if(table1 == "Movie" && table2 == "Director"){
                base = base + ids.get(table1) + " = "+ids.get(table2+"2");
            }
            else if(table2 == "Movie" && table1 == "Director"){
                base = base+ids.get(table1+"2")+" = "+ids.get(table2);
            }
            else{
                base = base+ids.get(table1)+
                        " = "+ids.get(table2);
            }


            if(table2 == "Movie" && table3 == "Oscar"){
                base = base+" INNER JOIN "+table3+" ON "+ids.get(table3) + " = "+ids.get(table2+"2");
            }
            else if(table3 == "Movie" && table2 == "Oscar"){
                base = base+" INNER JOIN "+table3+" ON "+ids.get(table3)+" = "+ids.get(table2+"2");
            }
            else if(table2 == "Movie" && table3 == "Actor"){
                base = base+" INNER JOIN "+table3+" ON "+ids.get(table3) + " = "+ids.get(table2+"2");
            }
            else if(table3 == "Movie" && table2 == "Actor"){
                base = base+" INNER JOIN "+table3+" ON "+ids.get(table3)+" = "+ids.get(table2+"2");
            }
            else if(table2 == "Movie" && table3 == "Director"){
                base = base+" INNER JOIN "+table3+" ON "+ ids.get(table2) + " = "+ids.get(table3+"2");
            }
            else if(table3 == "Movie" && table2 == "Director"){
                base = base+" INNER JOIN "+table3+" ON "+ids.get(table3)+" = "+ids.get(table2+"2");
            }

            else{
                base = base+" INNER JOIN "+table3+" ON "+ids.get(table2)+
                        " = "+ids.get(table3);
            }
        }

        public void buildTables(String table1, String table2, String table3, String table4){

            base = base+" FROM "+table1 +" INNER JOIN "+table2+" ON ";

            if(table1 == "Movie" && table2 == "Oscar"){
                base = base + ids.get(table1) + " = "+ids.get(table2+"2");
            }
            else if(table2 == "Movie" && table1 == "Oscar"){
                base = base+ids.get(table1+"2")+" = "+ids.get(table2);
            }
            else if(table1 == "Movie" && table2 == "Actor"){
                base = base + ids.get(table1) + " = "+ids.get(table2+"2");
            }
            else if(table2 == "Movie" && table1 == "Actor"){
                base = base+ids.get(table1+"2")+" = "+ids.get(table2);
            }
            else if(table1 == "Movie" && table2 == "Director"){
                base = base + ids.get(table1) + " = "+ids.get(table2+"2");
            }
            else if(table2 == "Movie" && table1 == "Director"){
                base = base+ids.get(table1+"2")+" = "+ids.get(table2);
            }
            else{
                base = base+ids.get(table1)+
                        " = "+ids.get(table2);
            }


            if(table2 == "Movie" && table3 == "Oscar"){
                base = base+" INNER JOIN "+table3+" ON "+ids.get(table3+"2") + " = "+ids.get(table2);
            }
            else if(table3 == "Movie" && table2 == "Oscar"){
                base = base+" INNER JOIN "+table3+" ON "+ids.get(table3)+" = "+ids.get(table2+"2");
            }
            else if(table2 == "Movie" && table3 == "Actor"){
                base = base+" INNER JOIN "+table3+" ON "+ids.get(table3+"2") + " = "+ids.get(table2);
            }
            else if(table3 == "Movie" && table2 == "Actor"){
                base = base+" INNER JOIN "+table3+" ON "+ids.get(table3)+" = "+ids.get(table2+"2");
            }
            else if(table2 == "Movie" && table3 == "Director"){
                base = base+" INNER JOIN "+table3+" ON "+ ids.get(table2) + " = "+ids.get(table3+"2");
            }
            else if(table3 == "Movie" && table2 == "Director"){
                base = base+" INNER JOIN "+table3+" ON "+ids.get(table3)+" = "+ids.get(table2+"2");
            }
            else{
                base = base+" INNER JOIN "+table3+" ON "+ids.get(table2)+
                        " = "+ids.get(table3);
            }

            if(table3 == "Movie" && table4 == "Oscar"){
                base = base+" INNER JOIN "+table4+" ON "+ids.get(table4+"2") + " = "+ids.get(table3);
            }
            else if(table4 == "Movie" && table3 == "Oscar"){
                base = base+" INNER JOIN "+table4+" ON "+ids.get(table3+"2")+" = "+ids.get(table4);
            }
            else if(table3 == "Movie" && table4 == "Actor"){
                base = base+" INNER JOIN "+table4+" ON "+ids.get(table4+"2") + " = "+ids.get(table3);
            }
            else if(table4 == "Movie" && table3 == "Actor"){
                base = base+" INNER JOIN "+table4+" ON "+ids.get(table4)+" = "+ids.get(table3+"2");
            }
            else if(table3 == "Movie" && table4 == "Director"){
                base = base+" INNER JOIN "+table4+" ON "+ ids.get(table3) + " = "+ids.get(table4+"2");
            }
            else if(table4 == "Movie" && table3 == "Director"){
                base = base+" INNER JOIN "+table4+" ON "+ids.get(table4)+" = "+ids.get(table3+"2");
            }
            else{
                base = base+" INNER JOIN "+table4+" ON "+ids.get(table3)+
                        " = "+ids.get(table4);
            }
        }

        public String finalQuery(NounTableTuple tuple){

            base = base + " WHERE "+tuple.getTable()+"."+tuple.getColumn()+" LIKE '%"+tuple.getNoun()+"%'";

            return base;
        }

        public String finalQuery(NounTableTuple tuple, NounTableTuple tuple2){

            base = base + " WHERE "+tuple.getTable()+"."+tuple.getColumn()+" LIKE '%"+tuple.getNoun()+"%'"+
                    " AND "+tuple2.getTable()+"."+tuple2.getColumn()+" LIKE '%"+tuple2.getNoun()+"%'";

            return base;
        }

        public String finalQuery(NounTableTuple tuple, NounTableTuple tuple2, NounTableTuple tuple3){

            base = base + " WHERE "+tuple.getTable()+"."+tuple.getColumn()+" LIKE '%"+tuple.getNoun()+"%'"+
                    " AND "+tuple2.getTable()+"."+tuple2.getColumn()+" LIKE '%"+tuple2.getNoun()+"%'"+
                    " AND "+tuple3.getTable()+"."+tuple3.getColumn()+" LIKE '%"+tuple3.getNoun()+"%'";

            return base;
        }

        public void printQuery(){
            System.out.println("QUERY: "+base);
        }
}
