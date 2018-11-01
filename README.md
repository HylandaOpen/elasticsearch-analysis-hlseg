hlseg Analysis for Elasticsearch
=============================

海量分词是天津海量信息技术股份有限公司自主研发的中文分词核心，已于2018年7月将分词5.0版免费开放给大家，欢迎试用。

海量分词演示界面 `http://bigdata.hylanda.com/smartCenter2018/index`

另外，海量提供免费API接口，文档详见`http://bigdata.hylanda.com/smartCenter2018/doc`，欢迎大家试用，如有疑问，请联系`nlp@hylanda.com`

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
  "query" : { "match" : { "content" : "破产" }
  },
  "highlight": {
        "fields" : {
            "content" : {}
        }
    }
}
```

### Dictionary Configuration

`海量分词分为基础词词典CoreDict.dat和自定义词典userDict_utf8.txt。基础词词典在dictionary目录下，需要将CoreDict.rar解压后放在config目录下，可以通过修改config下的userDict_utf8.txt来更新自定义词典`

自定义词典格式如下


```bash
1.用户自定义词典采用文本格式，utf-8编码，每行一个词

2.每个词包含三列属性，分别是词串、词的属性以及idf值的加权等级，并以Tab作为分隔，其中除了词串必填外，其他列可以不填，不填写则系统采用默认值

3.“#”表示注释，会在加载时被忽略

4.词的属性以西文逗号分隔，可以是词性、停止词标志或者自定义属性

5.词性标记参考北大标准，用于词性标注时参考，该项不填则默认为名词

6.停止词标志为：stopword，由SegOption.outputStopWord来控制是否输出停止词

7.自定义属性不参与分词过程，分词结果中若Token.userTag不为空，则可以获取到该词的自定义属性。

8.idf值的加权分5级，从低到高的定义是idf-lv1 — idf-lv5，等级越高则该词在关键词计算时的权重会越大，若不填写该值则系统默认是idf-lv3(中等权重）
```

### Segment Mode

海量分词支持三种不同模式的分词。

1.`hlseg_search`: 小颗粒度，适用于检索类的情境

2.`hlseg_normal`：普通颗粒度，适用于多数情境

3.`hlseg_large`：大颗粒度，适用于面向领域的分词情境（如果需要面向领域的分词优化，如专业词汇发现与整理，可以联系海量客服）


### Segment Parma
分词提供了以下几个参数，供用户选择配置

1.`mergeChineseName`：`true/false`。是否合并中文名称，默认合并`true`

2.`mergeOrgInNormalGrainMode`：`true/false`。当选择`hlseg_normal`模式时，是否合并机构名，默认不合并`false`

3.`mergeNumeralAndQuantity`：`true/false`。是否合并数量词，默认合并`true`

4.`outputStopWord`：`true/false`。是否输出停用词，默认不输出`false`

使用demo，例如设置输出停用词

```bash

PUT /test_index
{
  "settings": {
    "analysis": {
      "analyzer": {
        "default": {
          "type": "hlseg_large",
          "outputStopWord": true
        }
      }
    }
  }
}

```




