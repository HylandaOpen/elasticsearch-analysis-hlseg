hlseg Analysis for Elasticsearch
=============================

Analyzer: `hlseg_search` , `hlseg_large` , `hlseg_normal`, Tokenizer: `hlseg_search` , `hlseg_large` , `hlseg_normal`

Versions
--------

hlseg version | ES version | Release Link |
-----------|-----------|-----------
5.6.9| 5.6.9| Download: [v5.6.9](https://github.com/hylandahj/elasticsearch-analysis-hlseg/releases/tag/v5.6.9)
5.5.1| 5.5.1| Download: [v5.5.1](https://github.com/hylandahj/elasticsearch-analysis-hlseg/releases/tag/v5.5.1)

Install
-------

1.download or compile

* optional 1 - download pre-build package from here: https://github.com/hylandahj/elasticsearch-analysis-hlseg/releases
    
    unzip plugin to folder `your-es-root/plugins/`

* optional 2 - use elasticsearch-plugin to install ( version > v5.5.1 ):

    `./bin/elasticsearch-plugin install https://github.com/hylandahj/elasticsearch-analysis-hlseg/files/2533709/elasticsearch-analysis-hlseg-5.6.9.zip`

2.restart elasticsearch



#### Quick Example

1.create a index

```bash
PUT hlseg_large_index
{
  "settings": {
    "analysis": {
      "analyzer": {
        "default": {
          "type": "hlseg_large"
        }
      }
    }
  }
}
```

2.create a mapping

```bash
PUT /hlseg_large_index/data/_mapping
{
  "data": {
    "properties": {
       "title": {
         "type": "text",
         "index": true
      },
       "content": {
         "type": "text",
         "index": true
      }
    }
  }
}
```


3.query with highlighting

```bash
GET /hlseg_large_index/_search
{
  "query" : { "match" : { "format_content" : "破产" }
  },
  "highlight": {
        "fields" : {
            "content" : {}
        }
    }
}
'
```

### Dictionary Configuration

`IKAnalyzer.cfg.xml` can be located at `{conf}/analysis-ik/config/IKAnalyzer.cfg.xml`
or `{plugins}/elasticsearch-analysis-ik-*/config/IKAnalyzer.cfg.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
<properties>
	<comment>IK Analyzer 扩展配置</comment>
	<!--用户可以在这里配置自己的扩展字典 -->
	<entry key="ext_dict">custom/mydict.dic;custom/single_word_low_freq.dic</entry>
	 <!--用户可以在这里配置自己的扩展停止词字典-->
	<entry key="ext_stopwords">custom/ext_stopword.dic</entry>
 	<!--用户可以在这里配置远程扩展字典 -->
	<entry key="remote_ext_dict">location</entry>
 	<!--用户可以在这里配置远程扩展停止词字典-->
	<entry key="remote_ext_stopwords">http://xxx.com/xxx.dic</entry>
</properties>
```

### 热更新 IK 分词使用方法




Thanks
------
YourKit supports IK Analysis for ElasticSearch project with its full-featured Java Profiler.
YourKit, LLC is the creator of innovative and intelligent tools for profiling
Java and .NET applications. Take a look at YourKit's leading software products:
<a href="http://www.yourkit.com/java/profiler/index.jsp">YourKit Java Profiler</a> and
<a href="http://www.yourkit.com/.net/profiler/index.jsp">YourKit .NET Profiler</a>.
