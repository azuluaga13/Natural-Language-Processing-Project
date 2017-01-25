import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;


public class PartOfSpeechParser {

    public static String currentQuery = new String();
    public static String sqlQuery = null;

    public static List<Tree> parse(String text) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        List<Tree> result = new ArrayList<Tree>();
        for (CoreMap sentence : sentences) {
            Tree tree = sentence.get(TreeAnnotation.class);
            result.add(tree);
        }

        return result;
    }

    public static Map<Integer, CorefChain> coreferenceResolution(String text) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);

        return document.get(CorefChainAnnotation.class);
    }

    public static void printYes(){
        System.out.println("Answer: Yes");
    }

    public static void printNo(){
        System.out.println("Answer: No");
    }


    public static String computeWhichOscarType(String str){
        String words[] = str.split(" ");

        if(words[0].equals("Which")){
            String str1 = words[1];
            switch(str1){
                case "actress" : return "best-actress";
                case "actor"   : return "best-actor";
                case "movie"   : return "best-picture";
                case "director": return "best-director";
            }
        }
        return "Table not in the database.";
    }

    static void checkType(String args[]) throws IOException, FileNotFoundException {

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            StringBuilder sb = new StringBuilder();
            String text = br.readLine();
            List<Tree> trees;
            String win = "";
            while (text != null) {
                sb.append(text);
                sb.append(System.lineSeparator());
                text = br.readLine();

                Map<Integer, String> mapCounter = new HashMap<>();


                int musicCount = CategorizeWord.check_Music_Word(text);
                int movieCount = CategorizeWord.check_Movie_Word(text);
                int locationCount = CategorizeWord.check_Geographical_Location(text);


                mapCounter.put(movieCount, " Movie ");
                mapCounter.put(locationCount, " Location ");
                mapCounter.put(musicCount, " Music ");


                Iterator it = mapCounter.entrySet().iterator();
                int max;
                while (it.hasNext()) {
                    max = 0;
                    Map.Entry c = (Map.Entry) it.next();
                    Integer categoryMaxkey = (Integer) c.getKey();
                    if (categoryMaxkey > max) {
                        max = categoryMaxkey;

                    }
                    win = mapCounter.get(max);
                }

                trees = parse(text);
                for (Tree tree : trees) {
                    System.out.println("\n" + "<SENTENCE>   " + text);
                    System.out.println("<CATEGORY>  " + win);
                    System.out.println("<PARSETREE> " + tree + "\n\n\n");
                }

            }
            String everything = sb.toString();
        }
    }

    static void parseQuery(){

        String str = "Who starred in Schindler’s List?";
        Tuple tupleTemp = Label.runSentence(str);
        BusinessTier bsnRn = new BusinessTier();
        boolean yesNoAnswer;
        String whAnswer;

        if(tupleTemp.questionWord.equals("Who") || tupleTemp.questionWord.equals("Which") || tupleTemp.questionWord.equals("When") ){

            str = "Who directed Schindler’s List?";
            System.out.println();
            System.out.println("QUESTION: " + str);
            tupleTemp = Label.runSentence(str);
            if(tupleTemp.oscarType.equals("Table not in the database.")){
                tupleTemp.oscarType = computeWhichOscarType(str);
            }
            whAnswer = bsnRn.determineWHQuestion(tupleTemp.labeledWordList, tupleTemp.questionWord, tupleTemp.oscarType);

            // print out the wh-Answer
            System.out.println("Answer: "+whAnswer);


            str = "Who won the oscar for best actor in 2005?";
            System.out.println();
            System.out.println("QUESTION: " + str);
            tupleTemp = Label.runSentence(str);
            if(tupleTemp.oscarType.equals("Table not in the database.")){
                tupleTemp.oscarType = computeWhichOscarType(str);
            }
            whAnswer = bsnRn.determineWHQuestion(tupleTemp.labeledWordList, tupleTemp.questionWord, tupleTemp.oscarType);

            // print out the wh-Answer
            System.out.println("Answer: "+whAnswer);


            str = "Which movie won the oscar in 2000?";
            System.out.println();
            System.out.println("QUESTION: " + str);
            tupleTemp = Label.runSentence(str);
            if(tupleTemp.oscarType.equals("Table not in the database.")){
                tupleTemp.oscarType = computeWhichOscarType(str);
            }
            whAnswer = bsnRn.determineWHQuestion(tupleTemp.labeledWordList, tupleTemp.questionWord, tupleTemp.oscarType);

            // print out the wh-Answer
            System.out.println("Answer: "+whAnswer);


            str = "Which actress won the oscar in 2012?";
            System.out.println();
            System.out.println("QUESTION: " + str);
            tupleTemp = Label.runSentence(str);
            if(tupleTemp.oscarType.equals("Table not in the database.")){
                tupleTemp.oscarType = computeWhichOscarType(str);
            }
            whAnswer = bsnRn.determineWHQuestion(tupleTemp.labeledWordList, tupleTemp.questionWord, tupleTemp.oscarType);

            if(whAnswer != null){
                System.out.println("Answer: "+whAnswer);
            }
            else{
                System.out.println("Answer: No one");
            }

            str = "Who directed the best movie in 2010?";
            System.out.println();
            System.out.println("QUESTION: " + str);
            tupleTemp = Label.runSentence(str);

            if(tupleTemp.oscarType.equals("Table not in the database.")){
                tupleTemp.oscarType = computeWhichOscarType(str);
            }

            whAnswer = bsnRn.determineWHQuestion(tupleTemp.labeledWordList, tupleTemp.questionWord, tupleTemp.oscarType);
            if(whAnswer != null){
                System.out.println("Answer: "+whAnswer);
            }
            else{
                System.out.println("Answer: No one");
            }

            str = "When did Blanchett win an oscar for best actress?";
            System.out.println();
            System.out.println("QUESTION: " + str);
            tupleTemp = Label.runSentence(str);
            if(tupleTemp.oscarType.equals("Table not in the database.")){
                tupleTemp.oscarType = computeWhichOscarType(str);
            }
            whAnswer = bsnRn.determineWHQuestion(tupleTemp.labeledWordList, tupleTemp.questionWord, tupleTemp.oscarType);
            if(whAnswer != null){
                System.out.println("Answer: "+whAnswer);
            }
            else{
                System.out.println("Answer: No one");
            }


        }

        str = "Is Kubrick a director?";
        System.out.println();
        System.out.println("QUESTION: " + str);
        tupleTemp = Label.runSentence(str);
        yesNoAnswer = bsnRn.determineYesNoQuestion(tupleTemp.labeledWordList, tupleTemp.oscarType);

        if(yesNoAnswer){
            printYes();
        }
        else{
            printNo();
        }

        str = "Is Mighty Aphrodite by Allen?";
        System.out.println();
        System.out.println("QUESTION: " + str);
        tupleTemp = Label.runSentence(str);
        yesNoAnswer = bsnRn.determineYesNoQuestion(tupleTemp.labeledWordList, tupleTemp.oscarType);

        if(yesNoAnswer){
            printYes();
        }
        else{
            printNo();
        }

        str = "Was Loren born in Italy?";
        System.out.println();
        System.out.println("QUESTION: " + str);
        tupleTemp = Label.runSentence(str);
        yesNoAnswer = bsnRn.determineYesNoQuestion(tupleTemp.labeledWordList, tupleTemp.oscarType);

        if(yesNoAnswer){
            printYes();
        }
        else{
            printNo();
        }

        str = "Was Birdman the best movie in 2015?";
        System.out.println();
        System.out.println("QUESTION: " + str);
        tupleTemp = Label.runSentence(str);
        bsnRn.determineYesNoQuestion(tupleTemp.labeledWordList, tupleTemp.oscarType);
        if(yesNoAnswer){
            printYes();
        }
        else{
            printNo();
        }
        str = "Did Neeson star in Schindler’s List?";
        System.out.println();
        System.out.println("QUESTION: " + str);
        tupleTemp = Label.runSentence(str);
        yesNoAnswer = bsnRn.determineYesNoQuestion(tupleTemp.labeledWordList, tupleTemp.oscarType);

        if(yesNoAnswer){
            printYes();
        }
        else{
            printNo();
        }

        str = "Did Swank win the oscar in 2000?";
        System.out.println();
        System.out.println("QUESTION: " + str);
        tupleTemp = Label.runSentence(str);
        yesNoAnswer = bsnRn.determineYesNoQuestion(tupleTemp.labeledWordList, tupleTemp.oscarType);

        if(yesNoAnswer){
            printYes();
        }
        else{
            printNo();
        }

        str = "Did a French actor win the oscar in 2012?";
        System.out.println();
        System.out.println("QUESTION: " + str);
        tupleTemp = Label.runSentence(str);
        yesNoAnswer = bsnRn.determineYesNoQuestion(tupleTemp.labeledWordList, tupleTemp.oscarType);

        if(yesNoAnswer){
            printYes();
        }
        else{
            printNo();
        }

        str = "Did a movie with Neeson win the oscar for best film?";
        System.out.println();
        System.out.println("QUESTION: " + str);
        tupleTemp = Label.runSentence(str);
        yesNoAnswer = bsnRn.determineYesNoQuestion(tupleTemp.labeledWordList, tupleTemp.oscarType);

        if(yesNoAnswer){
            printYes();
        }
        else{
            printNo();
        }

        str = "Did Hathaway win the oscar for best actress in 2013?";
        System.out.println();
        System.out.println("QUESTION: " + str);
        tupleTemp = Label.runSentence(str);
        yesNoAnswer = bsnRn.determineYesNoQuestion(tupleTemp.labeledWordList, tupleTemp.oscarType);

        if(yesNoAnswer){
            printYes();
        }
        else{
            printNo();
        }


    }

    public static void main(String[] args) throws IOException, FileNotFoundException {

        System.out.println("Welcome! This is MiniWatson.");
        //System.out.println("Please ask a question. Type 'q' when finished.");
        System.out.println();
        String input;
//        Scanner keyboard = new Scanner(System.in);
//        do{
//            input =keyboard.nextLine().trim();
//
//            if(!input.equalsIgnoreCase("q")){
//                currentQuery = input;
//                System.out.println("<QUERY>\n" + currentQuery);
//                //TODO perform any query processing
//                parseQuery(input);
//                // printSQL(); //TODO implement method below
//                // printAnswer(); //TODO implement method below
//                System.out.println();
//            }
//        }while(!input.equalsIgnoreCase("q"));
//
//        keyboard.close();


        //checkType(args);
        parseQuery();

        System.out.println("Goodbye.");

    }
}
