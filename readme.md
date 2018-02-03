## OmegaNews
使用[https://github.com/MoriatyC/nethard](https://github.com/MoriatyC/nethard)所爬取的数据进行展示和进一步的使用。整个页面几乎实现无刷，所有的导航栏和新闻都是通过ajax部分刷新实现，所有主题的文章列表均使用`category.html`进行渲染，所有文章通过`article.html`进行渲染。

使用redis对热门文章进行缓存,使用排行榜进行展示，具体步骤如下：

1. 爬取新闻，建立新的hash(news:id),设置过期时间为一周，并加入zset(time：)
2. 每周执行一次更新，删除zset(time：)中过期的任务，对未过期的任务分数进行更新。

## 兼容性
基础依赖:

* JAVA8
*  Maven3.3.9
*  spring-boot 1.5.8
*  mysql
*  redis3.0