import edu.stanford.nlp.util.ArrayUtils;

public class CategorizeWord {

    static public int check_Music_Word(String inputSen){
        String [] music_key_word = {"sing","album","track","released","sings","pop","rap","hip-hop","countrymusic","jazz","blues","rock","music","song","songs", "Beyonce","Swift","LadyGaga", "Thriller", "Beatit"};
        return categoryCounter(inputSen, music_key_word);
    }
    static public int check_Movie_Word(String inputSen){
        String [] movie_key_word = {"star","win","oscar","actor","film","movie","directed","scary","actress","won","stars","filmed","Shining","Swank", "Hugo", "Kubrik", "Nicholson", "Spielberg", "Neeson", "Schindler's List"};
        return categoryCounter(inputSen, movie_key_word);
    }
    static public int check_Geographical_Location(String inputSen){
        String []geography_key_word = {"capital","deeper","mountain","ocean","continent","countries","border","map","highest","higher","deepest", "Where"};
        return categoryCounter(inputSen, geography_key_word);
    }

    static private int categoryCounter(String inputSen, String [] word_training_Set){
        int i = 0;
        String [] splitter_sentence = inputSen.split(" ");

        for(  String checker : splitter_sentence){
            if (ArrayUtils.contains(word_training_Set,checker)){
                i++;
            }
        }

        return i;
    }
}
