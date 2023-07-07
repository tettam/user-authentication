package com.marcotettamanti.userauthentication.utils;

public class NameTitleCase {
  
  public static String titleCase(String name){
    StringBuilder result = new StringBuilder();

    String[] listName = name.split(" ");
    for (String item : listName) {
      if(item.length() > 1){
        result.append(item.substring(0, 1).toUpperCase() + item.substring(1).toLowerCase() + " ");
      } else {
        result.append(item + " ");
      }
    }
    return result.toString().trim();
  }
}
