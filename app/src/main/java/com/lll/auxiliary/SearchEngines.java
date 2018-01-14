package com.lll.auxiliary;

import android.content.Context;

import com.lll.auxiliary.bean.Topic;
import com.lll.auxiliary.utils.Log;

import java.util.ArrayList;

/**
 * Created by lll on 2018/1/13.
 * 搜索引擎
 */
public class SearchEngines {

    private static final String TAG = "SearchEngines";

    private static final ArrayList<Topic> mHistory = new ArrayList<>();

    public static void clearHistory() {
        mHistory.clear();
    }

    public static void search(Context context, Topic topic) {
        //已经搜索过 ， 不再重复搜索
        if (mHistory.contains(topic)) {
            Log.e(TAG, "已经搜索过的问题");
            return;
        }
        mHistory.add(topic);
        Log.e(TAG,topic.toString());
        SearchActivity.start(context, getSearchUrls(topic), getSearchTitles(topic));
    }

    private static ArrayList<String> getSearchTitles(Topic topic) {
        ArrayList<String> titles = new ArrayList<>();
        titles.add("百度");
        titles.add("知道");
        titles.add("知乎");
        titles.add("百科");
        titles.add("谷歌");
        return titles;
    }

    private static ArrayList<String> getSearchUrls(Topic topic) {
        ArrayList<String> urls = new ArrayList<>();
        String keyword = topic.getQuestion();
        urls.add("https://m.baidu.com/from=0a/pu=sz%401321_480/s?word=" + keyword + "&rn=30&tn=baidulocal");
        urls.add("https://m.baidu.com/from=0a/pu=sz%401321_480/s?word=site:zhidao.baidu.com " + keyword + "&rn=30&tn=baidulocal");
        urls.add("https://m.baidu.com/from=0a/pu=sz%401321_480/s?word=site:www.zhihu.com " + keyword + "&rn=30&tn=baidulocal");
        urls.add("https://m.baidu.com/from=0a/pu=sz%401321_480/s?word=site:baike.baidu.com " + keyword + "&rn=30&tn=baidulocal");
        urls.add("https://www.google.com/search?q=" + keyword + "&aqs=chrome..69i57.13964j0j7&sourceid=chrome-mobile&ie=UTF-8");

        return urls;
    }

//https://m.baidu.com/?from=0a&pu=sz%401321_480&wpo=btmfas&tn=baidulocal&rn=30
//https://m.baidu.com/from=0a/pu=sz%401321_480/s?word=asfd&sa=ib&ts=4659031&rsv_pq=2598676864&rsv_t=9526zYzAe5qICCdbF345nnfHLmbdEqtRIMgVGltedWIECJckW%252Bee87k
//https://m.baidu.com/from=0a/pu=sz%401321_480/s?word=zhangsan&rn=30&tn=baidulocal
//https://m.baidu.com/?pu=sz@1321_480?word=zhangsan&rn=30
//https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=baidulocal&wd=%E8%AE%BE%E7%BD%AE%E7%99%BE%E5%BA%A6%E4%B8%80%E9%A1%B5%E5%B1%95%E7%A4%BA%E5%A4%9A%E6%9D%A1&oq=%25E6%2589%258B%25E6%259C%25BA%25E7%2599%25BE%25E5%25BA%25A6%25E9%25A6%2596%25E9%25A1%25B5&rsv_pq=91b1ff630001b63d&rsv_t=88c7um1IfjIDrHA4qr%2B0W7yfv6W9HDDlpza4d1rcsi9tLAIANu%2B8qudSU9s&rqlang=cn&rsv_enter=1&rsv_sug3=53&rsv_sug1=44&rsv_sug7=100&sug=%25E8%25AE%25BE%25E7%25BD%25AE%25E7%2599%25BE%25E5%25BA%25A6%25E4%25B8%2580%25E9%25A1%25B5%25E5%25B1%2595%25E7%25A4%25BA%25E5%25A4%259A%25E5%25B0%2591%25E9%2592%25B1&rsv_n=1&rsv_sug2=0&inputT=12116&rsv_sug4=12749
}
