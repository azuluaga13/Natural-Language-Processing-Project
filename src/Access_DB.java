import java.sql.*;

public class Access_DB {

    private Connection con = null;

    public Access_DB(){
    }

    public boolean executeQuery(String query) {
        try{
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:" +
                    "C:\\Users\\Anthony\\Desktop\\SqliteDatabases\\oscar-movie_imdb.sqlite");

            Statement stmt;

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if(rs.next()){
                return true;
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }


        return false;
    }

    public String executeWHQuery(String query){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:" +
                    "C:\\Users\\Anthony\\Desktop\\SqliteDatabases\\oscar-movie_imdb.sqlite");

            Statement stmt;

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if(rs.next()){
                return rs.getString(1);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }


        return null;
    }

    public boolean searchPersonName(String name){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:" +
                    "C:\\Users\\Anthony\\Desktop\\SqliteDatabases\\oscar-movie_imdb.sqlite");

            Statement stmt;

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Person WHERE Person.name" +
                    " LIKE '%"+name+"'");

            if(rs.next()){
                return true;
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }

    public boolean searchPersonLocation(String place){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:" +
                    "C:\\Users\\Anthony\\Desktop\\SqliteDatabases\\oscar-movie_imdb.sqlite");

            Statement stmt;

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Person WHERE Person.pob" +
                    " LIKE '%"+place+"%'");

            if(rs.next()){
                return true;
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }

    public String searchMovieName(String name){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:" +
                    "C:\\Users\\Anthony\\Desktop\\SqliteDatabases\\oscar-movie_imdb.sqlite");

            Statement stmt;

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Movie WHERE Movie.name" +
                    " LIKE '%"+name+"%'");

            if(rs.next()){
                return rs.getString("name");
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return "Table not in database.";
    }

    public boolean searchMovieYear(String year){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:" +
                    "C:\\Users\\Anthony\\Desktop\\SqliteDatabases\\oscar-movie_imdb.sqlite");

            Statement stmt;

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Movie WHERE Movie.year" +
                    " LIKE '%"+year+"%'");

            if(rs.next()){
                return true;
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }

    public boolean searchIsActor(String name){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:" +
                    "C:\\Users\\Anthony\\Desktop\\SqliteDatabases\\oscar-movie_imdb.sqlite");

            Statement stmt;

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Person INNER JOIN ACTOR ON Actor.actor_id = Person.id WHERE Person.name" +
                    " LIKE '%"+name+"'");

            if(rs.next()){
                return true;
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }
}
