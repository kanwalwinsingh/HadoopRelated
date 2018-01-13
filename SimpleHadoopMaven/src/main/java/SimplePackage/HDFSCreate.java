package SimplePackage;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HDFSCreate {

	public static void main(String[] args) throws IOException {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://localhost:9000");
		FileSystem fileSystem = FileSystem.get(conf);
		boolean flag = fileSystem.mkdirs(new Path("hdfs://localhost:9000/user/waheguru/firstDirectory/ImportantDocs/myFileDir/testDiectory"));
	if(flag) {
		System.out.println("Success");
	}
	else {
		System.out.println("Abe yarr... :-(");
	}
	}

}
