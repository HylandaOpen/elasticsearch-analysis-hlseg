package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;

import com.hylanda.analyzer.lucene.HLSegTokenizer;
import com.hylanda.segmentor.BasicSegmentor;
import com.hylanda.segmentor.common.SegGrain;
import com.hylanda.segmentor.common.SegOption;
import com.hylanda.utils.SegTools;

public class HLSegTokenizerFactory extends AbstractTokenizerFactory {

	private SegOption option;
	
	public SegOption getOption() {
		return option;
	}

	public void setOption(SegOption option) {
		this.option = option;
	}

	public HLSegTokenizerFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) {
		super(indexSettings, name, settings);
		SegTools.loaddict(env);
	}

	public static HLSegTokenizerFactory getHLSegSearchTokenizerFactory(IndexSettings indexSettings, Environment env,
			String name, Settings settings) {
		HLSegTokenizerFactory tokenizerFactory = new HLSegTokenizerFactory(indexSettings, env, name, settings);
		tokenizerFactory.setOption(SegTools.getOption(settings));
		return tokenizerFactory;
	}
	
	@Override
	public Tokenizer create() {
		return new HLSegTokenizer(option);
	}
}
