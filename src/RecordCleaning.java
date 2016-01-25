import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class RecordCleaning {
	
	/*
	 * Input: String
	 * Output: TRUE if the string is a number (collection of digits)
	 * 		   False if the string is not a number
	 */
	public boolean isNumeric(String str)
	{
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}

	public static void main(String[] args) throws IOException {
		RecordCleaning obj = new RecordCleaning();
		StringBuilder output = new StringBuilder();
		try{
			/*
			 * Read input file to clean the data
			 */
	    	FileReader inputFile = new FileReader("input/input.txt");
	        BufferedReader bufferReader = new BufferedReader(inputFile);
	        String line;
	        
	        int lineCount = 0;
	        
	        
	        while ((line = bufferReader.readLine()) != null)   {
	        	lineCount++;
	        	
	        	/*
	        	 * Ignore headers
	        	 */
	        	if (lineCount == 1)
	        		continue;
	        	
	        	/*
	        	 * Skip empty lines
	        	 */
	        	if (!(line.isEmpty())){
	        		
		        	String []brk = line.split("\\t");
		        	/*
		        	 * Return true if the first word of the line is a number
		        	 * Else false
		        	 */
		        	boolean result = obj.isNumeric(brk[0]);
		        	
		        	if (result){
		        		output.append("\n"+line);
		        	}
		        	else {
		        		output.append(line);
		        	}
	        	}
	        }
	        bufferReader.close();
	        
	    }
	    catch(Exception e){
	        System.out.println("Error while reading file line by line:" + e.getMessage());                      
	    }
		
		/*
		 * Write the clean records to a output file
		 */
		BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));
		out.write(output.toString());
		out.close();  
	}

}
