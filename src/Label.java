import java.util.*;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.ArrayUtils;


public class Label extends PartOfSpeechParser{


    private static String associateTable(String word)
    {
        String[] directorList = {"director","by","directed"};
        String[] actorList = {"actor","actress","star","starred"};
        String[] oscarList = {"oscar"};
        String[] movieList = {"movie"};

        if(ArrayUtils.contains(directorList,word))   return     "Director";
        else if(ArrayUtils.contains(actorList,word)) return     "Actor";
        else if(ArrayUtils.contains(oscarList,word)) return     "Oscar";
        else if(ArrayUtils.contains(movieList,word)) return     "Movie";

        return "Table not in the database.";
    }

    private static void mapWords(ArrayList<WordProperty> importantWords)
    {
        for(WordProperty tuple : importantWords)
        {

            if(!tuple.POStag.equals("NNP"))
            {
                tuple.table = associateTable(tuple.word);
            }
        }
    }

    private static String replaceNationality(String nationality)
    {
        if(nationality.equals("Italian"))
        {
            return "Italy";
        }
        else if(nationality.equals("German"))
        {
            return "Germany";
        }
        else if(nationality.equals("British"))
        {
            return "UK";
        }
        else if(nationality.equals("French"))
        {
            return "France";
        }
        else if(nationality.equals("American"))
        {
            return "USA";
        }
        else
        {
            return nationality;
        }
    }

    public static Tuple runSentence(String sentence)
    {
        String questionWord = sentence.split(" ",2)[0];
        sentence = sentence.split(" ",2)[1];
        List<Tree> trees = parse(sentence);
        ArrayList<WordProperty> importantWords = new ArrayList<WordProperty>();

        String noun;
        String currentTag = "";
        String award = "Table not in the database.";
        String prevWord = "";

        for(Tree tree : trees)
        {
            for(Tree subtree : tree)
            {
                if(subtree.label().value().equals("NNP") || subtree.label().value().equals("IN") || subtree.label().value().equals("NN")
                        || subtree.label().value().equals("VB") || subtree.label().value().equals("VBD") || subtree.label().value().equals("JJR") || subtree.label().value().equals("JJ")
                        || subtree.label().value().equals("JJS") || subtree.label().value().equals("CD") ||  subtree.label().value().equals("NNPS")
                        || subtree.label().value().equals("POS") || subtree.label().value().equals("VBN"))
                {
                    importantWords.add(new WordProperty(subtree.getChild(0).value(),subtree.label().value()));

                    if(prevWord.equals("best"))
                    {
                        if(subtree.getChild(0).value().equals("movie") || subtree.getChild(0).value().equals("film"))
                        {
                            award = "best-" + "picture";
                        }
                        else
                        {
                            award = "best-" + subtree.getChild(0).value();
                        }
                    }

                    prevWord = subtree.getChild(0).value();
                }
            }
        }


        for(WordProperty x : importantWords)
        {
            if(Character.isUpperCase(x.word.charAt(0)))
            {
                x.POStag = "NNP";
            }

            if(x.POStag.equals("NNPS"))
            {
                x.POStag = "NNP";
            }
        }

        int size = importantWords.size();

        for(int i = 0 ; i < size-1; i++)
        {
            if(importantWords.get(i).POStag.equals("NNP") && importantWords.get(i+1).POStag.equals("NNP"))
            {
                importantWords.get(i+1).word = importantWords.get(i).word + " " + importantWords.get(i+1).word;
                importantWords.get(i).word = "NULL";
            }
            else if(importantWords.get(i).POStag.equals("NNP") && importantWords.get(i+1).POStag.equals("POS"))
            {
                importantWords.get(i+1).word = importantWords.get(i).word + importantWords.get(i+1).word;
                importantWords.get(i+1).POStag = "NNP";
                importantWords.get(i).word = "NULL";
            }
            else if(importantWords.get(i).POStag.equals("NNP") && importantWords.get(i+1).POStag.equals("CD"))
            {
                importantWords.get(i+1).word = importantWords.get(i).word + " " + importantWords.get(i+1).word;
                importantWords.get(i+1).POStag = "NNP";
                importantWords.get(i).word = "NULL";
            }

        }


        ArrayList<WordProperty> finalList = new ArrayList<WordProperty>();

        for(WordProperty x : importantWords)
        {
            if(!x.word.equals("NULL"))
            {
                finalList.add(x);
            }
        }

        mapWords(finalList);

        for(WordProperty x : finalList)
        {
            x.word = replaceNationality(x.word);
        }

        return new Tuple(finalList,questionWord,award);
    }
}