package com.hylanda.analyzer.lucene;

import java.io.IOException;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hylanda.analyzer.core.HLSegmenterAdapter;
import com.hylanda.segmentor.common.SegGrain;
import com.hylanda.segmentor.common.SegOption;
import com.hylanda.segmentor.common.Token;
import com.hylanda.segmentor.Segmentor;

public class HLSegTokenizer  extends Tokenizer {
	
	private HLSegmenterAdapter segmenterAdapter;
	//词元文本属性
	private final CharTermAttribute termAtt;
	//词元位移属性
	private final OffsetAttribute offsetAtt;
	//词元分类属性
	private final TypeAttribute typeAtt;
	//记录最后一个词元的结束位置
	private int endPosition;
	
	public HLSegTokenizer(SegOption option){
	    super();
	    offsetAtt = addAttribute(OffsetAttribute.class);
	    termAtt = addAttribute(CharTermAttribute.class);
	    typeAtt = addAttribute(TypeAttribute.class);

	    segmenterAdapter = new HLSegmenterAdapter(option);
	}


	@Override
	public boolean incrementToken() throws IOException {

	    clearAttributes();
	    if (segmenterAdapter.hasNext()) {
	      Token token = segmenterAdapter.next();
	       
	      termAtt.append(token.wordStr);
	      termAtt.setLength(token.len);
	      offsetAtt.setOffset(token.offset, token.offset + token.len);
	      	      	      
	      endPosition = token.offset + token.len;
	      typeAtt.setType(Segmentor.getNatureString(token.nature));
	      return true;
	    }
	    return false;
	}
	

	@Override
	public void reset() throws IOException {
	    super.reset();
	    segmenterAdapter.reset(this.input);
	}	
	
	@Override
	public final void end() throws IOException {
	    int finalOffset = correctOffset(this.endPosition);
	    offsetAtt.setOffset(finalOffset, finalOffset);
	}
}
