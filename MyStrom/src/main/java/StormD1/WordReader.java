package StormD1;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.storm.shade.org.apache.commons.io.FileUtils;
import org.apache.storm.shade.org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class WordReader extends BaseRichSpout {
	private static final ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<String, Long>();
	private static final long serialVersionUID = 2197521792014017918L;
	private String inputPath;
	
	private SpoutOutputCollector collector;

	@SuppressWarnings("rawtypes")
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
		inputPath = (String) conf.get("INPUT_PATH");
	}

	public void nextTuple() {
		File[] listFiles = new File(inputPath).listFiles();
		for (File f : listFiles) {
			try {
				if(f.canRead() && f.isFile()){//判断是否可读文件
					String absolutePath = f.getAbsolutePath();
					if(!map.containsKey(absolutePath)){
						map.put(absolutePath, f.lastModified());
					}
					else if(map.get(absolutePath)==f.lastModified()){
						continue;
					}
					//系统用GBK
					List<String> lines = FileUtils.readLines(f, "gbk");
					for (String line : lines) {
						collector.emit(new Values(line));
					}
					map.put(absolutePath, f.lastModified());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("line"));
	}
}
