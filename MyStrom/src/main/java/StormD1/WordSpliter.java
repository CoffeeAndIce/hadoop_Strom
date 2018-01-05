package StormD1;

import org.apache.storm.shade.org.apache.commons.lang.StringUtils;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class WordSpliter extends BaseBasicBolt {

	private static final long serialVersionUID = -5653803832498574866L;

	public void execute(Tuple input, BasicOutputCollector collector) {
		String line = input.getString(0);
		String[] words = line.split(" ");
		for (String word : words) {
			word = word.trim();
			if (StringUtils.isNotBlank(word)) {
				word = word.toLowerCase();
				collector.emit(new Values(word));
			}
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));

	}

}