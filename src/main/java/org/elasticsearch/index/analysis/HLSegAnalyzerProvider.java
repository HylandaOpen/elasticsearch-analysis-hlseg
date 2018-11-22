package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;

import com.hylanda.analyzer.lucene.HLSegAnalyzer;
import com.hylanda.segmentor.common.SegOption;
import com.hylanda.utils.SegTools;

public class HLSegAnalyzerProvider extends AbstractIndexAnalyzerProvider<HLSegAnalyzer> {
	private HLSegAnalyzer hlSegAnalyzer;
	
	public HLSegAnalyzerProvider(IndexSettings indexSettings, Environment env, String name, Settings settings) {
		super(indexSettings, name, settings);
		
		SegOption option = SegTools.getOption(settings);
		hlSegAnalyzer = new HLSegAnalyzer(option);

		SegTools.loaddict(env);
	}
	
	@Override
	public HLSegAnalyzer get() {
		return hlSegAnalyzer;
	}
	

	public static AnalyzerProvider<? extends Analyzer> getHLSegSearchAnalyzerProvider(IndexSettings indexSettings,
			Environment env, String s, Settings settings) {
		HLSegAnalyzerProvider hlSegAnalyzerProvider = new HLSegAnalyzerProvider(indexSettings, env, s, settings);

		return hlSegAnalyzerProvider;
	}
}
