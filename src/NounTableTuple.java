
public class NounTableTuple {

        private String noun;
        private String table;
        private String column;

        public NounTableTuple(String n, String t, String c){
            this.noun = n;
            this.table = t;
            this.column = c;
        }

        public String getNoun() {
            return noun;
        }

        public String getTable() {
            return table;
        }

        public String getColumn() {
            return column;
        }

}
