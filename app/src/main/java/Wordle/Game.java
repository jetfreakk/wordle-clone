package Wordle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    protected String userDictionary;
    protected List<String> userWordList;
    protected String chosenWordListFile;
    protected String chosenWord;
    protected List<String> chosenWordList;
    protected List<Character> greenChars = new ArrayList<>();
    protected List<Character> yellowChars = new ArrayList<>();
    protected List<Character> redChars = new ArrayList<>();
    protected String result;
    Game()
    {
        chosenWordListFile = "dic.txt";
        userDictionary = "user-dic.txt";
        result = "Result: ";
    }
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[32m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[33m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[31m";


    
    public String getRandomWord(List<String> wordList) {
        Random rand = new Random();
        int bound = wordList.size();
        int randint = rand.nextInt(bound);
        return wordList.get(randint);
    }

    
    public String validateWord(List <String> wList, int cnt)
    {
        Scanner sc = new Scanner(System.in);
        String userWord = sc.nextLine();
        userWord = userWord.toLowerCase();

        while ((userWord.length() != 5) || !(wList.contains(userWord))) {
            if ((userWord.length() != 5)) {
                System.out.println("The word " + userWord + " is not 5 letters.");
            } else {
                System.out.println("The word " + userWord + " is invalid");
            }
            System.out.println("Enter a new word.");
            System.out.print("--> " + (cnt + 1) + ") ");
            userWord = sc.nextLine();
        }
        return userWord;
    }
    public void printingColouredAlphabet(List<Character> greenChars, List<Character> yellowChars, List<Character> redChars) {
        char c;

        for (c = 'A'; c <= 'Z'; ++c) {
            if (greenChars.contains(c)) {
                System.out.print(ANSI_GREEN_BACKGROUND + c + ANSI_RESET + " ");
            } else if (yellowChars.contains(c)) {
                System.out.print(ANSI_YELLOW_BACKGROUND + c + ANSI_RESET + " ");
            } else if (redChars.contains(c)) {
                System.out.print(ANSI_RED_BACKGROUND + c + ANSI_RESET + " ");
            } else {
                System.out.print(c + " ");
            }
        }

    }

    public String replaceChar(String str, char ch, int index) {
        char[] chars = str.toCharArray();
        chars[index] = ch;
        return String.valueOf(chars);
    }

    public void Guesses(List<String> wordList) {

        for (int j = 0; j < 6; j++) {

            System.out.print("--> " + (j + 1) + ") ");

            String userWord = validateWord(wordList, j);

            String chosenWordWithoutGreensAndYellows = chosenWord;

            if (userWord.equals(chosenWord)) {
                System.out.println((result + ANSI_GREEN_BACKGROUND + userWord.toUpperCase() + ANSI_RESET));
                System.out.println();
                System.out.println("You won");
                break;
            } else {

                System.out.print(result);
                String userWordWithoutGreensAndYellows = userWord;
                String[] positionColors = new String[5];

                for (int i = 0; i < 5; i++) {
                    if (userWord.charAt(i) == chosenWord.charAt(i)) {
                        userWordWithoutGreensAndYellows = replaceChar(userWordWithoutGreensAndYellows, '0', i);
                        chosenWordWithoutGreensAndYellows = replaceChar(chosenWordWithoutGreensAndYellows, '0', i);
                        greenChars.add(userWord.toUpperCase().charAt(i));
                        positionColors[i] = ANSI_GREEN_BACKGROUND;
                    }
                }
                for (int i = 0; i < 5; i++) {
                    if (userWordWithoutGreensAndYellows.charAt(i) == '0') {

                    } else if (chosenWordWithoutGreensAndYellows.indexOf(userWordWithoutGreensAndYellows.charAt(i)) != -1) {
                        chosenWordWithoutGreensAndYellows = replaceChar(chosenWordWithoutGreensAndYellows, '0', chosenWordWithoutGreensAndYellows.indexOf(userWordWithoutGreensAndYellows.charAt(i)));
                        userWordWithoutGreensAndYellows = replaceChar(userWordWithoutGreensAndYellows, '0', i);
                        yellowChars.add(userWord.toUpperCase().charAt(i));
                        positionColors[i] = ANSI_YELLOW_BACKGROUND;
                    } else {
                        redChars.add(userWord.toUpperCase().charAt(i));
                        positionColors[i] = ANSI_RED_BACKGROUND;
                    }
                }


                for (int i = 0; i < 5; i++) {
                    System.out.print(positionColors[i] + userWord.toUpperCase().charAt(i) + ANSI_RESET);
                }
                System.out.println();

                printingColouredAlphabet(greenChars, yellowChars, redChars);

            }

            System.out.println();
            if (j == 5) {
                System.out.println();
                System.out.println("Try again next time");
                System.out.println("The word was "+ chosenWord);
                System.out.println();

            }
        }
    }
    public void start()
    {
        FileProcessor File = new FileProcessor();
        List <String> chosenWordList= File.read(chosenWordListFile);
        List <String> userWordList= File.read(userDictionary);
        chosenWord = getRandomWord(chosenWordList);
        this.Guesses(userWordList);
    }

}
