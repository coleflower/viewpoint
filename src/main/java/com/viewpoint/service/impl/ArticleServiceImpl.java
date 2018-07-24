package com.viewpoint.service.impl;

import com.viewpoint.dataobject.Article;
import com.viewpoint.enums.ResultEnum;
import com.viewpoint.repository.ArticleNodesRepository;
import com.viewpoint.repository.ArticleReponsitory;
import com.viewpoint.service.ArticleService;
import com.viewpoint.utils.ResultVOUtil;
import com.viewpoint.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleReponsitory articleReponsitory;
    @Autowired
    private ArticleNodesRepository articleNodesRepository;

    /**添加一篇文章*/
    @Override
    @Transactional
    public Article addArticle(Article article) {
        Article article1 = articleReponsitory.save(article);
        if(StringUtils.isEmpty(article1)){
            return null;
        }
        return article1;
    }

    /**删除文章*/
    @Override
    @Transactional
    public void deleteArticle(String articleId) {

        articleReponsitory.deleteById(articleId);

    }

    /**修改文章*/
    @Override
    public ResultVO updateArticle(Article article) {
        boolean articleIsNullOrNotnull = false;
        //判断article的值是否为空
        try {
            articleIsNullOrNotnull = isAllFieldNull(article);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //article为null
        if(articleIsNullOrNotnull){
            return ResultVOUtil.error(ResultEnum.ARTICLE_NOT_EXIST.getCode(),"参数错误");
        }
        //article不为null
        articleReponsitory.save(article);
        return ResultVOUtil.success();
    }

    /**通过ID查看文章*/
    @Override
    public Article findArticleById(String  articleId) {

        Optional<Article> article = articleReponsitory.findById(articleId);

        Article article1 = article.orElse(null);
        return article1;
    }

    /**查看所有文章*/
    @Override
    public ResultVO findAllArticle(Pageable pageable) {
        Page<Article> articlePage = articleReponsitory.findAll(pageable);
        if(articlePage.getContent() == null){
            return ResultVOUtil.error(ResultEnum.ARTICLE_NOT_EXIST.getCode(),"没有文章,请添加！");
        }
        return ResultVOUtil.success(articlePage);
    }

    /**
     * 产品的上下架
     * @param articleId
     * @param enabled
     * @return
     */
    @Override
    public Article updateSale(Integer articleId, Integer enabled) {
        Optional<Article> articleOptional = articleReponsitory.findById(articleId.toString());
        Article article = articleOptional.orElse(null);
        article.setEnabled(enabled);
        return articleReponsitory.save(article);
    }


    //判断该对象是否: 返回ture表示所有属性为null  返回false表示不是所有属性都是null
    private boolean isAllFieldNull(Object obj) throws Exception{
        Class stuCla = (Class) obj.getClass();// 得到类对象
        Field[] fs = stuCla.getDeclaredFields();//得到属性集合
        boolean flag = true;
        for (Field f : fs) {//遍历属性
            f.setAccessible(true); // 设置属性是可以访问的(私有的也可以)
            Object val = f.get(obj);// 得到此属性的值
            if(val!=null) {//只要有1个属性不为空,那么就不是所有的属性值都为空
                flag = false;
                break;
            }
        }
        return flag;
    }


}
