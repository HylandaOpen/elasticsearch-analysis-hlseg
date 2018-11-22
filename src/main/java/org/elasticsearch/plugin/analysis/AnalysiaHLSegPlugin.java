package org.elasticsearch.plugin.analysis;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.index.analysis.AnalyzerProvider;
import org.elasticsearch.index.analysis.HLSegAnalyzerProvider;
import org.elasticsearch.index.analysis.HLSegTokenizerFactory;
import org.elasticsearch.index.analysis.TokenizerFactory;
import org.elasticsearch.indices.analysis.AnalysisModule;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;

public class AnalysiaHLSegPlugin extends Plugin implements AnalysisPlugin {

	public static String PLUGIN_NAME = "analysis-hlseg";

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> getTokenizers() {
        Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> extra = new HashMap<>();

        extra.put("hlseg_search", HLSegTokenizerFactory::getHLSegSearchTokenizerFactory);
       
        return extra;
    }

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
        Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> extra = new HashMap<>();

        extra.put("hlseg_search", HLSegAnalyzerProvider::getHLSegSearchAnalyzerProvider);
        
        return extra;
    }

}
