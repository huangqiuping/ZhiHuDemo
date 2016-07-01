package com.learn.terry.zhihudemo.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dvb-sky on 2016/7/1.
 */
public class NewsDetail {

    /**
     * body : <div class="main-wrap content-wrap">
     <div class="headline">

     <div class="img-place-holder"></div>



     </div>

     <div class="content-inner">




     <div class="question">
     <h2 class="question-title">你有哪些内行人才知道的省钱诀窍？</h2>

     <div class="answer">

     <div class="meta">
     <img class="avatar" src="http://pic3.zhimg.com/9d1e0cd48aa3e21129e06c4b5558cb0e_is.jpg">
     <span class="author">陈子杨，</span><span class="bio">致力于医疗o2o/甲乳外科</span>
     </div>

     <div class="content">
     <p>家里如果有人有高血压等慢性疾病，需要终身服药，国家是有补贴的。可以说如果是基本国产药那就是免费吃。</p>
     <p>病种范围</p>
     <p><img class="content-image" src="http://pic2.zhimg.com/70/3aea3cc514bab02b3f6f4ba3cb51c301_b.jpg" alt="" /></p>
     <p>方法</p>
     <p><img class="content-image" src="http://pic2.zhimg.com/70/36701860cbb6ecfb6455ea474a87df31_b.jpg" alt="" /></p>
     <p>（知乎日报注：各地区具体能报销范围可能有差异，请以当地人力资源和社会保障局网站上给出的信息为准）</p>
     <p>不要总是抱怨医院看病难看病贵。国家一直有这方面补贴。</p>
     <p>手续确实复杂，但也起到一个筛选作用。</p>
     <p>你一没钱二不想跑怪医生怪国家咯(&acute;･_･`)</p>
     <p>一天来收到了很多朋友的询问与质疑，我大概说两点。</p>
     <p>一，慢性病报销是曾经有一个病人来找我开证明时得知的。据说还是能省下不少钱。毕竟几千块是这里山区半年的收入。但具体怎么开，在哪开。各个地方都不大相同。恕我不能为各位解答。主要工作还是在看病这一方面，社保的方面可以@周粥这位朋友。</p>
     <p>二，家父高血压三年了，一直在口服药物治疗。我曾陪他去办慢病手续。很快就放弃了。手续繁琐，部门众多，审核时间长。后来决定这点钱还是自己掏。太 TM 难办。侧面反应了慢性病报销繁杂的手续也起到了一个筛选的作用。</p>
     <p><img class="content-image" src="http://pic2.zhimg.com/70/1795d659b2a2c13650297080d7ece271_b.jpg" alt="" /></p>
     <p>get 请赞</p>
     </div>
     </div>


     <div class="view-more
     "><a href="http://www.zhihu.com/question/41854964">查看知乎讨论<span class="js-question-holder"></span></a></div>

     </div>


     </div>
     </div>
     * image_source : Jamie / CC BY
     * title : 省钱诀窍：其实一些慢性病的费用，是可以报销的
     * image : http://pic4.zhimg.com/db54a16aedbb0c4c1beac9c60cde93d7.jpg
     * share_url : http://daily.zhihu.com/story/8509177
     * js : []
     * ga_prefix : 070108
     * images : ["http://pic1.zhimg.com/eded9a837734ef31fddac44c14bd5a50.jpg"]
     * type : 0
     * id : 8509177
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
     */

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private String ga_prefix;
    private int type;
    private long id;
    private ArrayList<String> js;
    private ArrayList<String> images;
    private ArrayList<String> css;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<?> getJs() {
        return js;
    }

    public void setJs(ArrayList<String> js) {
        this.js = js;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<String> getCss() {
        return css;
    }

    public void setCss(ArrayList<String> css) {
        this.css = css;
    }

    @Override
    public String toString() {
        return "NewsDetail{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", css=" + css +
                '}';
    }
}
