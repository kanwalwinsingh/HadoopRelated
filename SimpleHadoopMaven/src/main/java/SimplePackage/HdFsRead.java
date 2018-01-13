package SimplePackage;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class HdFsRead {

	public static void main(String[] args) throws IOException {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://localhost:9000");
		FileSystem fileSystem = FileSystem.get(conf);
		Path path = new Path("hdfs://localhost:9000/user/waheguru/firstDirectory/ImportantDocs/myFileDir/testWccount.txt");
		InputStream inputStream = fileSystem.open(path);
		IOUtils.copyBytes(inputStream, System.out, 4096,false);
		IOUtils.closeStream(inputStream);
	}

}
