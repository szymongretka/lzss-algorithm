import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    final static int sizeOfDictionary = 3;
    final static int sizeOfBuffer = 3;
    static int currentPos = 0;


    static boolean containsChar(String s, char searchedChar) {
        if (s.length() == 0)
            return false;
        else
            return s.charAt(0) == searchedChar || containsChar(s.substring(1), searchedChar);
    }

    static void initialInsertDictionary(Object element, ArrayList arrayList){
        if(arrayList.size() == 0)
            arrayList.add(' ');
        for(int i = 0; i < arrayList.size(); i++)
            arrayList.add(element);
    }

    static void UpdateBuffer(ArrayList text, ArrayList buffer){
        int i = 0;

        if (buffer.size() <= sizeOfBuffer){
            for (Object o : text) {
                if(i <= sizeOfBuffer) {
                    buffer.add(o);
                    i++;
                    currentPos++;
                }
            }
        }
    }

    static void DeleteFromList(ArrayList array){

        int i = 0;

        Iterator iter = array.iterator();
        while (iter.hasNext()) {
            Character c = (Character)iter.next();
            if(i < currentPos) {
                iter.remove();
                i++;
            }
        }

        currentPos = 0;

    }

    static int[] GetIndexes(String text, String subStr){

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

        int[] arr = Arrays.copyOfRange(array, 0, j);

        return arr;
    }


    static boolean isAnyMatch(String buffer, String dictionary){
        if(dictionary.indexOf(buffer.charAt(0)) != (-1))
            return true;
        else
            return false;
    }


    public static void main(String[] args) {

        final List<Output> Coder = new ArrayList<>();

        String textToDecode = "javaisez";

        LimitedSizeQueue Dictionary = new LimitedSizeQueue(sizeOfDictionary);
        LimitedSizeQueue Buffer = new LimitedSizeQueue(sizeOfBuffer);

        ArrayList<Character> CharArrayList = new ArrayList<>(
                textToDecode
                        .chars()
                        .mapToObj(e -> (char) e)
                        .collect(Collectors.toList())
        );

        UpdateBuffer(CharArrayList, Buffer); //Initialize buffer

        initialInsertDictionary(Buffer.getFirstElement(), Dictionary); //Initialize dictionary

        DeleteFromList(CharArrayList);




        String subString = "aaajavaju";

        int[] indexArr = GetIndexes(textToDecode, subString);

        for(int i = 0; i < indexArr.length; i++){
            for(int j = 0; j < subString.length(); j++){
                textToDecode.regionMatches(0, subString, indexArr[i], j);
            }
        }

        //System.out.println(textToDecode.regionMatches(0, subString, 3, 4));



        /*while(!Buffer.isEmpty()){

            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            for (Object ob : Buffer) {
                sb.append(ob);
            }
            for (Object ob : Dictionary) {
                sb2.append(ob);
            }
            String newBuff = sb.toString();
            String newDictionary = sb2.toString();

            if(isAnyMatch(newBuff, newDictionary)) {



                //Coder.add(true,)
            } else {
                Coder.add(new Output(false, newBuff.charAt(0))); //Return (1,c) where c = xn
                Dictionary.add(newBuff.charAt(0)); //Update the Dictionary
                Buffer.remove(0);   //Update the Buffer
                UpdateBuffer(CharArrayList, Buffer);
                DeleteFromList(CharArrayList);
            }

            //System.out.println(newDictionary);
        }*/

    }
}

//brute force - ma ciąg znaków i szuka iterując po calym secie az znajdzie - jesli nie znajdzie to wstawia na koniec szukany element sztucznie
//cyclic buffer - w ciągu po zapelnieniu pamieci nadpisuje najstarsze "elementy" - FIFO
