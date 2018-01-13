package SimplePackage;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

public class HDFScopy {

	public static void main(String[] args) throws URISyntaxException, IOException {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://localhost:9000");
		InputStream inputStream = new BufferedInputStream(
				new FileInputStream("/home/waheguru/DevelopmentPrograms/HdFs Commands/sample.txt"));
		FileSystem fileSystem = FileSystem.get(conf);

		OutputStream outputStream = fileSystem
				.create(new Path("hdfs://localhost:9000/firstDir/sample.txt"));

		new Progressable() {

			public void progress() {
				System.out.println("----------");

			}
		};

		try {
			System.out.println("----------");
			IOUtils.copyBytes(inputStream, outputStream, 4096, false);
		} finally {
			IOUtils.closeStream(outputStream);
		}

	}

}
