import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    final static int sizeOfDictionary = 7;
    static int sizeOfBuffer = 3;

    static boolean containsChar(String s, char searchedChar) {
        if (s.length() == 0)
            return false;
        else
            return s.charAt(0) == searchedChar || containsChar(s.substring(1), searchedChar);
    }

    static void UpdateDictionary(String buffer, ArrayList dictionary, int howManyElements){
        if(dictionary.size() == 0) {
            dictionary.add(' ');
            for (int i = 0; i < dictionary.size(); i++)
                dictionary.add(buffer.charAt(0)); //initialize with first element
        } else {
            int i = 0;
            for (int j = 0; j < buffer.length(); j++) {
                if(i < howManyElements) {
                    dictionary.add(buffer.charAt(j));
                    i++;
                }
            }
        }


    }

    static void UpdateBuffer(ArrayList text, ArrayList buffer, int howManyElements){
        int i = 0;

        if (text.size() != 0){
            for (Object o : text) {
                if(i < howManyElements) {
                    buffer.add(o);
                    i++;
                }
            }
        }
    }

    static void DeleteFromList(ArrayList array, int howManyElements){

        int i = 0;

        Iterator iter = array.iterator();
        while (iter.hasNext()) {
            Character c = (Character)iter.next();
            if(i < howManyElements) {
                iter.remove();
                i++;
            }
        }
    }

    static int[] GetOffsetsAndLengths(String text, String subStr){

        //int index = subStr.indexOf(text.charAt(0));
        int index = text.charAt(0);
        int[] array = new int[subStr.length()];
        Arrays.fill(array, -1);

        int j = 0;

        for(int i=0; i < subStr.length(); i++) {
            char c = subStr.charAt(i);
            if(c == index) {
                array[j] = i;
                j++;
            }
        }

        int[] offsets = Arrays.copyOfRange(array, 0, j);

        int[] longestSubstring = new int[offsets.length];

        for(int i = 0; i < offsets.length; i++){
            for(int k = 0; k < subStr.length(); k++) {
                if(text.regionMatches(0, subStr, offsets[i], k)) {
                    longestSubstring[i] = k;
                    continue;
                }
            }
        }

        int maxAt = 0;

        for (int i = 0; i < longestSubstring.length; i++) {
            maxAt = longestSubstring[i] > longestSubstring[maxAt] ? i : maxAt;
        }

        int[] offsetsAndLengths = new int[2];

        offsetsAndLengths[0] = offsets[maxAt];
        offsetsAndLengths[1] = longestSubstring[maxAt];

        return offsetsAndLengths;
    }


    static boolean isAnyMatch(String buffer, String dictionary){
        return dictionary.indexOf(buffer.charAt(0)) != (-1);
    }


    public static void main(String[] args) {

        final List<Output> Coder = new ArrayList<>();

        String textToDecode = "ABABCDABCABCDCADABCA";

        LimitedSizeQueue Dictionary = new LimitedSizeQueue(sizeOfDictionary);
        LimitedSizeQueue Buffer = new LimitedSizeQueue(sizeOfBuffer);

        ArrayList<Character> CharArrayList = new ArrayList<>(
                textToDecode
                        .chars()
                        .mapToObj(e -> (char) e)
                        .collect(Collectors.toList())
        );

        UpdateBuffer(CharArrayList, Buffer, sizeOfBuffer+1); //Initialize buffer

        UpdateDictionary(textToDecode, Dictionary, sizeOfDictionary); //Initialize dictionary

        DeleteFromList(CharArrayList, sizeOfBuffer+1);


        while(!Buffer.isEmpty()){ //compression
            StringBuilder buf = new StringBuilder();
            StringBuilder dic = new StringBuilder();
            for (Object ob : Buffer) {
                buf.append(ob);
            }
            for (Object ob : Dictionary) {
                dic.append(ob);
            }
            String newBuff = buf.toString();
            String newDictionary = dic.toString();

            int[] OffsetAndLength;

            if(isAnyMatch(newBuff, newDictionary)) {
                OffsetAndLength = GetOffsetsAndLengths(newBuff, newDictionary); //find longest substring and offset
                DeleteFromList(Buffer, OffsetAndLength[1]);
                UpdateBuffer(CharArrayList, Buffer, OffsetAndLength[1]); //Update Buffer with given length
                UpdateDictionary(newBuff, Dictionary, OffsetAndLength[1]);
                DeleteFromList(CharArrayList, OffsetAndLength[1]);
                Coder.add(new Output(true, OffsetAndLength[0], OffsetAndLength[1]));
            } else if(!isAnyMatch(newBuff, newDictionary)) {
                Coder.add(new Output(false, newBuff.charAt(0))); //Return (1,c) where c = xn
                Dictionary.add(newBuff.charAt(0)); //Update the Dictionary
                UpdateBuffer(CharArrayList, Buffer, 1); //Update the Buffer
                DeleteFromList(CharArrayList, 1);
            }

          System.out.println("Dictionary: " + newDictionary + " buffer: " + newBuff);

        }

    }
}

//brute force - ma ciąg znaków i szuka iterując po calym secie az znajdzie - jesli nie znajdzie to wstawia na koniec szukany element sztucznie
//cyclic buffer - w ciągu po zapelnieniu pamieci nadpisuje najstarsze "elementy" - FIFO
