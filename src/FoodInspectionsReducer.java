import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;


public class FoodInspectionsReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text>{
	static int i=0;
	@Override
	public void reduce(Text key, Iterator<Text> values,
			OutputCollector<Text, Text> output, Reporter r) throws IOException {
		double no_of_inspections=0;
		double no_of_fails=0;
		double fail_ratio;
		double no_of_comments=0;
		String location="";
		while(values.hasNext()){
			// val contains results, comments, location
			String val = values.next().toString();
			no_of_inspections++;
			String[] str = val.split("\\t");
			
			//str[0]-> result, str[1] comment, str[2]-> location
			if(str[0].equalsIgnoreCase("fail")){
				no_of_fails++;
				String[] voilations = str[1].split("\\|");
				no_of_comments=no_of_comments+voilations.length;
			}
			 location = str[2].replace("\"", "").replace("(", "").replace(")", "").replace(" ", "");
		}
		fail_ratio=no_of_fails/no_of_inspections;
		double score=0;
		// to sort the conflicts between the records with 0 ratio
		// solution: more #inspections, less rank
		// multiplying number of inspections by a constant because of no of maximum #inspections is 10000
		if(fail_ratio==0){
			
			score = fail_ratio+(no_of_inspections*0.001);
			
		}
		else if (fail_ratio>0){
			
			score = fail_ratio+no_of_comments;
		}
		
		String val= Double.toString(score);
		output.collect(new Text(key+"|"+location), new Text(val));
		
		
	}
	
}
