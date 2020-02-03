import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * 
 * A simple example to send HTTP request from Java
 *
 */
public class Main {

  public static void main(String args[]) throws IOException {
	  int ids[] = {
			  		505651,
				  	667024,
				  	1111691,
				  	635342,
				  	556315,
				  	798319,
				  	538796,
				  	213688,
				  	500506,
				  	245589,
				  	496373,
				  	595476,
				  	245099,
				  	548180,
				  	628008,
				  	500379,
				  	694988,
				  	621207,
				  	613283,
				  	492701,
				  	495363,
				  	547720,
				  	490875,
				  	185005,
				  	374064,
				  	248174,
				  	485424,
				  	826953,
				  	723984,
				  	245370,
				  	1072520,
				  	770019,
			  	};
	   
	  
	  
	  
	  HashMap<String, String> hmap = new HashMap<String, String>();
   // URL url = new URL("https://api.spoonacular.com/recipes/search?apiKey=720fde9020ea45788f837f177ff1f509&query=-pie&number=1");
    
    URL url = new URL("https://api.spoonacular.com/recipes/616539/information?apiKey=720fde9020ea45788f837f177ff1f509&includeNutrition=false");
    
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");

    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    System.out.println("in print");
        
    FileWriter csvWriter = new FileWriter("test.csv");
    
    csvWriter.write("Title");
    csvWriter.write(",");
    csvWriter.write("ID");
    csvWriter.write(",");
    csvWriter.write("Price Per Serving");
    csvWriter.write(",");
    csvWriter.write("Cheap");
    csvWriter.write(",");
    csvWriter.write("Health Score");
    csvWriter.write(",");
    csvWriter.write("Likes");
    csvWriter.write(",");
    csvWriter.write("Ready Time");
    csvWriter.write(",");
    csvWriter.write("Vegetarian");
    csvWriter.write(",");
    csvWriter.write("Vegan");
    csvWriter.write(",");
    csvWriter.write("Gluten Free");
    csvWriter.write(",");
    csvWriter.write("Ketogenic");
    csvWriter.write(",");
    csvWriter.write("Dish Types");
    csvWriter.write(",");
    csvWriter.write("\n");
    
    String inputLine;
    int i = 0;
    while (((inputLine = in.readLine()) != null) && i < ids.length) {
      System.out.println(inputLine);
      
      csvWriter.write(getValue(inputLine, "title"));
      csvWriter.write(",");
      csvWriter.write(""+i);
      csvWriter.write(",");
      csvWriter.write(getValue(inputLine, "pricePerServing"));
      csvWriter.write(",");
      csvWriter.write(getValue(inputLine, "cheap"));
      csvWriter.write(",");
      csvWriter.write(getValue(inputLine, "healthScore"));
      csvWriter.write(",");
      csvWriter.write(getValue(inputLine, "aggregateLikes"));
      csvWriter.write(",");
      csvWriter.write(getValue(inputLine, "readyInMinutes"));
      csvWriter.write(",");
      csvWriter.write(getValue(inputLine, "vegetarian"));
      csvWriter.write(",");
      csvWriter.write(getValue(inputLine, "vegan"));
      csvWriter.write(",");
      csvWriter.write(getValue(inputLine, "glutenFree"));
      csvWriter.write(",");
      csvWriter.write(getValue(inputLine, "ketogenic"));
      csvWriter.write(",");
      csvWriter.write(getValue(inputLine, "dishTypes"));
      csvWriter.write("\n");
      
      
      url = new URL("https://api.spoonacular.com/recipes/" + ids[i] + "/information?apiKey=720fde9020ea45788f837f177ff1f509&includeNutrition=false");
      
      con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");

      in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      i++;
     
    }
    csvWriter.close();
    in.close();
    
  }
  
  public static String getValue (String inputLine, String query) {
	  if (inputLine.indexOf(query) != -1){
		  int startingVal = inputLine.indexOf(query);
		  int finalVal = startingVal + query.length() + 2;
		  
		  String finalString = "";
		  

		  if (inputLine.charAt(finalVal) == '\"') {
			  int i = finalVal +1;
			  while (inputLine.charAt(i) != '\"') {
				  finalString += inputLine.substring(i, i+1);
				  i++;
			  }  
		  }else if (inputLine.charAt(finalVal) == '[') {
			  int i = finalVal+1;
			  while (inputLine.charAt(i) != ']') {
				  finalString += inputLine.substring(i, i+1);
				  i++;
			  }  
		  }
		  else {
			  int i = finalVal;
			  while (inputLine.charAt(i) != ',') {
				  finalString += inputLine.substring(i, i+1);
				  i++;
			  }  
		  }
		  
	
		  return finalString;
		  
		  
	  }else {
		  return "NOT HERE";
	  }  
  }
}