package StormD1;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

public class WordCountTopo {

	/**
	 * Storm word count demo
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		args = new String [] {"E:/lg_file/leaning/count","10"};
		if (args.length != 2) {
			System.err.println("Usage: inputPath timeOffset");
			System.err.println("such as : java -jar  WordCount.jar D://input/ 2");
			System.exit(2);
		}
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("word-reader", new WordReader());
		builder.setBolt("word-spilter", new WordSpliter()).shuffleGrouping("word-reader");
		builder.setBolt("word-counter", new WordCounter()).shuffleGrouping("word-spilter");
		String inputPaht = args[0];
		String timeOffset = args[1];
		Config conf = new Config();
		conf.put("INPUT_PATH", inputPaht);
		conf.put("TIME_OFFSET", timeOffset);
		conf.setDebug(false);
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("WordCount", conf, builder.createTopology());

	}

}