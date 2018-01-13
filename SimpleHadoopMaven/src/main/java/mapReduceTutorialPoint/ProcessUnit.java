package mapReduceTutorialPoint;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.RunningJob;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.util.Progressable;

public class ProcessUnit {

	//mapper class
	public static class E_EMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {

		//map function
		public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter)
				throws IOException {
			String line = value.toString();
			String lasttoken = null;
			StringTokenizer s = new StringTokenizer(line, ",");
			String year = s.nextToken();
			while (s.hasMoreTokens()) {
				lasttoken = s.nextToken();
			}
			int avgprice = Integer.parseInt(lasttoken);
			output.collect(new Text(year), new IntWritable(avgprice));

		}

	}
	
	//reducer class
	public static class E_EReduce extends MapReduceBase implements
	Reducer<Text, IntWritable, Text, IntWritable>{

		public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output,
				Reporter reporter) throws IOException {
			int maxavg=30;
			int val=Integer.MIN_VALUE;
			
			while(values.hasNext()) {
				if((val=values.next().get())>maxavg) {
					output.collect(key, new IntWritable(val));
				}
			}
			
		}
		
		public static void main(String[] args) throws Exception {
			JobConf conf = new JobConf(ProcessUnit.class);
			conf.set("fs.defaultFS", "hdfs://localhost:9000");
			conf.setUser("waheguru");
			conf.setJobName("max_electricityunit");
			conf.setOutputKeyClass(Text.class);
			conf.setOutputValueClass(IntWritable.class);
			conf.setMapperClass(E_EMapper.class);
			conf.setCombinerClass(E_EReduce.class);
			conf.setInputFormat(TextInputFormat.class);
			conf.setOutputFormat(TextOutputFormat.class);
			
			FileInputFormat.setInputPaths(conf, new Path("input_dir/sample.txt"));
			FileOutputFormat.setOutputPath(conf, new Path("output_dir2/sample.txt"));
			
			
			new Progressable() {

				public void progress() {
					System.out.println("----------");

				}
			};
			System.out.println("----------");
			RunningJob rj = myCustomRunJob(conf);
			System.out.println(rj.getFailureInfo());
			System.out.println(rj.getJobID());
			System.out.println(rj.getJobState());
			System.out.println(rj.isComplete());
			System.out.println(rj.isRetired());
			System.out.println("----------");
			System.out.println("Job runned yo pajji..");
			
			
			
			
			
		}
		
		public static RunningJob myCustomRunJob(JobConf job) throws Exception {
		    JobClient jc = new JobClient(job);
		    RunningJob rj = jc.submitJob(job);
		    
		    if (!jc.monitorAndPrintJob(job, rj)) {
		    	rj.waitForCompletion();
		      throw new IOException("Job failed with info: " + rj.getFailureInfo());
		    }
		    return rj;
		  }
		
	}

}
