import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;


public class FoodInspectionsMapper extends MapReduceBase implements
Mapper<LongWritable, Text, Text, Text> { 
	public static int i=0;
	public void map(WritableComparable key, Writable values,
			OutputCollector output, Reporter reporter) throws IOException {
	}
	@Override
	public void map(LongWritable key, Text value,
			OutputCollector<Text, Text> output, Reporter r) throws IOException {
		
		InspectionStruct ins = new InspectionStruct(value.toString());
		String curr_pincode =null;
		
		Path pt=new Path("zip/temp.txt");
        FileSystem fs = FileSystem.get(new Configuration());
        BufferedReader br2 = new BufferedReader(new InputStreamReader(fs.open(pt)));
		String line2;
		while((line2=br2.readLine())!=null){
			curr_pincode = line2;		
		}
		br2.close();
		// for filtering records of type restaurant
		String facility_type = "Restaurant";
		
		if(curr_pincode.equals(ins.zipcode)&&facility_type.equalsIgnoreCase(ins.type)){
			String val="";
			val = ins.result+"\t"+ins.comments+"\t"+ins.location;
			//{key, value} = {name,address ; result,comments,location}
			output.collect(new Text(ins.name+","+ins.address), new Text(val));
		}
		
		
	}

}
