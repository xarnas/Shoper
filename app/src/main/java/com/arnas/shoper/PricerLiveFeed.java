package com.arnas.shoper;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.*;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;


/**
 * Created by arnaspetrauskas on 17/09/2017.
 */

public class PricerLiveFeed extends AsyncTask<Void,Void,Void> {

       private String Picture;
       private String Brand;
       private String Product;
       private String Price;
       private String ShopLogo;
       private String ShopName;
       private String PriceDate;
       dbSQL sql;


     public PricerLiveFeed(dbSQL sqli)
     {
         sql=sqli;
     }
        public void PriceParser(dbSQL sql) throws Exception {

            String currentShops[] = new String[7];
            currentShops[0] = "barboralt";
            currentShops[1] = "aibe";
            currentShops[2] = "iki";
            currentShops[3] = "lidl";
            currentShops[4] = "maxima";
            currentShops[5] = "norfa";
            currentShops[6] = "rimi";
            int z = 1;
            String tmpPic;
            int first;
            int last;

           // String httpsURL = "https://pricer.lt/research?categorySlug=produktai&retailChainSlug=barboralt&page=1";


            for (int i = 0; i < currentShops.length; i++) {
                while (z < 4) {
                    String httpsURL2 = "https://pricer.lt/research?categorySlug=produktai&retailChainSlug=" + currentShops[i] + "&page=" + z;
                    z++;
                    URL myUrl = new URL(httpsURL2);
                    HttpsURLConnection conn = (HttpsURLConnection) myUrl.openConnection();
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                    InputStream is = conn.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);

                    String inputLine;
                    String fullHtml = "";

                    while ((inputLine = br.readLine()) != null) {
                        fullHtml = fullHtml + inputLine;
                        //System.out.println(inputLine);
                    }

                    Document doc = Jsoup.parse(fullHtml);
                    //Single price
            /*String attrImg=	doc.getElementsByClass("image_back").attr("style");
            System.out.println(attrImg.substring(attrImg.indexOf("https://"), attrImg.indexOf(")") ) );
            System.out.println(doc.select("span[itemprop=name]").text());
            System.out.println(doc.select("div[class=main_price]").text());
            System.out.println(doc.select("p[class=text-gray]").text());
            System.out.println(doc.select("span[itemprop=highPrice]").first().attr("content"));
            System.out.println(doc.select("span[itemprop=lowPrice]").first().attr("content"));
	        System.out.println("https://pricer.lt"+doc.select("div[class=product-price-container]").select("h4").select("img").first().attr("src"));*/

                    // List of prices
                    Elements productList = doc.select("div[class=product product-list]");

                    for (Element e : productList) {
                        tmpPic =e.select("div[class=product-top]").select("figure").select("div[class=image_back_cat]").attr("style");
                        first = tmpPic.indexOf("(");
                        last = tmpPic.indexOf(")");
                        Picture=tmpPic.substring(first+1, last);
                        Brand = e.select("div[class=product-content]").select("div[class=product-brand]").text();
                        Product=e.select("h3[class=product-title]").select("a").text();
                        Price= e.select("div[class=product-meta-container]").select("div[class=product-price]").text();
                        ShopLogo = e.select("div[class=product-meta-container]").select("div[class=shop-visit-date]").select("div[class=shop-logo]").select("img").first().attr("src");
                        ShopName= e.select("div[class=product-meta-container]").select("div[class=shop-visit-date]").select("div[class=shop-logo]").select("img").first().attr("alt");
                        PriceDate= e.select("div[class=product-meta-container]").select("div[class=shop-visit-date]").text();
                        sql.insertPrice(Picture,Brand,Product,Price,ShopLogo,ShopName,PriceDate);
                    }

                    br.close();
                }
                z=1;
            }
        }


    @Override
    protected Void doInBackground(Void... params) {
        try {
            PriceParser(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
