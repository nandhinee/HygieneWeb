import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


	public class FoodInspections extends Configured implements Tool {
		
		public Set<String> getKeysByValue(Map<String, Double> map, double value) {
		    Set<String> keys = new HashSet<String>();
		    for (Map.Entry<String, Double> entry : map.entrySet()) {
		        if (value == entry.getValue()) {
		            keys.add(entry.getKey());
		        }
		    }
		    return keys;
		}
		
		public static void main(String[] args) throws Exception {
			int res = ToolRunner.run(new Configuration(), new FoodInspections(), args);
		}


	public int run(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
			JobClient client = new JobClient();
			JobConf conf = new JobConf(getConf(), FoodInspections.class);
			// set the job name
			conf.setJobName("FoodInspections");
			// set the output data types for map-reduce job
			conf.setOutputKeyClass(Text.class);
			conf.setOutputValueClass(Text.class);
			conf.setJarByClass(FoodInspections.class);
			// set the mapper class for the job conf
			conf.setMapperClass(FoodInspectionsMapper.class);
			// set the reducer class for the job conf
			conf.setReducerClass(FoodInspectionsReducer.class);
			String input, output;
			input = args[0];
			output = args[1];
			FileInputFormat.setInputPaths(conf, new Path(input));
			FileOutputFormat.setOutputPath(conf, new Path(output));
			client.setConf(conf);
			JobClient.runJob(conf);
			
			Path pt=new Path("output/part-00000");
	        FileSystem fs = FileSystem.get(new Configuration());
	        BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(pt)));
			String line;
			int s=0;
			while((line=br.readLine())!=null){
				s++;
				String[] str = line.split("\\t");	
			}
			br.close();
			
			String[] key = new String[s];
			double[] score = new double[s];
			// to sort the restaurants based on failed score in ascending order
			// read from the output file and store it in a hashmap
			Path pt1=new Path("output/part-00000");
	        FileSystem fs1 = FileSystem.get(new Configuration());
	        BufferedReader br2 = new BufferedReader(new InputStreamReader(fs1.open(pt)));
			String line2;
			int index=0;
			while((line2=br2.readLine())!=null){
				String[] str = line2.split("\\t");
				key[index]=str[0];
				score[index]=Double.parseDouble(str[1]);
				index++;			
			}
			br.close();
			HashMap<String, Double> map = new HashMap<String, Double>();
			for(int i=0; i<s; i++){
				map.put(key[i], score[i]);
			}			
			// sort the hashmap based on the failed score 
	        LinkedHashSet<Double> values = new LinkedHashSet<Double>();
	        for (Map.Entry<String, Double> entry : map.entrySet()) {
	            values.add(entry.getValue());
	        }
	        
	        ArrayList<Double> valueF = new ArrayList<Double>();
	        for(double temp: values){
	        	valueF.add(temp);
	        }
	        Collections.sort(valueF);
	        
	        Path pt3=new Path("final.txt");
			FileSystem fs3 = FileSystem.get(new Configuration());
			FSDataOutputStream fsOutStream = fs3.create(pt3, true);
			BufferedWriter br3 = new BufferedWriter( new OutputStreamWriter(fsOutStream, "UTF-8" ) );
            

	        int count=0;
	        for(double temp: valueF){
	        	Set<String> tempKeys = getKeysByValue(map, temp);
	        	if(!tempKeys.isEmpty()){
	        		for(String temp1: tempKeys){
	        		if(count == 10) {
	        			br3.close();
	        			System.exit(0);
	        		}
	        		br3.write(temp1+"\t"+temp+"\n");
	        		count++;
	        		}
	        	}
	        	
	        }
	        
			return 0;
		}	


}
