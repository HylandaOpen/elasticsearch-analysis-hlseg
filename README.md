hlseg Analysis for Elasticsearch
=============================

海量分词是天津海量信息技术股份有限公司自主研发的中文分词核心，已于2018年7月将分词5.0版免费开放给大家，欢迎试用。

海量分词演示界面 http://www.hylanda.com/smartCenter2018/index

海量分词核心和词典包不定期进行更新，请在http://www.hylanda.com/smartCenter2018/download 进行下载，并将对应的jar和词典包分别拷贝至elasticsearch-hlseg和elasticsearch-hlseg/config目录下

另外，海量提供免费API接口，文档详见`http://bigdata.hylanda.com/smartCenter2018/doc`，欢迎大家试用，如有疑问，请联系`nlp@hylanda.com`

Analyzer: `hlseg_search`, Tokenizer: `hlseg_search`

Versions
--------

hlseg version | ES version | Release Link |
-----------|-----------|-----------
6.4.2| 6.4.2| Download: [v6.4.2](https://github.com/HylandaOpen/elasticsearch-analysis-hlseg/releases/tag/v6.4.2)
6.4.0| 6.4.0| Download: [v6.4.0](https://github.com/HylandaOpen/elasticsearch-analysis-hlseg/releases/tag/v6.4.0)
6.3.2| 6.3.2| Download: [v6.3.2](https://github.com/HylandaOpen/elasticsearch-analysis-hlseg/releases/tag/v6.3.2)
6.2.4| 6.2.4| Download: [v6.2.4](https://github.com/HylandaOpen/elasticsearch-analysis-hlseg/releases/tag/v6.2.4)
6.2.0| 6.2.0| Download: [v6.2.0](https://github.com/HylandaOpen/elasticsearch-analysis-hlseg/releases/tag/v6.2.0)
6.1.0| 6.1.0| Download: [v6.1.0](https://github.com/HylandaOpen/elasticsearch-analysis-hlseg/releases/tag/v6.1.0)
6.0.1| 6.0.1| Download: [v6.0.1](https://github.com/HylandaOpen/elasticsearch-analysis-hlseg/releases/tag/v6.0.1)
6.0.0| 6.0.0| Download: [v6.0.0](https://github.com/HylandaOpen/elasticsearch-analysis-hlseg/releases/tag/v6.0.0)
5.6.12| 5.6.12| Download: [v5.6.12](https://github.com/HylandaOpen/elasticsearch-analysis-hlseg/releases/tag/v5.6.12)
5.6.9| 5.6.9| Download: [v5.6.9](https://github.com/HylandaOpen/elasticsearch-analysis-hlseg/releases/tag/v5.6.9)
5.5.3| 5.5.3| Download: [v5.5.3](https://github.com/HylandaOpen/elasticsearch-analysis-hlseg/releases/tag/v5.5.3)
5.5.1| 5.5.1| Download: [v5.5.1](https://github.com/HylandaOpen/elasticsearch-analysis-hlseg/releases/tag/v5.5.1)

Install
-------

1.download or compile

download pre-build package from here: https://github.com/HylandaOpen/elasticsearch-analysis-hlseg/releases
    
    unzip plugin to folder `your-es-root/plugins/`

2.download hlseg dict

download CoreDict.zip in the dictionary directory, Unzip the CoreDict.zip and copy it to the config directory

3.restart elasticsearch



#### Quick Example

1.create a index

```bash
PUT hlseg_search_index
{
  "settings": {
    "analysis": {
      "analyzer": {
        "default": {
          "type": "hlseg_search"
        }
      }
    }
  }
}
```

2.create a mapping

```bash
PUT /hlseg_search_index/data/_mapping
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
GET /hlseg_search_index/_search
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

`海量分词分为基础词词典CoreDict.dat和自定义词典userDict_utf8.txt。基础词词典在dictionary目录下，需要将CoreDict.zip解压后放在config目录下，可以通过修改config下的userDict_utf8.txt来更新自定义词典`

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

海量分词支持以下模式分词。

`hlseg_search`: 适用于检索类的情境



### Segment Parma
分词提供了以下几个参数，供用户选择配置

1.`outputStopWord`：`true/false`。是否输出停用词，默认不输出`false`

使用demo，例如设置输出停用词

```bash

PUT /test_index
{
  "settings": {
    "analysis": {
      "analyzer": {
        "default": {
          "type": "hlseg_search",
          "outputStopWord": true
        }
      }
    }
  }
}

```

### 自主打包插件

如release中的版本不支持您使用的es版本，请下载源码自主打包。下载源码至本地，此源码问Maven工程，操作方法如下：

1.修改pom.xml中的elasticsearch.version为您目前使用的elasticsearch版本

```xml

<groupId>org.elasticsearch</groupId>

<artifactId>elasticsearch-analysis-hlseg</artifactId>
<version>5.5.1</version>   <!--此处修改成对应版本号-->
<packaging>jar</packaging>

<name>elasticsearch-analysis-hlseg</name>
<url>http://maven.apache.org</url>

<properties>
	<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	<elasticsearch.version>5.5.1</elasticsearch.version>  <!--此处修改您目前的es的版本号，目前只支持5.5.1以上-->
	<maven.compiler.target>1.8</maven.compiler.target>
	<elasticsearch.assembly.descriptor>${project.basedir}/src/main/assemblies/plugin.xml</elasticsearch.assembly.descriptor>
	<elasticsearch.plugin.name>analysis-hlseg</elasticsearch.plugin.name>
	<elasticsearch.plugin.classname>org.elasticsearch.plugin.analysis.AnalysiaHLSegPlugin</elasticsearch.plugin.classname>
	<elasticsearch.plugin.jvm>true</elasticsearch.plugin.jvm>
	<tests.rest.load_packaged>false</tests.rest.load_packaged>
	<skip.unit.tests>true</skip.unit.tests>
</properties>

```

2.修改src/main/resources/plugin-descriptor.properties中的es版本号

```bash

version=5.5.1#修改插件版本

elasticsearch.version=5.5.1#修改修改您目前的es的版本号

```

3.打包

```bash
执行maven install后，即可在target/releases下找到对应的插件包

```


### 使用协议

hlseg es中文分词插件依赖海量分词核心包segment-5.2.16-SNAPSHOT.jar，请使用时遵循海量分词使用协议，文件见`dicitonary/天津海量信息技术股份有限责任公司分词（免费版）使用协议.docx`


### 自主打包插件

版本更新记录


2018/11/22 更新，1.删除之前的三个分词模式，只保留hlseg_search模式。2解决hlseg_search模式下，合并中文人名


### 常见问题

1.如果安装了分词插件后，启动es报如下错误，请将elasticsearch-hlseg/plugin-descriptor.properties中的jvm, site, isolated三个配置注释掉
java.lang.IllegalArgumentException: Unknown properties in plugin descriptor: [jvm, site, isolated]
