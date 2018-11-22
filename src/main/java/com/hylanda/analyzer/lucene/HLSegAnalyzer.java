package com.hylanda.analyzer.lucene;

import org.apache.lucene.analysis.Analyzer;

import com.hylanda.segmentor.common.SegOption;
import com.hylanda.utils.SegTools;

public class HLSegAnalyzer extends Analyzer {

	private SegOption option = null;
	
	public HLSegAnalyzer() {
		option = SegTools.getOption(null);
	}

	public HLSegAnalyzer(SegOption option) {
		this.option = option;
	}

	public HLSegAnalyzer(ReuseStrategy reuseStrategy) {
		super(reuseStrategy);
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		return new TokenStreamComponents(new HLSegTokenizer(this.option));
	}
}
