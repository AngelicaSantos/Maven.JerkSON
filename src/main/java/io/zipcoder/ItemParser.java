package io.zipcoder;

import io.zipcoder.utils.Item;
import io.zipcoder.utils.ItemParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {


    private Integer wrongEntry = 0;
    private Integer total = 0;

    public List<Item> parseItemList(String valueToParse) throws ItemParseException {
        List<Item> items = new ArrayList<>();
        Pattern pattern = Pattern.compile("([^#]*)##");
        Matcher matcher = pattern.matcher(valueToParse);

        for(int i = 0; matcher.find(); i++){
            Item temp = parseSingleItem(matcher.group());
            if(temp != null) items.add(temp);
        }

        return items;
    }

    public Item parseSingleItem(String singleItem) throws ItemParseException {
        // every item includes a name,price,type, and expiration in that order. set each with its type.
        String name = "";
        Double price = 0.0;
        String type = "";
        String expiration = "";

        // Pattern can be used to create a matcher object
        Pattern pattern = Pattern.compile("(?<=[:@^*%])(.*?)(?=[;#])");
        //matcher is used to search through a text for multiple occurances
        Matcher matcher = pattern.matcher(singleItem);

        //try catch is used to test for any errors while it executes
        try {
            //loop through the item
            for (int i = 0; matcher.find(); i++) {
                if (i == 0){ name = matcher.group().toLowerCase(); total++;}
                else if (i == 1) {price = Double.parseDouble(matcher.group()); total++;}
                else if (i == 2){ type = matcher.group().toLowerCase(); total++;}
                else if (i == 3) {expiration = matcher.group(); total++;}
                else{ System.out.println("error"); }

            }

            //should have a total of 4 items.if its less, return wrong entry.
            if (total < 4){
                wrongEntry++;
                throw new ItemParseException();

            }

            if (name.equals("") || price.equals(0.0) || type.equals("") || expiration.equals("")){
                wrongEntry++;
                return null;
            }

            return new Item(name, price, type, expiration);

        }catch (Exception e){
            wrongEntry++;
            throw new ItemParseException();
        }

    }

    public Integer getWrongEntry(){
        return wrongEntry;
    }

}



